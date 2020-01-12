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
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	String sql = "Select Area.id_area, Area.descripcion FROM Area WHERE Area.id_area > 2"; // no se toma en cuenta el area de administracion
	PreparedStatement stm = conexion.prepareStatement(sql); 
	ResultSet resultado = stm.executeQuery(); 
	return GeneradorHTML.convertirSelector(resultado, ""); 
    }
    
    public String consutarAreas() throws SQLException {
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	String sql = "Select Area.id_area, Area.descripcion FROM Area";
	PreparedStatement stm = conexion.prepareStatement(sql); 
	ResultSet resultado = stm.executeQuery(); 
	return GeneradorHTML.convertirSelector(resultado, "seleccionarArea()"); 
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
    
    public String actualizarArea(int codigo, String descripcion) {
	String respuesta = null;
	String sql = "UPDATE Area SET descripcion = ? " + 
		"WHERE id_area = ? "; 
	PreparedStatement stm;
	try {
	    stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
	    stm.setString(1, descripcion);
	    stm.setInt(2, codigo);
	    stm.execute(); 
	    respuesta = "Actualizacion exitosa"; 
	} catch (SQLException e) {
	    respuesta = "error de actualizacion"; 
	    e.printStackTrace();
	} 
	return respuesta; 
    }
    
    public String borrarArea(int codigo) {
	String respuesta = null;
	String sql = "DELETE FROM Area " + 
		"WHERE id_area = ? "; 
	PreparedStatement stm;
	try {
	    stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
	    stm.setInt(1, codigo);
	    stm.execute(); 
	    respuesta = "Actualizacion exitosa"; 
	} catch (SQLException e) {
	    respuesta = "Error, hay empleados referenciando esta area";
	    e.printStackTrace();
	} 
	return respuesta; 
    }
    
    public String leerAreas () {
	String respuesta = null;
	String sql = "SELECT id_area, descripcion, Modulo.nombre AS 'Modulo' "
		+ " FROM Area INNER JOIN Modulo ON Modulo.id_modulo = Area.id_modulo "
		+ " GROUP BY id_area"; 
	PreparedStatement stm;
	try {
	    stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
	    
	    ResultSet r = stm.executeQuery(); 
	    respuesta =GeneradorHTML.convertirTabla(r, "seleccionarArea(this)", "Editar", false, false, true); 
	} catch (SQLException e) {
	    respuesta = "error de actualizacion"; 
	    e.printStackTrace();
	} 
	return respuesta; 
    }
    
    public String leerArea(int codigo) {
	String respuesta = null;
	String sql = "SELECT descripcion "
		+ " FROM Area " + 
		"WHERE id_area = ? "; 
	PreparedStatement stm;
	try {
	    stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
	    stm.setInt(1, codigo);
	    ResultSet r = stm.executeQuery(); 
	    r.next(); 
	    respuesta = "<div>" + 
	    	"    <h3>Edicion de area</h3>" + 
	    	"    <div class=\"form-group mb-2\">" + 
	    	"        <label for=\"staticEmail2\" class=\"sr-only\">Descripcion</label>" + 
	    	"        Descripcion <input type=\"text\" class=\"form-control-plaintext\" id=\"descripcionArea\" value=\""+r.getString(1)+"\">" + 
	    	"      </div>"
	    	+ "<div class=\"button-group\">" + 
	    	"  <input class=\"btn btn-primary\" value=\"Editar\" type=\"button\" id=\""+codigo+"\"    onclick=\"actualizarArea(this)\">" + 
	    	"  <input class=\"btn btn-danger\" value=\"Eliminar\"  type=\"button\" id=\""+codigo+"\"  onclick=\"eliminarArea(this)\">" + 
	    	"</div>" + 
	    	"</div>";
	} catch (SQLException e) {
	    respuesta = "error de actualizacion"; 
	    e.printStackTrace();
	} 
	return respuesta; 
    }
    
    }
    

