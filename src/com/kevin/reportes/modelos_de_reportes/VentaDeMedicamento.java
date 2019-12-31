/**
 * 
 */
package com.kevin.reportes.modelos_de_reportes;

import java.util.List;

/**
 * @author kevin
 *
 */
public class VentaDeMedicamento {
    private int codigo; 
    private String nombre; 
    private int cantidadEnExistencia; 
    private int cantidadMinima; 
    private double ganancia;
    private List<SubVentaDeMedicamento> subReportBeanList;
    
    public VentaDeMedicamento () {
	super(); 
    }
    
    /**
     * @param codigo
     * @param nombre
     * @param cantidadEnExistencia
     * @param cantidadMinima
     * @param ganancia
     * @param subReporteDeVentas
     */
    public VentaDeMedicamento(int codigo, String nombre, int cantidadEnExistencia, int cantidadMinima, double ganancia,
	    List<SubVentaDeMedicamento> subReportBeanList) {
	super();
	this.codigo = codigo;
	this.nombre = nombre;
	this.cantidadEnExistencia = cantidadEnExistencia;
	this.cantidadMinima = cantidadMinima;
	this.ganancia = ganancia;
	this.subReportBeanList = subReportBeanList;
    }

    /**
     * @return the codigo
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the cantidadEnExistencia
     */
    public int getCantidadEnExistencia() {
        return cantidadEnExistencia;
    }

    /**
     * @param cantidadEnExistencia the cantidadEnExistencia to set
     */
    public void setCantidadEnExistencia(int cantidadEnExistencia) {
        this.cantidadEnExistencia = cantidadEnExistencia;
    }

    /**
     * @return the cantidadMinima
     */
    public int getCantidadMinima() {
        return cantidadMinima;
    }

    /**
     * @param cantidadMinima the cantidadMinima to set
     */
    public void setCantidadMinima(int cantidadMinima) {
        this.cantidadMinima = cantidadMinima;
    }

    /**
     * @return the ganancia
     */
    public double getGanancia() {
        return ganancia;
    }

    /**
     * @param ganancia the ganancia to set
     */
    public void setGanancia(double ganancia) {
        this.ganancia = ganancia;
    }

    /**
     * @return the subReportBeanList
     */
    public List<SubVentaDeMedicamento> getSubReportBeanList() {
        return subReportBeanList;
    }

    /**
     * @param subReportBeanList the subReportBeanList to set
     */
    public void setSubReportBeanList(List<SubVentaDeMedicamento> subReportBeanList) {
        this.subReportBeanList = subReportBeanList;
    }
    
    
}
