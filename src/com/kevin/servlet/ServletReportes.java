package com.kevin.servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kevin.reportes.ControladorDeReportes;

import net.sf.jasperreports.engine.JasperPrint;

/**
 * Servlet implementation class ServletReportes
 */
@WebServlet(urlPatterns = {"/farmacia/reportes", "/recursos-humanos/reportes", "/administrador/reportes"})
public class ServletReportes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final int REPORTE_MEDICAMENTOS = 1; 
	private static final int REPORTE_GANANCIAS_MEDICAMENTOS= 2;
	private static final int REPORTE_VENTAS_POR_EMPLEADO=3;
	    
	private static final int REPORTE_EMPLEADOS_CONTRATADOS= 4;
	private static final int REPORTE_EMPLEADOS_RETIRADOS= 5;
	private static final int REPORTE_DE_MEDICOS= 6;
	private static final int REPORTE_DE_MEDICOS_CON_PACIENTES= 7;
	private static final int REPORTE_DE_MEDICOS_SIN_PACIENTES= 8;
	
	    
	private static final int REPORTE_INGRESOS_MONETARIOS= 9;
	private static final int REPORTE_EGRESOS_MONETARIOS= 10;
	private static final int REPORTE_GANANCIAS= 11;
	
	private static final String PATH_REPORTE_MEDICAMENTOS= "ReporteDeMedicamentos.pdf";
	private static final String PATH_REPORTE_GANANCIAS_MEDICAMENTOS= "ReporteGananciasMedicamentos.pdf";
	private static final String PATH_REPORTE_VENTAS_POR_EMPLEADO= "MasterVentasEmpleado.pdf";
	    
	    private static final String PATH_REPORTE_EMPLEADOS_CONTRATADOS= "EmpleadosContratados.pdf";
	    private static final String PATH_REPORTE_EMPLEADOS_RETIRADOS= "EmpleadosDespedidos.pdf";
	    private static final String PATH_REPORTE_DE_MEDICOS= "ReporteMedicos.pdf";
	    private static final String PATH_REPORTE_DE_MEDICOS_CON_PACIENTES= "ReporteMedicosConPacientes.pdf";
	    private static final String PATH_REPORTE_DE_MEDICOS_SIN_PACIENTES= "ReporteMedicosSinPacientes.pdf";
	    
	    private static final String PATH_REPORTE_INGRESOS_MONETARIOS= "ReporteDeIngresos.pdf";
	    private static final String PATH_REPORTE_EGRESOS_MONETARIOS= "ReporteDeEgresos.pdf";
	    private static final String PATH_REPORTE_GANANCIAS= "ReporteGananciasTotales.pdf";
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletReportes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response, JasperPrint impresora, String archivoSalida) 
		throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    ControladorDeReportes controladorDeReportes = new ControladorDeReportes(); 
	    	int reporte =Integer.parseInt( request.getParameter("tipoDeReporte")); 
	    	Map<String, Object> parametros = new HashMap<String, Object>();
	    	JasperPrint impresora= null;
	    	try {
	    	switch (reporte) {
		case REPORTE_MEDICAMENTOS:
		    parametros.put("FILTRO_NOMBRE", request.getParameter("filtroNombre")); 
		    impresora = controladorDeReportes.generarImpresora(parametros, ControladorDeReportes.REPORTE_DE_MEDICAMENTOS); 
		    response.getWriter().append(controladorDeReportes.export(impresora,PATH_REPORTE_MEDICAMENTOS,
			    Integer.parseInt(request.getParameter("tipo")))); 
		    break;
		case REPORTE_GANANCIAS_MEDICAMENTOS:
		    parametros.put("FILTRO_NOMBRE", request.getParameter("filtroNombre")); 
		    parametros.put("FECHA_INICIAL", new Date(Long.parseLong(request.getParameter("fechaInicial")))); 
		    parametros.put("FECHA_FINAL", new Date(Long.parseLong(request.getParameter("fechaFinal")))); 
		    impresora = controladorDeReportes.generarImpresora(parametros, ControladorDeReportes.REPORTE_GANANCIAS_MEDICAMENTOS); 
		    response.getWriter().append(controladorDeReportes.export(impresora, PATH_REPORTE_GANANCIAS_MEDICAMENTOS, 
			    Integer.parseInt(request.getParameter("tipo")))); 
		    break;
		case REPORTE_VENTAS_POR_EMPLEADO:
		    parametros.put("FILTRO_CUI", request.getParameter("filtroCui")); 
		    parametros.put("FILTRO_NOMBRE_PERSONA", request.getParameter("filtroNombrePersona")); 
		    parametros.put("FECHA_INICIAL", new Date(Long.parseLong(request.getParameter("fechaInicial")))); 
		    parametros.put("FECHA_FINAL", new Date(Long.parseLong(request.getParameter("fechaFinal")))); 
		    parametros.put("FILTRO_NOMBRE_MEDICAMENTO", request.getParameter("filtroNombreMedicamento"));
		    impresora = controladorDeReportes.generarImpresora(parametros, ControladorDeReportes.REPORTE_VENTAS_POR_EMPLEADO); 
		    response.getWriter().append(controladorDeReportes.export(impresora, PATH_REPORTE_VENTAS_POR_EMPLEADO,
			    Integer.parseInt(request.getParameter("tipo")))); 
		    break;
		case REPORTE_EMPLEADOS_CONTRATADOS:
		    parametros.put("FILTRO_AREA", request.getParameter("filtroArea"));
		    parametros.put("FECHA_INICIAL", new Date(Long.parseLong(request.getParameter("fechaInicial")))); 
		    parametros.put("FECHA_FINAL", new Date(Long.parseLong(request.getParameter("fechaFinal")))); 
		    impresora = controladorDeReportes.generarImpresora(parametros, ControladorDeReportes.REPORTE_EMPLEADOS_CONTRATADOS); 
		    response.getWriter().append(controladorDeReportes.export(impresora, PATH_REPORTE_EMPLEADOS_CONTRATADOS,
			    Integer.parseInt(request.getParameter("tipo")))); 
		    break;
		case REPORTE_EMPLEADOS_RETIRADOS:
		    parametros.put("FILTRO_AREA", request.getParameter("filtroArea")); 
		    parametros.put("FECHA_INICIAL", new Date(Long.parseLong(request.getParameter("fechaInicial")))); 
		    parametros.put("FECHA_FINAL", new Date(Long.parseLong(request.getParameter("fechaFinal")))); 
		    impresora = controladorDeReportes.generarImpresora(parametros, ControladorDeReportes.REPORTE_EMPLEADOS_RETIRADOS); 
		    response.getWriter().append(controladorDeReportes.export(impresora, PATH_REPORTE_EMPLEADOS_RETIRADOS,
			    Integer.parseInt(request.getParameter("tipo")))); 
		    break;
		case REPORTE_DE_MEDICOS:
		    impresora = controladorDeReportes.generarImpresora(null, ControladorDeReportes.REPORTE_DE_MEDICOS); 
		    response.getWriter().append(controladorDeReportes.export(impresora, PATH_REPORTE_DE_MEDICOS, Integer.parseInt(request.getParameter("tipo")))); 
		    break;
		case REPORTE_DE_MEDICOS_CON_PACIENTES:
		    impresora = controladorDeReportes.generarImpresora(null, ControladorDeReportes.REPORTE_DE_MEDICOS_CON_PACIENTES); 
		    response.getWriter().append(controladorDeReportes.export(impresora, PATH_REPORTE_DE_MEDICOS_CON_PACIENTES,
			    Integer.parseInt(request.getParameter("tipo")))); 
		    break;
		case REPORTE_DE_MEDICOS_SIN_PACIENTES:
		    impresora = controladorDeReportes.generarImpresora(null, ControladorDeReportes.REPORTE_DE_MEDICOS_SIN_PACIENTES); 
		    response.getWriter().append(controladorDeReportes.export(impresora, PATH_REPORTE_DE_MEDICOS_SIN_PACIENTES,
			    Integer.parseInt(request.getParameter("tipo")))); 
		    break;
		case REPORTE_INGRESOS_MONETARIOS:
		    parametros.put("FECHA_INICIAL", new Date(Long.parseLong(request.getParameter("fechaInicial")))); 
		    parametros.put("FECHA_FINAL", new Date(Long.parseLong(request.getParameter("fechaFinal")))); 
		    impresora = controladorDeReportes.generarImpresora(parametros, ControladorDeReportes.REPORTE_INGRESOS_MONETARIOS); 
		    response.getWriter().append(controladorDeReportes.export(impresora, PATH_REPORTE_INGRESOS_MONETARIOS,
			    Integer.parseInt(request.getParameter("tipo")))); 
		    break;
		case REPORTE_EGRESOS_MONETARIOS:
		    parametros.put("FECHA_INICIAL", new Date(Long.parseLong(request.getParameter("fechaInicial")))); 
		    parametros.put("FECHA_FINAL", new Date(Long.parseLong(request.getParameter("fechaFinal")))); 
		    impresora = controladorDeReportes.generarImpresora(parametros, ControladorDeReportes.REPORTE_EGRESOS_MONETARIOS); 
		    response.getWriter().append(controladorDeReportes.export(impresora, PATH_REPORTE_EGRESOS_MONETARIOS, 
			    Integer.parseInt(request.getParameter("tipo")))); 
		    break;
		case REPORTE_GANANCIAS:
		    parametros.put("FECHA_INICIAL", new Date(Long.parseLong(request.getParameter("fechaInicial")))); 
		    parametros.put("FECHA_FINAL", new Date(Long.parseLong(request.getParameter("fechaFinal")))); 
		    impresora = controladorDeReportes.generarImpresora(parametros, ControladorDeReportes.REPORTE_GANANCIAS); 
		    response.getWriter().append(controladorDeReportes.export(impresora, PATH_REPORTE_GANANCIAS, 
			    Integer.parseInt(request.getParameter("tipo")))); 
		    break;
		default : 
			response.getWriter().append("<div class=\"alert alert-danger\" role=\"alert\">" + 
				"  Oooppsss! Ocurrio un error" + 
				"</div>");
		break;
		}
		} catch (NumberFormatException e) {
		    e.printStackTrace();
		    response.getWriter().append("<div class=\"alert alert-danger\">"
			           +" Hacen falta parametros" 
			           +" </div>"); 
		}
	    	
	}

}
