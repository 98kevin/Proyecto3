package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kevin.modelos.Area;
import com.kevin.servicio.DBConnection;
import com.kevin.servicio.GeneradorHTML;

public class ManejadorArea extends DBConnection{

    
    /**
     * Registra el area en la base de datos
     * @param area
     */
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
    
    
    /**
     * Consulta las areas de trabajo junto a su modulo, y las agrega a un select, individualmetne como un <option> 
     * @return Un Arreglo de <option>
     * @throws SQLException
     */
    public String consultarAreasDeTrabajo() throws SQLException {
	StringBuffer areas = new StringBuffer(); 
	Connection conexion = conexion();
	String sql = "Select Area.id_area, Area.descripcion, Modulo.nombre FROM Area INNER JOIN Modulo ON Area.id_modulo= Modulo.id_modulo WHERE Area.id_area>2";
	PreparedStatement stm = conexion.prepareStatement(sql); 
	ResultSet resultado = stm.executeQuery(); 
	while(resultado.next()) {
	    areas.append("<option value=\""+resultado.getInt(1)+"\">"+resultado.getString(2) +" - " +resultado.getString(3)+"</option>");
	}
	return areas.toString();
    }
    
    public String consutarAreas() throws SQLException {
	Connection conexion = conexion();
	String sql = "Select Area.id_area, Area.descripcion, Modulo.nombre FROM Area INNER JOIN Modulo ON Area.id_modulo= Modulo.id_modulo";
	PreparedStatement stm = conexion.prepareStatement(sql); 
	ResultSet resultado = stm.executeQuery(); 
	return GeneradorHTML.convertirTabla(resultado); 
    }
    
    
}
