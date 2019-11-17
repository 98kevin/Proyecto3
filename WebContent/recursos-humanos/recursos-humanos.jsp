<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Recursos Humanos</title>
 <link rel="shortcut icon" href="../icons/farmacia.png" />
</head>
<body>
	${encabezado}
	 <c:import var = "encabezadoRecursosHumanos" url = "encabezado-recursos-humanos.html" charEncoding="UTF-8" scope="application"></c:import>
	${encabezadoRecursosHumanos}
		<c:import var = "formMedicoEspecialista" url = "form-registro-medico-especialista.html" charEncoding="UTF-8" scope="page"></c:import>
	${formMedicoEspecialista}
	<c:import var = "formContratacion" url = "form-contratacion-empleado.html" charEncoding="UTF-8" scope="page"></c:import>
	${formContratacion}
	

	
	${foother}
	
	<script src="../scripts/RecursosHumanos/recursos-humanos.js"></script>
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
	<script src="../scripts/RecursosHumanos/contratacion.js"></script>
</body>
