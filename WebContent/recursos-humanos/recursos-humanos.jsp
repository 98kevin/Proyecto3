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
</head>
<body>
	${encabezado}
	 <c:import var = "encabezadoRecursosHumanos" 		url = "encabezado-recursos-humanos.html" 		charEncoding="UTF-8" scope="application">	</c:import>
	${encabezadoRecursosHumanos}
		<c:import var = "formMedicoEspecialista" 					url = "form-registro-medico-especialista.html" 	charEncoding="UTF-8" scope="page">				</c:import>
	${formMedicoEspecialista}
	<c:import var = "formContratacion" 								url = "form-contratacion-empleado.html" 			charEncoding="UTF-8" scope="page">				</c:import>
	${formContratacion}
	
	<div			id = "empleados">
	</div>

	
	${foother}
	
	<script src="../scripts/RecursosHumanos/recursos-humanos.js">				</script>
	<script src="../scripts/RecursosHumanos/contratacion.js">							</script>	
	<script src="../scripts/alertifyjs/alertify.js" 								type="text/javascript"></script>
	<script src="../JQuery/jquery-3.4.1.js"										type="text/javascript"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.15.0/popper.min.js"										type="text/javascript"></script>
	<script src="../bootstrap-4.4.1-dist/js/bootstrap.js"				type="text/javascript"></script>
</body>
