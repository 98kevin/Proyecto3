package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import com.kevin.modelos.Medicamento;
import com.kevin.servicio.DBConnection;
import com.kevin.servicio.GeneradorHTML;

public class ManejadorRegistrosMonetarios {

    /**
     * Registra la transaccion de medicamentos, ya sea compra o venta. 
     * @param idUsuario
     * @param medicamento
     * @param tipoDeOperacion
     * @param cantidad
     * @param areaFarmacia
     * @param conexion
     * @throws SQLException
     */
    public int registroMonetarioMedicamento( int idUsuario, Medicamento medicamento, 
	    boolean tipoDeOperacion, int cantidad, int areaFarmacia, Connection conexion) throws SQLException {
	int registro = 0;
	Date fechaActual = new Date(new java.util.Date(Calendar.getInstance().getTimeInMillis()).getTime());
	String descripcion = getTextoOperacion(tipoDeOperacion)+" de "+ cantidad+" unidades de "+ medicamento.getNombre(); 
	double monto = medicamento.getCostoCompra() * cantidad;
	String sql = "INSERT INTO Registro_Monetario (descripcion,monto,fecha,tipo,id_area) VALUES (?,?,?,?,?)";
	    PreparedStatement stm = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    stm.setString(1, descripcion);
	    stm.setDouble(2, monto);
	    stm.setDate(3, fechaActual);
	    stm.setBoolean(4, tipoDeOperacion);
	    stm.setInt(5, areaFarmacia);
	    stm.execute();
	    ResultSet resultado = stm.getGeneratedKeys();
	    if(resultado.next())
		registro = resultado.getInt(1);
	    return registro;
    }
    

    /**
     * Devuelve el texto indicado segun el tipo de operacion. 
     * @param tipoDeOperacion
     * @return
     */
    private String getTextoOperacion(boolean tipoDeOperacion) {
	return (tipoDeOperacion==false) ? "Compra": "Venta";
    }

    
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
