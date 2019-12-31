package com.kevin.modelos;

import java.sql.Date;
import java.sql.SQLException;

import com.kevin.manejadores.ManejadorFarmacia;
import com.kevin.reportes.ControladorDeReportes;

public class Main {
    public static final int CONSULTA_AREAS=1;
    public static final int CONSULTAR_MODULOS=2;
    public static final int CONSULTAR_EMPLEADOS=3;
    public static final int CONSULTAR_TARIFAS=4;
    public static final int REGISTRAR_CIRUGIA = 5;
    
    public static final boolean INGRESOS = true;
    public static final boolean EGRESOS= false;
    
    
    public static void main(String args[])  {
	/*
	Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("FILTRO_NOMBRE", "a");
	new ControladorDeReportes().imprimirReporte(parametros,   //parametros
		"/ReporteDeMedicamentos.jasper", //reporte
		"ReporteDeMedicametnos.pdf");  //documento		        
		
	ControladorDeReportes reportes = new ControladorDeReportes(); 		
	      DataBeanList DataBeanList = new DataBeanList();
	      ArrayList<DataBean> dataList = DataBeanList.getDataBeanList();
	      JRBeanCollectionDataSource beanColDataSource = new 
	         JRBeanCollectionDataSource(dataList);
	     reportes.generarReporteConSubReporte(ManejadorFarmacia.REPORTE_MAESTRO_GANANCIAS_MEDICAMENTOS,
		     ManejadorFarmacia.SUB_REPORTE_GANANCIAS_MEDICAMENTOS, dataList, "ejemplo de Reporte.pdf", ControladorDeReportes.PDF);
	 */ 
	     

	      ManejadorFarmacia farmacia = new ManejadorFarmacia(); 
	      try {
		  farmacia.reporteDeGanancias("",Date.valueOf("2019-09-20"),  Date.valueOf("2019-12-25"), "reporteDeVentas.pdf", ControladorDeReportes.PDF);
	    } catch (SQLException e) {
		System.out.println("Causa"  + e.getCause());
		e.printStackTrace();
	    }

	

    }    
}
