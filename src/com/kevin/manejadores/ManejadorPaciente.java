package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.kevin.modelos.Paciente;
import com.kevin.servicio.DBConnection;

public class ManejadorPaciente extends DBConnection{

    public String registrarPaciente(Paciente paciente) {
	Connection conexion = conexion();
	StringBuffer mensaje= new StringBuffer();
	try {
	    conexion.setAutoCommit(false);
	    ManjeadorPersona persona = new ManjeadorPersona(); 
	    persona.registrarPersona(paciente,conexion); 
	    registrarPaciente(paciente, conexion); 
	    conexion.commit();
	   mensaje.append("Creacion de paciente exitosa realizada con exito"); 
	} catch (SQLException e) {
	    e.printStackTrace();
	    mensaje.append("Error de intregridad de base de datos.  \n codigo de error " + e.getErrorCode());
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
    
    
}
