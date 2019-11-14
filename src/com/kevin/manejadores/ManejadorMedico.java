package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import com.kevin.exceptions.ConversionDeNumeros;
import com.kevin.exceptions.ManejoDePaciente;
import com.kevin.modelos.Administrador;
import com.kevin.servicio.DBConnection;

public class ManejadorMedico {
    
    private StringBuffer respuesta;
    
    /**
     * Lee y ejecuta las transacciones involucradas con una consulta
     * @param request
     * @return
     */
    public String nuevaConsulta(HttpServletRequest request) {
	respuesta = new StringBuffer(); 
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	int idMedico= (Integer) request.getSession().getAttribute("user"); //empleado
	try {
	    conexion.setAutoCommit(false);
	    registrarPago(request, conexion, idMedico);   //registro monetario
	    registrarConsulta(request, conexion, idMedico);  
	    asignarMedicamentos(request, conexion); 
	    conexion.commit();
	    respuesta.append("Consulta realizada con exito");
	}
	catch(SQLException e) {
	    e.printStackTrace();
	    respuesta.append(e.getErrorCode());
	    rollback(conexion);
	} catch(ManejoDePaciente exp) {
	    exp.printStackTrace();
	    respuesta.append(exp.getMessage());
	    rollback(conexion);
	}  catch(NumberFormatException nfe) {
	    nfe.printStackTrace();
	    respuesta.append(nfe.getMessage());
	    rollback(conexion);
	}
	finally {
	    habilitarCommit(conexion);
	}
	return respuesta.toString();
    }
    
    
    private void habilitarCommit(Connection conexion) {
	try {
		conexion.setAutoCommit(true);
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
    }


    private void rollback(Connection conexion) {
	try {
		conexion.rollback();
	    } catch (SQLException e1) {
		e1.printStackTrace();
	    }
    }


    /**
     * Verifica si el paciente se queda internado. Y le asinga sus medicametnos. 
     * @param request
     * @param conexion
     * @throws SQLException 
     * @throws ManejoDePaciente 
     */
    private void asignarMedicamentos(HttpServletRequest request, Connection conexion) throws SQLException, ManejoDePaciente, NumberFormatException {
	boolean internado = Boolean.parseBoolean(request.getParameter("internado"));
	int idInternado,paciente,habitacion;
	if(internado) {
	    registrarMedicamentos(request, conexion);
	}
	else {
	    try {
		 idInternado = DBConnection.getInstanceConnection().maximo("Internado", "id_internado")+1;  //el ultimo paciente internado
		 paciente = Integer.parseInt(request.getParameter("idPaciente"));
		 habitacion = Integer.parseInt(request.getParameter("habitacion"));
	    } catch (Exception e) {
		throw new ConversionDeNumeros ("Error de lectura de datos");
	    }
	    registrarInternado(paciente, habitacion, request,conexion);
	    new ManejadorHabitacion().registrarCambioEnHabitacion(habitacion, true);
	    registrarMedicosYEnfermeras(paciente, idInternado, request, conexion);
	    registrarMedicamentos(paciente, idInternado,request,conexion);
	}
    }


    /**
     * Registra los medicamentos recetados del doctor a un paciente
     * @param idInternado
     * @param request
     * @param conexion
     * @throws SQLException 
     */
    private void registrarMedicamentos(int paciente, int idInternado, HttpServletRequest request, Connection conexion) throws SQLException {
	String [] codigosMedicamentos  = request.getParameterValues("codigos[]");
	String [] cantidadesMedicamentos  = request.getParameterValues("cantidades[]");
	  for (int i = 0; i < codigosMedicamentos.length; i++) {
	      String sql = "INSERT INTO Internado_tiene_Medicamento (id_internado, id_paciente, id_medicamento, cantidad) "
	      	+ " VALUES (?,?,?,?)"; 
		  PreparedStatement stm = conexion.prepareStatement(sql); 
		  stm.setInt(1, idInternado);
		  stm.setInt(2, paciente );
		  stm.setInt(3, Integer.parseInt(codigosMedicamentos[i]));
		  stm.setInt(4, Integer.parseInt(cantidadesMedicamentos[i]));
		  stm.execute();
	}
	
    }

    /**
     * Asinga los medicos y enfermeras a un paciente internado
     * @param request
     * @param conexion
     * @throws SQLException 
     */
    private void registrarMedicosYEnfermeras(int paciente, int idInternado, HttpServletRequest request, Connection conexion) throws SQLException {
	  String [] medicos  = request.getParameterValues("medicos[]");
	  String [] enfermeras  = request.getParameterValues("enfermeras[]");
	  for (int i = 0; i < enfermeras.length; i++) {
	      String sql = "INSERT INTO Internado_tiene_Empleado VALUES (?,?,?)"; 
		  PreparedStatement stm = conexion.prepareStatement(sql); 
		  stm.setInt(1, idInternado);
		  stm.setInt(2, paciente );
		  stm.setInt(3, Integer.parseInt(enfermeras[i]));
		  stm.execute();
	}
	for (int i = 0; i < medicos.length; i++) {
	    String sql = "INSERT INTO Internado_tiene_Empleado VALUES (?,?,?)"; 
		  PreparedStatement stm = conexion.prepareStatement(sql); 
		  stm.setInt(1, idInternado);
		  stm.setInt(2, paciente );
		  stm.setInt(3, Integer.parseInt(medicos[i]));
		  stm.execute();
	}
    }

    /**
     * Registra un nuevo internado en la tabla de Internados
     * @param request
     * @param conexion
     * @throws SQLException 
     * @throws ManejoDePaciente 
     */
    private void registrarInternado(int paciente, int habitacion, HttpServletRequest request, Connection conexion)  throws SQLException, ManejoDePaciente {
	ManejadorPaciente manejador = new ManejadorPaciente();
	boolean estaInternado = manejador.isInternadoActualmente(paciente);
	if(estaInternado) {
	    throw new ManejoDePaciente("El paciente se encuentra internado");
	}else {
	    Date fecha= getFechaIngresada(request);
		  String sql = "INSERT INTO Internado(id_paciente, inicio, id_habitacion) VALUES (?,?,?)";
		  PreparedStatement stm = conexion.prepareStatement(sql);
		  stm.setInt(1, paciente);
		  stm.setDate(2, fecha);
		  stm.setInt(3, habitacion);
		  stm.execute();
	}
    }


    /**
     * Consulta la base de dato en busca de los medicos registrados
     * @return
     * @throws SQLException
     */
    private ResultSet consultarMedicosEnDB() throws SQLException {
	String sql ="SELECT e.id_empleado, p.nombre FROM Persona p INNER JOIN "
		+ " Empleado e ON p.cui=e.cui_persona WHERE e.id_area=4"; 
	ResultSet enfermeras = null;
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	Statement stm =conexion.createStatement();
	enfermeras = stm.executeQuery(sql);
	return enfermeras;
    }
    
    
    /**
     * Ingresa los medicos registrados en una tabla HTML
     * @return
     */
    public String consultarMedicos() {
	    StringBuffer registros= new StringBuffer(); 
		registros.append("<input type=\"text\" id=\"cajaFiltro\" class=\"form-control\" onkeyup=\"filtrarTabla()\" "
			+ " placeholder=\"Filtrar por medico..\">");
		registros.append("<table class=\"table\" id=\"tabla\">");
		registros.append("<tr>").append("<th>Codigo</th>");
		registros.append("<th>Medico</th>").append("</tr>");
		try {
		    ResultSet medicos = consultarMedicosEnDB();
		    while(medicos.next()) {
		        registros.append("<tr class=\"\">");
		        registros.append("<td>"+medicos.getString(1)+"</td>");
		        registros.append("<td>"+medicos.getString(2)+"</td>");
		        registros.append("<td><button id=\""+medicos.getString(1)+"\" onClick=\"agregarMedico(this)\" "
		        	+ " class=\"btn-agregar-medico\">Asignar</button></td>");
		        registros.append("</tr>");
		    }
		} catch(SQLException e ) {
		    e.printStackTrace();
		}
		return registros.toString();
	    }


    /**
     * Se ecarga de leer los codigos y cantidades y mandarlos a escribir en la base de datos en caso que solo se consulta
     * @param request
     * @param conexion
     */
    private void registrarMedicamentos(HttpServletRequest request, Connection conexion)throws SQLException {
	int idConsulta = DBConnection.getInstanceConnection().maximo("Consulta", "id_consulta") + 1;
	String [] cantidades = request.getParameterValues("cantidades[]"); //cantidades de los medicamentos
	String [] medicamentos = request.getParameterValues("codigos[]"); // codigos de los medicamentos
	for(int i=0; i < cantidades.length ; i++) {
	    asingarMedicamento(idConsulta, cantidades[i], medicamentos[i], conexion);
	}
    }


    private void asingarMedicamento(int idConsulta, String cantidad, String codigo, Connection conexion ) throws SQLException {
	String sql = "INSERT INTO Consulta_tiene_Medicamentos (id_consulta, id_medicamento, cantidad) VALUES (?,?,?)";
	PreparedStatement stm = conexion.prepareStatement(sql);
	stm.setInt(1, idConsulta);
	stm.setInt(2, Integer.parseInt(codigo));
	stm.setInt(3, Integer.parseInt(cantidad));
	stm.execute();
    }


    /**
     * Registra el pago de una consulta en la tabla de registros monetarios
     * @param request
     * @param conexion
     * @throws SQLException 
     */
    private void registrarPago(HttpServletRequest request, Connection conexion, int idMedico) throws SQLException {
	String sql = "INSERT INTO Registro_Monetario(descripcion, monto, fecha, tipo, id_area) VALUES (?,?,?,?,?)";
	PreparedStatement stm = conexion.prepareStatement(sql);
	stm.setString(1,getDescripcionConsulta());
	stm.setDouble(2, getMontoConsulta(conexion));
	stm.setDate(3, getFechaIngresada(request));
	stm.setBoolean(4, true);  //ingreso
	stm.setInt(5, getIdArea(idMedico, conexion));
	stm.execute();
    }

    /**
     * Devuelve el codigo del area de un empleado
     * @param idMedico
     * @param conexion
     * @return
     * @throws SQLException
     */
    private int getIdArea(int idMedico, Connection conexion) throws SQLException {
	String sql = "SELECT id_area FROM Empleado where id_empleado = ?";
	PreparedStatement stm = conexion.prepareStatement(sql); 
	stm.setInt(1, idMedico);
	ResultSet area  = stm.executeQuery();
	area.next();
	return area.getInt(1);
    }

    /**
     * Lee la solicitud y retorna un objeto java.sql.Date
     * @param request
     * @return
     */
    private Date getFechaIngresada(HttpServletRequest request) {
	return  new Date(Long.parseLong(request.getParameter("fecha")) + Administrador.MILIS_DIA);
    }

    /**
     * Lee el monto del precio de una consulta ordinaria en la DB
     * @param conexion
     * @return
     * @throws SQLException
     */
    private double getMontoConsulta(Connection conexion) throws SQLException {
	String sql = "SELECT * FROM Constantes WHERE id_constante=1";
	PreparedStatement stm = conexion.prepareStatement(sql); 
	ResultSet precio  = stm.executeQuery();
	precio.next();
	return precio.getDouble(3);
    }

    /**
     * Devuelve la descripcion del registro monetario de la consulta
     * @return
     */
    private String getDescripcionConsulta() {
	return "consulta ordinaria a cliente";
    }

    
    /**
     * Registra la consulta generada por el medico en la base de datos 
     * @param request
     * @throws SQLException 
     */
    private void registrarConsulta(HttpServletRequest request,  Connection conexion, int idMedico) throws SQLException {
	String sql = "INSERT INTO Consulta (precio_de_consulta, id_registro_monetario, id_paciente, id_empleado) VALUES (?,?,?,?)";
	PreparedStatement stm = conexion.prepareStatement(sql);
	stm.setDouble(1,getMontoConsulta(conexion));
	stm.setInt(2,  DBConnection.getInstanceConnection().maximo("Registro_Monetario", "id_registro"));
	stm.setInt(3, Integer.parseInt(request.getParameter("idPaciente")));
	stm.setInt(4, idMedico); 
	stm.execute();
    }
    
}
