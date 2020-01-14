<%@ page language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Medico</title>
 	<link href="../icons/farmacia.png" 										rel="shortcut icon">
	<link href="../scripts/alertifyjs/css/alertify.css" 					rel="stylesheet">
	<link href="../scripts/alertifyjs/css/themes/default.css" 	rel="stylesheet">
	 <link href="../bootstrap-4.4.1-dist/css/bootstrap.css" 		rel="stylesheet">
	 	<script src="../JQuery/jquery-3.4.1.js"										type="text/javascript"></script>
</head>
<body>
	${encabezado}  <!--  encabezado general -->
	 <c:import var = "encabezadoCajero" url = "encabezado-cajero.html" charEncoding="UTF-8" scope="application"></c:import>
	${encabezadoCajero}
	
	<input 	id="fecha-consulta ocultable" 	class="form-control"  			type="date"  			required/><br> <!-- control de fecha -->

	
	<div id="tablaPacientes">
	
	</div>
	
	<button 	id="botonPagarCuenta" 				type="button" class="btn btn-success">Pagar Cuenta</button>
	${foother}
	
	<script src="../scripts/cajero/cajero.js">											</script>
	<script src ="../scripts/filtro-tabla.js"> 												</script>
	<script src="../scripts/alertifyjs/alertify.js" 								type="text/javascript"></script>

	<script src="../bootstrap-4.4.1-dist/js/bootstrap.js"	type="text/javascript"></script>
</body>
