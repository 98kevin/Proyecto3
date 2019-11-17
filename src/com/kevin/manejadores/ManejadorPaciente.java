package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.kevin.modelos.Paciente;
import com.kevin.servicio.DBConnection;

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
    
    
    public String pacientesRegistrados() {
	StringBuffer registros= new StringBuffer(); 
	registros.append("<input type=\"text\" id=\"cajaFiltro\" class=\"form-control\" onkeyup=\"filtrarTabla()\""
		+ " placeholder=\"Filtrar por nombre..\">");
	registros.append("<table id=\"tabla\">");
	registros.append("<tr>").append("<th>Cui</th>");
	registros.append("<th>Nombre</th>");
	registros.append("<th>Direccion</th>");
	try {
	    ResultSet pacientes = consultarPacientes();
	    while(pacientes.next()) {
	        registros.append("<tr class=\"\">");
	        registros.append("<td>"+pacientes.getInt(1)+"</td>");
	        registros.append("<td>"+pacientes.getString(2)+"</td>");
	        registros.append("<td>"+pacientes.getString(3)+"</td>");
	        registros.append("<td><button id=\""+pacientes.getInt(4)+"\" onClick=\"seleccionarPaciente(this)\" "
	        	+ "class=\"botonSeleccionar btn btn-info \">Seleccionar Paciente</button></td>");
	        registros.append("</tr>");
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	registros.append("</table>");
	return registros.toString(); 
    }


    private ResultSet consultarPacientes() {
	ResultSet pacientes = null; 
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	String sql = "SELECT p.cui, p.nombre, p.direccion, q.id_paciente FROM Persona p INNER JOIN Paciente q ON p.cui=q.cui";
	Statement stm;
	try {
	    stm = conexion.createStatement();
		pacientes = stm.executeQuery(sql);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return pacientes;
    }
    
    public boolean isInternadoActualmente(int paciente) {
	boolean resultado= false;
	String sql = "SELECT * FROM Internado WHERE id_paciente=? AND fin IS NULL";
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	PreparedStatement stm;
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
   
}
