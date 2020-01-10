package com.kevin.manejadores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kevin.servicio.DBConnection;
import com.kevin.servicio.GeneradorHTML;

public class ManejadorRegistrosMonetarios {
    
    public String consultarCuentaCliente(String cuiPaciente) {
	StringBuffer detalleDeCuenta = new StringBuffer(); 
	try {
	    detalleDeCuenta.append(consultarDetalleCuenta(cuiPaciente));
	    detalleDeCuenta.append(consultarTotalCuenta(cuiPaciente)); 
	} catch (SQLException e) {
	    e.printStackTrace();
	} 
	return detalleDeCuenta.toString();
    }
    
    private Object consultarTotalCuenta(String cuiPaciente) throws SQLException {
	String sql = "SELECT SUM(Cuenta.Monto) AS 'Total de Gasto' FROM Cuenta " + 
		"INNER JOIN Paciente ON Paciente.id_paciente = Cuenta.id_paciente " + 
		"INNER JOIN Persona ON Persona.cui = Paciente.cui " + 
		"WHERE Persona.cui = ? " + 
		"AND Cuenta.pagado = false"; 
	PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql); 
	stm.setString(1, cuiPaciente);
	ResultSet result = stm.executeQuery(); 
	return GeneradorHTML.convertirTabla(result, "", "", false, false, false); 
    }


    private String consultarDetalleCuenta(String cuiCliente) throws SQLException {
	String sql = "SELECT Cuenta.id_cuenta AS 'Codigo de Registro', Cuenta.detalle AS 'Detalle de gasto', Cuenta.monto AS 'Monto Individual', Cuenta.fecha AS 'Fecha de ejecucion' " + 
		"FROM Cuenta " + 
		"INNER JOIN Paciente ON Paciente.id_paciente = Cuenta.id_paciente " + 
		"INNER JOIN Persona ON Persona.cui = Paciente.cui " + 
		"WHERE Persona.cui = ? " + 
		"AND Cuenta.pagado = false " ; 
	PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql); 
	stm.setString(1, cuiCliente);
	ResultSet result = stm.executeQuery(); 
	return GeneradorHTML.convertirTabla(result, "", "", false, false,false); 
    }
    
    
    public String pagarCuentaCliente(String cuiCliente) {
	PreparedStatement stm = null ; 
	StringBuffer response = new StringBuffer(); 
	try {
	    String sql = "UPDATE Cuenta, Paciente, Persona" + 
	    	"	SET Cuenta.pagado = true" + 
	    	"	WHERE Cuenta.id_paciente = Paciente.id_paciente " + 
	    	"	AND Persona.cui = Paciente.cui " + 
	    	"	AND Persona.cui = ?";
	    stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
	    stm.setString(1, cuiCliente);
	    stm.execute(); 
	    response.append("Registro exitoso"); 
	} catch (SQLException e) {
	    e.printStackTrace();
	    response.append("Ocurrio un error en el registro. Codigo de error ");
	    response.append(e.getErrorCode());
	}
	return response.toString(); 
    }
}
