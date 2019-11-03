package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kevin.modelos.Area;
import com.kevin.servicio.DBConnection;

public class ManejadorArea extends DBConnection{

    public void registrarArea(Area area) {
	Connection conexion = conexion();
	//codigo sql para el registro
	String sql = "INSERT INTO Area (descripcion, id_modulo) values (?,?)";
	try {
	    PreparedStatement stm = conexion.prepareStatement(sql);
	    stm.setString(1, area.getDescripcion());
	    stm.setInt(2, area.getModulo());
	    stm.execute();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }
    
    public String consultarAreasDeTrabajo() throws SQLException {
	StringBuffer areas = new StringBuffer(); 
	Connection conexion = conexion();
	String sql = "Select Area.id_area, Area.descripcion, Modulo.nombre FROM Area INNER JOIN Modulo ON Area.id_modulo= Modulo.id_modulo WHERE Area.id_area>2";
	PreparedStatement stm = conexion.prepareStatement(sql); 
	ResultSet resultado = stm.executeQuery(); 
	while(resultado.next()) {
	    areas.append("<option value=\""+resultado.getInt(1)+"\">"+resultado.getString(2) +" - " +resultado.getString(3));
	}
	return areas.toString();
    }
    
    
}
