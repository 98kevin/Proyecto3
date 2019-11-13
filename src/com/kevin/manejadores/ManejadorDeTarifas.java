package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.kevin.modelos.Tarifa;
import com.kevin.servicio.DBConnection;

public class ManejadorDeTarifas {
        /**
         * Registra una nueva tarifa en la base de datos 
         * @param tarifa
         * @throws SQLException 
         */
	    public void registrarTarifa(Tarifa tarifa) throws SQLException {
		Connection conexion = DBConnection.getInstanceConnection().getConexion();
		String sql = "INSERT INTO TarifaDeEspecialista (descripcion, costo_al_hospital, tarifa_de_especialista, precio_al_cliente) values (?,?,?,?);";
		    PreparedStatement stm = conexion.prepareStatement(sql);
		    stm.setString(1, tarifa.getDescripcion());
		    stm.setDouble(2, tarifa.getCostoHospital());
		    stm.setDouble(3, tarifa.getCostoEspecialista());
		    stm.setDouble(4, tarifa.getPrecioCliente());
		    stm.execute();
    }
}
