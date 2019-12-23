package com.kevin.modelos;

public class Area {
    public static final int ADMINISTRACION = 1; 
    public static final int RECURSOS_HUMANOS = 2; 
    public static final int FARMACIA= 3; 
    public static final int MEDICOS= 4; 
    public static final int ENFERMEROS= 5;
    public static final int CAJA = 7; 
    
    private int modulo; 
    private String descripcion;
    
    /**
     * Crea una nueva area vacia
     */
    public Area() {
	super();
    }
    
    /**
     * @param modulo
     * @param descripcion
     */
    public Area(int modulo, String descripcion) {
	super();
	this.modulo = modulo;
	this.descripcion = descripcion;
    }

    /**
     * @return the modulo
     */
    public int getModulo() {
        return modulo;
    }

    /**
     * @param modulo the modulo to set
     */
    public void setModulo(int modulo) {
        this.modulo = modulo;
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
     


}
