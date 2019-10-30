package com.kevin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kevin.manejadores.ManejadorArea;
import com.kevin.manejadores.ManejadorModulo;
import com.kevin.modelos.Area;

/**
 * Servlet implementation class creacionDeArea
 */
@WebServlet("/administrador/creacionDeArea")
public class creacionDeArea extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public creacionDeArea() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    ManejadorModulo modulo = new ManejadorModulo(); 
	    	String [][] modulos = modulo.modulosRegistrados();
	    	int contador=0;
	    	int a = modulos.length;
	    	for(int i=0; i<a; i++) {
	    		 response.getWriter().append("<option value=\""+modulos[i][0]+"\">"+modulos[i][1]+"</option>");		   
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    System.out.println("Metodo post");
	  int   codigoModulo= Integer.parseInt(request.getParameter("codigo"));
	  String descripcion = request.getParameter("desc");
	  Area area = new Area(codigoModulo, descripcion);
	 ManejadorArea manejador = new ManejadorArea(); 
	 manejador.registrarArea(area);
	}

}
