package com.kevin.exceptions;

public class DataBaseException extends Exception {
    
    
    /**
     * Serializacion de la clase DataBaseException
     */
    private static final long serialVersionUID = -8039718022663544365L;

    public DataBaseException(String mensaje) {
	super(mensaje);
    }

}
