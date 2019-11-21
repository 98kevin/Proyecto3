<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Enfermera</title>
 <link rel="shortcut icon" href="../icons/farmacia.png" />
<link href="../scripts/alertifyjs/css/alertify.css" rel="stylesheet">
<link href="../scripts/alertifyjs/css/themes/default.css" rel="stylesheet">
</head>
<body>
	${encabezado}
	 <c:import var = "encabezadoEnfermera" url = "encabezado-enfermera.html" charEncoding="UTF-8" scope="application"></c:import>
	${encabezadoEnfermera}
	
	
	
	
	
	${foother}
	
	<script src="../scripts/admin.js"></script>
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
	 <script src="../scripts/alertifyjs/alertify.js" type="text/javascript"></script>
</body>