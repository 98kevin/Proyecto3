package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.kevin.exceptions.DataBaseException;
import com.kevin.exceptions.ManejoDePaciente;
import com.kevin.modelos.Administrador;
import com.kevin.modelos.Area;
import com.kevin.modelos.Cirugia;
import com.kevin.modelos.Medicamento;
import com.kevin.servicio.DBConnection;
import com.kevin.servicio.GeneradorHTML;

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
	String cuiPaciente = request.getParameter("idPaciente");
	try {
	    conexion.setAutoCommit(false);
	    int consulta = registrarConsulta(request, conexion, idMedico);   //registro monetario
	    asignarMedicamentos(request, conexion, consulta,cuiPaciente); 
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
    
    /**
     * Habilita el autoCommit de un conexion
     * @param conexion
     */
    private void habilitarCommit(Connection conexion) {
	try {
		conexion.setAutoCommit(true);
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
    }

/**
 * Reintegra la base de datos, de una mala transaccion. 
 * @param conexion
 */
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
    private void asignarMedicamentos(HttpServletRequest request, Connection conexion, int idConsulta, String cuiPaciente) 
	    throws SQLException, ManejoDePaciente, NumberFormatException {
	boolean internado = Boolean.parseBoolean(request.getParameter("internado"));
	int habitacion;
	int idInternado = 0 ;
	if(internado) {
	    habitacion = Integer.parseInt(request.getParameter("habitacion"));
	    try {
		idInternado= registrarInternado(cuiPaciente, habitacion, request,conexion);
		    registrarMedicosYEnfermeras(cuiPaciente, idInternado, request, conexion);
		    new ManejadorHabitacion().registrarCambioEnHabitacion(habitacion, true);
	    } catch (SQLException | ManejoDePaciente | DataBaseException e) {
		e.printStackTrace();
	    }
	    registrarMedicamentosPacienteInternado(cuiPaciente,request,conexion); //internadoTieneMedicamento
	} else {
	    registrarMedicamentosConsultaNormal(request, conexion, idConsulta);  // consulta tiene medicamentos
	    
	}	    
    }


    /**
     * Registra los medicamentos recetados del doctor a un paciente
     * @param cuiPaciente
     * @param request
     * @param conexion
     * @throws SQLException 
     */
    private void registrarMedicamentosPacienteInternado( String cuiPaciente, HttpServletRequest request, Connection conexion) throws SQLException {
	String [] codigosMedicamentos  = request.getParameterValues("codigos[]");
	String [] cantidadesMedicamentos  = request.getParameterValues("cantidades[]");
	  for (int i = 0; i < codigosMedicamentos.length; i++) {
	      String sql = "INSERT INTO Internado_tiene_Medicamento (id_internado, id_medicamento, cantidad) "
	      	+ " VALUES (?,?,?)"; 
		  PreparedStatement stm = conexion.prepareStatement(sql); 
		  stm.setInt(1, leerIdInternado(cuiPaciente));
		  stm.setInt(2, Integer.parseInt(codigosMedicamentos[i]));
		  stm.setInt(3, Integer.parseInt(cantidadesMedicamentos[i]));
		  stm.execute();
	}
    }

    private int leerIdInternado(String cuiPaciente) throws SQLException {
	String sql = "SELECT id_internado FROM Internado INNER JOIN Paciente on Paciente.id_paciente = Internado.id_paciente "
		+ "INNER JOIN Persona ON Paciente.cui = Persona.cui WHERE Persona.cui = ? "; 
	  PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql); 
	  stm.setString(1, cuiPaciente);
	  ResultSet r = stm.executeQuery(); 
	  r.next(); 
	  return r.getInt(1);
    }

    
    private int leerIdPaciente(String cuiPaciente) throws SQLException {
	String sql = "SELECT id_paciente FROM Paciente "
		+ "INNER JOIN Persona ON Paciente.cui = Persona.cui WHERE Persona.cui = ? "; 
	  PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql); 
	  stm.setString(1, cuiPaciente);
	  ResultSet r = stm.executeQuery(); 
	  r.next(); 
	  return r.getInt(1);
    }
    /**
     * Asinga los medicos y enfermeras a un paciente internado
     * @param request
     * @param conexion
     * @throws SQLException 
     */
    private void registrarMedicosYEnfermeras(String cuiPaciente, int idInternado, HttpServletRequest request, Connection conexion) throws SQLException {
	  String [] medicos  = request.getParameterValues("medicos[]");
	  String [] enfermeras  = request.getParameterValues("enfermeras[]");
	  for (int i = 0; i < enfermeras.length; i++) {
	      String sql = "INSERT INTO Internado_tiene_Empleado VALUES (?,?,?) "; 
		  PreparedStatement stm = conexion.prepareStatement(sql); 
		  stm.setInt(1, idInternado);
		  stm.setInt(2,leerIdPaciente(cuiPaciente) );
		  stm.setInt(3, Integer.parseInt(enfermeras[i]));
		  stm.execute();
	}
	for (int i = 0; i < medicos.length; i++) {
	    String sql = "INSERT INTO Internado_tiene_Empleado VALUES (?,?,?)"; 
		  PreparedStatement stm = conexion.prepareStatement(sql); 
		  stm.setInt(1, idInternado);
		  stm.setInt(2, leerIdPaciente(cuiPaciente) );
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
     * @throws DataBaseException 
     */
    private int registrarInternado(String cuiPaciente, int habitacion, HttpServletRequest request, Connection conexion)  throws SQLException, ManejoDePaciente, DataBaseException {
	ManejadorPaciente manejador = new ManejadorPaciente();
	boolean estaInternado = manejador.isInternadoActualmente(cuiPaciente);
	if(estaInternado) {
	    return leerIdInternado(cuiPaciente);
	}else {
	    Date fecha= getFechaIngresada(request);
		  String sql = "INSERT INTO Internado(id_paciente, inicio, id_habitacion) VALUES (?,?,?)";
		  PreparedStatement stm = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		  stm.setString(1, cuiPaciente);
		  stm.setDate(2, fecha);
		  stm.setInt(3, habitacion);
		  stm.execute();
		ResultSet resultado = stm.getGeneratedKeys();
		if(resultado.next())
		    return resultado.getInt(1);
		else throw new DataBaseException("Error, no se pudo registrar al internado");
	}
    }

    
    
    /**
     * Ingresa los medicos registrados en una tabla HTML
     * @return
     */
    public String consultarMedicos() {
	    String sql ="SELECT e.id_empleado, p.nombre FROM Persona p INNER JOIN "
			+ " Empleado e ON p.cui=e.cui_persona WHERE e.id_area=?"; 
		Connection conexion = DBConnection.getInstanceConnection().getConexion();
		ResultSet medicos = null;
			PreparedStatement stm;
			try {
			    stm = conexion.prepareStatement(sql);
			    stm.setInt(1, Area.MEDICOS);
			    medicos = stm.executeQuery();
			} catch (SQLException e) {
			    e.printStackTrace();
			} 
			return GeneradorHTML.convertirTabla(medicos, "agregarMedico(this)", "Agregar", false, false, true);
		
	    }


    /**
     * Se ecarga de leer los codigos y cantidades y mandarlos a escribir en la base de datos en caso que solo se consulta
     * @param request
     * @param conexion
     */
    private void registrarMedicamentosConsultaNormal(HttpServletRequest request, Connection conexion, int idConsulta)throws SQLException {
	Date fecha = new Date(Long.parseLong(request.getParameter("fecha")));
	String cuiPaciente = request.getParameter("idPaciente"); 
	int idEmpleado = (Integer) request.getSession().getAttribute("user"); 

	String [] cantidades = request.getParameterValues("cantidades[]"); //cantidades de los medicamentos
	String [] medicamentos = request.getParameterValues("codigos[]"); // codigos de los medicamentos
	for(int i=0; i < cantidades.length ; i++) {
		String sql = "INSERT INTO Consulta_tiene_Medicamentos (id_consulta, id_medicamento, cantidad) VALUES (?,?,?)";
		PreparedStatement stm = conexion.prepareStatement(sql);
		stm.setInt(1, idConsulta);
		stm.setInt(2, Integer.parseInt(medicamentos[i]));
		stm.setInt(3, Integer.parseInt(cantidades[i]));
		stm.execute();
		
		Medicamento  m = new Medicamento(Integer.parseInt(medicamentos[i])); 
		String sql2  = "INSERT INTO Transacciones_Medicamentos (fecha, costo_actual_medicamento, precio_actual_medicamento, cantidad, tipo_operacion, pagado, "
			+ "id_medicamento, id_empleado, cui_paciente) values (?,?,?,?,?,?,?,?,?)";
		PreparedStatement stm2 = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql2);
		stm2.setDate(1, fecha);
		stm2.setDouble(2, m.getCostoCompra());
		stm2.setDouble(3, m.getPrecioVenta());
		stm2.setInt(4, Integer.parseInt(cantidades[i]));
		stm2.setBoolean(5, true);
		stm2.setBoolean(6, false);
		stm2.setInt(7, Integer.parseInt(medicamentos[i]));
		stm2.setInt(8, idEmpleado);
		stm2.setString(9, cuiPaciente);
		stm2.execute(); 
	}
    }




    /**
     * Registra el pago de una consulta en la tabla de registros monetarios
     * @param request
     * @param conexion
     * @throws SQLException 
     */
    private int registrarConsulta(HttpServletRequest request, Connection conexion, int idMedico) throws SQLException {
	String sql = "INSERT INTO Consulta(id_paciente, descripcion,precio_de_consulta, pagado,fecha, id_area, id_empleado) VALUES (?,?,?,?,?,?,?)";
	PreparedStatement stm = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	stm.setInt(1,leerIdPaciente(request.getParameter("idPaciente")));
	stm.setString(2,getDescripcionConsulta());
	stm.setDouble(3, getMontoConsulta(conexion));
	stm.setBoolean(4, false);  //No se ha pagado
	stm.setDate(5, getFechaIngresada(request));
	stm.setInt(6, getIdArea(idMedico, conexion));
	stm.setInt(7, idMedico);
	stm.execute();
	ResultSet resultado = stm.getGeneratedKeys();
	if(resultado.next())
	    return resultado.getInt(1);
	else return -1;
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
     * Lee y asinga los medicamentos a un paciente internado. 
     * @param request
     * @return
     * @throws SQLException 
     */

    public String asignarMedicamentos(HttpServletRequest request) {
	StringBuffer respuesta = new StringBuffer();
	String cuiPaciente= request.getParameter("internado");  //paciente internado
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	try {
	    conexion.setAutoCommit(false);
	    registrarMedicamentosPacienteInternado(cuiPaciente, request, conexion);
	    respuesta.append("Medicamentos asignados correctamente");
	} catch (SQLException e) {
	    e.printStackTrace();
	    if(e.getErrorCode() == 1062) {
		 respuesta.append("No se puede asignar el mismo medicamento al mismo paciente");
	    } else
	    respuesta.append("Ocurrio un error al asignar los medicametnos");
	    rollback(conexion);
	} finally {
	    habilitarCommit(conexion);
	}
	return respuesta.toString();
    }
    

    /**
     * Genera condigo html de las cirugias disponibles en la DB
     * @return codigo html con las cirugias disponibles
     */
    public String consultarCirugias() {
	StringBuffer cirugias = new StringBuffer();
	try {
	    cirugias.append("<select id=\"selectCirugiasDisponibles\" class=\"form-control\">Cirugias Disponibles");
	    ResultSet resultado = consultarCirugiasEnDB();
	    while (resultado.next()) {
	        cirugias.append("<option value=\""+resultado.getInt(1)+"\">"+resultado.getString(2)+"</option>");
	    }
	    cirugias.append("</select>");
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return cirugias.toString();
    }
    
    
    /**
     * Consulta la DB en busca de las cirugias registradas
     * @return
     * @throws SQLException
     */
    public ResultSet consultarCirugiasEnDB() throws SQLException {
	String sql = "SELECT * FROM Cirugias_Disponibles";
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	PreparedStatement stm = conexion.prepareStatement(sql);
	return stm.executeQuery();
    }

    public String registrarCirugiaPaciente(HttpServletRequest request) {
	StringBuffer mensaje = new StringBuffer();
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	Cirugia cirugia = new Cirugia(request);
	try {
	    conexion.setAutoCommit(false);
	    int codigoCirugia = registrarCirugia(cirugia, conexion);
	    registrarMedicosAsingnados(codigoCirugia, conexion, request); 
	    registrarMedicosEspecialistasAsingnados(codigoCirugia, conexion, request); 
	    conexion.commit();
	    mensaje.append("Operacion realizada con exito");
	} catch (SQLException e) {
	    e.printStackTrace();
	    mensaje.append("Error en el registro de la operacion");
	    try {
		conexion.rollback();
	    } catch (SQLException e1) {
		e1.printStackTrace();
	    }
	} finally {
	    try {
		conexion.setAutoCommit(true);
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
	return mensaje.toString();
    }
    
    
    private void registrarMedicosAsingnados(int codigoCirugia, Connection conexion, HttpServletRequest request) throws SQLException {
	String sql = "INSERT INTO Cirugia_tiene_Empleado (id_cirugia, id_paciente, id_empleado) VALUES (?,?,?)"; 
	String [] medicos = request.getParameterValues("medicos[]");
	for (int i = 0; i < medicos.length; i++) {
	    PreparedStatement pstm = conexion.prepareStatement(sql);
		pstm.setInt(1, codigoCirugia);
		System.out.println(request.getParameter("paciente"));
		pstm.setInt(2, leerIdPaciente(request.getParameter("paciente")));
		pstm.setInt(3, Integer.parseInt(medicos[i]));
		pstm.execute();
	}
    }
    
    private void registrarMedicosEspecialistasAsingnados(int cirugia, Connection conexion, HttpServletRequest request) throws SQLException {
	String sql = "INSERT INTO Cirugia_tiene_Medico_Especialista  (id_cirugia, id_medico_especialista) VALUES (?,?)"; 
	String [] medicosEspecialistas = request.getParameterValues("medicosEspecialistas[]");
	for (int i = 0; i < medicosEspecialistas.length; i++) {
	    PreparedStatement pstm = conexion.prepareStatement(sql);
		pstm.setInt(1, cirugia);
		pstm.setInt(2, Integer.parseInt(medicosEspecialistas[i]));
		pstm.execute();
	}
    }
    

    private int registrarCirugia(Cirugia cirugia, Connection conexion) throws SQLException {
	String sql = "INSERT INTO Cirugia (fecha, id_tarifa, id_paciente) VALUES (?,?,?)";
	PreparedStatement stm = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	stm.setDate(1, cirugia.getFecha());
	stm.setInt(2, cirugia.getCodigoTarifa());
	stm.setInt(3, leerIdPaciente( cirugia.getCuiPaciente()));
	stm.execute();
	ResultSet resultado = stm.getGeneratedKeys();
	if(resultado.next())
	    return resultado.getInt(1);
	else return -1;
    }
    
    
 
    /**
     * Consulta las cirugias disponibles en la base de datos
     * @return
     */
    public String consultarCirugiasPendientes() {
	String resultado= null;
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	String sql = "SELECT Cirugia.id_cirugia AS 'Codigo', Persona.nombre, Cirugias_Disponibles.descripcion, Cirugia.fecha, Cirugias_Disponibles.precio_al_cliente, Cirugia.id_cirugia "+
		" FROM Persona JOIN Paciente ON Persona.cui=Paciente.cui "+
		" JOIN Cirugia ON Cirugia.id_paciente= Paciente.id_paciente "+
		" JOIN Cirugias_Disponibles ON Cirugia.id_tarifa = Cirugias_Disponibles.id_tarfia "+
		" WHERE Cirugia.realizada = 0";
	try {
	    resultado =  GeneradorHTML.convertirTabla(conexion.prepareStatement(sql).executeQuery(), 
	    	"registrarCirugiaTerminada(this)", "Finalizar Cirugia", false, false, true);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return resultado;
    }

    /**
     * 
     * @param response
     * @return
     */
    public String terminarCirugia(HttpServletRequest request) {
	respuesta = new StringBuffer();
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	try {
	    conexion.setAutoCommit(false);
	    int codigoCirugia = Integer.parseInt(request.getParameter("idCirugia")); 
	    Date fecha = getFechaIngresada(request);
	    double monto = consultarMonto(conexion,codigoCirugia); 
	    registrarCirugiaTerminada(conexion, codigoCirugia); 
	    registrarPagosDeMedicosEspecialistas(conexion, monto,fecha);
	    conexion.commit();
	    respuesta.append("Registro completo"); 
	} catch (SQLException e) {
	    e.printStackTrace();
	    respuesta.append("Ocurrio un error. Codigo de error: " + e.getErrorCode() ); 
	    try {
		conexion.rollback();
	    } catch (SQLException e1) {
		e1.printStackTrace();
	    }
	} finally {
	    try {
		conexion.setAutoCommit(true);
	    } catch (Exception e2) {
	    }
	}
	return respuesta.toString();
    }

    private double consultarMonto(Connection conexion,int codigoCirugia) {
	String sql = "SELECT tarifa_de_especialista FROM Cirugias_Disponibles " + 
		"INNER JOIN Cirugia ON Cirugia.id_tarifa = Cirugias_Disponibles.id_tarfia " + 
		"WHERE Cirugia.id_cirugia = ?";
	double monto =0;
	PreparedStatement stm;
	try {
	    	stm = conexion.prepareStatement(sql);
		stm.setInt(1, codigoCirugia);
		ResultSet resultado = stm.executeQuery();
		if (resultado.next()) {
		    monto = resultado.getDouble(1);
		}
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return monto;
    }

    private void registrarPagosDeMedicosEspecialistas(Connection conexion, double monto, Date fecha) throws SQLException {
	String sql = "INSERT INTO Pago_Especialista (descripcion, monto, fecha, id_area) VALUES (?,?,?,?)";
	PreparedStatement stm = conexion.prepareStatement(sql);
	stm.setString(1, "Pago por cirugia realizada");
	stm.setDouble(2, monto);
	stm.setDate(3, fecha);
	stm.setInt(4, 4);  //area de medicos
	stm.execute();
    }

    private void registrarCirugiaTerminada(Connection conexion, int codigoCirugia) throws SQLException {
	String sql = "UPDATE  Cirugia SET realizada=TRUE WHERE id_cirugia= ?";
	PreparedStatement stm = conexion.prepareStatement(sql);
	stm.setInt(1, codigoCirugia);
	stm.execute();
    }

    public String consultarMedicosEspecialistas() {
	String resp="";
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	String sql = "SELECT Medico_Especialista.id_medico_especialista AS 'codigo',  Persona.nombre AS 'Nombre Completo', "
		+ " Persona.direccion AS 'Direccion',  Medico_Especialista.no_de_colegiado  AS 'No. de Colegiado' "+
		"FROM Persona JOIN Medico_Especialista ON Persona.cui=Medico_Especialista.cui_persona";
	try {
	    PreparedStatement stm = conexion.prepareStatement(sql);
	    resp= GeneradorHTML.convertirTabla(stm.executeQuery(), "seleccionarMedicoEspecialista(this)", "Asingar", false, false,true);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return resp;
    }
    
    
    public String consultarPacientesInternados(String evento, String texto, int medico) {
	String sql =" SELECT Persona.cui as 'CUI', Persona.nombre AS 'Nombre Completo', Internado.inicio AS 'Fecha de Ingreso', Internado.id_habitacion AS 'Habitacion' "+
		" FROM Persona "+
		" INNER JOIN Paciente ON Persona.cui = Paciente.cui "+
		" INNER JOIN Internado ON Paciente.id_paciente = Internado.id_paciente "+
		" INNER JOIN Internado_tiene_Empleado ON Internado_tiene_Empleado.id_internado= Internado.id_internado "+
		" INNER JOIN Empleado ON Empleado.id_empleado = Internado_tiene_Empleado.id_empleado "+
		" WHERE Empleado.id_empleado = ? "+
		" AND Internado.fin IS NULL";
	ResultSet resultados = null;
	try {
	    PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
	    stm.setInt(1, medico);
	    resultados= stm.executeQuery();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return GeneradorHTML.convertirTabla(resultados, evento, texto, false, false, true);
    }

    public String darDeAlta(String cuiPaciente, long millisDate) {
	ArrayList<PreparedStatement> operaciones =  new ArrayList<PreparedStatement>();
	ManejadorPaciente manejador= new ManejadorPaciente(); 
	ManejadorHabitacion manejadorHabitacion = new ManejadorHabitacion(); 
	Date fecha = new Date(millisDate); 
	try {
	    operaciones.add(registrarFinInternado(cuiPaciente, fecha));
	    operaciones.add(manejador.registroDiasInternadosPaciente(cuiPaciente, fecha)); 
	    operaciones.add(manejadorHabitacion.registrarCostosDeHabitacion(cuiPaciente, fecha)); 
	    operaciones.add(manejadorHabitacion.registrarSalidaPaciente(cuiPaciente)); 
	} catch (Exception e) {
	    e.printStackTrace();
	} 
	return DBConnection.getInstanceConnection().transaccion(operaciones); 
    }
    
    
    private PreparedStatement registrarFinInternado(String cuiPaciente, Date fecha) throws SQLException, DataBaseException {
	PreparedStatement stm = null ; 
	    String sql = "UPDATE Internado SET fin = ? WHERE id_internado = ? "; 
	    stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
	    stm.setDate(1, fecha);
	    stm.setInt(2, getIdInternado(cuiPaciente));
	    stm.execute(); 
	return stm; 
    }
    
    private int getIdInternado(String cuiPaciente) throws SQLException, DataBaseException {
	String sql =" Select Internado.id_internado "+
		" FROM Internado "+
		" INNER JOIN Paciente ON Paciente.id_paciente = Internado.id_paciente "+
		" WHERE Paciente.cui = ? "
		+ " AND Internado.fin IS NULL";
	PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
	stm.setString(1, cuiPaciente);
	ResultSet resultado = stm.executeQuery(); 
	if(resultado.next())
	    return resultado.getInt(1); 
	else 
	    throw new DataBaseException("No existe el internado con el cui " + cuiPaciente ); 
    }
    
    
}