<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Inicio de Sesion</title>
 <link rel="shortcut icon" href="../icons/farmacia.png" />
 <link href="../bootstrap-4.4.1-dist/css/bootstrap.css" rel="stylesheet" />
 <link href="../bootstrap-4.4.1-dist/css/">
</head>
<body>
      <c:import 	var = "encabezado" 	url = "../encabezado.html" 		charEncoding="UTF-8" scope="application"></c:import>
	${encabezado}
	  <c:import 	var = "credenciales" 	url = "../credenciales.html" 		charEncoding="UTF-8" scope="page"></c:import>
	${credenciales}
	<c:import 	var = "foother" 			url ="../pie-de-pagina.html" 	charEncoding= "UTF-8" scope="application"> </c:import>
	${foother}
	<script src="../scripts/inicio-de-sesion.js"></script>
	<script src="../scripts/alertifyjs/alertify.js" 								type="text/javascript"></script>
	<script src="../JQuery/jquery-3.4.1.js"										type="text/javascript"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.15.0/popper.min.js"										type="text/javascript"></script>
		<script src="../bootstrap-4.4.1-dist/js/bootstrap.js"	type="text/javascript"></script>
</body>
</html>