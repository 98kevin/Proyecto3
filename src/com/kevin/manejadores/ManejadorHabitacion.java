package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kevin.modelos.Area;
import com.kevin.modelos.Habitacion;
import com.kevin.servicio.DBConnection;

public class ManejadorHabitacion {
    
    public String registrarHabitacion(Habitacion habitacion) {
	StringBuffer mensaje = new StringBuffer(); 
	Connection conexion =DBConnection.getInstanceConnection().getConexion();
	String sql  = "INSERT INTO Habitacion (precio_de_mantenimiento) values (?)"; 
	try {
	    PreparedStatement stm = conexion.prepareStatement(sql);
	    stm.setDouble(1, habitacion.getMantenimiento() );
	    stm.execute();
	    mensaje.append("Habitacion creada con exito");
	} catch (SQLException e) {
	    e.printStackTrace();
	    mensaje.append("Error en el registro en la base de datos. Codigo de error: " +e.getErrorCode());
	} 
	return mensaje.toString();
    }

    public String consultarHabitacionesLibres()  {
	StringBuffer  resultados = new StringBuffer();
	//habitaciones libres y disponibles
	String sql = "SELECT id_habitacion FROM Habitacion WHERE esta_ocupada=0 & esta_habilitada=1";
	Connection conexion  = DBConnection.getInstanceConnection().getConexion();
	PreparedStatement stm;
	try {
	    stm = conexion.prepareStatement(sql);
	    ResultSet habitaciones = stm.executeQuery();
	    	resultados.append("<h3>Seleccion de habitacion</h3>");
		resultados.append("<select id=\"habitaciones\" class=\"form-control\">");
		while (habitaciones.next()) {
		    resultados.append("<option value=\""+habitaciones.getInt(1)+"\">"+habitaciones.getInt(1)+"</option>");
		}
		resultados.append("</select>");
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return resultados.toString();
    }

    
    public void registrarCambioEnHabitacion(int codigo, boolean ocupada) throws SQLException {
	String sql = "UPDATE Habitacion SET esta_ocupada=? WHERE id_habitacion=?";
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	PreparedStatement stm = conexion.prepareStatement(sql);
	stm.setBoolean(1, ocupada);
	stm.setInt(2, codigo);
	stm.execute();
    }
    
    public PreparedStatement registrarCostosDeHabitacion(String cuiPaciente, Date fecha) throws SQLException {
	PreparedStatement stm = null; 
	String sql =" INSERT INTO Costos_Habitacion (descripcion, monto, fecha, tipo, id_area) VALUES ( "+
		" 'Costo de mantenimiento de la habitacion', "+
		" (SELECT Habitacion.precio_de_mantenimiento FROM Habitacion WHERE id_habitacion = "+
		"	(SELECT Habitacion.id_habitacion FROM Habitacion "+
		"	INNER JOIN Internado ON Internado.id_habitacion = Habitacion.id_habitacion "+ 
		"	INNER JOIN Paciente ON Internado.id_paciente = Paciente.id_paciente "+
		"	WHERE Paciente.cui = '?')) * 	(SELECT TIMESTAMPDIFF(DAY, Internado.inicio, ?) " + 
		"									FROM Internado " + 
		"									INNER JOIN Paciente ON Internado.id_paciente = Paciente.id_paciente " + 
		"									WHERE Paciente.cui = ? ), "+
	" ?, "+
	" ?)";  //area de medicos
	stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
	stm.setString(1, cuiPaciente);
	stm.setDate(2, fecha);
	stm.setString(3, cuiPaciente);
	stm.setDate(4, fecha);
	stm.setInt(5, Area.MEDICOS);
	return stm;
    }
    
    public PreparedStatement registrarSalidaPaciente(String cuiPaciente) throws SQLException {
	String sql = " UPDATE Habitacion SET esta_ocupada = 0 " + 
		" WHERE Habitacion.id_habitacion = " + 
		"	(SELECT Habitacion.id_habitacion " + 
		"    FROM Habitacion " + 
		"    INNER JOIN Internado ON Habitacion.id_habitacion = Internado.id_habitacion " + 
		"    INNER JOIN Paciente ON  Internado.id_paciente = Paciente.id_paciente " + 
		"    WHERE Paciente.cui = ? )";
	PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql); 
	stm.setString(1, cuiPaciente);
	return stm;
    }
}
