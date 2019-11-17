package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.kevin.modelos.Area;
import com.kevin.servicio.DBConnection;
import com.kevin.servicio.GeneradorHTML;

public class ManejadorArea{

    
    /**
     * Registra el area en la base de datos
     * @param area
     */
    public void registrarArea(Area area) {
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
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
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	String sql = "Select Area.id_area, Area.descripcion, Modulo.nombre FROM Area INNER JOIN Modulo ON Area.id_modulo= Modulo.id_modulo WHERE Area.id_area>2";
	PreparedStatement stm = conexion.prepareStatement(sql); 
	ResultSet resultado = stm.executeQuery(); 
	while(resultado.next()) {
	    areas.append("<option value=\""+resultado.getInt(1)+"\">"+resultado.getString(2) +" - " +resultado.getString(3)+"</option>");
	}
	return areas.toString();
    }
    
    public String consutarAreas() throws SQLException {
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	String sql = "Select Area.id_area, Area.descripcion, Modulo.nombre FROM Area INNER JOIN Modulo ON Area.id_modulo= Modulo.id_modulo";
	PreparedStatement stm = conexion.prepareStatement(sql); 
	ResultSet resultado = stm.executeQuery(); 
	return GeneradorHTML.convertirTabla(resultado); 
    }


    public CharSequence registrarCirugia(HttpServletRequest request) {
	StringBuffer respuesta = new StringBuffer(); 
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	String sql = "INSERT INTO Cirugias_Disponibles (descripcion, costo_al_hospital, tarifa_de_especialista, precio_al_cliente)  VALUES (?,?,?,?)";
	PreparedStatement stm;
	try {
	    stm = conexion.prepareStatement(sql);
	    stm.setString(1, request.getParameter("descripcion"));
	    stm.setDouble(2, Double.parseDouble(request.getParameter("costo")));
	    stm.setDouble(3, Double.parseDouble(request.getParameter("tarifaEspecialista")));
	    stm.setDouble(4, Double.parseDouble(request.getParameter("precio")));
	    stm.execute();
	    respuesta.append("Registro de Nueva Cirugia correcto");
	} catch (SQLException e) {
	    e.printStackTrace();
	    respuesta.append("Ocurrio un error al registrar la cirugia. Codigo de error: "+e.getErrorCode());
	} 
	return respuesta.toString();
    }
    
    
}
