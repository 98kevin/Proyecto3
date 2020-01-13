package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kevin.modelos.Area;
import com.kevin.modelos.Habitacion;
import com.kevin.modelos.Main;
import com.kevin.servicio.DBConnection;
import com.kevin.servicio.GeneradorHTML;


public class ManejadorHabitacion {
    
    public static final String MENSAJE_ERROR = "<div class=\"alert alert-danger\" role=\"alert\">" + 
    	"  No se puede ejecutar la operacion. La habitacion esta ocupada " + 
    	"</div>"; 
    
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
    
    
    public String consultarHabitaciones() {
	String sql = "SELECT Habitacion.id_habitacion, IF((SELECT COUNT(Internado.id_habitacion) FROM Internado " + 
		" WHERE Internado.id_habitacion = Habitacion.id_habitacion AND Internado.fin IS NULL ) > 0 , 'Ocupada', '-') AS 'Ocupada'," + 
		" IFNULL(Persona.nombre, '--') AS 'Paciente',  IF(esta_habilitada, 'Habilitada', '-') AS 'Estado', precio_de_mantenimiento AS 'Costo de Mantenimiento'" + 
		" FROM Habitacion" + 
		" LEFT JOIN Internado ON Habitacion.id_habitacion = Internado.id_habitacion" + 
		" LEFT JOIN Paciente ON Internado.id_paciente = Paciente.id_paciente" + 
		" LEFT JOIN Persona ON Paciente.cui = Persona.cui" + 
		" WHERE Internado.fin is null " ; 
	ResultSet registros = null; 
	try {
		PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql); 
	    registros = stm.executeQuery(); 
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return GeneradorHTML.convertirTabla(registros, "seleccionarHabitacion(this)", "Editar", false, false, true);
    }
    
    
    public String consultarHabitacion(int codigo) {
	String respuesta = null; 
	String sql = "SELECT Habitacion.id_habitacion, IF((SELECT COUNT(Internado.id_habitacion) FROM Internado " + 
		" WHERE Internado.id_habitacion = Habitacion.id_habitacion AND Internado.fin IS NULL ) > 0 , 'true', 'false') AS 'Ocupada'," + 
		" IFNULL(Persona.nombre, '--') AS 'Paciente',  esta_habilitada, precio_de_mantenimiento AS 'Costo de Mantenimiento'" + 
		" FROM Habitacion" + 
		" LEFT JOIN Internado ON Habitacion.id_habitacion = Internado.id_habitacion" + 
		" LEFT JOIN Paciente ON Internado.id_paciente = Paciente.id_paciente" + 
		" LEFT JOIN Persona ON Paciente.cui = Persona.cui" + 
		" WHERE Internado.fin is null "
		+ " AND Habitacion.id_habitacion = ? " ; 
	ResultSet registros = null; 
	try {
		PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql); 
		stm.setInt(1, codigo);
		registros = stm.executeQuery(); 
		registros.next(); 
		respuesta = "<div>" + 
			"    <h3>Edicion de Habitacion</h3>" + 
			"    <div class=\"form-group row\">" + 
			"      <label class=\"col-sm-2 col-form-label\">Codigo </label>" + 
			"      <div class=\"col-sm-10\">" + 
			"        <input type=\"nubmer\" class=\"form-control\" id=\"codigoHabitacion\" readonly value=\""+registros.getInt(1)+"\" >" + 
			"      </div>" + 
			"    </div>" + 
			"" + 
			"    <div class=\"form-group row\">" + 
			"      <label class=\"col-sm-2 col-form-label\">Esta Ocupada</label>" + 
			"      <div class=\"form-check\">" + 
			"        <input class=\"form-check-input\" type=\"checkbox\" id=\"checkOcupada\" disabled checked=\""+registros.getBoolean(2)+"\">" + 
			"      </div>" + 
			"    </div>" + 
			"" + 
			"    <div class=\"form-group row\">" + 
			"      <label class=\"col-sm-2 col-form-label\">Codigo </label>" + 
			"      <div class=\"col-sm-10\">" + 
			"        <input type=\"text\" class=\"form-control\" id=\"paciente\" readonly value=\""+registros.getString(3)+"\" >" + 
			"      </div>" + 
			"    </div>" + 
			"" + 
			"    <div class=\"form-group row\">" + 
			"      <label class=\"col-sm-2 col-form-label\">Esta Habilitada</label>" + 
			"      <div class=\"form-check\">" + 
			"        <input class=\"form-check-input\" type=\"checkbox\" checked=\""+registros.getBoolean(4)+"\" id=\"checkHabilitada\">" + 
			"      </div>" + 
			"    </div>" + 
			"" + 
			"    <div class=\"form-group row\">" + 
			"      <label class=\"col-sm-2 col-form-label\">Costo de mantenimiento</label>" + 
			"      <div class=\"col-sm-10\">" + 
			"        <input type=\"number\" step=\"0.01\" class=\"form-control\" id=\"costoDeMantenimiento\" value=\""+registros.getDouble(5)+"\">" + 
			"      </div>" + 
			"    </div>" + 
			"</div>" + 
			"" + 
			"<div class=\"button-group\">" + 
			"  <input class=\"btn btn-primary\"  type=\"button\" id=\"\"  value=\"Guardar Cambios\"  onclick=\"actualizarHabitacion()\">" + 
			"  <input class=\"btn btn-danger\"  type=\"button\" id=\"\"  value=\"Eliminar\"  onclick=\"eliminarHabitacion()\">" + 
			"</div>"; 
	} catch (SQLException e) {
	    e.printStackTrace();
	    respuesta = "Opps! Ocurrio un error. Codigo " + e.getErrorCode(); 
	}
	return respuesta; 
    }
    
    public String eliminarHabitacion(int codigo ) {
	String respuesta = null;
	String sql = "DELETE FROM Habitacion WHERE id_habitacion = ?  "; 
	try {
		PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql); 
		stm.setInt(1, codigo);
		stm.execute(); 
		respuesta = Main.MENSAJE_EXITO;
	} catch (SQLException e) {
	    e.printStackTrace();
	    if (e.getErrorCode() == 1451) 
		respuesta = MENSAJE_ERROR;
	    else 
		respuesta = "Ha ocurrido un error inesperado. Codigo " + e.getErrorCode();  
	}
	return respuesta;
    }
    
    public String actualizarHabitacion(int codigo, boolean habilitada, double precio_mantenimiento ) {
	String respuesta = null;
	String sql = "UPDATE Habitacion SET esta_habilitada = ?, precio_de_mantenimiento = ? WHERE id_habitacion = ? "; 
	try {
	    if (actualizable(codigo)) {
		PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql); 
		stm.setBoolean(1, habilitada);
		stm.setDouble(2, precio_mantenimiento);
		stm.setInt(3, codigo);
		stm.execute(); 
		respuesta = Main.MENSAJE_EXITO;
	    } else {
	    respuesta = MENSAJE_ERROR;
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	    respuesta = "Ha ocurrido un error inesperado. Codigo " + e.getErrorCode();  
	}
	return respuesta;
    }
    
    public boolean actualizable(int codigo) throws SQLException {
	String sql = " SELECT COUNT(Internado.id_habitacion) FROM Internado " + 
		" INNER JOIN Habitacion ON  Internado.id_habitacion = Habitacion.id_habitacion " + 
		" WHERE Internado.fin IS NULL " + 
		" AND Habitacion.id_habitacion = ? ";
	PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql); 
	stm.setInt(1, codigo);
	ResultSet r = stm.executeQuery(); 
	r.next(); 
	return (r.getInt(1) > 0 ) ? false : true; 
    }
}
