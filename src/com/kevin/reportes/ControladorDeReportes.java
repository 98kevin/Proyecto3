package com.kevin.reportes;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.kevin.modelos.Main;
import com.kevin.servicio.DBConnection;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
/**
 * Clase controladora de los reportes 
 * @author kevin
 * @param <E> 
 */

public class ControladorDeReportes<E> {

    public static final int HTML = 1;
    public static final int PDF = 2;


    public  void reporteSimpleEnBaseDeDatos(Map<String, Object> parametros, String pathReporte, String nombreDocumento) {
        try {
            JasperPrint jasperPrint1 = JasperFillManager.fillReport(getClass().getResourceAsStream(pathReporte), parametros, 
        	    DBConnection.getInstanceConnection().getConexion()); 
            export(jasperPrint1, nombreDocumento, PDF);
        } catch (JRException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String generarReporteConSubReporte(String reporteMaestro, String subReporte, ArrayList<E> ventas, 
	String archivoSalida, int tipoReporte) {
	String respuesta =null;
	      try {
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ventas);
		  Map<String, Object> parameters = new HashMap<String, Object>();
		         parameters.put("subreportParameter", subReporte);
		         JasperPrint jasperPrint = JasperFillManager.fillReport(reporteMaestro, 
		             parameters, dataSource);
		         respuesta = export(jasperPrint, archivoSalida, tipoReporte); 
	      } catch (JRException e) {
	         e.printStackTrace();
	      }
	    return respuesta;
    }
    
    
    public String export(JasperPrint impresoraJasper, String archivoSalida, int formatoDeReporte) throws JRException {
	    switch (formatoDeReporte) {
	        case HTML:
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
	            HtmlExporter exporter = new HtmlExporter();
	            exporter.setExporterInput(new SimpleExporterInput(impresoraJasper));
	            exporter.setExporterOutput(new SimpleHtmlExporterOutput(out));
		    exporter.exportReport();
		    return out.toString();
	        case PDF:
	            JRPdfExporter pdfExporter = new JRPdfExporter();
	            pdfExporter.setExporterInput(new SimpleExporterInput(impresoraJasper));
	            pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(archivoSalida));
	            pdfExporter.setConfiguration(new SimplePdfExporterConfiguration());
	            pdfExporter.exportReport();
	            return "reporte exportado correctamente";
	        default:
	            throw new JRException("No se reconoce el formato de salida");
	    }
	}
    
}

 