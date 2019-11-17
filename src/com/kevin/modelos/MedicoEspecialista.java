package com.kevin.modelos;

public class MedicoEspecialista extends Persona {
    
    private int colegiado;

    /**
     * @return the colegiado
     */
    public int getColegiado() {
        return colegiado;
    }

    /**
     * @param colegiado the colegiado to set
     */
    public void setColegiado(int colegiado) {
        this.colegiado = colegiado;
    }

    /**
     * @param cui
     * @param nombre
     * @param direccion
     * @param colegiado
     */
    public MedicoEspecialista(String cui, String nombre, String direccion, int colegiado) {
	super(cui, nombre, direccion);
	this.colegiado = colegiado;
    }
    

}
