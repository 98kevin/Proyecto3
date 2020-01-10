package com.kevin.reportes;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.kevin.exceptions.ParameterException;
import com.kevin.modelos.Main;
import com.kevin.servicio.DBConnection;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
/**
 * Clase controladora de los reportes 
 * @author kevin
 */

public class ControladorDeReportes {

	public  static final int HTML = 1;
	public static final int PDF = 2;
    
    public static final String REPORTE_DE_MEDICAMENTOS= "/ReporteDeMedicamentos.jasper";
    public static final String REPORTE_GANANCIAS_MEDICAMENTOS= "/MasterGananciasMedicamentos.jasper";  
    public static final String REPORTE_VENTAS_POR_EMPLEADO= "/MasterVentasEmpleado.jasper";
    
    public static final String REPORTE_EMPLEADOS_CONTRATADOS= "/EmpleadosContratados.jasper";
    public static final String REPORTE_EMPLEADOS_RETIRADOS= "/EmpleadosDespedidos.jasper";
    public static final String REPORTE_DE_MEDICOS= "/ReporteMedicos.jasper";
    public static final String REPORTE_DE_MEDICOS_CON_PACIENTES= "/ReporteMedicosConPacientes.jasper";
    public static final String REPORTE_DE_MEDICOS_SIN_PACIENTES= "/ReporteMedicosSinPacientes.jasper";
    
    
    public static final String REPORTE_INGRESOS_MONETARIOS= "/ReporteDeIngresos.jasper";
    public static final String REPORTE_EGRESOS_MONETARIOS= "/ReporteDeEgresos.jasper";
    public static final String REPORTE_GANANCIAS= "/ReporteDeGanancias.jasper";
    
    private static final String MENSAJE_EXITO = 
	    		"<div class=\"alert alert-success\">"
	           +" Reporte exportado correctamente" 
	           +" </div>";
    
    private static final String MENSAJE_REPORTE_VACIO = 
		"<div class=\"alert alert-info\">"
		           +" El reporte se encuentra vacio" 
		           +" </div>";
    
    public  JasperPrint generarImpresora(Map<String, Object> parametros, String pathReporte) {
	JasperPrint jasperPrint= null; 
	try {
	    evaluarParametros(parametros);
            jasperPrint = JasperFillManager.fillReport(getClass().getResourceAsStream(pathReporte), parametros, 
        	    DBConnection.getInstanceConnection().getConexion()); 
        } catch (JRException ex) {
            ex.printStackTrace();
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch(ParameterException p) {
            p.printStackTrace(); 
        }
	return jasperPrint;
    }
    
    
    private void evaluarParametros(Map<String, Object> parametros) throws ParameterException {
	if(parametros.containsValue(null)) {
	    throw new ParameterException("Hace falta un parametro, para generar el reporte"); 
	}
	
    }


    public String export(JasperPrint impresoraJasper, String archivoSalida, int formatoDeReporte) {
	String resp = null;
	    switch (formatoDeReporte) {
	        case HTML:
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
	            HtmlExporter exporter = new HtmlExporter();
	            exporter.setExporterInput(new SimpleExporterInput(impresoraJasper));
	            exporter.setExporterOutput(new SimpleHtmlExporterOutput(out));
		try {
		    exporter.exportReport();
		} catch (JRException e) {
		    e.printStackTrace();
		}
		resp =  (out.toString().length() < 600) ? MENSAJE_REPORTE_VACIO : out.toString(); 
		break;
	        case PDF:
	            JRPdfExporter pdfExporter = new JRPdfExporter();
	            pdfExporter.setExporterInput(new SimpleExporterInput(impresoraJasper));
	            pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(archivoSalida));
	            pdfExporter.setConfiguration(new SimplePdfExporterConfiguration());
		try {
		    pdfExporter.exportReport();
		} catch (JRException e) {
		    e.printStackTrace();
		}
	            resp =  MENSAJE_EXITO;
	            break;
	         default : 
	             resp=  "<h3 class=\"text-warning\">No se reconoce el formato del reporte</h3>"; 
	             break;
	    }
	    return resp; 
	}

}

 