const CONSULTAR_AREAS = 1;
const CONSULTAR_MODULOS = 2;
const CONSULTAR_EMPLEADOS = 3;
const CONSULTAR_TARIFAS = 4;
const REGISTRAR_CIRUGIA = 5; 

//botones del panel superior
let botonCrear =  document.getElementById('crearModulo');
let botonArea = document.getElementById('crearArea');
let botonCrearCirugia = document.getElementById('crearCirugia');

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

//formulario de creacion de cirugia
let formCirugia = document.getElementById('form-crear-cirugia');
let registrarNuevaCirugia = document.getElementById('registrarNuevaCirugia');

formEmpleado.style.display='none';
formCirugia.style.display='none';


//eventos de los botones
botonCrear.addEventListener('click',crearModulo);
botonEnvio.addEventListener('click', enviarDatos); 
botonArea.addEventListener('click', mostrarFormArea);
botonArea.addEventListener('click', mostrarAreas); 
botonCrearArea.addEventListener('click', enviarArea);
botonCrearEmpleado.addEventListener('click', enviarEmpleado);
botonCrearCirugia.addEventListener('click', mostrarFormCirugia);
mostrarFormNuevoEmpleado.addEventListener('click', mostrarFormEmpleado);
registrarNuevaCirugia.addEventListener('click', registrarCirugia);


function mostrarFormCirugia(){
	formCirugia.style.display='block';
}

/**
 * Envio de solicitud de creacion de nueva cirugia
 */
function registrarCirugia(){
	$.post('consultar', {
		operacion : REGISTRAR_CIRUGIA,
		descripcion: document.getElementById('descripcion-cirugia').value,
		costo : document.getElementById('costo-al-hospital').value,
		tarifaEspecialista : document.getElementById('tarifa-de-especialista').value,
		precio : document.getElementById('precio-al-cliente').value
	}).done(
		function(responseText){
		alertify.message(responseText,2);
		alertify.message('Creacion correcta',2);
		formCirugia.style.display='none';
	}).fail(
		function(xhr, status, error){
			alert('estado del registro '+ status + '\n'+ error);
		}
    );
}


function mostrarAreas (){
	let tablaResultados = document.getElementById('tablaResultados');
	$.post('consultar', {
		operacion : CONSULTAR_AREAS
	}).done(
		function(responseText){
		tablaResultados.innerHTML= responseText;
	}).fail(
		function(xhr, status, error){
			alert('estado del registro '+ status + '\n'+ error);
		}
    );
}



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
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert('estado del registro '+ textStatus + '\n'+ errorThrown);
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
	let varPassword1 = document.getElementById('pass1');
	let varDireccion = document.getElementById('direccion');
	let varIggs = document.getElementById('iggs');
	let varIrtra = document.getElementById('irtra');
	let vacacionesValue = document.getElementById('vacaciones').value; //fecha de vacaciones
	let varFechaVacaciones = new Date(vacacionesValue);
	let contratoValue = document.getElementById('contrato').value; //fecha de contrato
	let varFechaDeContrato = new Date(contratoValue);
	let varSalario = document.getElementById('salario');
	let varTipoEmpleado = document.getElementById('tipoEmpleado').value;

	$.post('creacionDeEmpleado', {
		cui : varCui.value,
		nombre : varNombre.value,
		mail : varEmail.value,
		direccion : varDireccion.value,
		iggs : varIggs.value,
		irtra : varIrtra.value,
		vacaciones : varFechaVacaciones.getTime(),
		contrato : varFechaDeContrato.getTime(),
		salario : varSalario.value,
		tipoEmpleado: varTipoEmpleado,
		password : varPassword1.value
	}).done(
		function(msg){
		alert(msg);
	}).fail(
		function(xhr, status, error){
			alert('estado del registro '+ status + '\n'+ error);
		}
    );
	
}