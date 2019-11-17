package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.kevin.modelos.Persona;

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
}
