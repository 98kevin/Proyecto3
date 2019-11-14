<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Medico</title>
 <link rel="shortcut icon" href="../icons/farmacia.png" />
<script src="../scripts/alertifyjs/alertify.js" type="text/javascript"></script>
<link href="../scripts/alertifyjs/css/alertify.css" rel="stylesheet">
<link href="../scripts/alertifyjs/css/themes/default.css" rel="stylesheet">
</head>
<body>
	${encabezado}  <!--  encabezado general -->
	 <c:import var = "encabezadoMedico" url = "encabezado-medico.html" charEncoding="UTF-8" scope="application"></c:import>
	${encabezadoMedico}
	
	<div id="tablaResultados" class= "table"></div>
	
	
	<form id="formConsulta">
		Fecha de consulta <input id="fecha-consulta" class="form-control"  type="date"  required  /><br>
		  <div class="form-check">
	    <input type="checkbox" class="form-check-input " id="internado">
	    <label class="form-check-label" for="exampleCheck1">Internado</label>
	  </div>
	</form>
	
	<div id="tablaDeMedicos">
	<h3>Medicos a asignar</h3>
	</div>
	
	<div id="tablaDeEnfermeras">
	<h3>Enfermeras a asignar</h3>
	</div>
	<div id="habitacion">
	<h3>Seleccion de habitacion</h3>
	</div>
	
	<br><br>
	<button id="botonAgregarMedicamento"class="btn btn-primary center form-control">Aceptar</button>	
		<button id="btonAsignarMedicamentos"class="btn btn-primary center form-control">Asignar Medicamentos</button>
	
	${foother}
	
	<script src="../scripts/medico/medico.js"></script>
	<script src ="../scripts/filtro-tabla.js"> </script>
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
 
</body>
