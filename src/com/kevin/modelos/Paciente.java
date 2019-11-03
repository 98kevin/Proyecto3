package com.kevin.modelos;

import javax.servlet.http.HttpServletRequest;

public class Paciente extends Persona{

    private String nit;
    private boolean internado; 
    private double saldo;
    /**
     * @return the nit
     */
    public String getNit() {
        return nit;
    }
    /**
     * @param nit the nit to set
     */
    public void setNit(String nit) {
        this.nit = nit;
    }
    /**
     * @return the internado
     */
    public boolean isInternado() {
        return internado;
    }
    /**
     * @param internado the internado to set
     */
    public void setInternado(boolean internado) {
        this.internado = internado;
    }
    /**
     * @return the saldo
     */
    public double getSaldo() {
        return saldo;
    }
    /**
     * @param saldo the saldo to set
     */
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    /**
     * @param cui
     * @param nombre
     * @param direccion
     * @param nit
     * @param internado
     * @param saldo
     */
    public Paciente(String cui, String nombre, String direccion, String nit, boolean internado, double saldo) {
	super(cui, nombre, direccion);
	this.nit = nit;
	this.internado = internado;
	this.saldo = saldo;
    }
    /**
     * @param cui
     * @param nombre
     * @param direccion
     * @param nit
     */
    public Paciente(String cui, String nombre, String direccion, String nit) {
	super(cui, nombre, direccion);
	this.nit = nit;
    } 

    
    /**
     * Genera un nuevo paciente usando un request como parametro. 
     * @param request
     */
    public Paciente(HttpServletRequest request) {
	this(request.getParameter("cui"),
		request.getParameter("nombre"), 
		request.getParameter("direccion"), 
		request.getParameter("nit")); 
    }
    
}
