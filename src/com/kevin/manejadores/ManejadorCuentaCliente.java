package com.kevin.manejadores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kevin.servicio.DBConnection;
import com.kevin.servicio.GeneradorHTML;

public class ManejadorCuentaCliente {
    
    public String consultarPacientesConCuentaPendiente(String funcionJS, String textoBoton) {
	PreparedStatement stm;
	ResultSet resultados = null;
	String sql ="	SELECT persona.cui, persona.nombre, IFNULL(Internado.inicio, '--') AS 'Ingreso', IFNULL(Internado.fin, '--') AS 'Egreso', " + 
		" (IFNULL((SELECT SUM(precio_de_consulta) FROM Consulta 	WHERE Consulta.pagado = false AND Consulta.id_paciente = paciente.id_paciente), 0) + " + 
		" IFNULL((SELECT SUM(precio_actual_medicamento) FROM Transacciones_Medicamentos WHERE Transacciones_Medicamentos.cui_paciente = persona.cui AND Transacciones_Medicamentos.pagado = false), 0) +" + 
		" IFNULL((SELECT SUM(Cirugias_Disponibles.precio_al_cliente) FROM Cirugias_Disponibles INNER JOIN Cirugia  ON Cirugia.id_tarifa = Cirugias_Disponibles.id_tarfia WHERE Cirugia.id_paciente = paciente.id_paciente AND Cirugia.pagada = false), 0 )  + " + 
		" IFNULL((SELECT SUM(monto) FROM Registro_Internados WHERE id_paciente = paciente.id_paciente AND pagado = false) , 0 ))  AS 'Cuenta'" + 
		" FROM Persona persona" + 
		" INNER JOIN Paciente paciente ON persona.cui = paciente.cui  " + 
		" LEFT JOIN Internado ON paciente.id_paciente = Internado.id_paciente  " + 
		" LEFT JOIN Registro_Internados ON paciente.id_paciente = Registro_Internados.id_paciente" + 
		" WHERE (IFNULL((SELECT SUM(precio_de_consulta) FROM Consulta 	WHERE Consulta.pagado = false AND Consulta.id_paciente = paciente.id_paciente), 0) + " + 
		" IFNULL((SELECT SUM(precio_actual_medicamento) FROM Transacciones_Medicamentos WHERE Transacciones_Medicamentos.cui_paciente = persona.cui AND Transacciones_Medicamentos.pagado = false), 0) +" + 
		" IFNULL((SELECT SUM(Cirugias_Disponibles.precio_al_cliente) FROM Cirugias_Disponibles INNER JOIN Cirugia  ON Cirugia.id_tarifa = Cirugias_Disponibles.id_tarfia WHERE Cirugia.id_paciente = paciente.id_paciente AND Cirugia.pagada = false), 0 )  + " + 
		" IFNULL((SELECT SUM(monto) FROM Registro_Internados WHERE id_paciente = paciente.id_paciente AND pagado = false) , 0 )) > 0" + 
		" GROUP BY persona.cui ";
	try {
	    stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
	    resultados = stm.executeQuery(); 
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return GeneradorHTML.convertirTabla(resultados, funcionJS, textoBoton, false, false, true);
    }
    
    public String consultarCuentaCliente(String cuiPaciente) {
	StringBuffer detalleDeCuenta = new StringBuffer(); 
	try {
	    detalleDeCuenta.append(consultarDetalleMedicamentos(cuiPaciente));
	    detalleDeCuenta.append(consultarDetalleCirugias(cuiPaciente)); 
	    detalleDeCuenta.append(consultarDetalleConsulta(cuiPaciente)); 
	    detalleDeCuenta.append(consultarDetalleInternado(cuiPaciente)); 
	    detalleDeCuenta.append(consultarTotalCuenta(cuiPaciente)); 
	} catch (SQLException e) {
	    e.printStackTrace();
	} 
	return detalleDeCuenta.toString();
    }
    
    private Object consultarTotalCuenta(String cuiPaciente) throws SQLException {
	String sql ="	SELECT " + 
		" (IFNULL((SELECT SUM(precio_de_consulta) FROM Consulta 	WHERE Consulta.pagado = false AND Consulta.id_paciente = paciente.id_paciente), 0) + " + 
		" IFNULL((SELECT SUM(precio_actual_medicamento) FROM Transacciones_Medicamentos WHERE Transacciones_Medicamentos.cui_paciente = persona.cui AND Transacciones_Medicamentos.pagado = false), 0) +" + 
		" IFNULL((SELECT SUM(Cirugias_Disponibles.precio_al_cliente) FROM Cirugias_Disponibles INNER JOIN Cirugia  ON Cirugia.id_tarifa = Cirugias_Disponibles.id_tarfia WHERE Cirugia.id_paciente = paciente.id_paciente AND Cirugia.pagada = false), 0 )  + " + 
		" IFNULL((SELECT SUM(monto) FROM Registro_Internados WHERE id_paciente = paciente.id_paciente AND pagado = false) , 0 ))  AS 'Total'" + 
		" FROM Persona persona" + 
		" INNER JOIN Paciente paciente ON persona.cui = paciente.cui  " + 
		" LEFT JOIN Internado ON paciente.id_paciente = Internado.id_paciente  " + 
		" LEFT JOIN Registro_Internados ON paciente.id_paciente = Registro_Internados.id_paciente" + 
		" WHERE persona.cui = ? ";
	PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql); 
	stm.setString(1, cuiPaciente);
	ResultSet result = stm.executeQuery(); 
	return GeneradorHTML.convertirTabla(result, "", "", false, false, false); 
    }


