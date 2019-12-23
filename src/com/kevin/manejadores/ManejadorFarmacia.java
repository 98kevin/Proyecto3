package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import com.kevin.modelos.Area;
import com.kevin.modelos.Medicamento;
import com.kevin.servicio.DBConnection;

public class ManejadorFarmacia{

    
    /**
     * Ejecuta la transaccion del registro de medicamentos
     * @param medicamento
     * @return
     */
    public String registrarMedicamento(Medicamento medicamento) {
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
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
  
    /**
     * Consulta los medicamentos existentes en la DB 
     * @return
     * @throws SQLException
     */
    private ResultSet consultarMedicamentos() throws SQLException {
	ResultSet medicamentos = null; 
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	String sql = "SELECT id_medicamento as Codigo, nombre as 'Descripcion', costo, cant_existencia as 'Unidades existentes', cant_minima as 'Cantidad Minima'"
		+ " FROM Medicamento";
	Statement stm =conexion.createStatement();
	medicamentos = stm.executeQuery(sql);
	return medicamentos;
    }
    
    /**
     * Se encarga de registrar el medicamento en la base de datos 
     * @param medicamento
     * @param conexion
     * @throws SQLException
     */
    private void registrarMedicamento(Medicamento medicamento, Connection conexion) throws SQLException{
	    String sql = "INSERT INTO Medicamento (nombre, costo, precio,cant_minima) VALUES (?,?,?,?)";
	    PreparedStatement stm = conexion.prepareStatement(sql);
	    stm.setString(1, medicamento.getNombre());
	    stm.setDouble(2, medicamento.getCostoCompra());
	    stm.setDouble(3, medicamento.getPrecioVenta());
	    stm.setInt(4, medicamento.getCantidadMinima());
	    stm.execute();
    }
    
    /**
     * Agrega los 
     * @return
     */
    public String agregarRegistrosDeMedicamentos()  {
	StringBuffer registros= new StringBuffer(); 
	registros.append("<input type=\"text\" id=\"cajaFiltro\" class=\"form-control\" onkeyup=\"filtrarTabla()\" placeholder=\"Filtrar por medicamento..\">");
	registros.append("<table class=\"table\" id=\"tabla\">");
	registros.append("<tr>").append("<th>Codigo</th>");
	registros.append("<th>Nombre</th>");
	registros.append("<th>Precio Compra</th>");
	registros.append("<th>Existencia</th>");
	registros.append("<th>Cantidad Minima</th>");
	registros.append("<th>Cantidad de Compra</th>").append("</tr>");
	try {
	    ResultSet medicamentos = consultarMedicamentos();
	    while(medicamentos.next()) {
	        registros.append("<tr class=\""+colorearTexto(medicamentos)+"\">");
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
	    e.printStackTrace();
	}
	registros.append("</table>");
	return registros.toString(); 
    }

    /**
     * Registra la compra de un medicamentos
     * @param request
     * @return
     */
    public String comprarMedicamento(HttpServletRequest request) {
	StringBuffer mensaje = new StringBuffer(); 
	ManejadorRegistrosMonetarios manejador = new ManejadorRegistrosMonetarios();
	int idMedicamento = Integer.parseInt(request.getParameter("idMedicamento"));
	int cantidad = Integer.parseInt(request.getParameter("cantidad"));
	int idUsuario = (Integer) (request.getSession().getAttribute("user"));
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	try {
		conexion.setAutoCommit(false);
		Medicamento medicamento = leerMedicamento(idMedicamento);
		int registro= manejador.registrarTransaccionMedicamento(idUsuario,medicamento,false, cantidad,
			Area.FARMACIA,conexion);
		registrarVenta(medicamento, cantidad, idUsuario,conexion, false, registro);
		actualizarCantidades(medicamento, medicamento.getCantidadExistente()+ cantidad, conexion);
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

    
    /**
     * Actualiza una cantidad de un medicamento en la base de datos. 
     * @param medicamento
     * @param nuevaCantidad
     * @param conexion
     * @throws SQLException
     */
    private void actualizarCantidades(Medicamento medicamento, int nuevaCantidad, Connection conexion) throws SQLException {
	String sql = "UPDATE Medicamento set cant_existencia=?  WHERE id_medicamento=?";
	    PreparedStatement stm = conexion.prepareStatement(sql);
	    stm.setInt(1, nuevaCantidad);
	    stm.setInt(2, medicamento.getCodigo());
	    stm.execute();
    }

    
    /**
     * Registra el la transaccion de     public String registrarMedicamento(Medicamento medicamento) {
	Connection conexion = conexion(); 
	StringBuffer mensaje= new StringBuffer();
	try {
	    conexion.setAutoCommit(false);
	    registrarMedicamento(medicamento,conexion);
	    conexion.commit();
	    mensaje.append("Registro de medicamento con exito"); medicamento, el la tabla de Registros_Medicamento. Especialmente para medicamentos. 
     * @param medicamento
     * @param cantidad
     * @param idUsuario
     * @param conexion
     * @param tipoDeOperacion
     * @throws SQLException
     */
    private void registrarVenta(Medicamento medicamento, int cantidad, int idUsuario, Connection conexion,
	    boolean tipoDeOperacion, int registro) throws SQLException {
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
	    stm.setInt(8, registro);
	    stm.execute();
    }



    /**
     * Lee un medicamento en base a su codigo
     * @param idMedicamento
     * @return
     * @throws SQLException
     */
    public Medicamento leerMedicamento(int idMedicamento) throws SQLException {
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
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

    
    /**
     * Consulta el inventario de medicamentos existentes
     * @return
     */
    public String consultarInventario() {
	StringBuffer registros= new StringBuffer(); 
	registros.append("<input type=\"text\" id=\"cajaFiltro\" class=\"form-control\" onkeyup=\"filtrarTabla()\" placeholder=\"Filtrar por medicamento..\">");
	registros.append("<table class=\"table\" id=\"tabla\">");
	registros.append("<tr>").append("<th>Codigo</th>");
	registros.append("<th>Nombre</th>");
	registros.append("<th>Precio Compra</th>");
	registros.append("<th>Existencia</th>");
	registros.append("<th>Cantidad Minima</th>");
	registros.append("<th>Nueva Cantidad</th>").append("</tr>");
	try {
	    ResultSet medicamentos = consultarMedicamentos();
	    while(medicamentos.next()) {
	        registros.append("<tr class=\""+colorearTexto(medicamentos)+"\">");
	        registros.append("<td>"+medicamentos.getInt(1)+"</td>");
	        registros.append("<td>"+medicamentos.getString(2)+"</td>");
	        registros.append("<td>"+medicamentos.getDouble(3)+"</td>");
	        registros.append("<td>"+medicamentos.getInt(4)+"</td>");
	        registros.append("<td>"+medicamentos.getInt(5)+"</td>");
	        registros.append("<td ><input id=\"cant"+medicamentos.getInt(1)+"\"  class = \"form-control\" min=\"0\" placeholder=\"nueva cantidad...\"></input></td>");
	        registros.append("<td><button id=\""+medicamentos.getInt(1)+"\" onClick=\"botonActualizar(this)\" class=\"botonActualizar\">Actualizar</button></td>");
	        registros.append("</tr>");
	    }
    } catch(SQLException e ) {
	e.printStackTrace();
	}
	return registros.toString();
    }

    /**
     * Da color rojo a la fila si la cantidad minima del medicamento es menor que su cantidad existente
     * @param medicamentos
     * @return
     * @throws SQLException
     */
    private String colorearTexto(ResultSet medicamentos) throws SQLException {
	String clase="";
	//cantidad existente es menor que la cantidad actual
	if (medicamentos.getInt(4) < medicamentos.getInt(5))
	    clase = "text-danger";
	return clase;
    }

    /**
     * Registra una nueva cantidad de medicamento. Al momento de la actualizacion de inventario. 
     * @param request
     * @return
     * @throws NumberFormatException
     * @throws SQLException
     */
    public String registrarActualizacion(HttpServletRequest request) throws NumberFormatException, SQLException {
	Medicamento medicamento = leerMedicamento(Integer.parseInt(request.getParameter("idMedicamento")));
	int nuevaCantidad = Integer.parseInt(request.getParameter("cantidad")); 
	Connection conexion  =DBConnection.getInstanceConnection().getConexion();
	actualizarCantidades(medicamento, nuevaCantidad, conexion);
	return consultarInventario();
    }

    
    /**
     * Consulta los medicamentos que se mostraran a los medicos al momento de recetar
     * @return
     */
    public String consultarMedicamentosMedico() {
	StringBuffer registros= new StringBuffer(); 
	registros.append("<br><h3>Receta de Medicamentos</h3>"); 
	registros.append("<br><input type=\"text\" id=\"cajaFiltro\" class=\"form-control\" onkeyup=\"filtrarTabla()\" placeholder=\"Filtrar por medicamento..\">");
	registros.append("<table class=\"table\"  id=\"tabla\">"); 
	registros.append("<tr>").append("<th>Codigo</th>");
	registros.append("<th>Nombre</th>");
	registros.append("<th>Cantidad</th>").append("</tr>");
	try {
	    ResultSet medicamentos = consultarMedicamentos();
	    while(medicamentos.next()) {
	        registros.append("<tr class=\"\">");
	        registros.append("<td>"+medicamentos.getInt(1)+"</td>");
	        registros.append("<td>"+medicamentos.getString(2)+"</td>");
	        registros.append("<td ><input id=\"cant"+medicamentos.getInt(1)+"\"  class = \"form-control\" min=\"0\" "
	        	+ "placeholder=\"nueva cantidad...\"></input></td>");
	        registros.append("<td><button id=\""+medicamentos.getInt(1)+"\" onClick=\"agregarMedicamento(this)\" class=\"botonActualizar\">Agregar</button></td>");
	        registros.append("</tr>");
	    }
    } catch(SQLException e ) {
	e.printStackTrace();
	}
	return registros.toString();
    }


}
