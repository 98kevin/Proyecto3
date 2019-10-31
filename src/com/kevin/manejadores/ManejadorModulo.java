package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kevin.modelos.Modulo;
import com.kevin.servicio.DBConnection;

public class ManejadorModulo extends DBConnection{
    /**
     * Registra el modulo en la base de datos
     */
    public void registrarModulo(Modulo modulo) {
	Connection conexion = conexion();
	String sql = "INSERT INTO Modulo (nombre) values (?);";
	try {
	    PreparedStatement stm = conexion.prepareStatement(sql);
	    stm.setString(1, modulo.getNombre());
	    stm.execute();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }
    
    public String [][] modulosRegistrados() {
	String [][] resultados= null;
	DBConnection sqlConn = new DBConnection(); 
	Connection conexion =conexion(); 
	String sql = "SELECT * FROM Modulo";
        	try {
            	    PreparedStatement stm = conexion.prepareStatement(sql);
            	    ResultSet modulos = stm.executeQuery();
            	    int registros= sqlConn.contadorDeRegistros("SELECT COUNT(*) FROM Modulo");
            	    resultados = new String[registros][2];
            	    for(int i=0; i<registros; i++) {
            		modulos.next();
            		resultados[i][0]=modulos.getString(1);
            		resultados[i][1]=String.valueOf(modulos.getString(2));
            	    }
        	} catch (SQLException e) {
        	    e.printStackTrace();
        	}
	return resultados;
    }
}
