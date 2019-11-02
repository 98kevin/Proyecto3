package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.kevin.modelos.Administrador;
import com.kevin.servicio.DBConnection;

public class ManejadorAdministrador extends DBConnection{

    /**
     * Registra un nuevo administrador en la base de datos
     * @param administrador
     */
    public String registrarAdministador(Administrador administrador) {
	Connection conexion = conexion(); 
	String msj= "";
	try {
	    conexion.setAutoCommit(false);
	    registrarPersona(administrador,conexion); 
	    registrarEmpleado(administrador, conexion);
	    registrarCredenciales(administrador, conexion);
	    conexion.commit();
	    registrarPeriodo(administrador, conexion);
	    conexion.commit();
	    msj += "Creacion de administrador exitosa"; 
	} catch (SQLException e) {
	    e.printStackTrace();
	    msj += "Error de intregridad de base de datos.  \n codigo de error" + e.getErrorCode();
	    System.out.println(msj);
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
	return msj;
    }
    
    private void registrarCredenciales(Administrador administrador, Connection conexion) throws SQLException {
	String sqlCredenciales = "INSERT INTO Credenciales (correo_electronico, password,id_empleado, id_area) VALUES (?,?,?,?)";
	PreparedStatement stmCredenciales;
	stmCredenciales = conexion.prepareStatement(sqlCredenciales);
	stmCredenciales.setString(1, administrador.getEmail());
	stmCredenciales.setString(2, administrador.getPassword());
		//el utlimo valor en la tabla empleado
	stmCredenciales.setInt(3, maximo("Empleado", "id_empleado"));
	stmCredenciales.setInt(4, administrador.getAreaDeTrabajo());
	stmCredenciales.execute();
    }

    /**enviarEmpleado
     * Registro de la pers		// TODO Auto-generated catch blockona del administrador
     * @param administrador
     * @throws SQLException 
     */
    public void registrarPersona(Administrador administrador, Connection conexion) throws SQLException {
	String sqlPersona = "INSERT INTO Persona (cui, nombre, direccion) VALUES (?,?,?)";
	    PreparedStatement stmPersona = conexion.prepareStatement(sqlPersona);
	    stmPersona.setString(1, administrador.getCui());
	    stmPersona.setString(2, administrador.getNombre());
	    stmPersona.setString(3, administrador.getDireccion());
	    stmPersona.execute();
    }
    
    /**
     * registro del periodo del administrador
     * @param administrador
     * @throws SQLException 
     */
    public void registrarPeriodo(Administrador administrador, Connection conexion) throws SQLException {
	String sqlPeriodoLaboral = "INSERT INTO Contrato (salario, fecha_inicial,id_empleado) VALUES (?,?,?)";
	PreparedStatement stmPeriodo;
	    	stmPeriodo = conexion.prepareStatement(sqlPeriodoLaboral);
		stmPeriodo.setDouble(1, administrador.getSalario());
		stmPeriodo.setDate(2, administrador.getFechaDeInicio());
		//el utlimo valor en la tabla empleado
		stmPeriodo.setInt(3, maximo("Empleado", "id_empleado"));
		stmPeriodo.execute();
    }
    
    /**
     * Registro del empleado Administrador
     * @throws SQLException 
     */
    public void registrarEmpleado(Administrador administrador, Connection conexion) throws SQLException {
	String sqlEmpleado = "INSERT INTO Empleado (porcentaje_igss, porcentaje_irtra,fecha_de_vacaciones, cui_persona, id_area)  VALUES (?,?,?,?,?)";
	PreparedStatement stmEmpleado;
	    stmEmpleado = conexion.prepareStatement(sqlEmpleado);
	    stmEmpleado.setInt(1, administrador.getIggs());
	    stmEmpleado.setInt(2, administrador.getIrtra());
	    stmEmpleado.setDate(3, administrador.getFechaDeVacaciones());
	    stmEmpleado.setString(4, administrador.getCui());
	    stmEmpleado.setInt(5, administrador.getAreaDeTrabajo());
	    stmEmpleado.execute();
    }
    
    
}
