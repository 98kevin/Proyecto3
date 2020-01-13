package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kevin.exceptions.DataBaseException;
import com.kevin.modelos.Area;
import com.kevin.modelos.Main;
import com.kevin.modelos.Paciente;
import com.kevin.servicio.DBConnection;
import com.kevin.servicio.GeneradorHTML;

public class ManejadorPaciente {

    public String registrarPaciente(Paciente paciente) {
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	StringBuffer mensaje= new StringBuffer();
	try {
	    conexion.setAutoCommit(false);
	    ManejadorPersona persona = new ManejadorPersona(); 
	    persona.registrarPersona(paciente,conexion); 
	    registrarPaciente(paciente, conexion); 
	    conexion.commit();
	   mensaje.append("Creacion de paciente exitosa realizada con exito"); 
	} catch (SQLException e) {
	    e.printStackTrace();
	    mensaje.append("Error de intregridad de base de datos.   codigo de error " + e.getErrorCode());
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
    
    
    private void registrarPaciente(Paciente paciente, Connection conexion) throws SQLException {
	String sql = "INSERT INTO Paciente (nit, cui) VALUES (?,?)";
	PreparedStatement stm = conexion.prepareStatement(sql);
	stm.setString(1, paciente.getNit());
	stm.setString(2, paciente.getCui());
	stm.execute();
    }
    
    
    public String pacientesRegistrados() {
	String sql = "SELECT Paciente.id_paciente, Persona.nombre, Persona.direccion, Persona.cui "+
		" FROM Persona "+
		" INNER JOIN Paciente ON Persona.cui=Paciente.cui ";
	PreparedStatement stm = null ;
	ResultSet pacientes = null ;
	try {
	    stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
	    pacientes = stm.executeQuery();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return GeneradorHTML.convertirTabla(pacientes, "seleccionarPaciente(this)", "Seleccionar Paciente", true ,true,true ); 
    }


    public boolean isInternadoActualmente(int paciente) {
	boolean resultado= false;
	String sql = "SELECT * FROM Internado WHERE id_paciente=? AND fin IS NULL";
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	PreparedStatement stm=null;
	try { 
	    stm = conexion.prepareStatement(sql);
	    stm.setInt(1, paciente);
	    ResultSet resultados = stm.executeQuery();	
	    resultado =  (resultados.next()) ? true : false;
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return resultado;
    }
    
    public String consultarMedicamentosDePacientes(int codigoEnfermera) {
	 ResultSet resultados=null;
	String sql = " SELECT Medicamento.id_medicamento, Medicamento.nombre, Persona.nombre AS 'Paciente', " +
		" Internado_tiene_Medicamento.cantidad AS 'Cantidad Faltante' FROM Medicamento  " +
		" INNER JOIN Internado_tiene_Medicamento ON Internado_tiene_Medicamento.id_medicamento = Medicamento.id_medicamento "+
		" INNER JOIN Internado ON Internado_tiene_Medicamento.id_internado = Internado.id_internado"+
		" INNER JOIN Internado_tiene_Empleado ON Internado_tiene_Empleado.id_internado = Internado.id_internado"+
		" INNER JOIN Empleado ON Empleado.id_empleado = Internado_tiene_Empleado.id_empleado"+
		" INNER JOIN Paciente ON Paciente.id_paciente = Internado.id_paciente "+
		" INNER JOIN Persona ON Persona.cui = Paciente.cui "+
		" WHERE Internado.fin IS NULL AND Empleado.id_empleado = ? ";
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	PreparedStatement stm=null;
	try { 
	    stm = conexion.prepareStatement(sql);
	    stm.setInt(1, codigoEnfermera);
	    resultados = stm.executeQuery();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return GeneradorHTML.convertirTabla(resultados, "suministrarMedicamento(this)", " Suministrar ", true, true, true);
    }
    
    public String consultarPacinetesDeEnfermera(int codigoEnfermera) {
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	ResultSet resultado = null; 
	PreparedStatement stm;
	String sql = "Select Persona.cui, Persona.nombre " +
		" FROM Persona  "+
		" INNER JOIN Paciente ON Paciente.cui = Persona.cui " +
		" INNER JOIN Internado ON Internado.id_paciente =Paciente.id_paciente "+
		" INNER JOIN Internado_tiene_Empleado ON Internado_tiene_Empleado.id_internado = Internado.id_internado  "+
		" INNER JOIN Empleado ON Empleado.id_empleado = Internado_tiene_Empleado.id_empleado "+
		" WHERE Internado.fin IS NULL AND Empleado.id_empleado =  ?";
	try {
	    stm = conexion.prepareStatement(sql);
	    stm.setInt(1, codigoEnfermera);
	    resultado = stm.executeQuery(); 
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return GeneradorHTML.convertirSelector(resultado, "seleccionarPaciente()");
    }
   
    public int consultarCodigoDeInternado(String cui) throws SQLException, DataBaseException{
	String sql  = " SELECT Internado_tiene_Medicamento.id_internado as 'Codigo de Internado' " + 
		" FROM Internado_tiene_Medicamento " + 
		" INNER JOIN Internado ON Internado_tiene_Medicamento.id_internado = Internado.id_internado" + 
		" INNER JOIN Paciente ON Internado.id_paciente = Paciente.id_paciente " + 
		" INNER JOIN Persona ON Paciente.cui = Persona.cui " + 
		" WHERE Persona.cui =  ?"  + 
		" AND Internado.fin is null " + 
		" GROUP BY Internado.id_internado";
	PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql); 
	stm.setString(1, cui);
	ResultSet resultado = stm.executeQuery();
	if (resultado.next()) 
	    return resultado.getInt(1);
	else
	    throw new DataBaseException("No existe el internado con el cui: " + cui);
    }
    
    
    public int consultarDiasInternado(String cuiPaciente, Date fecha ) throws SQLException, DataBaseException {
	String sql = "SELECT TIMESTAMPDIFF(DAY, Internado.inicio, ?) as 'Dias' "+ 
		" FROM Internado"+ 
		" INNER JOIN Paciente ON Internado.id_paciente = Paciente.id_paciente"+ 
		" WHERE Paciente.cui = ?";  
	PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql); 
	stm.setDate(1, fecha);
	stm.setString(2, cuiPaciente);
	ResultSet dias = stm.executeQuery(); 
	if(dias.next())
	    return dias.getInt(1); 
	else 
	    throw new DataBaseException("No se pudo leer los dias del paciente con el cui" + cuiPaciente); 
    }


    public PreparedStatement registroCuentaCliente(String cuiPaciente, Date fecha) throws SQLException, DataBaseException {
	int dias = consultarDiasInternado(cuiPaciente, fecha); 
	String sql ="INSERT INTO Cuenta (id_paciente, detalle, monto, pagado, fecha, id_area) "
		+ " VALUES ((SELECT id_paciente FROM Paciente WHERE cui = ?), " //1
		+"?, "//2
		+ "(SELECT monto FROM Constantes WHERE id_constante = 2) * ?, " //3
		+ "?, "//4
		+ "?, " //5
		+ "? )"; //6
	PreparedStatement stm = null; 
	stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql); 
	stm.setString(1, cuiPaciente);
	stm.setString(2, getDescripcionDiasInternado(dias));
	stm.setInt(3, dias);
	stm.setBoolean(4, Main.INGRESOS);
	stm.setDate(5, fecha);
	stm.setInt(6, Area.MEDICOS);
	return stm;
    }


    private String getDescripcionDiasInternado(int dias) {
	StringBuffer descripcion = new StringBuffer(); 
	descripcion.append("Por "); 
	descripcion.append(dias); 
	descripcion.append(" internado en el hospital"); 
	return descripcion.toString();
    }


    public String consultarPacientesConCuentaPendiente(String funcionJS, String textoBoton) {
	PreparedStatement stm;
	ResultSet resultados = null;
	String sql ="	SELECT Persona.cui, Persona.nombre, IFNULL(Internado.inicio, '--') AS 'Ingreso', IFNULL(Internado.fin, '--') AS 'Egreso', " + 
		"	(SELECT SUM(Cuenta.monto) FROM Cuenta WHERE id_paciente = Paciente.id_paciente AND pagado = false) AS 'Cuenta' FROM Persona  " + 
		"	INNER JOIN Paciente ON Persona.cui = Paciente.cui  " + 
		"	INNER JOIN Internado ON Paciente.id_paciente = Internado.id_paciente  " + 
		"	INNER JOIN Cuenta ON Paciente.id_paciente = Cuenta.id_paciente   " + 
		"	WHERE (SELECT SUM(Cuenta.monto) FROM Cuenta WHERE id_paciente = Paciente.id_paciente AND pagado = false) > 0 " + 
		"	GROUP BY Persona.cui";
	try {
	    stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
	    resultados = stm.executeQuery(); 
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return GeneradorHTML.convertirTabla(resultados, funcionJS, textoBoton, false, false, true);
    }
    
    
    public String leerPacientesRegistrados() {
	String sql = "SELECT Persona.cui,  Persona.nombre, Persona.direccion, Paciente.nit "+
		" FROM Persona "+
		" INNER JOIN Paciente ON Persona.cui=Paciente.cui ";
	PreparedStatement stm = null ;
	ResultSet pacientes = null ;
	try {
	    stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
	    pacientes = stm.executeQuery();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return GeneradorHTML.convertirTabla(pacientes, "seleccionarPaciente(this)", "Editar", false ,false,true ); 
    }
    
    public String leerPaciente(String cui) {
	String respuesta= null;
	String sql = "SELECT Persona.cui,  Persona.nombre, Persona.direccion, Paciente.nit "+
		" FROM Persona "+
		" INNER JOIN Paciente ON Persona.cui=Paciente.cui "
		+ " WHERE Paciente.cui = ? ";

	ResultSet paciente = null ;
	try {
	    PreparedStatement stm  = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
	    stm.setString(1, cui);
	    paciente = stm.executeQuery();
	    if (paciente.next()) {
		respuesta = "<div>" + 
			"    <h3>Edicion de Paciente</h3>" + 
			"    <div class=\"form-group row\">" + 
			"      <label class=\"col-sm-2 col-form-label\">CUI </label>" + 
			"      <div class=\"col-sm-10\">" + 
			"        <input type=\"text\" class=\"form-control\" id=\"cuiPacienteNuevo\" value=\""+paciente.getString(1)+"\">" + 
			"        <input type=\"hidden\" class=\"form-control\" id=\"cuiPacienteActual\" value=\""+paciente.getString(1)+"\">" + 
			"      </div>" + 
			"    </div>" + 
			"" + 
			"    <div class=\"form-group row\">" + 
			"      <label class=\"col-sm-2 col-form-label\">Nombre </label>" + 
			"      <div class=\"col-sm-10\">" + 
			"        <input type=\"text\" class=\"form-control\" id=\"nombrePaciente\" value=\""+paciente.getString(2)+"\">" + 
			"      </div>" + 
			"    </div>" + 
			"" + 
			"    <div class=\"form-group row\">" + 
			"      <label class=\"col-sm-2 col-form-label\">Direccion </label>" + 
			"      <div class=\"col-sm-10\">" + 
			"        <input type=\"text\" class=\"form-control\" id=\"direccionPaciente\" value=\""+paciente.getString(3)+"\">" + 
			"      </div>" + 
			"    </div>" + 
			"" + 
			"    <div class=\"form-group row\">" + 
			"      <label class=\"col-sm-2 col-form-label\">Nit </label>" + 
			"      <div class=\"col-sm-10\">" + 
			"        <input type=\"text\" class=\"form-control\" id=\"nitPaciente\" value=\""+paciente.getString(4)+"\">" + 
			"      </div>" + 
			"    </div>" + 
			"</div>" + 
			"" + 
			"<div class=\"button-group\">" + 
			"  <input class=\"btn btn-primary\"  type=\"button\" id=\"\"  value=\"Guardar Cambios\"  onclick=\"actualizarPaciente()\">" + 
			"</div>"; 
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	    respuesta = "Ha ocurrido un error"+ e.getErrorCode(); 
	}
	return respuesta; 
    }
    
    public String actualizarPaciente(String cuiActual, String cuiNuevo, String nombre, String direccion, String nit) {
	String respuesta = null; 
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	try {
	conexion.setAutoCommit(false);
	ManejadorPersona p = new ManejadorPersona(); 
	p.acutalizarPersona(cuiActual, cuiNuevo, nombre, direccion); 
	String sql = "UPDATE Paciente SET nit = ?   " + 
		" WHERE Paciente.cui  = ?" ; 
	PreparedStatement stm;
	    stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
	    stm.setString(1, nit);
	    stm.setString(2, cuiNuevo);
	    stm.execute(); 
	    respuesta = Main.MENSAJE_EXITO;
	} catch (SQLException e) {
	    e.printStackTrace();
		    try {
			conexion.rollback();
		    } catch (SQLException e1) {
			e1.printStackTrace();
		    }
	    respuesta  = "Error de registro. Codigo " + e.getErrorCode(); 
	} finally {
	    try {
		conexion.setAutoCommit(true);
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
	return respuesta; 
    }
  
    
    
}