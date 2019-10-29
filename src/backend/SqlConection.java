package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SqlConection {
    
    private static Connection conexion;
    
    private final static String SQL_USER="admin";
    private final static String PASSWORD="admin";
    private final static String SQL_PORT="jdbc:mysql://localhost:3306/";
    private final static String DATABASE_NAME="Hospital";
    
    public SqlConection() {
	try {
	    if(conexion==null) {
		Class.forName("org.mariadb.jdbc.Driver"); 
		conexion = DriverManager.getConnection(SQL_PORT+DATABASE_NAME, SQL_USER, PASSWORD);
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	}
    }
    
    public Connection getConexion() {
	return conexion;
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
    
}
