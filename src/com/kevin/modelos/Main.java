package com.kevin.modelos;

import java.util.HashMap;
import java.util.Map;

import com.kevin.reportes.ControladorDeReportes;

public class Main {
    public static final int CONSULTA_AREAS=1;
    public static final int CONSULTAR_MODULOS=2;
    public static final int CONSULTAR_EMPLEADOS=3;
    public static final int CONSULTAR_TARIFAS=4;
    public static final int REGISTRAR_CIRUGIA = 5;
    
    public static final boolean INGRESOS = true;
    public static final boolean EGRESOS= false;
    
    
    public static void main(String args[]) {
	Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("FILTRO_NOMBRE", "a");
	 new ControladorDeReportes().imprimirReporte(parametros,   //parametros
		"/ReporteDeMedicamentos.jasper", //reporte
		"ReporteDeMedicametnos.pdf");  //documento
    }    
}
