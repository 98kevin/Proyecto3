<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Enfermera</title>
<link href="../icons/farmacia.png"										rel="shortcut icon">
<link href="../scripts/alertifyjs/css/alertify.css" 					rel="stylesheet">
<link href="../scripts/alertifyjs/css/themes/default.css" 	rel="stylesheet">
<link href="../bootstrap-4.4.1-dist/css/bootstrap.css" 		rel="stylesheet">
</head>
<body>

	${encabezado}
	 <c:import var = "encabezadoEnfermera" url = "encabezado-enfermera.html" charEncoding="UTF-8" scope="application"></c:import>
	${encabezadoEnfermera}	
	 
	 <div	id = "pacientes"></div>
	 <input 	id="controlFecha" 	class="form-control"  				type="date"  			required/><br> <!-- control de fecha -->
	 <div 	id= "tablaMedicamentos"> </div>
	
	${foother}
	
	<script src="../scripts/enfermera/enfermera.js" 					type="text/javascript"></script>
	<script src="../scripts/alertifyjs/alertify.js" 								type="text/javascript"></script>
	<script src="../JQuery/jquery-3.4.1.js"										type="text/javascript"></script>
	<script src="../bootstrap-4.4.1-dist/js/bootstrap.js"				type="text/javascript"></script>
</body>
