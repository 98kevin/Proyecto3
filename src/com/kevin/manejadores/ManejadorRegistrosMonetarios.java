package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import com.kevin.modelos.Medicamento;

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
    public int registrarTransaccionMedicamento( int idUsuario, Medicamento medicamento, 
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

}
