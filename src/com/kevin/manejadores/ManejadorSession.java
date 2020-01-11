package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kevin.modelos.Area;
import com.kevin.servicio.DBConnection;
import com.kevin.servicio.GeneradorHTML;

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
    
    public String leerUsuarios() {
	String credenciales = null;
	String sql = "SELECT id_credenciales, correo_electronico, password FROM Credenciales"; 
	try {
	    PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
	    ResultSet r = stm.executeQuery(); 
	    credenciales =GeneradorHTML.convertirTabla(r, "seleccionarCredenciales(this)", "Editar", false, false, true); 
	} catch (SQLException e) {
	    e.printStackTrace();
	} 
	return credenciales; 
    }
    
    public String actualizarCredenciales(int codigo, String correo, String password) {
	String respuesta = null;
	String sql = "UPDATE Credenciales SET correo_electronico = ? , password = ?" + 
		"WHERE id_credenciales = ?"; 
	try {
	    PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
	    stm.setString(1, correo);
	    stm.setString(2, password);
	    stm.setInt(3, codigo);
	    stm.execute(); 
	    respuesta = "Actualizacion con exito";
	} catch (SQLException e) {
	    e.printStackTrace();
	    respuesta = "Error de actualizacion";
	} 
	return respuesta; 
    }
    
    
    public String borrarCredenciales(int codigo) {//4
	String respuesta = null;
	String sql = "DELETE FROM Credenciales" + 
		" WHERE id_credenciales = ?"; 
	try {
	    PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
	    stm.setInt(1, codigo);
	    stm.execute(); 
	    respuesta = "Actualizacion con exito";
	} catch (SQLException e) {
	    e.printStackTrace();
	    respuesta = "Error de actualizacion";
	} 
	return respuesta; 
    }
    
    public String leerCredencial(int codigo) {
	String credenciales = null;
	String sql = "SELECT id_credenciales, correo_electronico, password FROM Credenciales WHERE id_credenciales = ? "; 
	try {
	    PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
	    stm.setInt(1, codigo);
	    ResultSet r = stm.executeQuery(); 
	    r.next(); 
	    credenciales = "    <div id = 'form-edicion-credenciales'>" + 
	    	"        <div class=\"input-group\">" + 
	    	"            <input id='correo"+codigo+"' type=\"text\" value=\""+r.getString(2)+"\">" + 
	    	"            <input id='password"+codigo+"'type=\"password\" value=\""+r.getString(3)+"\">" + 
	    	"        </div>" + 
	    	"        <div class=\"button-group\">" + 
	    	"            <input id=\""+r.getInt(1)+"\" class=\"btn btn-primary\"    type=\"button\" value=\"Actualizar\" onclick='actualizarCredencial(this)'>" + 
	    	"            <input id=\""+r.getInt(1)+"\" class=\"btn btn-info\"       type=\"button\" value=\"Eliminar\" onclick='borrarCredencial(this)'>" + 
	    	"        </div>" + 
	    	"    </div>";
	} catch (SQLException e) {
	    e.printStackTrace();
	} 
	return credenciales; 
    }
}
