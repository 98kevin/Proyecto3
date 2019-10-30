package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.kevin.modelos.Administrador;
import com.kevin.servicio.DBConnection;

public class ManejadorAdministrador extends DBConnection{

    
    public void registrarAdministador(Administrador administrador) {
	
	String sqlPersona = "INSERT INTO Persona (cui, nombre, direccion) VALUES (?,?,?)";
	
	String sqlPeriodoLaboral = "INSERT INTO Contrato (salario, fecha_inicial,id_empleado) VALUES (?,?,?)";
	
	String sqlEmpleado = "INSERT INTO Empleado(porcentaje_igss, porcentajeIrtra,fecha_de_vacaciones, cui_persona, id_area)  VALUES (?,?,?,?,?)";
	
	try {
	    Connection conexion = conexion();
	    PreparedStatement stmPersona = conexion.prepareStatement(sqlPersona);
	    
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
