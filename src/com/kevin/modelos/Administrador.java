package com.kevin.modelos;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import com.kevin.manejadores.ManejadorAdministrador;

public class Administrador extends Empleado{
    
    private String email; 
    private String password;
    
    private static long unDia = 86400000;

    public Administrador(String cui, String nombre, String direccion, double salario, int iggs, int irtra,
	    Date fechaDeVacaciones, Date fechaDeInicio, Date fechaDeFin, int areaDeTrabajo) {
	super(cui, nombre, direccion, salario, iggs, irtra, fechaDeVacaciones, fechaDeInicio, fechaDeFin, areaDeTrabajo);
    }
    
    public Administrador(HttpServletRequest req) {
	super.cui= req.getParameter("cui");
	super.nombre=req.getParameter("nombre");
	this.email= req.getParameter("mail");
	super.direccion=req.getParameter("direccion");
	super.iggs = Integer.parseInt(req.getParameter("iggs"));
	super.irtra = Integer.parseInt(req.getParameter("irtra"));
	super.fechaDeVacaciones = new Date(Long.parseLong(req.getParameter("vacaciones"))+ unDia);  //se le suma un dia 
	super.fechaDeInicio= new Date(Long.parseLong(req.getParameter("contrato"))+ unDia);  //se le suma un dia 
	super.fechaDeFin = null;
	super.salario = Double.parseDouble(req.getParameter("salario"));
	super.areaDeTrabajo = Integer.parseInt(req.getParameter("area"));
	this.password= req.getParameter("cui");
	ManejadorAdministrador manejador = new ManejadorAdministrador();
	manejador.registrarAdministador(this);
    }


    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

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
     * @param email
     * @param password
     */
    public Administrador(String cui, String nombre, String direccion, double salario, int iggs, int irtra,
	    Date fechaDeVacaciones, Date fechaDeInicio, Date fechaDeFin, int areaDeTrabajo, String email,
	    String password) {
	super(cui, nombre, direccion, salario, iggs, irtra, fechaDeVacaciones, fechaDeInicio, fechaDeFin,
		areaDeTrabajo);
	this.email = email;
	this.password = password;
    }


}
