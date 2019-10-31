package com.kevin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import com.kevin.manejadores.ManejadorAdministrador;
import com.kevin.modelos.Administrador;

/**
 * Servlet implementation class creacionDeEmpleado
 */
@WebServlet( "/administrador/creacionDeEmpleado")
public class creacionDeEmpleado extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public creacionDeEmpleado() {
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
	    Administrador admin = new Administrador(request);
	    ManejadorAdministrador manejador = new ManejadorAdministrador(); 
	    String mensaje = manejador.registrarAdministador(admin); 
	    response.getWriter().append(mensaje);
	}

}
