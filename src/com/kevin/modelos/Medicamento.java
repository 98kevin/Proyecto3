package com.kevin.modelos;

import javax.servlet.http.HttpServletRequest;

public class Medicamento {

    private int codigo;
    private String nombre;
    private double costoCompra;
    private double precioVenta;
    private int cantidadExistente;
    private int cantidadMinima;
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
     * @return the costoCompra
     */
    public double getCostoCompra() {
        return costoCompra;
    }
    /**
     * @param costoCompra the costoCompra to set
     */
    public void setCostoCompra(double costoCompra) {
        this.costoCompra = costoCompra;
    }
    /**
     * @return the precioVenta
     */
    public double getPrecioVenta() {
        return precioVenta;
    }
    /**
     * @param precioVenta the precioVenta to set
     */
    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }
    /**
     * @return the cantidadExistente
     */
    public int getCantidadExistente() {
        return cantidadExistente;
    }
    /**
     * @param cantidadExistente the cantidadExistente to set
     */
    public void setCantidadExistente(int cantidadExistente) {
        this.cantidadExistente = cantidadExistente;
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
     * @param codigo
     * @param nombre
     * @param costoCompra
     * @param precioVenta
     * @param cantidadExistente
     * @param cantidadMinima
     */
    public Medicamento(int codigo, String nombre, double costoCompra, double precioVenta, int cantidadExistente,
	    int cantidadMinima) {
	super();
	this.codigo = codigo;
	this.nombre = nombre;
	this.costoCompra = costoCompra;
	this.precioVenta = precioVenta;
	this.cantidadExistente = cantidadExistente;
	this.cantidadMinima = cantidadMinima;
    }
    /**
     * @param nombre
     * @param costoCompra
     * @param precioVenta
     */
    public Medicamento(String nombre, double costoCompra, double precioVenta, int cantMinima) {
	super();
	this.nombre = nombre;
	this.costoCompra = costoCompra;
	this.precioVenta = precioVenta;
	this.cantidadMinima = cantMinima;
    }
    
    public Medicamento(HttpServletRequest request) {
	this(request.getParameter("nombre"),
		Double.parseDouble(request.getParameter("costo")), 
		Double.parseDouble(request.getParameter("precio")),
		Integer.parseInt(request.getParameter("minimo")));
    }
    
}
