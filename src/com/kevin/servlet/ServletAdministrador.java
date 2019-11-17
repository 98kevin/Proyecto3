package com.kevin.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kevin.manejadores.ManejadorArea;
import com.kevin.modelos.Main;

/**
 * Servlet implementation class ServletAdministrador
 */
@WebServlet("/administrador/consultar")
public class ServletAdministrador extends HttpServlet {
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
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int operacion =Integer.parseInt(request.getParameter("operacion")); 
	    ManejadorArea manejador = new ManejadorArea(); 
	    try {
		switch (operacion) {
		case Main.CONSULTA_AREAS:
		    String resultado = manejador.consutarAreas();
		    response.getWriter().append(resultado);
		    break;
		case Main.REGISTRAR_CIRUGIA:
		    response.getWriter().append(manejador.registrarCirugia(request));
	    default:
		break;
	    }
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}

}
