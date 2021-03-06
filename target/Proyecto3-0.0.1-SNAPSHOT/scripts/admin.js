
//botones del panel superior
let botonCrear =  document.getElementById('crearModulo');
let botonArea = document.getElementById('crearArea');

//formulario de modulo
let campoNombre = document.getElementById('campoNombre');
let botonEnvio = document.getElementById('botonEnviar');

//formulario de area
let descripcion = document.getElementById('campoDescripcion');
let modulosDeArea = document.getElementById('modulos');
let botonCrearArea = document.getElementById('botonEnviarArea');
let botonCrearEmpleado = document.getElementById('enviarEmpleado');

//formulario de empleado
let botonMostrarFormNuevoEmpleado = document.getElementById('mostrarFormNuevoEmpleado');
let formEmpleado = document.getElementById('form-empleado');
formEmpleado.style.display="none";

//eventos de los botones
botonCrear.addEventListener('click',crearModulo);
botonEnvio.addEventListener('click', enviarDatos); 
botonArea.addEventListener('click', mostrarFormArea);
botonCrearArea.addEventListener('click', enviarArea);
botonCrearEmpleado.addEventListener('click', enviarEmpleado);
mostrarFormNuevoEmpleado.addEventListener('click', mostrarFormEmpleado);


//muestra los componentes de creacion de modulo
function crearModulo(){
    campoNombre.hidden=false;
    botonEnvio.hidden=false; 
}

//Envia los datos del nuevo modulo
function enviarDatos(){
	$.post('creacionDeModulo', {
		modulo : campoNombre.value
	}, function(responseText) {
        alert('creacion del modulo exitosa');
		campoNombre.hidden=true;
		botonEnvio.hidden=true; 
	});
}

//muestra los componentes de creacion de un area 
function mostrarFormArea(){
	console.log('metodo mostrar Form Area');
	descripcion.hidden=false;
	modulosDeArea.hidden=false;
	botonCrearArea.hidden = false;
	$.ajax({
		type: "GET",
		url: "creacionDeArea",
		success: function(responseText){
			modulosDeArea.innerHTML = responseText; 
			console.log(responseText);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(XMLHttpRequest);
			console.log(textStatus);
			console.log(errorThrown);
		}
	});
}

//envia los datos del area al servidor 
function enviarArea(){
	//capturamos el valor del modulo seleccionado
	let valorSeleccionado = modulosDeArea.options[modulosDeArea.selectedIndex].value; 
	console.log(valorSeleccionado);
	$.post('creacionDeArea', {
		codigo : valorSeleccionado,
		desc : descripcion.value
	}, function(responseText) {
        alert('creacion del area exitosa');
		descripcion.hidden=true;
		modulosDeArea.hidden=true;
		botonCrearArea.hidden = true;
	});
}

function mostrarFormEmpleado(){
	formEmpleado.style.display="block";
}



function enviarEmpleado(){
	let varCui = document.getElementById('cui');
	let varNombre = document.getElementById('nombre');
	let varEmail = document.getElementById('email');
	let varDireccion = document.getElementById('direccion');
	let varIggs = document.getElementById('iggs');
	let varIrtra = document.getElementById('irtra');
	let vacacionesValue = document.getElementById('vacaciones').value; //fecha de vacaciones
	let varFechaVacaciones = new Date(vacacionesValue);
	let contratoValue = document.getElementById('contrato').value; //fecha de contrato
	let varFechaDeContrato = new Date(contratoValue);
	let varSalario = document.getElementById('salario');
	let controlArea = document.getElementById('area');
	let varArea= controlArea.options[controlArea.selectedIndex].value; 

	$.post('creacionDeEmpleado', {
		cui : varCui.value,
		nombre : varNombre.value,
		mail : varEmail.value,
		direccion : varDireccion.value,
		iggs : varIggs.value,
		irtra : varIrtra.value,
		vacaciones : varFechaVacaciones.value,
		contrato : varFechaDeContrato.value,
		salario : varSalario.value,
		area : varArea.value
	}, function(responseText) {
        alert('Creacion del empleado realizada exitosamente');
		formEmpleado.hidden='true';
	});
}