package com.kevin.modelos;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

public class Cirugia {
    private int codigo;
    private Date fecha;
    private boolean realizada;
    private int codigoTarifa;
    private String cuiPaciente; 
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
     * @param cuiPaciente
     */
    public Cirugia(int codigo, Date fecha, boolean realizada, int codigoTarifa, String cuiPaciente) {
	super();
	this.codigo = codigo;
	this.realizada = realizada;
	this.codigoTarifa = codigoTarifa;
	this.cuiPaciente = cuiPaciente;

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
    public String getCuiPaciente() {
        return cuiPaciente;
    }
    /**
     * @param cuiPaciente the codigoPaciente to set
     */
    public void setCodigoPaciente(String cuiPaciente) {
        this.cuiPaciente = cuiPaciente;
    }
    /**
     * @param fecha
     * @param realizada
     * @param codigoTarifa
     * @param cuiPaciente
     */
    public Cirugia( Date fecha, int codigoTarifa, String cuiPaciente) {
	super();
	this.fecha = fecha;
	this.codigoTarifa = codigoTarifa;
	this.cuiPaciente = cuiPaciente;
    }
    
    public Cirugia(HttpServletRequest request) {
	this(new Date(Long.parseLong(request.getParameter("fecha"))), 
		Integer.parseInt(request.getParameter("cirugia")), 
		(request.getParameter("paciente")));
    }
    
}
