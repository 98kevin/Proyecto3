package com.kevin.modelos;

import javax.servlet.http.HttpServletRequest;

public class Tarifa {
    private int codigo; 
    private String descripcion; 
    private double costoHospital; 
    private double costoEspecialista; 
    private double precioCliente;
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
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }
    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    /**
     * @return the costoHospital
     */
    public double getCostoHospital() {
        return costoHospital;
    }
    /**
     * @param costoHospital the costoHospital to set
     */
    public void setCostoHospital(double costoHospital) {
        this.costoHospital = costoHospital;
    }
    /**
     * @return the costoEspecialista
     */
    public double getCostoEspecialista() {
        return costoEspecialista;
    }
    /**
     * @param costoEspecialista the costoEspecialista to set
     */
    public void setCostoEspecialista(double costoEspecialista) {
        this.costoEspecialista = costoEspecialista;
    }
    /**
     * @return the precioCliente
     */
    public double getPrecioCliente() {
        return precioCliente;
    }
    /**
     * @param precioCliente the precioCliente to set
     */
    public void setPrecioCliente(double precioCliente) {
        this.precioCliente = precioCliente;
    }
    /**
     * @param codigo
     * @param descripcion
     * @param costoHospital
     * @param costoEspecialista
     * @param precioCliente
     */
    public Tarifa(int codigo, String descripcion, double costoHospital, double costoEspecialista,
	    double precioCliente) {
	super();
	this.codigo = codigo;
	this.descripcion = descripcion;
	this.costoHospital = costoHospital;
	this.costoEspecialista = costoEspecialista;
	this.precioCliente = precioCliente;
    }
    /**
     * @param descripcion
     * @param costoHospital
     * @param costoEspecialista
     * @param precioCliente
     */
    public Tarifa(String descripcion, double costoHospital, double costoEspecialista, double precioCliente) {
	super();
	this.descripcion = descripcion;
	this.costoHospital = costoHospital;
	this.costoEspecialista = costoEspecialista;
	this.precioCliente = precioCliente;
    } 
    
    public Tarifa(HttpServletRequest request) {
	this(request.getParameter("descipcion"),
		Double.parseDouble(request.getParameter("costoHospital")),
		Double.parseDouble(request.getParameter("pagoEspecialista")),
		Double.parseDouble(request.getParameter("precioCliente")));
    }
    
    
}
