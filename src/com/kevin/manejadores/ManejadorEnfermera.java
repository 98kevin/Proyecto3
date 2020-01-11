package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.kevin.exceptions.DataBaseException;
import com.kevin.modelos.Area;
import com.kevin.modelos.Empleado;
import com.kevin.modelos.Medicamento;
import com.kevin.servicio.DBConnection;
import com.kevin.servicio.GeneradorHTML;

public class ManejadorEnfermera {

    
    /**
     * Consulta las enfermeras disponibles
     * @return
     * @throws SQLException 
     */
    public String consultarEnfermeras()  {
    String sql = "SELECT e.id_empleado, p.nombre FROM Persona p INNER JOIN Empleado e ON p.cui=e.cui_persona WHERE e.id_area=?";
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	ResultSet enfermeras = null;
	try {
		PreparedStatement stm = conexion.prepareStatement(sql); 
		stm.setInt(1, Area.ENFERMEROS);
		enfermeras = stm.executeQuery(sql);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return GeneradorHTML.convertirTabla(enfermeras, "agregarEnfermera(this)", "Asingar", false, false, true); 
    }
    
    
    /**
     * 
     * @param empleado
     * @return
     */
    public String consultarMedicamentosDePacientes(int empleado) {
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	PreparedStatement stm;
	ResultSet resultado =null;
	String sql = "SELECT Persona.nombre, Medicamento.id_medicamento, Medicamento.nombre, Internado_tiene_Medicamento.cantidad " + 
		"FROM Medicamento, Internado_tiene_Medicamento " + 
		"INNER JOIN Internado ON Internado.id_internado = Internado_tiene_Medicamento.id_internado " + 
		"INNER JOIN Internado_tiene_Empleado ON Internado_tiene_Empleado.id_internado = Internado.id_internado " + 
		"INNER JOIN Empleado ON Internado_tiene_Empleado.id_empleado = Empleado.id_empleado " + 
		"INNER JOIN Paciente ON Internado.id_paciente = Paciente.id_paciente " + 
		"INNER JOIN Persona ON Paciente.cui= Persona.cui " + 
		"WHERE Internado_tiene_Medicamento.id_medicamento= Medicamento.id_medicamento AND Empleado.id_empleado=?";
	try {
	     stm = conexion.prepareStatement(sql);
	     stm.setInt(1, empleado);
	     resultado = stm.executeQuery();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return GeneradorHTML.convertirTabla(resultado, "suministrarMedicamento(this)", "Suministrar", true, true, true);
    }

    /**
     * Suministra medicamento a un paciente
     * @param request
     */ 
    public String suministrarMedicamento(int idMedicamento, int cantidad, String cuiPaciente, Date fecha, int idEmpleado) { 
	StringBuffer respuesta = new StringBuffer(); 
	ArrayList<PreparedStatement> preparedStatements = new ArrayList<PreparedStatement>();
	try {
	    ManejadorFarmacia manejador = new ManejadorFarmacia(); 	
	    ManejadorPaciente manejadorPaciente = new ManejadorPaciente();
	    Medicamento medicamento = manejador.leerMedicamento(idMedicamento);
	    int idInternado  = manejadorPaciente.consultarCodigoDeInternado(cuiPaciente);
	    preparedStatements.add(suministrarMedicamento(idInternado, medicamento.getCodigo(), cantidad)); 
	    preparedStatements.add(sumarCuentaCliente(getIdPaciente(cuiPaciente), medicamento, cantidad, fecha, consultarArea(idEmpleado)));
	    preparedStatements.add(restarCantidad(medicamento, cantidad));
	    manejador.transaccionMedicamento(medicamento, fecha, cantidad, ManejadorFarmacia.VENTA, false, new Empleado(idEmpleado), cuiPaciente);
	    DBConnection.getInstanceConnection().transaccion(preparedStatements);
	    respuesta.append("Actualizacion realizada correctamente ");
	} catch (SQLException | DataBaseException e ) {
	    respuesta.append("Error durante la actualizacion. ");
	    e.printStackTrace();
	} 
	return respuesta.toString(); 
    }
    
    
    private int getIdPaciente(String cui) throws SQLException {
	String consulta  = "SELECT id_paciente FROM Paciente WHERE cui= ?";
	PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(consulta);
	stm.setString(1, cui);
	ResultSet resultado = stm.executeQuery();
	if(resultado.next())
	    return resultado.getInt(1);
	else
	    return 0;
    }

    public PreparedStatement sumarCuentaCliente(int paciente, Medicamento medicamento, int cantidad, Date fecha, int area) 
	    throws SQLException, DataBaseException {
	    String sql = "INSERT INTO Cuenta (id_paciente, detalle, monto, pagado, fecha, id_area) VALUES (?,?,?,?,?,?)"; 
	    PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);  
	    stm.setInt(1, paciente); 
	    stm.setString(2, getDescripcionSuministroMedicamento(medicamento)); 
	    stm.setDouble(3, getMontoSuministro(cantidad, medicamento)); 
	    stm.setBoolean(4, false);
	    stm.setDate(5, fecha);
	    stm.setInt(6, area);
	return stm; 
    } 
    
    
    private double getMontoSuministro(int cantidad, Medicamento medicamento) throws SQLException {
	return medicamento.getPrecioVenta() * cantidad ;
    }

    /**
     * Consulta la cantidad actual de un medicamento en la tabla de Internado_tiene_Medicamento
     * @param cui
     * @param medicamento
     * @return
     * @throws SQLException
     */
    public int consultarCantidadActual(int idInternado, int medicamento) throws SQLException {
	String consulta  = "SELECT Internado_tiene_Medicamento.cantidad " + 
		"FROM Internado_tiene_Medicamento " + 
		"WHERE id_internado = ? AND id_medicamento = ? ";
	PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(consulta);
	stm.setInt(1, idInternado);
	stm.setInt(2, medicamento);
	ResultSet resultado = stm.executeQuery();
	if(resultado.next())
	    return resultado.getInt(1);
	else
	    return 0;
    }
    
    
    
    /**
     * 
     * @param idMedicamento
     * @return
     * @throws SQLException
     * @throws DataBaseException
     */
    public String getDescripcionSuministroMedicamento(Medicamento medicamento) throws SQLException, DataBaseException {
	String sql = "SELECT nombre FROM Medicamento WHERE id_medicamento= ? ";
	PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
	stm.setInt(1, medicamento.getCodigo());
	ResultSet resultado = stm.executeQuery();
	String nombre; 
	if(resultado.next())
	    nombre = resultado.getString(1);
	else 
	    throw new DataBaseException("No existe el medicamento que esta consultando");
	return "Compra de " + nombre; 
    }
    
    
    /**
     * Inserta en la base de datos el descuento de medicamentos
     * @param cui
     * @param medicamento
     * @param cantidad
     * @return
     * @throws SQLException
     */
    public PreparedStatement suministrarMedicamento(int idInternado, int medicamento,int  cantidad) throws SQLException {
	    int cantidadActual = consultarCantidadActual(idInternado, medicamento);
	    int nuevaCantidad = cantidadActual - cantidad; 
	    String sql = "UPDATE Internado_tiene_Medicamento SET cantidad = ? WHERE id_medicamento = ? AND id_internado = ? ";
	    PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql); 
	    stm.setInt(1, nuevaCantidad);
	    stm.setInt(2, medicamento);
	    stm.setInt(3,idInternado );
	return stm;
    }
    
    private int consultarArea(int idEmpleado) throws SQLException, DataBaseException {
	String consulta  = "SELECT id_area FROM Empleado WHERE id_empleado = ? "; 
	PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(consulta);
	stm.setInt(1, idEmpleado);
	ResultSet resultado = stm.executeQuery();
	if(resultado.next())
	    return resultado.getInt(1);
	else
	    throw  new DataBaseException("No existe el empleado con codigo " + idEmpleado);
    }
    
    private PreparedStatement restarCantidad(Medicamento medicamento, int cantidadASuministrar) throws SQLException, DataBaseException {
	PreparedStatement stm = null; 
	if (cantidadASuministrar > medicamento.getCantidadExistente())
	    throw new DataBaseException("No existe suficiente " + medicamento.getNombre() + "para suministrar");
	else {
	    String sql = "UPDATE Medicamento SET cant_existencia= ? WHERE id_medicamento = ?"; 
	    stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
	    stm.setInt(1, cantidadASuministrar);
	    stm.setInt(2, medicamento.getCodigo());
	}
	return stm; 
    }
    
}
