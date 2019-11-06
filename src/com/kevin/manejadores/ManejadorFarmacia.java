package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import com.kevin.modelos.Medicamento;
import com.kevin.servicio.DBConnection;

public class ManejadorFarmacia extends DBConnection{

    public String registrarMedicamento(Medicamento medicamento) {
	Connection conexion = conexion(); 
	StringBuffer mensaje= new StringBuffer();
	try {
	    conexion.setAutoCommit(false);
	    registrarMedicamento(medicamento,conexion);
	    conexion.commit();
	    mensaje.append("Registro de medicamento con exito"); 
	} catch (SQLException e) {
	    e.printStackTrace();
	    mensaje.append("Error de intregridad de base de datos.  \n codigo de error " + e.getErrorCode()) ;
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
  
    private ResultSet consultarMedicamentos() throws SQLException {
	ResultSet medicamentos = null; 
	Connection conexion = conexion();
	String sql = "SELECT id_medicamento as Codigo, nombre as 'Descripcion', costo, cant_existencia as 'Unidades existentes', cant_minima as 'Cantidad Minima'"
		+ "FROM Medicamento";
	Statement stm =conexion.createStatement();
	medicamentos = stm.executeQuery(sql);
	return medicamentos;
    }
    
    
    private void registrarMedicamento(Medicamento medicamento, Connection conexion) throws SQLException{
	    String sql = "INSERT INTO Medicamento (nombre, costo, precio,cant_minima) VALUES (?,?,?,?)";
	    PreparedStatement stm = conexion.prepareStatement(sql);
	    stm.setString(1, medicamento.getNombre());
	    stm.setDouble(2, medicamento.getCostoCompra());
	    stm.setDouble(3, medicamento.getPrecioVenta());
	    stm.setInt(4, medicamento.getCantidadMinima());
	    stm.execute();
    }
    
    public String agregarRegistrosDeMedicamentos()  {
	StringBuffer registros= new StringBuffer(); 
	registros.append("<input type=\"text\" id=\"cajaFiltro\" class=\"form-control\" onkeyup=\"filtrarTabla()\" placeholder=\"Filtrar por medicamento..\">");
	registros.append("<table id=\"tabla\">");
	registros.append("<tr>").append("<th>Codigo</th>");
	registros.append("<th>Nombre</th>");
	registros.append("<th>Precio Compra</th>");
	registros.append("<th>Existencia</th>");
	registros.append("<th>Cantidad Minima</th>");
	registros.append("<th>Cantidad de Compra</th>").append("</tr>");
	try {
	    ResultSet medicamentos = consultarMedicamentos();
	    while(medicamentos.next()) {
	        registros.append("<tr>");
	        registros.append("<td>"+medicamentos.getInt(1)+"</td>");
	        registros.append("<td>"+medicamentos.getString(2)+"</td>");
	        registros.append("<td>"+medicamentos.getDouble(3)+"</td>");
	        registros.append("<td>"+medicamentos.getInt(4)+"</td>");
	        registros.append("<td>"+medicamentos.getInt(5)+"</td>");
	        registros.append("<td ><input id=\"cant"+medicamentos.getInt(1)+"\"  class = \"form-control\" min=\"0\" placeholder=\"cantidad a comprar...\"></input></td>");
	        registros.append("<td><button id=\""+medicamentos.getInt(1)+"\" onClick=\"botonComprar(this)\" class=\"botonComprar\">Comprar</button></td>");
	        registros.append("</tr>");
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	registros.append("</table>");
	return registros.toString(); 
    }

    public String comprarMedicamento(HttpServletRequest request) {
	StringBuffer mensaje = new StringBuffer(); 
	int idMedicamento = Integer.parseInt(request.getParameter("idMedicamento"));
	int cantidad = Integer.parseInt(request.getParameter("cantidad"));
	int idUsuario = (Integer) (request.getSession().getAttribute("user"));
	Connection conexion = conexion();
	try {
		conexion.setAutoCommit(false);
		Medicamento medicamento = leerMedicamento(idMedicamento);
		registrarTransaccion(idUsuario,medicamento,false, cantidad,
			ManejadorSession.AREA_FARMACIA,conexion);
		registrarVenta(medicamento, cantidad, idUsuario,conexion, false);
		actualizarCantidades(medicamento, cantidad, conexion);
		conexion.commit();
		mensaje.append("Registro realizado con exito");
	} catch (SQLException e) {
	   mensaje.append("Ocurrio un error al registrar la compra. \n Codigo de error: "+e.getErrorCode());
	   e.printStackTrace();
	} finally {
	    try {
		conexion.setAutoCommit(true);
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
	return mensaje.toString();
    }

    private void actualizarCantidades(Medicamento medicamento, int cantidad, Connection conexion) throws SQLException {
	String sql = "UPDATE Medicamento set cant_existencia=?  WHERE id_medicamento=?";
	    PreparedStatement stm = conexion.prepareStatement(sql);
	    stm.setInt(1, medicamento.getCantidadExistente()+ cantidad);
	    stm.setInt(2, medicamento.getCodigo());
	    stm.execute();
    }

    private void registrarVenta(Medicamento medicamento, int cantidad, int idUsuario, Connection conexion,
	    boolean tipoDeOperacion) throws SQLException {
	Date fechaActual = new Date(new java.util.Date(Calendar.getInstance().getTimeInMillis()).getTime());
	String sql = "INSERT INTO Registros_Medicamento (fecha,costo_actual_medicamento, "
		+ "precio_actual_medicamento, cantidad, tipo_operacion, id_medicamento, "
		+ "id_empleado, id_registro) VALUES (?,?,?,?,?,?,?,?)";
	    PreparedStatement stm = conexion.prepareStatement(sql);
	    stm.setDate(1, fechaActual);
	    stm.setDouble(2, medicamento.getCostoCompra());
	    stm.setDouble(3, medicamento.getPrecioVenta());
	    stm.setInt(4, cantidad);
	    stm.setBoolean(5, tipoDeOperacion);
	    stm.setInt(6, medicamento.getCodigo());
	    stm.setInt(7,idUsuario);
	    stm.setInt(8, new DBConnection().maximo("Registro_Monetario", "id_registro"));
	    stm.execute();
    }

    private void registrarTransaccion( int idUsuario, Medicamento medicamento, 
	    boolean tipoDeOperacion, int cantidad, int areaFarmacia, Connection conexion) throws SQLException {
	Date fechaActual = new Date(new java.util.Date(Calendar.getInstance().getTimeInMillis()).getTime());
	String descripcion = getTextoOperacion(tipoDeOperacion)+" de "+ cantidad+" unidades de "+ medicamento.getNombre(); 
	double monto = medicamento.getCostoCompra() * cantidad;
	String sql = "INSERT INTO Registro_Monetario (descripcion,monto,fecha,tipo,id_area) VALUES (?,?,?,?,?)";
	    PreparedStatement stm = conexion.prepareStatement(sql);
	    stm.setString(1, descripcion);
	    stm.setDouble(2, monto);
	    stm.setDate(3, fechaActual);
	    stm.setBoolean(4, tipoDeOperacion);
	    stm.setInt(5, areaFarmacia);
	    stm.execute();
    }

    private String getTextoOperacion(boolean tipoDeOperacion) {
	return (tipoDeOperacion==false) ? "Compra": "Venta";
    }

    private Medicamento leerMedicamento(int idMedicamento) throws SQLException {
	Connection conexion = conexion();
	String sqlStatement = "SELECT * FROM Medicamento WHERE id_medicamento= ?";
	PreparedStatement stm = conexion.prepareStatement(sqlStatement);
	stm.setInt(1, idMedicamento);
	ResultSet resultado = stm.executeQuery();
	resultado.next();
	return new Medicamento(resultado.getInt(1),
		resultado.getString(2),
		resultado.getDouble(3), 
		resultado.getDouble(4),
		resultado.getInt(5),
		resultado.getInt(6));
    }
}
