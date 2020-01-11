package com.kevin.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kevin.manejadores.ManejadorSession;

/**
 * Servlet implementation class ServletCredenciales
 */
@WebServlet("/recursos-humanos/credenciales")
public class ServletCredenciales extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public static final int LEER_USUARIOS = 1; 
	public static final int ACTUALIZAR_CREDENCIALES = 2; 
	public static final int BORRAR_CREDENCIALES = 3; 
	public static final int LEER_CREDENCIAL = 4; 
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCredenciales() {
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
		int operacion =Integer.parseInt( request.getParameter("operacion")); 
		ManejadorSession m = new ManejadorSession();
		switch (operacion) {
		case LEER_USUARIOS:
		    response.getWriter().append(m.leerUsuarios());
		    break;
		case ACTUALIZAR_CREDENCIALES:
		    response.getWriter().append(m.actualizarCredenciales(
			    Integer.parseInt(request.getParameter("codigo")),
			    request.getParameter("correo"),
			    request.getParameter("password")));
		    break;
		case BORRAR_CREDENCIALES:
		    response.getWriter().append(m.borrarCredenciales(
			    Integer.parseInt(request.getParameter("codigo"))));
		    break;
		case LEER_CREDENCIAL:
		    response.getWriter().append(m.leerCredencial(
			    Integer.parseInt(request.getParameter("codigo"))));
		    break;
		default:
		    break;
		}
	}

}
