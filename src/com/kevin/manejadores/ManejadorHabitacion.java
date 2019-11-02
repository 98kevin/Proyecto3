package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.kevin.modelos.Habitacion;
import com.kevin.servicio.DBConnection;

public class ManejadorHabitacion extends DBConnection{
    
    public String registrarHabitacion(Habitacion habitacion) {
	StringBuffer mensaje = new StringBuffer(); 
	Connection conexion = conexion();
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

}
