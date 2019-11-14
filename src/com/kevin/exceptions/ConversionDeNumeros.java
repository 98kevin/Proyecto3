package com.kevin.exceptions;

public class ConversionDeNumeros extends NumberFormatException{

    /**
     * Serializacion de la clase
     */
    private static final long serialVersionUID = -6547988198316671626L;

    /**
     * Crea una excepcion ConversionDeNumeros
     * @param mensaje
     */
    public ConversionDeNumeros(String mensaje) {
	super(mensaje);
    }
}
