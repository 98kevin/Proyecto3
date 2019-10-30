package com.kevin.modelos;

import java.sql.Date;


/**
 * Clase Empleado 
 * Se encarga del manejo y validacion de los empleados 
 * @author kevin
 *
 */
public class Empleado extends Persona{
    
    protected double salario;
    protected int iggs;
    protected int irtra; 
    protected Date fechaDeVacaciones;
    protected Date fechaDeInicio; 
    protected Date fechaDeFin;
    protected int areaDeTrabajo;

    /**
     * @param cui
     * @param nombre
     * @param direccion
     * @param salario
     * @param iggs
     * @param irtra
     * @param fechaDeVacaciones
     * @param fechaDeInicio
     * @param fechaDeFin
     * @param areaDeTrabajo
     */
    public Empleado(String cui, String nombre, String direccion, double salario, int iggs, int irtra,
	    Date fechaDeVacaciones, Date fechaDeInicio, Date fechaDeFin, int areaDeTrabajo) {
	super(cui, nombre, direccion);
	this.salario = salario;
	this.iggs = iggs;
	this.irtra = irtra;
	this.fechaDeVacaciones = fechaDeVacaciones;
	this.fechaDeInicio = fechaDeInicio;
	this.fechaDeFin = fechaDeFin;
	this.areaDeTrabajo = areaDeTrabajo;
    }
    
    
    public Empleado(String cui, String nombre, String direccion) {
	super(cui, nombre, direccion);
    }
    
    public Empleado() {
	super();
    }
    /**
     * @return the salario
     */
    public double getSalario() {
        return salario;
    }

    /**
     * @param salario the salario to set
     */
    public void setSalario(double salario) {
        this.salario = salario;
    }

    /**
     * @return the iggs
     */
    public int getIggs() {
        return iggs;
    }

    /**
     * @param iggs the iggs to set
     */
    public void setIggs(int iggs) {
        this.iggs = iggs;
    }

    /**
     * @return the irtra
     */
    public int getIrtra() {
        return irtra;
    }

    /**
     * @param irtra the irtra to set
     */
    public void setIrtra(int irtra) {
        this.irtra = irtra;
    }

    /**
     * @return the fechaDeVacaciones
     */
    public Date getFechaDeVacaciones() {
        return fechaDeVacaciones;
    }

    /**
     * @param fechaDeVacaciones the fechaDeVacaciones to set
     */
    public void setFechaDeVacaciones(Date fechaDeVacaciones) {
        this.fechaDeVacaciones = fechaDeVacaciones;
    }

    /**
     * @return the fechaDeInicio
     */
    public Date getFechaDeInicio() {
        return fechaDeInicio;
    }

    /**
     * @param fechaDeInicio the fechaDeInicio to set
     */
    public void setFechaDeInicio(Date fechaDeInicio) {
        this.fechaDeInicio = fechaDeInicio;
    }

    /**
     * @return the fechaDeFin
     */
    public Date getFechaDeFin() {
        return fechaDeFin;
    }

    /**
     * @param fechaDeFin the fechaDeFin to set
     */
    public void setFechaDeFin(Date fechaDeFin) {
        this.fechaDeFin = fechaDeFin;
    }

    /**
     * @return the areaDeTrabajo
     */
    public int getAreaDeTrabajo() {
        return areaDeTrabajo;
    }

    /**
     * @param areaDeTrabajo the areaDeTrabajo to set
     */
    public void setAreaDeTrabajo(int areaDeTrabajo) {
        this.areaDeTrabajo = areaDeTrabajo;
    }



    

}
