package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.kevin.modelos.MedicoEspecialista;
import com.kevin.servicio.DBConnection;
import com.kevin.servicio.GeneradorHTML;

public class ManejadorRecursosHumanos {
    
    public static final String MENSAJE_EXITO = 
		"<div class=\"alert alert-success\">"
       +" Operacion realizada exitosamente" 
       +" </div>";
    
    
/**
 * Se encarga del proceso de registrar un medico especiliasta en la DB.
 * Pasando por las tablas de Persona y Medico_Especialista
 * @param request
 * @return El mensaje resulado de la operacion
 */
    public String registrarMedicoEspecialista(HttpServletRequest request) {
	StringBuffer mensaje = new StringBuffer();
	MedicoEspecialista medico = leerDatos(request);
	ManejadorPersona manejadorPersona = new ManejadorPersona();
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	try {
	    conexion.setAutoCommit(false);
	    manejadorPersona.registrarPersona(medico, conexion);
	    registrarMedicoEspecialista(medico, conexion);
	    conexion.commit();
	    mensaje.append("Registro del medico especialista con exito");
	} catch (SQLException e) {
	    e.printStackTrace();
	    mensaje.append("Error en el registro del medico especialista. Codigo de error: "+ e.getErrorCode());
	    try {
		conexion.rollback();
	    } catch (SQLException e1) {
		e1.printStackTrace();
	    }
	}
	return mensaje.toString();
    }
    
    
    /**
     * Registra informacion de un medico especialista en la tabla de medicos especialistas
     * @param medico
     * @param conexion
     * @throws SQLException
     */
    private void registrarMedicoEspecialista(MedicoEspecialista medico, Connection conexion) throws SQLException {
	String sql = "INSERT INTO Medico_Especialista (no_de_colegiado, cui_persona) VALUES (?,?)";
	PreparedStatement stm = conexion.prepareStatement(sql);
	stm.setInt(1, medico.getColegiado());
	stm.setString(2, medico.getCui());
	stm.execute();
    }
    
