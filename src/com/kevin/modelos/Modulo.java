package com.kevin.modelos;

/**
 * Clase encargada del manejo de Modulos
 * @author kevin
 *
 */
public class Modulo {
    private String nombre; 
    
    /**
     * Crea un nuevo modulo vacio
     */
    public Modulo() {
	super();
    }
    
    /**
     * Crea un nuevo modulo con nombre
     * @param nombre El nombre del nuevo modulo
     */
    public Modulo(String nombre) {
	this.nombre=nombre;
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
    
    
}
