package com.kevin.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kevin.manejadores.ManejadorHabitacion;
import com.kevin.modelos.Habitacion;

/**
 * Servlet implementation class Habitacion
 */
@WebServlet("/administrador/habitacion")
public class ServletHabitacion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletHabitacion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int operacion = Integer.parseInt(request.getParameter("operacion"));
		switch(operacion) {
		case Habitacion.CREAR:
		    Habitacion habitacion = new Habitacion(request);
		    ManejadorHabitacion manjeador = new ManejadorHabitacion(); 
		    String resultado = manjeador.registrarHabitacion(habitacion);
		    response.getWriter().append(resultado); 
		}
	}

}
