package com.kevin.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kevin.manejadores.ManejadorArea;

/**
 * Servlet implementation class ServletArea
 */
@WebServlet("/administrador/servlet-area")
public class ServletArea extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public static final int LEER_AREAS = 1; 
	public static final int ACTUALIZAR_AREAS  = 2; 
	public static final int BORRAR_AREAS  = 3; 
	public static final int LEER_AREA = 4; 
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletArea() {
        super();
        // TODO Auto-generated constructor stub
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
	    	int operacion = Integer.parseInt(request.getParameter("operacion")); 
	    	ManejadorArea a = new ManejadorArea(); 
	    	switch (operacion) {
		case LEER_AREAS:
		    response.getWriter().append(a.leerAreas()); 
		    break;
		case ACTUALIZAR_AREAS:
		    response.getWriter().append(a.actualizarArea(
			    Integer.parseInt(request.getParameter("codigo")), 
			    request.getParameter("descripcion"))); 
		    break;		
		case BORRAR_AREAS:
		    response.getWriter().append(a.borrarArea(
			    Integer.parseInt(request.getParameter("codigo"))));
		    break;
		case LEER_AREA:
		    response.getWriter().append(a.leerArea(
			    Integer.parseInt(request.getParameter("codigo"))));
		    break;
		default:
		    break;
		}
	}

}
