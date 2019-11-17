package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.kevin.modelos.MedicoEspecialista;
import com.kevin.servicio.DBConnection;

public class ManejadorRecursosHumanos {
    
    
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
    

}
