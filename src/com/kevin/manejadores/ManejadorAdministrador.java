package com.kevin.manejadores;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.kevin.modelos.Administrador;
import com.kevin.modelos.Main;
import com.kevin.servicio.DBConnection;
import com.kevin.servicio.GeneradorHTML;

public class ManejadorAdministrador {

    /**
     * Registra un nuevo administrador en la base de datos
     * @param administrador
     */
    public String registrarAdministador(Administrador administrador) {
	Connection conexion = DBConnection.getInstanceConnection().getConexion();
	String msj= "";
	try {
	    conexion.setAutoCommit(false);
	    ManejadorPersona persona = new ManejadorPersona(); 
	    persona.registrarPersona(administrador,conexion); 
	    int empleado  =   registrarEmpleado(administrador, conexion);
	    registrarCredenciales(administrador, conexion, empleado);
	    registrarPeriodo(administrador, conexion,empleado);
	    conexion.commit();
	    msj += "Creacion de administrador exitosa"; 
	} catch (SQLException e) {
	    e.printStackTrace();
	    msj += "Error de intregridad de base de datos.   codigo de error" + e.getErrorCode();
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
    
    private void registrarCredenciales(Administrador administrador, Connection conexion,int  idEmpleado) throws SQLException {
	String sqlCredenciales = "INSERT INTO Credenciales (correo_electronico, password,id_empleado, id_area) VALUES (?,?,?,?)";
	PreparedStatement stmCredenciales;
	stmCredenciales = conexion.prepareStatement(sqlCredenciales);
	stmCredenciales.setString(1, administrador.getEmail());
	stmCredenciales.setString(2, administrador.getPassword());
	//el utlimo valor en la tabla empleado
	stmCredenciales.setInt(3, idEmpleado);
	stmCredenciales.setInt(4, administrador.getAreaDeTrabajo());
	stmCredenciales.execute();
    }

    
    
    /**
     * registro del periodo del administrador
     * @param administrador
     * @throws SQLException 
     */
    public void registrarPeriodo(Administrador administrador, Connection conexion, int empleado) throws SQLException {
	String sqlPeriodoLaboral = "INSERT INTO Contrato (salario, fecha_inicial,id_empleado) VALUES (?,?,?)";
	PreparedStatement stmPeriodo;
	    	stmPeriodo = conexion.prepareStatement(sqlPeriodoLaboral);
		stmPeriodo.setDouble(1, administrador.getSalario());
		stmPeriodo.setDate(2, administrador.getFechaDeInicio());
		//el utlimo valor en la tabla empleado
		stmPeriodo.setInt(3, empleado);
		stmPeriodo.execute();
    }
    
/**
 * Registra un nuevo empleado y retorna el valor de la clave primaria insertada
 * @param administrador
 * @param conexion
 * @return
 * @throws SQLException
 */
    public int registrarEmpleado(Administrador administrador, Connection conexion) throws SQLException {
	int clavePrimaria=0;
	ResultSet resultado = null;
	String sqlEmpleado = "INSERT INTO Empleado (porcentaje_igss, porcentaje_irtra,fecha_de_vacaciones, cui_persona, id_area)  VALUES (?,?,?,?,?)";
	PreparedStatement stmEmpleado;
	    stmEmpleado = conexion.prepareStatement(sqlEmpleado, Statement.RETURN_GENERATED_KEYS);
	    stmEmpleado.setInt(1, administrador.getIggs());
	    stmEmpleado.setInt(2, administrador.getIrtra());
	    stmEmpleado.setDate(3, administrador.getFechaDeVacaciones());
	    stmEmpleado.setString(4, administrador.getCui());
	    stmEmpleado.setInt(5, administrador.getAreaDeTrabajo());
	    stmEmpleado.execute();
	resultado = stmEmpleado.getGeneratedKeys(); 
	if(resultado.next())
	    clavePrimaria = resultado.getInt(1); 
	return clavePrimaria; 
    }
    
    
    public String consultarSalariosPendientes(int mes, int anio) {
	ResultSet registros = null;
	String sql = "SELECT Persona.cui, Persona.nombre, Contrato.salario" + 
		"	FROM Persona " + 
		"	INNER JOIN Empleado ON Persona.cui = Empleado.cui_persona" + 
		"	INNER JOIN Contrato ON Empleado.id_empleado = Contrato.id_empleado" + 
		"	WHERE ? NOT IN (SELECT pp.id_mes FROM Pagos_Planilla pp " + 
		"		INNER JOIN Contrato c ON pp.id_contrato = c.id_contrato" + 
		"    		WHERE c.id_contrato = Contrato.id_contrato)" + 
		"	AND ? NOT IN (SELECT pp.anio FROM Pagos_Planilla pp " + 
		"		INNER JOIN Contrato c ON pp.id_contrato = c.id_contrato" + 
		"    		WHERE c.id_contrato = Contrato.id_contrato)" + 
		"	AND Contrato.fecha_final IS NULL";
	PreparedStatement stm; 
	try {
	    stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql); 
	    stm.setInt(1, mes);
	    stm.setInt(2, anio);
	    registros = stm.executeQuery(); 
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return GeneradorHTML.convertirTabla(registros, "pagarSalario(this)", "Pagar salario", false, false, true);
    }
    
    
    public String pagarSalario(String cuiEmpleado, int mes, int anio, Date fecha) {
	StringBuffer resp = new StringBuffer(); 
	String sql = "INSERT INTO Pagos_Planilla (descripcion, monto, id_contrato, id_mes, anio, area, fecha) VALUES (" + 
		"'Pago de salario Mensual', " +  
		" 	(SELECT Contrato.salario " + 
		"		FROM Contrato " + 
		"		INNER JOIN Empleado  ON Contrato.id_empleado = Empleado.id_empleado " + 
		"		WHERE Empleado.cui_persona = ?" + //1
		"		AND Contrato.fecha_final IS NULL), " + 
		" 	(SELECT Contrato.id_contrato " + 
		"		FROM Contrato " + 
		"		INNER JOIN Empleado e1 ON Contrato.id_empleado = e1.id_empleado " + 
		"		WHERE e1.cui_persona = ? " + //2
		"		AND Contrato.fecha_final IS NULL), " + 
		"?, " + //3
		"?, " + //4 
		" 	(SELECT e.id_area " + 
		"		FROM Persona p, Empleado e " + 
		"		WHERE p.cui = e.cui_persona " + 
		"		AND p.cui = ?), " + //5
		"?)"; //6
	PreparedStatement pstm;
	try {
	    	pstm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
		pstm.setString(1, cuiEmpleado);
		pstm.setString(2, cuiEmpleado);
		pstm.setInt(3, mes);
		pstm.setInt(4, anio);
		pstm.setString(5, cuiEmpleado);
		pstm.setDate(6, fecha);
		pstm.execute(); 
		resp.append("Registro realizado exitosamente"); 
	} catch (SQLException e) {
	    	e.printStackTrace();
	    	resp.append("Ocurrio un error. Codigo de error " + e.getErrorCode()); 
	}
	return resp.toString(); 
    }
    
    public String consultarTarifas() {
	String sql = "SELECT id_tarfia, descripcion AS 'Cirugia', costo_al_hospital AS 'Costo', tarifa_de_especialista AS 'Precio', " + 
		" precio_al_cliente AS 'Tarifa de especialista'" + 
		" FROM Cirugias_Disponibles" ; 
	ResultSet registros = null; 
	try {
		PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql); 
	    registros = stm.executeQuery(); 
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return GeneradorHTML.convertirTabla(registros, "seleccionarCirugia(this)", "Editar", false, false, true);
    }
    
    public String consultarTarifa(int codigo) {
	String respuesta  = null; 
	String sql = "SELECT id_tarfia, descripcion AS 'Cirugia', costo_al_hospital AS 'Costo', tarifa_de_especialista AS 'Precio', " + 
		" precio_al_cliente AS 'Tarifa de especialista'" + 
		" FROM Cirugias_Disponibles "
		+ " WHERE id_tarfia = ? " ; 
	ResultSet r = null; 
	try {
		PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql); 
		stm.setInt(1, codigo);
	    r = stm.executeQuery(); 
	    if (r.next()) {
		respuesta  = "  <div>" + 
			"    <h3>Edicion de Cirugia</h3>" + 
			"    <div class=\"form-group row\">" + 
			"      <label class=\"col-sm-2 col-form-label\">Codigo </label>" + 
			"      <div class=\"col-sm-10\">" + 
			"        <input type=\"nubmer\" class=\"form-control\" id=\"codigoCirugia\" value=\""+r.getInt(1)+ "\" readonly>" + 
			"      </div>" + 
			"    </div>" + 
			"" + 
			"    <div class=\"form-group row\">" + 
			"      <label class=\"col-sm-2 col-form-label\">Cirugia </label>" + 
			"      <div class=\"col-sm-10\">" + 
			"        <input type=\"text\" class=\"form-control\" id=\"descripcionCirugia\" value=\""+r.getString(2)+"\">" + 
			"      </div>" + 
			"    </div>" + 
			"" + 
			"    <div class=\"form-group row\">" + 
			"      <label class=\"col-sm-2 col-form-label\">Costo </label>" + 
			"      <div class=\"col-sm-10\">" + 
			"        <input type=\"number\" step=\"0.01\" class=\"form-control\" id=\"costoCirugia\" value=\""+r.getDouble(3)+"\">" + 
			"      </div>" + 
			"    </div>" + 
			"" + 
			"    <div class=\"form-group row\">" + 
			"      <label class=\"col-sm-2 col-form-label\">Precio </label>" + 
			"      <div class=\"col-sm-10\">" + 
			"        <input type=\"number\" step=\"0.01\" class=\"form-control\" id=\"precioCirugia\" value=\""+r.getDouble(4)+"\">" + 
			"      </div>" + 
			"    </div>" + 
			"" + 
			"" + 
			"    <div class=\"form-group row\">" + 
			"      <label class=\"col-sm-2 col-form-label\">Tarifa de Especialista </label>" + 
			"      <div class=\"col-sm-10\">" + 
			"        <input type=\"number\" step=\"0.01\" class=\"form-control\" id=\"tarifaEspecialista\" value=\""+r.getDouble(5)+ "\">" + 
			"      </div>" + 
			"    </div>" + 
			"</div>" + 
			"" + 
			"<div class=\"button-group\">" + 
			"  <input class=\"btn btn-primary\"  type=\"button\" id=\"\"  value=\"Guardar Cambios\"  onclick=\"actualizarCirugia()\">" + 
			"</div>" ;
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return respuesta;
    }
    
    public String actualizarCirugia(int codigo, String descripcion, double costo, double precio, double tarifaEspecialista) {
	String respuesta  = null; 
	String sql  = "UPDATE Cirugias_Disponibles SET descripcion = ?, costo_al_hospital  = ?, precio_al_cliente = ?, tarifa_de_especialista = ? " + 
		" WHERE id_tarfia = ? "; 									
	try {
	    PreparedStatement stm = DBConnection.getInstanceConnection().getConexion().prepareStatement(sql);
	    stm.setString(1, descripcion);
	    stm.setDouble(2, costo);
	    stm.setDouble(3, precio);
	    stm.setDouble(4, tarifaEspecialista);
	    stm.setInt(5, codigo);
	    stm.execute(); 
	    respuesta = Main.MENSAJE_EXITO; 
	} catch (SQLException e) {
	    respuesta = "Ha ocurrido un error. Codigo  " + e.getErrorCode(); 
	    e.printStackTrace();
	} 
	return respuesta ; 
    }
}
