/**
 * 
 */
package com.kevin.reportes.modelos_de_reportes;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author kevin
 *
 */
public class ArregloDeMedicamentos {

    public ArrayList<VentaDeMedicamento> getDataBeanList() {
	SubVentaDeMedicamento subVenta1 = new SubVentaDeMedicamento(new Date(Calendar.getInstance().getTimeInMillis()), 5,10, 12, 10);
	SubVentaDeMedicamento subVenta2 = new SubVentaDeMedicamento(new Date(Calendar.getInstance().getTimeInMillis()), 10,10, 12, 20);
	SubVentaDeMedicamento subVenta3 = new SubVentaDeMedicamento(new Date(Calendar.getInstance().getTimeInMillis()), 4,10, 12, 8);
	List<SubVentaDeMedicamento> subLista1 = new ArrayList<SubVentaDeMedicamento>();
	subLista1.add(subVenta1);
	subLista1.add(subVenta2); 
	List<SubVentaDeMedicamento> subLista2 = new ArrayList<SubVentaDeMedicamento>();
	subLista2.add(subVenta3);
	subLista2.add(subVenta2); 
	ArrayList<VentaDeMedicamento> medicamentos = new ArrayList<VentaDeMedicamento>();
	medicamentos.add(new VentaDeMedicamento(10,"Medicamento1",20,10,40, subLista1)); 
	medicamentos.add(new VentaDeMedicamento(20,"Medicamento2",50,6,2, subLista2)); 
	return medicamentos;
    }
    
}
