package com.kevin.servicio;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class GeneradorHTML {

/**
 * Convierte un resultado SQL a una tabla HTML
 * @param resutladoConsulta
 * @param funcionJs
 * @param textoBoton
 * @param tieneCajaDeTexto
 * @param rango
 * @return
 */
    public static String convertirTabla(ResultSet resutladoConsulta, String funcionJs, String textoBoton,
	    boolean tieneCajaDeTexto, boolean rango, boolean tieneBoton)  {
		StringBuffer sourceTable = new StringBuffer();
		try {
		    sourceTable.append("<p><table border=1 class=\"table\">");
		     ResultSetMetaData rsmd = resutladoConsulta.getMetaData();
			 int columnCount = rsmd.getColumnCount();
			 // table header
			 sourceTable.append("<thead><tr>");
			 for (int i = 0; i < columnCount; i++) {
			     sourceTable.append("<th>" + rsmd.getColumnLabel(i + 1) + "</th>");
			   }
			 sourceTable.append("</tr></thead>");
			 sourceTable.append(agregarData(resutladoConsulta, columnCount, funcionJs, textoBoton, tieneCajaDeTexto, rango, tieneBoton));
			 sourceTable.append("</table></p>");
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		 return sourceTable.toString();
    }
    
    private static String agregarData(ResultSet resultadoConsulta,int columnCount,String funcionJs, String textoBoton,
	    boolean tieneCajaDeTexto, boolean rango, boolean tieneBoton)  throws SQLException {
	StringBuffer data = new StringBuffer();
	 while (resultadoConsulta.next()) { 
	     data.append("<tr>");
	     for (int i = 0; i < columnCount; i++) { //columnas de la consulta
		 data.append("<td>" + resultadoConsulta.getString(i + 1) + "</td>");
	     }
	  if(tieneCajaDeTexto)
	      data.append(agregarCajaDeTexto(rango, columnCount, resultadoConsulta));
	  if(tieneBoton)
	  data.append("<td><button id=\""+resultadoConsulta.getInt(1)+"\" " +
	  	 "onClick=\""+funcionJs+" \" class=\"btn btn-info \">"+textoBoton+"</button></td>"); 
	  }
	 return data.toString();
}

    /**
     * Agrega la caja de texto a una tabla
     * @param rango 
     * 	Indica si se va a agregar valor maximo a la caja
     * @param columnCount
     * 	El ultimo valor de la columna 
     * @param resultado
     * 	Resultado de la consulta
     * @return
     * 	Una nueva Tabla en codigo html
     * @throws SQLException 
     * 	Lanza esta excepcion si ocurre un error con la base de datos
     */
    private static String agregarCajaDeTexto(boolean rango, int columnCount, ResultSet resultado) throws SQLException {
	StringBuffer caja = new StringBuffer();
	caja.append("<td><input id=\"caja"+resultado.getInt(1)+"\" type=\"number\" min = \"0\" ");  //id de esta form id="cajaX"
	if (rango)
	    caja.append("max = \""+resultado.getInt(columnCount)+"\"");
	caja.append("></td>");
	return caja.toString();
    }

    /**
     * Convierte una consulta de dos columnas en un select HTML
     * @param resultados
     * @return
     */
    public static String convertirSelector(ResultSet resultados, String evento) {
	StringBuffer select = new StringBuffer(); 
	select.append("<select id=\"selector\" class=\"form-control\" onchange = \""+evento+"\">"); 
	try {
	    while (resultados.next()) {
	       select.append("<option value=\""+resultados.getInt(1)+"\">"+resultados.getString(2)+"</option> ");
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	select.append("</select>"); 
	return select.toString();
    }

}
