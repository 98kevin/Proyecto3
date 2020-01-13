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
    	
    	//POST
	private static final int REGISTRO_MEDICAMENTO = 1;
	private static final int CONSULTAR_MEDICAMENTOS = 2;
	private static final int COMPRAR_MEDICAMENTOS = 3;
	private static final int CONSULTAR_INVENTARIO = 4;
	private static final int REGISTRAR_ACTUALIZACION = 5;
	private static final int ACTUALIZAR_MEDICAMENTO = 6;
	
	//GET 
	private static final int LEER_MEDICAMENTOS= 1; 
	private static final int LEER_MEDICAMENTO= 2; 
	
	
	       
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
	    int operacion= Integer.parseInt(request.getParameter("operacion"));
	    ManejadorFarmacia f = new ManejadorFarmacia(); 
	    switch (operacion) {
	    case LEER_MEDICAMENTOS:
		response.getWriter().append(f.leerMedicamentos()); 
		break;
	    case LEER_MEDICAMENTO:
		response.getWriter().append(f.leerMedicamentoEnFormulario((
			Integer.parseInt(request.getParameter("codigo"))))); 
		break;
	    default:
		break;
	    }
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
	    case ACTUALIZAR_MEDICAMENTO: 
		response.getWriter().append(farmacia.actualizarMedicamento(
			Integer.parseInt(request.getParameter("codigo")), 
			request.getParameter("nombre"), 
			Double.parseDouble(request.getParameter("costo")), 
			Double.parseDouble(request.getParameter("precio")),
			Integer.parseInt(request.getParameter("cantidadMinima")))); 
		break; 
	    default:
		break;
	    }
		
	}

}
