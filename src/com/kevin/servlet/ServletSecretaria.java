package com.kevin.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kevin.manejadores.ManejadorPaciente;
import com.kevin.modelos.Paciente;

/**
 * Servlet implementation class ServletSecretaria
 */
@WebServlet("/secretaria/secretaria")
public class ServletSecretaria extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int REGISTRAR_PACIENTE = 1;
	private static final int ACTUALIZAR_PACIENTE = 2;
	
	private static final int LEER_PACIENTES = 1;
	private static final int LEER_PACIENTE = 2;
	
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletSecretaria() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int operacion = Integer.parseInt(request.getParameter("operacion")); 
	    ManejadorPaciente manejador = new ManejadorPaciente(); 
	    switch (operacion) {
	    case LEER_PACIENTES: 
		response.getWriter().append(manejador.leerPacientesRegistrados()); 
		break; 
	    case LEER_PACIENTE: 
		response.getWriter().append(manejador.leerPaciente(request.getParameter("cui"))); 
		break; 
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int operacion = Integer.parseInt(request.getParameter("operacion")); 
	    ManejadorPaciente manejador = new ManejadorPaciente(); 
	    switch (operacion) {
	    case REGISTRAR_PACIENTE:
		    Paciente paciente = new Paciente(request);
		    response.getWriter().append(manejador.registrarPaciente(paciente));
		break;
	    case ACTUALIZAR_PACIENTE:
		response.getWriter().append(manejador.actualizarPaciente(
			request.getParameter("cuiActual"), 
			request.getParameter("cuiNuevo"), 
			request.getParameter("nombre"), 
			request.getParameter("direccion"), 
			request.getParameter("nit"))); 
		break; 
	    default:
		break;
	    }

	}

}
