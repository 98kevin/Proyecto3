package com.kevin.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kevin.manejadores.ManejadorRecursosHumanos;

/**
 * Servlet implementation class ServletRecurosHumanos
 */
@WebServlet(urlPatterns = {"/recursos-humanos/creacion-de-medico-especialista",
	"/recursos-humanos/recursos-humanos"})
public class ServletRecurosHumanos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//constantes POST
	private static final int REGISTRO_MEDICO_ESPECIALISTA = 1;
	private static final int DESPIDO_DE_EMPLEADO = 2;
	private static final int RECONTRATACION_DE_EMPLEADO = 3;
	//constantes GET
	private static final int CONSULTAR_EMPLEADOS_ACTIVOS = 1;
	private static final int CONSULTAR_EMPLEADOS_NO_ACTIVOS = 2;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletRecurosHumanos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int operacion = Integer.parseInt(request.getParameter("operacion"));
	    switch (operacion) {
	    case CONSULTAR_EMPLEADOS_ACTIVOS: 
		response.getWriter().append( new ManejadorRecursosHumanos().consultarEmpleadosActivos()); 
		break;
	    case CONSULTAR_EMPLEADOS_NO_ACTIVOS: 
		response.getWriter().append( new ManejadorRecursosHumanos().consultarEmpleadosDespedidos()); 
		break;
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int operacion = Integer.parseInt(request.getParameter("operacion"));
	    ManejadorRecursosHumanos manejador = new ManejadorRecursosHumanos();
	    switch (operacion) {
	    case REGISTRO_MEDICO_ESPECIALISTA: 
		response.getWriter().append(manejador.registrarMedicoEspecialista(request));
		break;
	    case DESPIDO_DE_EMPLEADO: 
		manejador.despedirEmpleado(request); 
		break;
	    case RECONTRATACION_DE_EMPLEADO: 
		manejador.recontratar(request);
		break;
	    }
	}

}
