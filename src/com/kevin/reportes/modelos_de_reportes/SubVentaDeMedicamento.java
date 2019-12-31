/**
 * 
 */
package com.kevin.reportes.modelos_de_reportes;

import java.sql.Date;

/**
 * @author kevin
 *
 */
public class SubVentaDeMedicamento {
    private Date fechaDeVenta;
    private int cantidadVendida; 
    private double costoDelMedicamento; 
    private double precioAlCliente; 
    private double ganancia;
    /**
     * @param fechaDeVenta
     * @param cantidadVendida
     * @param costoDelMedicamento
     * @param precioAlCliente
     * @param ganancia
     */
    public SubVentaDeMedicamento(Date fechaDeVenta, int cantidadVendida, double costoDelMedicamento,
	    double precioAlCliente, double ganancia) {
	super();
	this.fechaDeVenta = fechaDeVenta;
	this.cantidadVendida = cantidadVendida;
	this.costoDelMedicamento = costoDelMedicamento;
	this.precioAlCliente = precioAlCliente;
	this.ganancia = ganancia;
    }
    
    
    /**
     * @return the fechaDeVenta
     */
    public Date getFechaDeVenta() {
        return fechaDeVenta;
    }
    /**
     * @param fechaDeVenta the fechaDeVenta to set
     */
    public void setFechaDeVenta(Date fechaDeVenta) {
        this.fechaDeVenta = fechaDeVenta;
    }
    /**
     * @return the cantidadVendida
     */
    public int getCantidadVendida() {
        return cantidadVendida;
    }
    /**
     * @param cantidadVendida the cantidadVendida to set
     */
    public void setCantidadVendida(int cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }
    /**
     * @return the costoDelMedicamento
     */
    public double getCostoDelMedicamento() {
        return costoDelMedicamento;
    }
    /**
     * @param costoDelMedicamento the costoDelMedicamento to set
     */
    public void setCostoDelMedicamento(double costoDelMedicamento) {
        this.costoDelMedicamento = costoDelMedicamento;
    }
    /**
     * @return the precioAlCliente
     */
    public double getPrecioAlCliente() {
        return precioAlCliente;
    }
    /**
     * @param precioAlCliente the precioAlCliente to set
     */
    public void setPrecioAlCliente(double precioAlCliente) {
        this.precioAlCliente = precioAlCliente;
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

    
}
