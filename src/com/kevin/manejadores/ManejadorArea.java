package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
    
}
