<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Farmacia</title>
 <link rel="shortcut icon" href="../icons/farmacia.png" />
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

	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
</body>
