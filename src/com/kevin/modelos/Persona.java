package com.kevin.modelos;

public abstract class Persona {

    protected String cui;
    protected String nombre; 
    protected String direccion;
    /**
     * @return the cui
     */
    public String getCui() {
        return cui;
    }
    /**
     * @param cui the cui to set
     */
    public void setCui(String cui) {
        this.cui = cui;
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
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }
    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    /**
     * Crea una nueva persona
     * @param cui el codigo unico de identificacion 
     * @param nombre el nombre de la persona
     * @param direccion la direccion fisica de la persona
     */
    public Persona(String cui, String nombre, String direccion) {
	super();
	this.cui = cui;
	this.nombre = nombre;
	this.direccion = direccion;
    }
    
    public Persona() {
	super();
    }
    
    
}
