package com.kevin.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kevin.manejadores.ManejadorDeTarifas;
import com.kevin.modelos.Tarifa;

/**
 * Servlet implementation class Tarifa
 */
@WebServlet("/administrador/tarifa")
public class ServletTarifa extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int CREAR= 1; 
	private static final int EDITAR= 2; 
	private static final int ELIMINAR= 3; 
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletTarifa() {
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
		int operacion= Integer.parseInt(request.getParameter("operacion")); 
		switch(operacion) {
		case CREAR: 
		    Tarifa nuevaTarifa= new Tarifa(request);
		    ManejadorDeTarifas manejador = new ManejadorDeTarifas(); 
		    try {
			manejador.registrarTarifa(nuevaTarifa);
			response.getWriter().append("Registro de tarifa con exito"); 
		    } catch (SQLException e) {
			response.getWriter().append("Error de integridad de la base de datos, Revise los datos"); 
			e.printStackTrace();
		    }
		    
		}
	}

}
