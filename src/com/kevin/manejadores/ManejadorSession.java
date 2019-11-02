package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kevin.servicio.DBConnection;

public class ManejadorSession extends DBConnection {

    public boolean verificarPassword(String email, String password) {
	String passwordEnDB = null; 
	try {
	    passwordEnDB= consultarPassword(email, password);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return (password.equals(passwordEnDB));

    }

    private String consultarPassword(String email, String password) throws SQLException {
	String passwordDB= null; 
	Connection conexion = conexion();
	String consultaSQL = "SELECT password FROM Credenciales WHERE correo_electronico = ?"; 
	PreparedStatement stm = conexion.prepareStatement(consultaSQL);
	stm.setString(1, email);
	ResultSet consulta = stm.executeQuery(); 
	if(consulta.next())
	    passwordDB = consulta.getString(1);
	else 
	    passwordDB= null;
	return passwordDB;
    }
    
    public int consultarArea(String email) {
	int areaDeTrabajo = 0; 
	Connection conexion = conexion();
	String consultaSQL = "SELECT id_area FROM Credenciales WHERE correo_electronico = ?"; 
	PreparedStatement stm;
	try {
	    stm = conexion.prepareStatement(consultaSQL);
		stm.setString(1, email);
		ResultSet consulta = stm.executeQuery(); 
		consulta.next();
		areaDeTrabajo = consulta.getInt(1);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return areaDeTrabajo;
    }

    public String obtenerDireccion(int area) {
	String direccion= null; 
	String socket = "http://localhost:8080/Proyecto3";
	switch(area) {
	case 1: 
	    direccion= socket + "/administrador/admin.jsp";
	}
	return direccion;
    }
    
}
