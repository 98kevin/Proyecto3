<%@ page language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Medico</title>
 	<link href="../icons/farmacia.png" 										rel="shortcut icon" />
	<link href="../scripts/alertifyjs/css/alertify.css" 					rel="stylesheet">
	<link href="../scripts/alertifyjs/css/themes/default.css" 	rel="stylesheet">
	 <link href="../bootstrap-4.4.1-dist/css/bootstrap.css" 		rel="stylesheet" />
</head>
<body>
	${encabezado}  <!--  encabezado general -->
	 <c:import var = "encabezadoMedico" url = "encabezado-medico.html" charEncoding="UTF-8" scope="application"></c:import>
	${encabezadoMedico}
	
	<div id="tablaResultados">
	
	</div>
	
	<div id="tablaMedicamentos">
	
	</div>
	
	<input 	id="fecha-consulta" 	class="form-control"  			type="date"  			required/><br> <!-- control de fecha -->
	
	<form 	id="formConsulta">
	<div 											class="form-check">
	<input 	id="internado"			class="form-check-input "	type="checkbox">
	<label class="form-check-label">Internado	</label>
	</div>
	</form>
	
	<div 		id="tablaDeMedicos">
	</div>
	
	<div		id="tablaDeMedicosEspecialistas">
	</div>
	
	<div 		id="tablaDeEnfermeras">
	</div>
	
	<div 		id="tablaDeCirugias">
	</div>
	
	<div 		id="habitacion">
	</div>
	
	<br><br>
	<button id="botonAgregarMedicamento"		class="btn btn-primary center form-control">Aceptar								</button>
	<button id="btonAsignarMedicamentos"		class="btn btn-primary center form-control">Asignar Medicamentos	</button>
	<button id="btnAsignarCirugia"						class="btn btn-primary center form-control">Asingar Cirugia				</button>
	
	${foother}
	
	<script src="../scripts/medico/medico.js">											</script>
	<script src ="../scripts/filtro-tabla.js"> 												</script>
	<script src="../scripts/alertifyjs/alertify.js" 								type="text/javascript"></script>
	<script src="../JQuery/jquery-3.4.1.js"										type="text/javascript"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.15.0/popper.min.js"										type="text/javascript"></script>
	<script src="../bootstrap-4.4.1-dist/js/bootstrap.js"	type="text/javascript"></script>
	
 
</body>
