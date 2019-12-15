package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kevin.exceptions.DataBaseException;
import com.kevin.modelos.Paciente;
import com.kevin.servicio.DBConnection;
import com.kevin.servicio.GeneradorHTML;

public class ManejadorPaciente {

    public String registrarPaciente(Paciente paciente) {
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	StringBuffer mensaje= new StringBuffer();
	try {
	    conexion.setAutoCommit(false);
	    ManejadorPersona persona = new ManejadorPersona(); 
	    persona.registrarPersona(paciente,conexion); 
	    registrarPaciente(paciente, conexion); 
	    conexion.commit();
	   mensaje.append("Creacion de paciente exitosa realizada con exito"); 
	} catch (SQLException e) {
	    e.printStackTrace();
	    mensaje.append("Error de intregridad de base de datos.  \n codigo de error " + e.getErrorCode());
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
	return mensaje.toString();
    }
    
    
    private void registrarPaciente(Paciente paciente, Connection conexion) throws SQLException {
	String sql = "INSERT INTO Paciente (nit, cui) VALUES (?,?)";
	PreparedStatement stm = conexion.prepareStatement(sql);
	stm.setString(1, paciente.getNit());
	stm.setString(2, paciente.getCui());
	stm.execute();
    }
    
    
    public String pacientesRegistrados() {
	String sql = "SELECT Paciente.id_paciente, Persona.nombre, Persona.direccion, Persona.cui "+
		" FROM Persona "+
		" INNER JOIN Paciente ON Persona.cui=Paciente.cui ";
	PreparedStatement stm = null ;
	ResultSet pacientes = null ;
	try {
	    stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
	    pacientes = stm.executeQuery();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return GeneradorHTML.convertirTabla(pacientes, "seleccionarPaciente(this)", "Seleccionar Paciente", true ,true ); 
    }


    public boolean isInternadoActualmente(int paciente) {
	boolean resultado= false;
	String sql = "SELECT * FROM Internado WHERE id_paciente=? AND fin IS NULL";
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	PreparedStatement stm=null;
	try { 
	    stm = conexion.prepareStatement(sql);
	    stm.setInt(1, paciente);
	    ResultSet resultados = stm.executeQuery();	
	    resultado =  (resultados.next()) ? true : false;
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return resultado;
    }
    
    public String consultarMedicamentosDePacientes(int codigoEnfermera) {
	 ResultSet resultados=null;
	String sql = " SELECT Medicamento.id_medicamento, Medicamento.nombre, Persona.nombre AS 'Paciente', " +
		" Internado_tiene_Medicamento.cantidad AS 'Cantidad Faltante' FROM Medicamento  " +
		" INNER JOIN Internado_tiene_Medicamento ON Internado_tiene_Medicamento.id_medicamento = Medicamento.id_medicamento "+
		" INNER JOIN Internado ON Internado_tiene_Medicamento.id_internado = Internado.id_internado"+
		" INNER JOIN Internado_tiene_Empleado ON Internado_tiene_Empleado.id_internado = Internado.id_internado"+
		" INNER JOIN Empleado ON Empleado.id_empleado = Internado_tiene_Empleado.id_empleado"+
		" INNER JOIN Paciente ON Paciente.id_paciente = Internado.id_paciente "+
		" INNER JOIN Persona ON Persona.cui = Paciente.cui "+
		" WHERE Internado.fin IS NULL AND Empleado.id_empleado = ? ";
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	PreparedStatement stm=null;
	try { 
	    stm = conexion.prepareStatement(sql);
	    stm.setInt(1, codigoEnfermera);
	    resultados = stm.executeQuery();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return GeneradorHTML.convertirTabla(resultados, "suministrarMedicamento(this)", " Suministrar ", true, true);
    }
    
    public String consultarPacinetesDeEnfermera(int codigoEnfermera) {
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	ResultSet resultado = null; 
	PreparedStatement stm;
	String sql = "Select Persona.cui, Persona.nombre " +
		" FROM Persona  "+
		" INNER JOIN Paciente ON Paciente.cui = Persona.cui " +
		" INNER JOIN Internado ON Internado.id_paciente =Paciente.id_paciente "+
		" INNER JOIN Internado_tiene_Empleado ON Internado_tiene_Empleado.id_internado = Internado.id_internado  "+
		" INNER JOIN Empleado ON Empleado.id_empleado = Internado_tiene_Empleado.id_empleado "+
		" WHERE Internado.fin IS NULL AND Empleado.id_empleado =  ?";
	try {
	    stm = conexion.prepareStatement(sql);
	    stm.setInt(1, codigoEnfermera);
	    resultado = stm.executeQuery(); 
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return GeneradorHTML.convertirSelector(resultado, "seleccionarPaciente()");
    }
   
    public int consultarCodigoDeInternado(String cui) throws SQLException, DataBaseException{
	String sql  = " SELECT Internado_tiene_Medicamento.id_internado as 'Codigo de Internado' " + 
		" FROM Internado_tiene_Medicamento " + 
		" INNER JOIN Internado ON Internado_tiene_Medicamento.id_internado = Internado.id_internado" + 
		" INNER JOIN Paciente ON Internado.id_paciente = Paciente.id_paciente " + 
		" INNER JOIN Persona ON Paciente.cui = Persona.cui " + 
		" WHERE Persona.cui =  ?"  + 
		" AND Internado.fin is null " + 
		" GROUP BY Internado.id_internado";
	PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql); 
	stm.setString(1, cui);
	ResultSet resultado = stm.executeQuery();
	if (resultado.next()) 
	    return resultado.getInt(1);
	else
	    throw new DataBaseException("No existe el internado con el cui: " + cui);
    }
    
    
}