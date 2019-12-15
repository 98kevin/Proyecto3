package com.kevin.reportes;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.kevin.modelos.Main;
import com.kevin.servicio.DBConnection;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
public class ControladorDeReportes {
    
    public  void imprimirReporte(Map<String, Object> parametros, String pathReporte, String nombreDocumento) {
        try {
            JasperPrint jasperPrint1 = JasperFillManager.fillReport(getClass().getResourceAsStream(pathReporte), parametros, 
        	    DBConnection.getInstanceConnection().getConexion()); 
            JRPdfExporter exp = new JRPdfExporter();
            exp.setExporterInput(new SimpleExporterInput(jasperPrint1));
            exp.setExporterOutput(new SimpleOutputStreamExporterOutput(nombreDocumento));
            SimplePdfExporterConfiguration conf = new SimplePdfExporterConfiguration();
            exp.setConfiguration(conf);
            exp.exportReport();
        } catch (JRException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

 