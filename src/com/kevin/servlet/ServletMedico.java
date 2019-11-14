package com.kevin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kevin.manejadores.ManejadorEnfermera;
import com.kevin.manejadores.ManejadorFarmacia;
import com.kevin.manejadores.ManejadorHabitacion;
import com.kevin.manejadores.ManejadorMedico;
import com.kevin.manejadores.ManejadorPaciente;

/**
 * Servlet implementation class ServletMedico
 */
@WebServlet("/medico/medico")
public class ServletMedico extends HttpServlet {
    private static final int NUEVA_CONSULTA= 1;
    private static final int REGISTRAR_CIRUGIA= 2;
    private static final int REGISTRAR_CONSULTA= 3;    
    private static final int CONSULTAR_MEDICAMENTOS= 4;
    private static final int CONSULTAR_ENFERMERAS= 5;
    private static final int CONSULTAR_MEDICOS = 6;
    private static final int CONSULTAR_HABITACIONES = 8;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletMedico() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int operacion = Integer.parseInt(request.getParameter("operacion")); 
	    switch (operacion) {
	    case CONSULTAR_MEDICAMENTOS:
		response.getWriter().append(new ManejadorFarmacia().consultarMedicamentosMedico());
		break;
	    case CONSULTAR_ENFERMERAS:
		response.getWriter().append(new ManejadorEnfermera().consultarEnfermeras());
		break;
	    case CONSULTAR_MEDICOS:
		response.getWriter().append(new ManejadorMedico().consultarMedicos());
		break;
	    case CONSULTAR_HABITACIONES: 
		response.getWriter().append(new ManejadorHabitacion().consultarHabitacionesLibres());
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int operacion = Integer.parseInt(request.getParameter("operacion")); 
	    ManejadorPaciente manejador = new ManejadorPaciente(); 
	    switch (operacion) {
	    case NUEVA_CONSULTA: 
		response.getWriter().append(manejador.pacientesRegistrados());
		break;
	    case REGISTRAR_CIRUGIA:  
		
		break;
	    case REGISTRAR_CONSULTA: 
		response.getWriter().append(new ManejadorMedico().nuevaConsulta(request));
		break;
	    default: 
		
	    break;
	    }
	}

}