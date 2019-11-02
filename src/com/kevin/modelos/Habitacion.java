package com.kevin.modelos;

import javax.servlet.http.HttpServletRequest;

public class Habitacion {
    
    public static final int CREAR= 1; 
    public static final int EDITAR= 2; 
    public static final int ELIMINAR= 3; 
    
    
    private int codigo; 
    private boolean ocupada; 
    private boolean habilitada;
    private double mantenimiento;
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
     * @return the ocupada
     */
    public boolean isOcupada() {
        return ocupada;
    }
    /**
     * @param ocupada the ocupada to set
     */
    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }
    /**
     * @return the habilitada
     */
    public boolean isHabilitada() {
        return habilitada;
    }
    /**
     * @param habilitada the habilitada to set
     */
    public void setHabilitada(boolean habilitada) {
        this.habilitada = habilitada;
    }
    /**
     * @return the mantenimiento
     */
    public double getMantenimiento() {
        return mantenimiento;
    }
    /**
     * @param mantenimiento the mantenimiento to set
     */
    public void setMantenimiento(double mantenimiento) {
        this.mantenimiento = mantenimiento;
    }
    /**
     * @param codigo
     * @param ocupada
     * @param habilitada
     * @param mantenimiento
     */
    public Habitacion(int codigo, boolean ocupada, boolean habilitada, double mantenimiento) {
	super();
	this.codigo = codigo;
	this.ocupada = ocupada;
	this.habilitada = habilitada;
	this.mantenimiento = mantenimiento;
    }
    /**
     * @param mantenimiento
     */
    public Habitacion(double mantenimiento) {
	super();
	this.mantenimiento = mantenimiento;
    } 
    
    public Habitacion( HttpServletRequest req) {
	this(Integer.parseInt(req.getParameter("precioMantenimiento")));
    } 
    

}
