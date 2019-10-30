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
	
	${foother}
	<script src="../scripts/admin.js"></script>
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
</body>
