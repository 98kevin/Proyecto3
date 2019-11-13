package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kevin.modelos.Habitacion;
import com.kevin.servicio.DBConnection;

public class ManejadorHabitacion {
    
    public String registrarHabitacion(Habitacion habitacion) {
	StringBuffer mensaje = new StringBuffer(); 
	Connection conexion =DBConnection.getInstanceConnection().getConexion();
	String sql  = "INSERT INTO Habitacion (precio_de_mantenimiento) values (?)"; 
	try {
	    PreparedStatement stm = conexion.prepareStatement(sql);
	    stm.setDouble(1, habitacion.getMantenimiento() );
	    stm.execute();
	    mensaje.append("Habitacion creada con exito");
	} catch (SQLException e) {
	    e.printStackTrace();
	    mensaje.append("Error en el registro en la base de datos. Codigo de error: " +e.getErrorCode());
	} 
	return mensaje.toString();
    }

    public String consultarHabitacionesLibres()  {
	StringBuffer  resultados = new StringBuffer();
	//habitaciones libres y disponibles
	String sql = "SELECT id_habitacion FROM Habitacion WHERE esta_ocupada=0 & esta_habilitada=1";
	Connection conexion  = DBConnection.getInstanceConnection().getConexion();
	PreparedStatement stm;
	try {
	    stm = conexion.prepareStatement(sql);
	    ResultSet habitaciones = stm.executeQuery();
	    	resultados.append("<h3>Seleccion de habitacion</h3>");
		resultados.append("<select id=\"habitaciones\" class=\"form-control\">");
		while (habitaciones.next()) {
		    resultados.append("<option value=\""+habitaciones.getInt(1)+"\">"+habitaciones.getInt(1)+"</option>");
		}
		resultados.append("</select>");
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return resultados.toString();
    }

}
