<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Administrador</title>
 <link rel="shortcut icon" href="../icons/farmacia.png" />
  <link href="../bootstrap-4.4.1-dist/css/bootstrap.css" 	rel="stylesheet" />
  	<script src="../JQuery/jquery-3.4.1.js"										type="text/javascript"></script>
</head>
<body>
	${encabezado}
	 <c:import var = "encabezadoSecretaria" url = "encabezado-secretaria.html" charEncoding="UTF-8" scope="application"></c:import>
	${encabezadoSecretaria}
	
	<c:import var = "formularioPaciente" url = "form-registro-paciente.html" charEncoding="UTF-8" scope="application"></c:import>
	${formularioPaciente}
	
	<div id="resultado" class= "table"></div>
	
	${foother}
	
	<script src="../scripts/secretaria/secretaria.js"></script>
	<script src="../scripts/alertifyjs/alertify.js" 								type="text/javascript"></script>
	<script src="../Propper/propper.js"								type="text/javascript"></script>
	<script src="../bootstrap-4.4.1-dist/js/bootstrap.js"				type="text/javascript"></script>
</body>
