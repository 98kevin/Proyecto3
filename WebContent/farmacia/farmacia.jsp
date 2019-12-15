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
</head>
<body>
	${encabezado}
	 <c:import var = "encabezadoFarmacia" url = "encabezado-farmacia.html" charEncoding="UTF-8" scope="application"></c:import>
	${encabezadoFarmacia}
	
	<c:import var = "formMedicamento" url = "form-medicamento.html" charEncoding="UTF-8" scope="page"></c:import>
	${formMedicamento}

	<div id="medicamentosSeleccionados" class="table"></div>
	
	<div id="tablaResultados" class= "table"></div>
	
	${foother}
	
	<script src="farmacia.js"></script>
	<script src="../scripts/filtro-tabla.js"></script>
	<script src="../scripts/alertifyjs/alertify.js" 					type="text/javascript"></script>
	<script src="../JQuery/jquery-3.4.1.js"							type="text/javascript"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.15.0/popper.min.js"							type="text/javascript"></script>
	<script src="../bootstrap-4.4.1-dist/js/bootstrap.js"	type="text/javascript"></script>
	
</body>