    private String consultarDetalleMedicamentos(String cuiCliente) throws SQLException {
	String sql = "SELECT Medicamento.nombre, cantidad, precio_actual_medicamento as 'Precio Unitario',  cantidad * precio_actual_medicamento AS Subtotal" + 
		" FROM  Transacciones_Medicamentos" + 
		" INNER JOIN Medicamento ON Medicamento.id_medicamento = Transacciones_Medicamentos.id_medicamento " + 
		" WHERE pagado = false" + 
		" AND cui_paciente = ? " ; 
	PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql); 
	stm.setString(1, cuiCliente);
	ResultSet result = stm.executeQuery(); 
	return GeneradorHTML.convertirTabla(result, "", "", false, false,false); 
    }
    
    private String consultarDetalleCirugias(String cuiCliente) throws SQLException {
	String sql = "SELECT Cirugias_Disponibles.descripcion, Cirugias_Disponibles.precio_al_cliente, Cirugia.fecha " + 
		" FROM Cirugia INNER JOIN Cirugias_Disponibles ON Cirugia.id_tarifa = Cirugias_Disponibles.id_tarfia " + 
		" INNER JOIN Paciente ON Cirugia.id_paciente = Paciente.id_paciente " + 
		" INNER JOIN Persona ON Persona.cui = Paciente.cui" + 
		" WHERE Cirugia.pagada = false " + 
		" AND Persona.cui = ? " ; 
	PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql); 
	stm.setString(1, cuiCliente);
	ResultSet result = stm.executeQuery(); 
	return GeneradorHTML.convertirTabla(result, "", "", false, false,false); 
    }
    
    
    private String consultarDetalleInternado(String cuiCliente) throws SQLException {
	String sql = "SELECT detalle, fecha, monto" + 
		" FROM Registro_Internados " + 
		"INNER JOIN Paciente ON Registro_Internados.id_paciente = Paciente.id_paciente " + 
		" INNER JOIN Persona ON Persona.cui = Paciente.cui" + 
		" WHERE pagado = false " + 
		" AND Persona.cui = ? " ; 
	PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql); 
	stm.setString(1, cuiCliente);
	ResultSet result = stm.executeQuery(); 
	return GeneradorHTML.convertirTabla(result, "", "", false, false,false); 
    }
    
    private String consultarDetalleConsulta(String cuiCliente) throws SQLException {
	String sql = "SELECT descripcion, fecha, precio_de_consulta FROM Consulta " + 
		" INNER JOIN Paciente ON Consulta.id_paciente = Paciente.id_paciente " + 
		" INNER JOIN Persona ON Persona.cui = Paciente.cui" + 
		" WHERE pagado = false " + 
		" AND Persona.cui = ? " ; 
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
