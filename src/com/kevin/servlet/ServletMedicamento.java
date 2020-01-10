package com.kevin.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kevin.manejadores.ManejadorFarmacia;
import com.kevin.modelos.Medicamento;

/**
 * Servlet implementation class ServletMedicamento
 */
@WebServlet("/farmacia/medicamento")
public class ServletMedicamento extends HttpServlet {
    	private static final long serialVersionUID = 1L;
	private static final int REGISTRO_MEDICAMENTO = 1;
	private static final int CONSULTAR_MEDICAMENTOS = 2;
	private static final int COMPRAR_MEDICAMENTOS = 3;
	private static final int CONSULTAR_INVENTARIO = 4;
	private static final int REGISTRAR_ACTUALIZACION = 5;
	
	
	       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletMedicamento() {
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
	    int operacion= Integer.parseInt(request.getParameter("operacion"));
	    ManejadorFarmacia farmacia = new ManejadorFarmacia(); 
	    switch (operacion) {
	    case REGISTRO_MEDICAMENTO:
		Medicamento medicamento = new Medicamento(request);
		String mensaje = farmacia.registrarMedicamento(medicamento);
		response.getWriter().append(mensaje);
		break;
	    case CONSULTAR_MEDICAMENTOS:
		response.getWriter().append(farmacia.agregarRegistrosDeMedicamentos());
		break;
	    case COMPRAR_MEDICAMENTOS:
		response.getWriter().append(farmacia.comprarMedicamento(
			Integer.parseInt(request.getParameter("idMedicamento")),
			Integer.parseInt(request.getParameter("cantidad")), 
			(Integer) (request.getSession().getAttribute("user")), 
			new Date(Long.parseLong(request.getParameter("fecha"))),
			null));
		break;
	    case CONSULTAR_INVENTARIO:
		response.getWriter().append(farmacia.consultarInventario());
		break;
	    case REGISTRAR_ACTUALIZACION:
		try {
		    response.getWriter().append(farmacia.registrarActualizacion(request));
		} catch (NumberFormatException | SQLException e) {
		    e.printStackTrace();
		}
		break;
	    default:
		break;
	    }
		
	}

}