    /**
     * Lee la informacion de la solicitud y crea una nueva instancia del objeto MedicoEspecialista
     * @param request la solicitud que contiene la informacion
     * @return
     */
    private MedicoEspecialista leerDatos(HttpServletRequest request) {
	return new MedicoEspecialista(request.getParameter("cui"), 
		request.getParameter("nombre"), 
		request.getParameter("direccion"), 
		Integer.parseInt(request.getParameter("colegiado")));
    }
    
    
    public String consultarEmpleadosActivos() {
	String respuesta = null;
	String sql = "SELECT Contrato.id_contrato AS 'Contrato', Persona.nombre, Empleado.fecha_de_vacaciones, Area.descripcion AS 'Area de trabajo', Contrato.fecha_inicial, Contrato.salario "+
		" FROM Persona INNER JOIN Empleado ON Persona.cui= Empleado.cui_persona "+
		" INNER JOIN Contrato ON Empleado.id_empleado = Contrato.id_empleado "+
		" INNER JOIN Area ON Area.id_area = Empleado.id_area "+
		" WHERE Contrato.fecha_final is null "+
		" ORDER BY Contrato.id_contrato"; 
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	try {
	    respuesta = GeneradorHTML.convertirTabla(conexion.prepareStatement(sql).executeQuery(), "despedirEmpleado(this)", "Despedir", false, false, true);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return respuesta;
    }
    
    
    public String consultarEmpleadosDespedidos() {
	String respuesta = null;
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	String sql = "SELECT Contrato.id_contrato AS 'Contrato', Persona.nombre, Empleado.fecha_de_vacaciones, Area.descripcion AS 'Area de trabajo', Contrato.fecha_inicial, Contrato.salario "+
		" FROM Persona INNER JOIN Empleado ON Persona.cui= Empleado.cui_persona "+
		" INNER JOIN Contrato ON Empleado.id_empleado = Contrato.id_empleado "+
		" INNER JOIN Area ON Area.id_area = Empleado.id_area "+
		" WHERE Contrato.fecha_final IS NOT NULL AND Empleado.activo= 0"+
		" ORDER BY Contrato.id_contrato"; 
	try {
	    respuesta = GeneradorHTML.convertirTabla(conexion.prepareStatement(sql).executeQuery(), "recontratarEmpleado(this)", "Recontratar", false, false, true);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return respuesta;
    }

    public void despedirEmpleado(HttpServletRequest request) {
	int contrato = Integer.parseInt(request.getParameter("contrato"));
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	try {
	    String sql = "UPDATE Contrato SET fecha_final= CURDATE() WHERE id_contrato= ?";
	    conexion.setAutoCommit(false);
	    modificarContrato(sql, conexion, contrato);
	    modificarEstadoDelEmpleado(false, contrato, conexion);  //se desactiva
	    conexion.commit();
	} catch (SQLException e) {
	    e.printStackTrace();
	    revertirCambios(conexion);
	} finally {
	    cerrarConexion(conexion); 
	}
    }
    
    
    public String recontratar(HttpServletRequest request) {
	String respuesta = null;
	int contrato = Integer.parseInt(request.getParameter("contrato"));
	String sql = "INSERT INTO Contrato (salario, id_empleado) VALUES (?,?)"; 
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	try {
	    conexion.setAutoCommit(false);
	    PreparedStatement stm = conexion.prepareStatement(sql);
	    stm.setDouble(1, Double.parseDouble(request.getParameter("salario")));
	    stm.setInt(2, leerEmpleado(contrato));
	    stm.execute();
	    modificarEstadoDelEmpleado(true, contrato, conexion);  //se activa el usuario
	    conexion.commit();
	    respuesta = MENSAJE_EXITO;
	} catch (SQLException e) {
	    e.printStackTrace();
	    revertirCambios(conexion);
	    respuesta = "Error de la operacion"; 
	} finally {
	    cerrarConexion(conexion); 
	}
	return respuesta;
    }


    private void cerrarConexion(Connection conexion) {
	  try {
			conexion.setAutoCommit(true);
		    } catch (SQLException e) {
			e.printStackTrace();
		    }
    }
    
    private void modificarEstadoDelEmpleado(boolean estado, int contrato, Connection conexion) throws SQLException {
 	PreparedStatement stm;
	String sqlEmpleado = "UPDATE Empleado SET activo= ? WHERE id_empleado= ?"; 
	    int empleado = leerEmpleado(contrato);
	   stm = conexion.prepareStatement(sqlEmpleado); 
	    stm.setBoolean(1, estado);
	    stm.setInt(2,empleado); 
	    stm.execute();
    }
    


    private void modificarContrato(String sql, Connection conexion, int contrato) throws SQLException {
 	PreparedStatement stm;
 	    stm = conexion.prepareStatement(sql);
 	    stm.setInt(1, contrato);
 	    stm.execute();
     }
    
    
    private void revertirCambios(Connection conexion) {
	  try {
			conexion.rollback();
		    } catch (SQLException e1) {
			e1.printStackTrace();
		    }
    }

    private int leerEmpleado(int idContrato) throws SQLException {
	int empleado =0;
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	String sql = "SELECT id_empleado FROM Contrato WHERE id_contrato = ? "; 
	PreparedStatement stm = conexion.prepareStatement(sql);
	stm.setInt(1, idContrato);
	ResultSet resultado = stm.executeQuery();
	if (resultado.next())
	    empleado = resultado.getInt(1);
	return empleado;
    }
    
    public String cosultarPlanillaEmpleadosActivos() {
	String empleados = null;
	String sql = "SELECT Persona.cui, Persona.nombre, Persona.direccion, " + 
		"Empleado.porcentaje_igss AS 'IGGS', Empleado.porcentaje_irtra AS 'IRTRA', Area.descripcion AS 'Area'" + 
		"FROM Persona " + 
		"INNER JOIN Empleado ON Empleado.cui_persona = Persona.cui " + 
		"INNER JOIN Area ON Area.id_area = Empleado.id_area";
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	try {
	    PreparedStatement stm = conexion.prepareStatement(sql);
	    ResultSet r = stm.executeQuery();
	    empleados =GeneradorHTML.convertirTabla(r, "seleccionarEmpleado(this)", "Seleccionar", false, false, true); 
	} catch (SQLException e) {
	    e.printStackTrace();
	    empleados = "Ocurrio un error. Codigo " + e.getErrorCode(); 
	}
	return empleados; 
    }
    
    public String leerEmpleado(String cuiEmpleado) {
	String empleado = null;
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	String sql = "SELECT Persona.cui, Persona.nombre, Persona.direccion, " + 
		" Empleado.porcentaje_igss AS 'IGGS', Empleado.porcentaje_irtra AS 'IRTRA', Area.descripcion AS 'Area'" + 
		" FROM Persona " + 
		" INNER JOIN Empleado ON Empleado.cui_persona = Persona.cui " + 
		" INNER JOIN Area ON Area.id_area = Empleado.id_area" + 
		" WHERE Persona.cui = ? ";
	try {
	    PreparedStatement stm = conexion.prepareStatement(sql);
	    stm.setString(1, cuiEmpleado);
	    ResultSet r = stm.executeQuery();
	    r.next();
	    empleado =" <div>" + 
	    	"    <h3>Edicion de Empleado</h3>" + 
	    	"    <div class=\"form-group row\">" + 
	    	"      <label class=\"col-sm-2 col-form-label\">CUI </label>" + 
	    	"      <div class=\"col-sm-10\">" + 
	    	"        <input type=\"text\" class=\"form-control\" id=\"campo-cui\" value = '"+r.getString(1)+"'>" + 
	    	"        <input type=\"hidden\" class=\"form-control\" id=\"campo-cui-viejo\" value = '"+r.getString(1)+"'>" + 
	    	"      </div>" + 
	    	"    </div>" + 
	    	"" + 
	    	"    <div class=\"form-group row\">" + 
	    	"      <label class=\"col-sm-2 col-form-label\">Nombre </label>" + 
	    	"      <div class=\"col-sm-10\">" + 
	    	"        <input type=\"text\" class=\"form-control\" id=\"campo-nombre\" value = '"+r.getString(2)+"'>" + 
	    	"      </div>" + 
	    	"    </div>" + 
	    	"" + 
	    	"    <div class=\"form-group row\">" + 
	    	"      <label class=\"col-sm-2 col-form-label\">Direccion </label>" + 
	    	"      <div class=\"col-sm-10\">" + 
	    	"        <input type=\"text\" class=\"form-control\" id=\"campo-direccion\" value = '"+r.getString(3)+"'>" + 
	    	"      </div>" + 
	    	"    </div>" + 
	    	"" + 
	    	"    <div class=\"form-group row\">" + 
	    	"      <label class=\"col-sm-2 col-form-label\">IGGS </label>" + 
	    	"      <div class=\"col-sm-10\">" + 
	    	"        <input type=\"number\" class=\"form-control\" id=\"campo-iggs\" value = '"+r.getInt(4)+"'>" + 
	    	"      </div>" + 
	    	"    </div>" + 
	    	"" + 
	    	"    <div class=\"form-group row\">" + 
	    	"      <label class=\"col-sm-2 col-form-label\">IRTRA </label>" + 
	    	"      <div class=\"col-sm-10\">" + 
	    	"        <input type=\"number\" class=\"form-control\" id=\"campo-irtra\" value = '"+r.getInt(5)+"'>" + 
	    	"      </div>" + 
	    	"    </div>" + 
	    	"</div>"
	    	+ ""
	    	+ "<div class=\"button-group\">" + 
	    	"  <input class=\"btn btn-primary\"  type=\"button\" id=\"btnEditarEmpleado\"  value=\"Actualizar\"  onclick=\"actualizarEmpleado(this)\">" + 
	    	"</div>";
	} catch (SQLException e) {
	    e.printStackTrace();
	    empleado = "Ocurrio un error. Codigo " + e.getErrorCode(); 
	}
	return empleado; 
    }
    
    public String actualizarEmpleado(String cuiActual, String cuiNuevo, String nombre, String direccion, int iggs, int irtra) {
	String respuesta = null; 
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	try {
	conexion.setAutoCommit(false);
	ManejadorPersona p = new ManejadorPersona(); 
	p.acutalizarPersona(cuiActual, cuiNuevo, nombre, direccion); 
	String sql = "UPDATE Empleado SET Empleado.porcentaje_igss = ?, Empleado.porcentaje_irtra = ? " + 
		" WHERE Empleado.cui_persona = ?" ; 
	PreparedStatement stm;
	    stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
	    stm.setInt(1, iggs);
	    stm.setInt(2, irtra);
	    stm.setString(3, cuiNuevo);
	    stm.execute(); 
	    respuesta = MENSAJE_EXITO; 
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
