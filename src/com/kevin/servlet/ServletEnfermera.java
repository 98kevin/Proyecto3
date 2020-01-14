package com.kevin.servlet;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kevin.manejadores.ManejadorEnfermera;
import com.kevin.manejadores.ManejadorPaciente;

/**
 * Servlet implementation class ServletEnfermera
 */
@WebServlet(
	urlPatterns = {	"/enfermera/medicamentosDeInternados",
					"/enfermera/consultarPacientes", 
					"/enfermera/suministarMedicamento"	}
	)
public class ServletEnfermera extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//constantes del metodo get
	private static final int CONSULTAR_MEDICAMENTOS_DE_INTERNADOS = 1;
	private static final int CONSULTAR_PACIENTES_DE_ENFERMERA= 2;
	
       //constantes del metodo post 
	private static final int ASINGAR_MEDICAMENTOS_PACIENTE= 1; 
	private static final int CONSULTAR_MEDICAMENTOS_DE_PACIENTE = 2 ; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletEnfermera() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int operacion = Integer.parseInt(request.getParameter("operacion"));
		ManejadorPaciente manejador = new ManejadorPaciente(); 
		int codigoEnfermera = (Integer) request.getSession().getAttribute("user"); 
		switch ( operacion ) {
		case CONSULTAR_MEDICAMENTOS_DE_INTERNADOS:
		    response.getWriter().append(manejador.consultarMedicamentosDePacientes(codigoEnfermera));
		    break;
		case CONSULTAR_PACIENTES_DE_ENFERMERA: 
		    response.getWriter().append(manejador.consultarPacinetesDeEnfermera(codigoEnfermera));
		    break; 
		default:
		    break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int operacion = Integer.parseInt(request.getParameter("operacion"));
	    ManejadorEnfermera manejador = new ManejadorEnfermera(); 
		switch ( operacion ) {
		case ASINGAR_MEDICAMENTOS_PACIENTE:
		  response.getWriter().append(manejador.suministrarMedicamento(
			  Integer.parseInt(request.getParameter("medicamento")), 
			  Integer.parseInt(request.getParameter("cantidad")), 
			  String.valueOf(request.getParameter("pacienteSeleccionado")),
			  new Date(Long.parseLong(request.getParameter("fecha"))),
			  (Integer) request.getSession().getAttribute("user")));
		    break;
		case CONSULTAR_MEDICAMENTOS_DE_PACIENTE: 
		    response.getWriter().append(manejador.consultarMedicamentosIndividuales(
			    (Integer) request.getSession().getAttribute("user"),
			    request.getParameter("cui"))); 
		default:
		    break;
		}
		
	}

}
