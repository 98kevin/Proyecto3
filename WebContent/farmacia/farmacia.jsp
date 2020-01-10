<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Farmacia</title>
 <link rel="shortcut icon" href="../icons/farmacia.png" />
  <link href="../bootstrap-4.4.1-dist/css/bootstrap.css" rel="stylesheet" />
  	<script src="../JQuery/jquery-3.4.1.js"							type="text/javascript"></script>
</head>
<c:import var="controlesFecha" url= "/general/controlesFecha.html" charEncoding= "UTF-8" scope="page"></c:import>
<c:import var = "encabezadoFarmacia" url = "encabezado-farmacia.html" charEncoding="UTF-8" scope="application"></c:import>
<body>
	${encabezado}
	${encabezadoFarmacia}
	
	<div id = "formularioFiltros">
		Filtar por Medicamento
		<input type="text" class="form-control" id="filtroNombre" >
		<input id="generarReporteMedicamentos" type='button' class="btn btn-primary" value= 'Generar Reporte'>
		<input id="btnExportarReporteMedicamentos" type='button' class="btn btn-info" value= 'Exportar Reporte'>
	</div>
	
	<div id='filtrosReporteGanancias'>
	<div class='input-group'>
		<input type="text" class="form-control" id="filtroNombreGanancias" placeholder= 'nombre del medicamento'>
		<input id='controlFechaInicial' class='ocultable form-control'  type="date" placeholder= 'fecha inicial'>
		<input id="controlFechaFinal" 	class='ocultable form-control' type="date" placeholder= 'fecha final'>
	</div>
	<div class='btn-group-vertical'>
		<input id="generarReporteGanancias" type='button' class="btn btn-primary" value= 'Generar Reporte'>
		<input id="btnExportarReporteGanancias" type='button' class="btn btn-info" value= 'Exportar Reporte'>
	</div>
	</div>
	
	<div id='filtrosReporteVentas'> 
		<div  class="input-group">
				<input type="text" class="form-control"  	id="filtroNombreMedicamentoVentas"  	placeholder = 'filtro por nombre de medicamento'>
				<input type="text" class="form-control" 	id="filtroNombreEmpleadoVentas" 			placeholder = 'filtro por nombre del empleado' >
				<input type="text" class="form-control" 	id="filtroCuiEmpleadoVentas" 					placeholder = 'filtro por cui empleado' >
				<input type="date" class='form-control'  	id='controlFechaInicialVentas'   					placeholder= 'fecha inicial'>
				<input type="date" class='form-control'		id="controlFechaFinalVentas" 	  				placeholder= 'fecha final'>
		</div>
		<br>
		<div class='btn-group'>
				 <input id="generarReporteVentas" type='button' class="btn btn-primary" value= 'Generar Reporte'>
				<input id="btnExportarReporteVentas" type='button' class="btn btn-info" value= 'Exportar Reporte'>
		</div>
	</div>
	
	<c:import var = "formMedicamento" url = "form-medicamento.html" charEncoding="UTF-8" scope="page"></c:import>
	${formMedicamento}
	
	<div id = 'form-fecha' class="input-group">
  		<div class="input-group-prepend">
    		<span class="input-group-text" id="">Fecha de la compra</span>
  		</div>
	  <input id='controlFecha' type="date" class="form-control">
	</div>

	<div id="medicamentosSeleccionados" class="table"></div>
	
	<div id="tablaResultados" class= "table"></div>
	
	<div id = "reporte"></div>
	

	
	${foother}
	
	<script src="farmacia.js"></script>
	<script src="../scripts/filtro-tabla.js"></script>
	<script src="../scripts/alertifyjs/alertify.js" 					type="text/javascript"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"		type="text/javascript"></script>
	<script src="../bootstrap-4.4.1-dist/js/bootstrap.js"	type="text/javascript"></script>
	
</body>
