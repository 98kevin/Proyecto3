package com.kevin.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kevin.manejadores.ManejadorMedico;
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
	    ManejadorMedico medico= new ManejadorMedico(); 
	    switch (operacion) {
	    case CONSULTAR_CUENTAS_DE_INTERNADOS: 
		response.getWriter().append(medico.consultarPacientesInternados("consultarCuentaCliente(this)", 
			"Seleccionar ", 
			(Integer) request.getSession().getAttribute("user")));
		break;
	    case CONSULTAR_DETALLE_CUENTA : 
		response.getWriter().append(registrosMonetarios.consultarCuentaCliente(
			String.valueOf(request.getParameter("cuiCliente")))); 
		break;
	    }
	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int operacion = Integer.parseInt(request.getParameter("operacion")); 
	    switch (operacion) {}
	}

}
