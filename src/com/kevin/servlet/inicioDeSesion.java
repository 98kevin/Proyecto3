package com.kevin.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kevin.manejadores.ManejadorSession;

/**
 * Servlet implementation class inicioDeSesion
 */
@WebServlet("/general/inicioDeSesion")
public class inicioDeSesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public inicioDeSesion() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	String mail = request.getParameter("correo");
	    	String password= request.getParameter("password");
	    	ManejadorSession manejador = new ManejadorSession(); 
	    	boolean aceptado = manejador.verificarPassword(mail, password);
	    	if (aceptado) {    
	    	int area = manejador.consultarArea(mail); 
	    	int codigoUsuario= manejador.getCodigoUsuario(mail);
	    	request.getSession().setAttribute("user", codigoUsuario);
	    	response.getWriter().append(manejador.obtenerDireccion(area));
	    	}
	    	else {
	    	response.setStatus(405);
	    	response.sendError(405);
	    	}
	}
	

}
