<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Recursos Humanos</title>
 	<link rel="shortcut icon" href="../icons/farmacia.png" />
 	<link href="../scripts/alertifyjs/css/alertify.css" 					rel="stylesheet">
	<link href="../scripts/alertifyjs/css/themes/default.css" 	rel="stylesheet">
	 <link href="../bootstrap-4.4.1-dist/css/bootstrap.css" 		rel="stylesheet" />
	 	<script src="../JQuery/jquery-3.4.1.js"										type="text/javascript"></script>
</head>
<body>
	${encabezado}
	 <c:import var = "encabezadoRecursosHumanos" 		url = "encabezado-recursos-humanos.html" 		charEncoding="UTF-8" scope="application">	</c:import>
	${encabezadoRecursosHumanos}
		<c:import var = "formMedicoEspecialista" 					url = "form-registro-medico-especialista.html" 	charEncoding="UTF-8" scope="page">				</c:import>
	${formMedicoEspecialista}
	<c:import var = "formContratacion" 								url = "form-contratacion-empleado.html" 			charEncoding="UTF-8" scope="page">				</c:import>
	${formContratacion}
	
	
		<div id='filtrosReporteEmpleadosContratados'> 
		<div  class="input-group">
				<input type="text" class="form-control" 	id="filtroAreaContratados" 										placeholder = 'Area...' >
				<input type="date" class='form-control'  	id='controlFechaInicialContratados' >
				<input type="date" class='form-control'		id="controlFechaFinalContratados" >
		</div>
		<br>
		<div class='btn-group'>
				 <input id="generarReporteContratados" type='button' class="btn btn-primary" 	value= 'Generar Reporte'>
				<input id="exportarReporteContratados" type='button' class="btn btn-info" 			value= 'Exportar'>
		</div>
	</div>
	
	
	<div id='filtrosReporteEmpleadosDespedidos'> 
		<div  class="input-group">
				<input type="text" class="form-control" 	id="filtroAreaDespedidos" 										placeholder = 'Area...' >
				<input type="date" class='form-control'  	id='controlFechaInicialDespedidos' >
				<input type="date" class='form-control'		id="controlFechaFinalDespedidos" >
		</div>
		<br>
		<div class='btn-group'>
				 <input id="generarReporteDespedidos" type='button' class="btn btn-primary" 	value= 'Generar Reporte'>
				<input id="exportarReporteDespedidos" type='button' class="btn btn-info" 			value= 'Exportar'>
		</div>
	</div>
	
	
		<div id='filtrosReporteMedicos'> 
		<div  class="input-group">
				<select id= 'filtroMedicos'>
					<option value = '6'>Todos</option>
					<option  value = '7'>Con Pacientes</option>
					<option  value = '8'>Sin Pacientes</option>
				</select>
				<input id="generarReporteMedicos" type='button' class="btn btn-primary" 	value= 'Generar Reporte'>
				<input id="exportarReporteMedicos" type='button' class="btn btn-info" 			value= 'Exportar'>
		</div>
	</div>
	
	<div id = 'reporte'>
	
	</div>
	
	<div			id = "empleados">
	</div>

	
	${foother}
	
	<script src="../scripts/RecursosHumanos/recursos-humanos.js">				</script>
	<script src="../scripts/alertifyjs/alertify.js" 								type="text/javascript"></script>
	<script src="../Propper/propper.js"										type="text/javascript"></script>
	<script src="../bootstrap-4.4.1-dist/js/bootstrap.js"				type="text/javascript"></script>
</body>
