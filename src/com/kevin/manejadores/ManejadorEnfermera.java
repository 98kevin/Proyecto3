package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.kevin.servicio.DBConnection;

public class ManejadorEnfermera {

    private ResultSet consultarEnfermerasEnDB() throws SQLException {
	ResultSet enfermeras = null;
	String sql = "SELECT e.id_empleado, p.nombre FROM Persona p INNER JOIN Empleado e ON p.cui=e.cui_persona WHERE e.id_area=5";
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	Statement stm =conexion.createStatement();
	enfermeras = stm.executeQuery(sql);
	return enfermeras;
    }
    
    public String consultarEnfermeras() {
    StringBuffer registros= new StringBuffer(); 
	registros.append("<input type=\"text\" id=\"cajaFiltro\" class=\"form-control\" onkeyup=\"filtrarTabla()\" placeholder=\"Filtrar por enfermera..\">");
	registros.append("<table class=\"table\" id=\"tabla\">");
	registros.append("<tr>").append("<th>codigo</th>");
	registros.append("<th>Enfermera</th>").append("</tr>");
	try {
	    ResultSet enfermeras = consultarEnfermerasEnDB();
	    while(enfermeras.next()) {
	        registros.append("<tr class=\"\">");
	        registros.append("<td>"+enfermeras.getString(1)+"</td>");
	        registros.append("<td>"+enfermeras.getString(2)+"</td>");
	        registros.append("<td><button id=\""+enfermeras.getString(1)+"\" onClick=\"agregarEnfermera(this)\" class=\"btn-agregar-enfermera\">Asingar</button></td>");
	        registros.append("</tr>");
	    }
	} catch(SQLException e ) {
	    e.printStackTrace();
	}
	return registros.toString();
    }
}
