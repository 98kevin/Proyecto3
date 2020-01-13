package com.kevin.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kevin.manejadores.ManejadorAdministrador;
import com.kevin.manejadores.ManejadorArea;

/**
 * Servlet implementation class ServletAdministrador
 */
@WebServlet({"/administrador/consultar", "/administrador/administrador"})
public class ServletAdministrador extends HttpServlet {
    
    // CONSTANTES DEL METODO GET
    private static final int CONSULTAR_SALARIOS_PENDIENTES = 1;
    private static final int CONSULTAR_CIRUGIAS = 2;
    private static final int CONSULTAR_CIRUGIA = 3;
    
    //CONSTANTES DEL METODO POST
    private static final int CONSULTA_AREAS = 1;
    private static final int REGISTRAR_CIRUGIA = 2;
    private static final int PAGAR_SALARIO = 6;
    private static final int ACTUALIZAR_CIRUGIA = 7;
    
    
	private static final long serialVersionUID = 1L;


       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAdministrador() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int operacion =Integer.parseInt(request.getParameter("operacion")); 
	    ManejadorAdministrador administrador = new ManejadorAdministrador(); 
	    switch (operacion) {
	    case CONSULTAR_SALARIOS_PENDIENTES:
		response.getWriter().append(administrador.consultarSalariosPendientes(
			Integer.parseInt(request.getParameter("mes")), 
			Integer.parseInt(request.getParameter("anio")))); 
		break; 
	    case CONSULTAR_CIRUGIAS: 
		response.getWriter().append(administrador.consultarTarifas()); 
		break; 
	    case CONSULTAR_CIRUGIA: 
		response.getWriter().append(administrador.consultarTarifa(
			Integer.parseInt(request.getParameter("codigoCirugia"))
			)); 
		break; 
	    default:
		break;
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int operacion =Integer.parseInt(request.getParameter("operacion")); 
	    ManejadorArea manejador = new ManejadorArea(); 
	    ManejadorAdministrador administrador = new ManejadorAdministrador(); 
	    try {
		switch (operacion) {
		case CONSULTA_AREAS:
		    String resultado = manejador.consutarAreas();
		    response.getWriter().append(resultado);
		    break;
		case REGISTRAR_CIRUGIA:
		    response.getWriter().append(manejador.registrarCirugia(request));
		    break;
		case PAGAR_SALARIO: 
		    response.getWriter().append(administrador.pagarSalario(
			    request.getParameter("cuiEmpleado"), 
			    Integer.parseInt(request.getParameter("mes")),
			    Integer.parseInt(request.getParameter("anio")), 
			    new Date(Long.parseLong(request.getParameter("fechaEnMilisegundos"))))); 
		    break; 
		case ACTUALIZAR_CIRUGIA: 
		    response.getWriter().append(administrador.actualizarCirugia(
			    Integer.parseInt(request.getParameter("codigoCirugia")), 
			    request.getParameter("descripcionCirugia"), 
			    Double.parseDouble(request.getParameter("costoCirugia")), 
			    Double.parseDouble(request.getParameter("precioCirugia")), 
			    Double.parseDouble(request.getParameter("tarifaEspecialista")))); 
		    break; 
	    default:
		break;
	    }
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}

}
