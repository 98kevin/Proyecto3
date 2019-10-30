<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Inicio de Sesion</title>
 <link rel="shortcut icon" href="../icons/farmacia.png" />
</head>
<body>
      <c:import var = "encabezado" url = "../encabezado.html" charEncoding="UTF-8" scope="application"></c:import>
	${encabezado}
	  <c:import var = "credenciales" url = "../credenciales.html" charEncoding="UTF-8" scope="page"></c:import>
	${credenciales}
	<c:import var = "foother" url ="../pie-de-pagina.html" charEncoding= "UTF-8" scope="application"> </c:import>
	${foother}
	<script src="../scripts/inicio-de-sesion.js"></script>
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
</body>
</html>