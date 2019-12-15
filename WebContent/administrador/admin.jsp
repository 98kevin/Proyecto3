<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Administrador</title>
 <link rel="shortcut icon" href="../icons/farmacia.png" />
<link href="../scripts/alertifyjs/css/alertify.css" rel="stylesheet">
<link href="../scripts/alertifyjs/css/themes/default.css" rel="stylesheet">
  <link href="../bootstrap-4.4.1-dist/css/bootstrap.css" rel="stylesheet" />
</head>
<body>
	${encabezado}
	 <c:import var = "encabezadoAdministrador" url = "encabezado-administrador.html" charEncoding="UTF-8" scope="application"></c:import>
	${encabezadoAdministrador}
	
		<!-- Formulario de creacion de modulo -->
	<form>
	<input id="campoNombre" class="input"  type="text" hidden ="true" placeholder="nombre del modulo" required  />
	<input id='botonEnviar' class="btn btn-primary" type="button" hidden="true" value="Crear">
	</form>
	
	
	<!-- Formulario de creacion de area -->
	<form>
	<input id="campoDescripcion" class="input"  type="text" hidden ="true" placeholder="nombre del area" required  />
	<select id="modulos" hidden="true" >
	</select>
	<input id='botonEnviarArea' class="btn btn-primary" type="button" hidden="true" value="Crear">
	</form>
	
	<c:import var = "formEmpleado" url = "form-creacion-empleado.html" charEncoding="UTF-8" scope="page"></c:import>
	${formEmpleado}
	
		<c:import var = "formCirugia" url = "form-creacion-cirugia.html" charEncoding="UTF-8" scope="page"></c:import>
	${formCirugia}
	
	<c:import var = "formHabitacion" url = "form-creacion-habitacion.html" charEncoding="UTF-8" scope="page"></c:import>
	${formHabitacion}
	
	<c:import var= "fromTarifa"  url= "from-tarifa.html" charEncoding= "UTF-8" scope= "page"> </c:import>
	${fromTarifa}
	
	<div id="tablaResultados" class= "table"></div>
	
	${foother}
	
	<script src="../scripts/admin.js"></script>
	<script src="../scripts/alertifyjs/alertify.js" 								type="text/javascript"></script>
	<script src="../JQuery/jquery-3.4.1.js"										type="text/javascript"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.15.0/popper.min.js"										type="text/javascript"></script>
	<script src="../bootstrap-4.4.1-dist/js/bootstrap.js"				type="text/javascript"></script>
</body>
