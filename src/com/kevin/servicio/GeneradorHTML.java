package com.kevin.servicio;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class GeneradorHTML {

    public static String convertirTabla(ResultSet resutladoConsulta, String funcionJs, String textoBoton)  {
		String sourceTable="";
		try {
		    sourceTable = sourceTable +("<p><table border=1 class=\"table\">");
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
			  sourceTable = sourceTable + "<td><button id=\""+resutladoConsulta.getInt(1)+"\" " +
			  	 "onClick=\""+funcionJs+" \" class=\"btn btn-info \">"+textoBoton+"</button></td>"; 
			  }
			 sourceTable = sourceTable +("</table></p>");
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		 return sourceTable;
    }

}
