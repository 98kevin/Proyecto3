package backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Clase encargada del manejo de Modulos
 * @author kevin
 *
 */
public class Modulo {
    private String nombre; 
    
    /**
     * Crea un nuevo modulo vacio
     */
    public Modulo() {
	super();
    }
    
    /**
     * Crea un nuevo modulo con nombre
     * @param nombre El nombre del nuevo modulo
     */
    public Modulo(String nombre) {
	this.nombre=nombre;
    }
    
    /**
     * Registra el modulo en la base de datos
     */
    public void registrar() {
	Connection conexion = new SqlConection().getConexion();
	String sql = "INSERT INTO Modulo (nombre) values (?);";
	try {
	    PreparedStatement stm = conexion.prepareStatement(sql);
	    stm.setString(1, this.nombre);
	    stm.execute();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }
    
    public String [][] modulosRegistrados() {
	String [][] resultados= null;
	SqlConection sqlConn = new SqlConection(); 
	Connection conexion = sqlConn.getConexion();
	String sql = "SELECT * FROM Modulo";
	String sqlCount = "SELECT COUNT(*) FROM Modulo";
        	try {
            	    PreparedStatement stm = conexion.prepareStatement(sql);
            	    ResultSet modulos = stm.executeQuery();
            	    int registros= sqlConn.contadorDeRegistros("SELECT COUNT(*) FROM Modulo");
            	    resultados = new String[registros][2];
            	    for(int i=0; i<registros; i++) {
            		modulos.next();
            		resultados[i][0]=modulos.getString(1);
            		resultados[i][1]=String.valueOf(modulos.getInt(2));
            	    }
        	} catch (SQLException e) {
        	    e.printStackTrace();
        	}
	return resultados;
    }


	
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
