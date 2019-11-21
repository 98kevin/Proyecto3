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
    /**
     * 
     */
    private static final long serialVersionUID = 3516163412083326515L;
    private static final int CONSULTAR_PACIENTES_REGISTRADOS= 1;
    private static final int REGISTRAR_CONSULTA= 3;    
    private static final int CONSULTAR_MEDICAMENTOS= 4;
    private static final int CONSULTAR_ENFERMERAS= 5;
    private static final int CONSULTAR_MEDICOS = 6;
    private static final int AGREGAR_MEDICAMENTO = 7;
    private static final int CONSULTAR_HABITACIONES = 8;
    private static final int CONSULTAR_PACIENTES_INTERNADOS = 9;
    private static final int CONSUTLAR_CIRUGIAS = 10;
    private static final int ASINGAR_CIRUGIA = 11;
    private static final int TERMINAR_CIRUGIA= 12;
    private static final int CONSULTAR_CIRUGIAS_PENDIENTES = 13;
    private static final int CONSULTAR_MEDICOS_ESPECIALISTAS=14;
    

       
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
	    ManejadorMedico medico = new ManejadorMedico();
	    switch (operacion) {
	    case CONSULTAR_MEDICAMENTOS:
		response.getWriter().append(new ManejadorFarmacia().consultarMedicamentosMedico());
		break;
	    case CONSULTAR_ENFERMERAS:
		response.getWriter().append(new ManejadorEnfermera().consultarEnfermeras());
		break;
	    case CONSULTAR_MEDICOS:
		response.getWriter().append(medico.consultarMedicos());
		break;
	    case CONSULTAR_HABITACIONES: 
		response.getWriter().append(new ManejadorHabitacion().consultarHabitacionesLibres());
		break;
	    case CONSULTAR_PACIENTES_INTERNADOS:
		response.getWriter().append(medico.consultarTablaPacientesInternadosDeMedico((Integer)(request.getSession().getAttribute("user"))));
		break;
	    case CONSUTLAR_CIRUGIAS:
		response.getWriter().append(medico.consultarCirugias());
		break;
	    case CONSULTAR_CIRUGIAS_PENDIENTES:
		response.getWriter().append(medico.consultarCirugiasPendientes());
		break;
	    case CONSULTAR_MEDICOS_ESPECIALISTAS:
		response.getWriter().append(medico.consultarMedicosEspecialistas());
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int operacion = Integer.parseInt(request.getParameter("operacion")); 
	    ManejadorPaciente manejador = new ManejadorPaciente(); 
	    ManejadorMedico medico = new ManejadorMedico();
	    switch (operacion) {
	    case CONSULTAR_PACIENTES_REGISTRADOS: 
		response.getWriter().append(manejador.pacientesRegistrados());
		break;
	    case REGISTRAR_CONSULTA: 
		response.getWriter().append(medico.nuevaConsulta(request));
		break;
	    case AGREGAR_MEDICAMENTO:
		response.getWriter().append(medico.asignarMedicamentos(request));
		break;
	    case ASINGAR_CIRUGIA:
		response.getWriter().append(medico.registrarCirugiaPaciente(request));
		break;
	    case TERMINAR_CIRUGIA:
		response.getWriter().append(medico.terminarCirugia(request));
		break;
	    default: 
	    break;
	    }
	}

}