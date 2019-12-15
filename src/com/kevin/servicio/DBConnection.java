package com.kevin.servicio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DBConnection {    
    
    private final static String SQL_USER="admin";
    private final static String PASSWORD="admin";
    private final static String SQL_PORT="jdbc:mysql://localhost:3306/";
    private final static String DATABASE_NAME="Hospital";
    private static DBConnection dbConnection;
    
    private Connection conexion;
    
    /**
     * @return the conexion
     */
    public Connection getConexion() {
        return conexion;
    }


    // El constructor es privado, no permite que se genere un constructor por defecto.
    private DBConnection() {
	try {
		Class.forName("org.mariadb.jdbc.Driver"); 
		this.conexion = DriverManager.getConnection(SQL_PORT+DATABASE_NAME, SQL_USER, PASSWORD);
	} catch (SQLException | ClassNotFoundException e) {
	    e.printStackTrace();
    }
    }

    public static DBConnection getInstanceConnection() {
        if (dbConnection == null)
            dbConnection = new DBConnection();
        return dbConnection;
    }
    
    /**
     * Cuenta el total de registros de una tabla
     * @param sql
     * @return un entero con el numero de registros de la tabla
     */
    public int contadorDeRegistros(String sql) {
	int registros = 0 ; 
	try {
	    PreparedStatement stm = conexion.prepareStatement(sql);
	    ResultSet contador = stm.executeQuery();
	    if(contador.next()) {
		registros= contador.getInt(1);
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return registros;
    }
    
    
    /**
     * Transaccion en la base de datos. 
     * @param stm
     */
    public void transaccion(ArrayList<PreparedStatement> stm) {
	Connection conexion = getConexion(); 
	try {
		conexion.setAutoCommit(false);
		for (PreparedStatement preparedSteament : stm) {
		    preparedSteament.execute();
		    conexion.commit();
		}
	} catch (SQLException e) {
	    e.printStackTrace();
	    try {
		conexion.rollback();
	    } catch (SQLException e1) {
		e1.printStackTrace();
	    }
	} finally {
	    try {
		conexion.setAutoCommit(true);
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}

	
	
    }
    
}
