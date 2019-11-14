package com.kevin.exceptions;

public class ManejoDePaciente extends Exception{

    /**
     * Serializacion de la clase
     */
    private static final long serialVersionUID = -6066689943944575656L;
    
    /**
     * Crea una nueva excepcion  con un mensaje
     * @param msg
     */
    public ManejoDePaciente(String msg) {
        super(msg);
    }


}
