package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.kevin.servicio.DBConnection;
import com.kevin.servicio.GeneradorHTML;

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
    
    
    public String consultarMedicamentosDePacientes(int empleado) {
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	PreparedStatement stm;
	ResultSet resultado =null;
	String sql = "SELECT Persona.nombre, Medicamento.id_medicamento, Medicamento.nombre, Internado_tiene_Medicamento.cantidad " + 
		"FROM Medicamento, Internado_tiene_Medicamento " + 
		"INNER JOIN Internado ON Internado.id_internado = Internado_tiene_Medicamento.id_internado " + 
		"INNER JOIN Internado_tiene_Empleado ON Internado_tiene_Empleado.id_internado = Internado.id_internado " + 
		"INNER JOIN Empleado ON Internado_tiene_Empleado.id_empleado = Empleado.id_empleado " + 
		"INNER JOIN Paciente ON Internado.id_paciente = Paciente.id_paciente " + 
		"INNER JOIN Persona ON Paciente.cui= Persona.cui " + 
		"WHERE Internado_tiene_Medicamento.id_medicamento= Medicamento.id_medicamento AND Empleado.id_empleado=?";
	try {
	     stm = conexion.prepareStatement(sql);
	     stm.setInt(1, empleado);
	     resultado = stm.executeQuery();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return GeneradorHTML.convertirTabla(resultado, "suministrarMedicamento(this)", "Suministrar");
    }
}
