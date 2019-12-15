package com.kevin.modelos;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

public class Cirugia {
    private int codigo;
    private Date fecha;
    private boolean realizada;
    private int codigoTarifa;
    private int codigoPaciente; 
    /**
     * @return the codigo
     */
    public int getCodigo() {
        return codigo;
    }
    /**
     * @param codigo
     * @param fecha
     * @param realizada
     * @param codigoTarifa
     * @param codigoPaciente
     */
    public Cirugia(int codigo, Date fecha, boolean realizada, int codigoTarifa, int codigoPaciente) {
	super();
	this.codigo = codigo;
	this.realizada = realizada;
	this.codigoTarifa = codigoTarifa;
	this.codigoPaciente = codigoPaciente;

    }
    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }
    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    /**
     * @return the realizada
     */
    public boolean isRealizada() {
        return realizada;
    }
    /**
     * @param realizada the realizada to set
     */
    public void setRealizada(boolean realizada) {
        this.realizada = realizada;
    }
    /**
     * @return the codigoTarifa
     */
    public int getCodigoTarifa() {
        return codigoTarifa;
    }
    /**
     * @param codigoTarifa the codigoTarifa to set
     */
    public void setCodigoTarifa(int codigoTarifa) {
        this.codigoTarifa = codigoTarifa;
    }
    /**
     * @return the codigoPaciente
     */
    public int getCodigoPaciente() {
        return codigoPaciente;
    }
    /**
     * @param codigoPaciente the codigoPaciente to set
     */
    public void setCodigoPaciente(int codigoPaciente) {
        this.codigoPaciente = codigoPaciente;
    }
    /**
     * @param fecha
     * @param realizada
     * @param codigoTarifa
     * @param codigoPaciente
     */
    public Cirugia( Date fecha, int codigoTarifa, int codigoPaciente) {
	super();
	this.fecha = fecha;
	this.codigoTarifa = codigoTarifa;
	this.codigoPaciente = codigoPaciente;
    }
    
    
    public Cirugia(HttpServletRequest request) {
	this(new Date(Long.parseLong(request.getParameter("fecha"))), 
		Integer.parseInt(request.getParameter("cirugia")), 
		Integer.parseInt(request.getParameter("paciente")));
    }

    
}
