package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.kevin.modelos.Persona;
import com.kevin.servicio.DBConnection;

public class ManejadorPersona {

/**
 * 
 * @param persona
 * @param conexion
 * @throws SQLException
 */
    public void registrarPersona(Persona persona, Connection conexion) throws SQLException {
	String sqlPersona = "INSERT INTO Persona (cui, nombre, direccion) VALUES (?,?,?)";
	    PreparedStatement stmPersona = conexion.prepareStatement(sqlPersona);
	    stmPersona.setString(1, persona.getCui());
	    stmPersona.setString(2, persona.getNombre());
	    stmPersona.setString(3, persona.getDireccion());
	    stmPersona.execute();
    }
    
    public String acutalizarPersona(String cuiActual, String cuiNuevo, String nombre, String direccion) {
	String respuesta = null; 
	String sql = "UPDATE Persona SET cui = ? , nombre = ? , direccion = ? WHERE cui = ? "; 
	PreparedStatement stm;
	try {
	    stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
		stm.setString(1, cuiNuevo);
		stm.setString(2, nombre);
		stm.setString(3, direccion);
		stm.setString(4, cuiActual);
		stm.execute(); 
		respuesta = "registro exitoso";
	} catch (SQLException e) {
	    e.printStackTrace();
	    respuesta  = "Error de registro. Codigo " + e.getErrorCode(); 
	} 
	return respuesta; 
    }
}
