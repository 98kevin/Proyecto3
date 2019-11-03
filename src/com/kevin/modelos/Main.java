package com.kevin.modelos;

import com.kevin.servicio.DBConnection;

public class Main extends DBConnection{
    
    public static final int CONSULTA_AREAS=1;
    public static final int CONSULTAR_MODULOS=2;
    public static final int CONSULTAR_EMPLEADOS=3;
    public static final int CONSULTAR_TARIFAS=4;
    
    
    
    
    public static void main(String args[]) {
	new Main();
    }
    
    public Main() {
    }
    
    
}
