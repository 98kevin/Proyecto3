package com.kevin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kevin.manejadores.ManejadorPaciente;
import com.kevin.manejadores.ManejadorRegistrosMonetarios;

/**
 * Servlet implementation class ServletCajero
 */
@WebServlet("/cajero/cajero")
public class ServletCajero extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	//Constates del metodo GET
	private static final int CONSULTAR_CUENTAS_DE_INTERNADOS = 1; 
	private static final int CONSULTAR_DETALLE_CUENTA = 2 ; 
	
	//Constantes del metodo POST 
	private static final int PAGAR_CUENTA_CLIENTE = 1; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCajero() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int operacion = Integer.parseInt(request.getParameter("operacion")); 
	    ManejadorRegistrosMonetarios registrosMonetarios = new ManejadorRegistrosMonetarios();
	    ManejadorPaciente manejadorPacietne = new ManejadorPaciente(); 
	    switch (operacion) {
	    case CONSULTAR_CUENTAS_DE_INTERNADOS: 
		response.getWriter().append(manejadorPacietne.consultarPacientesConCuentaPendiente("consultarCuentaCliente(this)", 
			"Seleccionar "));
		break;
	    case CONSULTAR_DETALLE_CUENTA : 
		response.getWriter().append(registrosMonetarios.consultarCuentaCliente(
			String.valueOf(request.getParameter("cuiPaciente")))); 
		break;
	    }
	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int operacion = Integer.parseInt(request.getParameter("operacion")); 
	    ManejadorRegistrosMonetarios registrosMonetarios = new ManejadorRegistrosMonetarios(); 
	    switch (operacion) {
	    case PAGAR_CUENTA_CLIENTE: 
		response.getWriter().append(registrosMonetarios.pagarCuentaCliente(
			request.getParameter("cuiPaciente"))); 
		break;
	    }
	}

}
