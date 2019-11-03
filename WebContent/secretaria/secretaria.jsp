<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Administrador</title>
 <link rel="shortcut icon" href="../icons/farmacia.png" />
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
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
</body>
