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
    String sql = " SELECT Empleado.id_empleado, Persona.nombre " + 
    	" FROM Persona INNER JOIN Empleado ON Persona.cui=Empleado.cui_persona" + 
    	" WHERE Empleado.id_area= ?";
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	ResultSet enfermeras = null;
	try {
		PreparedStatement stm = conexion.prepareStatement(sql); 
		stm.setInt(1, Area.ENFERMEROS);
		enfermeras = stm.executeQuery();
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
	return GeneradorHTML.convertirTabla(resultado, "suministrarMedicamento(this)", "Suministrar", false, false, true);
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
	    preparedStatements.add(sumarCuentaCliente(cuiPaciente, medicamento,  cantidad, fecha, idEmpleado)); 
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
    
    
    private PreparedStatement sumarCuentaCliente(String cuiPaciente, Medicamento m, int cantidad, Date fecha, int idEmpelado) throws SQLException {
	String sql  = "INSERT INTO Transacciones_Medicamentos (fecha, costo_actual_medicamento, precio_actual_medicamento, cantidad, tipo_operacion, pagado, "
		+ "id_medicamento, id_empleado, cui_paciente) values (?,?,?,?,?,?,?,?,?)";
	PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
	stm.setDate(1, fecha);
	stm.setDouble(2, m.getCostoCompra());
	stm.setDouble(3, m.getPrecioVenta());
	stm.setInt(4, cantidad);
	stm.setBoolean(5, true);
	stm.setBoolean(6, false);
	stm.setInt(7, m.getCodigo());
	stm.setInt(8, idEmpelado);
	stm.setString(9, cuiPaciente);
	return stm;
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

    public PreparedStatement registrarPagoDeMedicamento(int paciente, Medicamento medicamento, int cantidad, Date fecha, int area) 
	    throws SQLException, DataBaseException {
	    String sql = "INSERT INTO Transacciones_Medicamentos SET  pagado = true WHERE id_medicamento = ? AND cui_paciente = ? "; 
	    PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);  
	    stm.setInt(1, medicamento.getCodigo()); 
	    stm.setString(2, consultarCui(paciente)); 
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
	    String sql = "UPDATE Internado_tiene_Medicamento SET cantidad = ? WHERE id_medicamento = ? AND id_internado = ? ";
	    PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql); 
	    stm.setInt(1, 0);
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
    
    
    private String consultarCui(int idPaciente) throws SQLException, DataBaseException {
	String consulta  = "SELECT cui FROM Persona inner join Empleado on Empleado.cui = Persona.cui WHERE id_paciente = ? "; 
	PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(consulta);
	stm.setInt(1, idPaciente);
	ResultSet resultado = stm.executeQuery();
	if(resultado.next())
	    return resultado.getString(1);
	else
	    throw  new DataBaseException("No existe el paciente con codigo " + idPaciente);
    }


    public String consultarMedicamentosIndividuales(int idEmpleado, String cuiPaciente) {
	PreparedStatement stm;
	ResultSet resultado =null;
	String sql ="SELECT Medicamento.id_medicamento, Medicamento.nombre, Persona.nombre AS 'Paciente', " +
		" Internado_tiene_Medicamento.cantidad AS 'Cantidad Faltante' FROM Medicamento  " +
		" INNER JOIN Internado_tiene_Medicamento ON Internado_tiene_Medicamento.id_medicamento = Medicamento.id_medicamento "+
		" INNER JOIN Internado ON Internado_tiene_Medicamento.id_internado = Internado.id_internado"+
		" INNER JOIN Internado_tiene_Empleado ON Internado_tiene_Empleado.id_internado = Internado.id_internado"+
		
		" INNER JOIN Empleado ON Empleado.id_empleado = Internado_tiene_Empleado.id_empleado"+
		
		" INNER JOIN Paciente ON Paciente.id_paciente = Internado.id_paciente "+
		" INNER JOIN Persona ON Persona.cui = Paciente.cui "+
		" WHERE Internado.fin IS NULL AND Empleado.id_empleado = ? "
		+ " AND Persona.cui = ? ";
	try {
	     stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
	     stm.setString(2, cuiPaciente);
	     stm.setInt(1, idEmpleado);
	     resultado = stm.executeQuery();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return GeneradorHTML.convertirTabla(resultado, "suministrarMedicamento(this)", "Suministrar", false, false, true);
    }
}
