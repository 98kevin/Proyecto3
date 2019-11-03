package com.kevin.servicio;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class GeneradorHTML {
    
/*
    public static void generarHTML(String titulo, String consulta) {
	Statement statement;
	ResultSet resultados=null;
	try {
	    statement = Main.conexion.createStatement();
	    resultados = statement.executeQuery(consulta);
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	String sourceHTML=" <!DOCTYPE html> "
		+ "	<head> 	    "
		+ "	<meta charset='UTF-8'> "
		+ " <title>"+titulo+"</title>     "
		+ "<link rel=\"stylesheet\" href=\"tabla.css\"> "
		+ "</head>"
		+ ""
		+ "<body>"
		+ "<h1>"+titulo+"</h1>"
		+ convertirTabla(resultados)
		+ "</body>"
		+ "</html>";
	
	Archivo.leerCodigos(sourceHTML);
    }
    */

    public static String convertirTabla(ResultSet resutladoConsulta)  {
		String sourceTable="";
		try {
		    sourceTable = sourceTable +("<p ALIGN='center'><TABLE BORDER=1>");
		     ResultSetMetaData rsmd = resutladoConsulta.getMetaData();
			 int columnCount = rsmd.getColumnCount();
			 // table header
			 sourceTable = sourceTable +("<thead><tr>");
			 for (int i = 0; i < columnCount; i++) {
			     sourceTable = sourceTable + ("<th>" + rsmd.getColumnLabel(i + 1) + "</th>");
			   }
			 sourceTable = sourceTable +("</tr></thead>");
			 // the data
			 while (resutladoConsulta.next()) {
			     sourceTable = sourceTable +("<tr>");
			  for (int i = 0; i < columnCount; i++) {
			      sourceTable = sourceTable +("<td>" + resutladoConsulta.getString(i + 1) + "</td>");
			    }
			  sourceTable = sourceTable +("</tr>");
			  }
			 sourceTable = sourceTable +("</table></p>");
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		 return sourceTable;
    }

}
