package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kevin.modelos.Area;
import com.kevin.servicio.DBConnection;

public class ManejadorSession  {
     

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
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
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
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
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
	case Area.ADMINISTRACION: 
	    direccion= socket + "/administrador/admin.jsp";
	    break;
	case Area.RECURSOS_HUMANOS: 
	    direccion = socket + "/recursos-humanos/recursos-humanos.jsp";
	    break;
	case Area.FARMACIA: 
	    direccion= socket + "/farmacia/farmacia.jsp";
	    break;
	case Area.MEDICOS: 
	    direccion= socket + "/medico/medico.jsp";
	    break;
	case Area.ENFERMEROS: 
	    direccion= socket + "/enfermera/enfermera.jsp";
	    break;
	case Area.CAJA: 
	    direccion = socket + "/cajero/cajero.jsp"; 
	    break;
	}
	return direccion;
    }

    public int getCodigoUsuario(String email) {
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	int codigoUsuario = 0;
	String consultaSQL = "SELECT id_empleado FROM Credenciales WHERE correo_electronico=?"; 
	PreparedStatement stm;
	try {
	    stm = conexion.prepareStatement(consultaSQL);
	    stm.setString(1, email);
	    ResultSet consulta = stm.executeQuery(); 
	    consulta.next();
	    codigoUsuario = consulta.getInt(1);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return codigoUsuario;
    }
    
}
