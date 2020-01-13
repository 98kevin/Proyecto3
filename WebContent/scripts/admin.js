//CONSTANTES DEL METODO POST
const CONSULTAR_SALARIOS_PENDIENTES = 1; 
const CONSULTAR_CIRUGIAS = 2;
const CONSULTAR_CIRUGIA = 3;
//CONSTANTES DEL METODO GET 
const CONSULTAR_AREAS = 1;
const CONSULTAR_MODULOS = 2;
const CONSULTAR_EMPLEADOS = 3;
const CONSULTAR_TARIFAS = 4;
const REGISTRAR_CIRUGIA = 5; 
const PAGAR_SALARIO = 6; 
const ACTUALIZAR_CIRUGIA = 7;


const REPORTE_INGRESOS_MONETARIOS= 9;
const REPORTE_EGRESOS_MONETARIOS= 10;
const REPORTE_GANANCIAS= 11;

const TIPO_HTML =1; 
const TIPO_PDF = 2; 

const LEER_AREAS = 1; 
const ACTUALIZAR_AREAS  = 2; 
const BORRAR_AREAS  = 3; 
const LEER_AREA = 4; 

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
let registrarNuevaCirugia = document.getElementById('registrarNuevaCirugia');

let controlFecha = document.getElementById('controlFecha'); 
let botonConsultaSalarios = document.getElementById('btnConsultarSalarios'); 
let formularioIngresoDeFechas = document.getElementById('formularioIngresoFechas');
let botonSolicitudPlanilla = document.getElementById('btnSolicitudDatosPlanilla'); 

botonSolicitudPlanilla.addEventListener('click', () => {
	let mes = document.getElementById('mesesDelAnio').value;
	let anio = document.getElementById('anio').value; 
	$.ajax({
        url: 'administrador',
        dataType: 'text',
        type: 'GET',
        data: {
			operacion: CONSULTAR_SALARIOS_PENDIENTES, 
			mes: mes, 
			anio: anio
       },
       success: function( response){
			ocultarComponentes();
			tablaResultados.innerHTML = response;
        },
        error: function( jqXhr, textStatus, errorThrown ){
        	alertify.error('estado del registros'+ textStatus+ ' error '+ errorThrown); 
        	console.log('estado del registros'+ textStatus+ ' error '+ errorThrown);
        }
    })
}); 

function pagarSalario(boton){
	let mes = document.getElementById('mesesDelAnio').value;
	let anio = document.getElementById('anio').value; 
	let cuiEmpleado = boton.getAttribute('id'); 
	let fecha = new Date(controlFecha.value);
	$.ajax({
        url: 'administrador',
        dataType: 'text',
        type: 'POST',
        data: {
			operacion: PAGAR_SALARIO, 
			cuiEmpleado: cuiEmpleado, 
			mes: mes, 
			anio: anio, 
			fechaEnMilisegundos: fecha.getTime()
       },
       success: function( response){
			ocultarComponentes(); 
			alertify.message(response); 
        },
        error: function( jqXhr, textStatus, errorThrown ){
        	alertify.error('estado del registros'+ textStatus+ ' error '+ errorThrown); 
        	console.log('estado del registros'+ textStatus+ ' error '+ errorThrown);
        }
    })
}

window.onload = function () {
	ocultarComponentes();
	acutalizarControlFecha();
}

function ocultarComponentes(){
	formEmpleado.style.display='none';
	$('#form-crear-cirugia').css('display', 'none')
	controlFecha.style.display='none';
	formularioIngresoDeFechas.style.display= 'none'; 
	$('#filtroReporteIngresos').css('display', 'none');
	$('#filtroReporteEgresos').css('display', 'none');
	$('#filtroReporteGanancias').css('display', 'none');
	 
}

//eventos de los botones
botonCrear.addEventListener('click',crearModulo);
botonEnvio.addEventListener('click', enviarDatos); 
botonArea.addEventListener('click', mostrarFormArea);
botonArea.addEventListener('click', mostrarAreas); 
botonCrearArea.addEventListener('click', enviarArea);
botonCrearEmpleado.addEventListener('click', enviarEmpleado);
$('#crearCirugia').click(() => { $('#form-crear-cirugia').css('display', 'block')})
mostrarFormNuevoEmpleado.addEventListener('click', mostrarFormEmpleado);
registrarNuevaCirugia.addEventListener('click', registrarCirugia);
botonConsultaSalarios.addEventListener('click', mostrarControlesDeSalarios); 

$('#reporteIngresos').click(() => {
	$('#filtroReporteIngresos').css('display', 'block');
})

$('#reporteEgresos').click(() => {
	$('#filtroReporteEgresos').css('display', 'block');
})

$('#reporteGanancias').click(() => {
	$('#filtroReporteGanancias').css('display', 'block');
})

var fechaInicio; 
var fechaFin; 

// reportes de ingresos
$('#generarReporteIngresos').click(() =>{
	fechaInicio = new Date(document.getElementById('fechaInicioIngresos').value); 
	fechaFin = new Date(document.getElementById('fechaFinIngresos').value);
	$.ajax({
        url: 'reportes',
        dataType: 'text',
        type: 'post',
        data: {
			tipoDeReporte: REPORTE_INGRESOS_MONETARIOS,
			fechaInicial: fechaInicio.getTime(), 
			fechaFinal:fechaFin.getTime(),
			tipo: TIPO_HTML
       },
       success: function(response){
           document.getElementById('reporte').innerHTML = response; 
           alertify.message('consulta terminada');
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error(' error '+ errorThrown);
        }
    })
})


$('#exportarReporteIngresos').click(() =>{
	$.ajax({
        url: 'reportes',
        dataType: 'text',
        type: 'post',
        data: {
			tipoDeReporte: REPORTE_INGRESOS_MONETARIOS,
			fechaInicial: fechaInicio.getTime(), 
			fechaFinal:fechaFin.getTime(),
			tipo: TIPO_PDF
       },
       success: function(response){
           document.getElementById('reporte').innerHTML = response; 
           alertify.message('consulta terminada');
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error(' error '+ errorThrown);
        }
    })
})

// reportes de egresos
$('#generarReporteEgresos').click(() =>{
	fechaInicio = new Date(document.getElementById('fechaInicioEgresos').value); 
	fechaFin = new Date(document.getElementById('fechaFinEgresos').value);
	$.ajax({
        url: 'reportes',
        dataType: 'text',
        type: 'post',
        data: {
			tipoDeReporte: REPORTE_EGRESOS_MONETARIOS,
			fechaInicial: fechaInicio.getTime(), 
			fechaFinal:fechaFin.getTime(),
			tipo: TIPO_HTML
       },
       success: function(response){
           document.getElementById('reporte').innerHTML = response; 
           alertify.message('consulta terminada');
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error(' error '+ errorThrown);
        }
    })
})


$('#exportarReporteEgresos').click(() =>{
	$.ajax({
        url: 'reportes',
        dataType: 'text',
        type: 'post',
        data: {
			tipoDeReporte: REPORTE_EGRESOS_MONETARIOS,
			fechaInicial: fechaInicio.getTime(), 
			fechaFinal:fechaFin.getTime(),
			tipo: TIPO_PDF
       },
       success: function(response){
           document.getElementById('reporte').innerHTML = response; 
           alertify.message('consulta terminada');
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error(' error '+ errorThrown);
        }
    })
})

// reportes de ganancias
$('#generarReporteGanancias').click(() =>{
	fechaInicio = new Date(document.getElementById('fechaInicioGanancias').value); 
	fechaFin = new Date(document.getElementById('fechaFinGanancias').value);
	$.ajax({
        url: 'reportes',
        dataType: 'text',
        type: 'post',
        data: {
			tipoDeReporte: REPORTE_GANANCIAS,
			fechaInicial: fechaInicio.getTime(), 
			fechaFinal:fechaFin.getTime(),
			tipo: TIPO_HTML
       },
       success: function(response){
           document.getElementById('reporte').innerHTML = response; 
           alertify.message('consulta terminada');
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error(' error '+ errorThrown);
        }
    })
})


$('#exportarReporteGanancias').click(() =>{
	$.ajax({
        url: 'reportes',
        dataType: 'text',
        type: 'post',
        data: {
			tipoDeReporte: REPORTE_GANANCIAS,
			fechaInicial: fechaInicio.getTime(), 
			fechaFinal:fechaFin.getTime(),
			tipo: TIPO_PDF
       },
       success: function(response){
           document.getElementById('reporte').innerHTML = response; 
           alertify.message('consulta terminada');
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error(' error '+ errorThrown);
        }
    })
})


function mostrarControlesDeSalarios(){
	formularioIngresoDeFechas.style.display = 'block'; 
}

/**
 * Actualiza el control de fechas a la fecha actual 
 */
function acutalizarControlFecha(){
    let fechaActual = new Date();
    let dias = fechaActual.getDate();
    let mes = fechaActual.getMonth()+1;
    let anio = fechaActual.getFullYear();
    controlFecha.value=anio+'-'+mes+'-'+dias;
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
	

	/**
	 * Realiza una peticion al servidor 
	 * @param {*} url 
	 * 	La direccion del recurso solicitado
	 * @param {*} metodo 
	 * 	El tipo de Metodo a llamar
	 * @param {*} datos 
	 * 	Los datos que se enviaran al servidor
	 * @param {*} esTabla 
	 * 	Si los resultados se mostraran en una tabla o en una alerta
	 * @param {*} tablaParaMostrar 
	 * 	El componente en donde se desplegaran los resultados 
	 */
	function ajaxPeticion(url, metodo, datos, esTabla, tablaParaMostrar){
		$.ajax({
			url: url,
			dataType: 'text',
			type: metodo,
			data: datos,
		   success: function( response){
				ocultarComponentes();
				if(esTabla)
					tablaParaMostrar.innerHTML = response; 
				else
					alertify.message(response, 2); 
			},
			error: function( jqXhr, textStatus, errorThrown ){
				alertify.error('estado del registros'+ textStatus+ ' error '+ errorThrown); 
			}
		})
	}
}

$('#btnConsultarAreas').click(() =>{
	let tablaResultados = document.getElementById('tablaResultados');
	$.ajax({
		url: 'servlet-area',
		dataType: 'text',
		type: 'post',
		data: {
			operacion: CONSULTAR_AREAS
       } ,
	   success: function( response){
			ocultarComponentes(); 
			tablaResultados.style.display = 'block';
			tablaResultados.innerHTML = response; 
		},
		error: function( jqXhr, textStatus, errorThrown ){
			alertify.error(errorThrown); 
		}
	})
});

function seleccionarArea(boton){
	let id = boton.getAttribute('id'); 
	let tablaResultados = document.getElementById('tablaResultados');
	$.ajax({
		url: 'servlet-area',
		dataType: 'text',
		type: 'post',
		data: {
			operacion: LEER_AREA, 
			codigo: id
       } ,
	   success: function( response){
			ocultarComponentes(); 
			tablaResultados.style.display = 'block';
			tablaResultados.innerHTML = response; 
		},
		error: function( jqXhr, textStatus, errorThrown ){
			alertify.error(errorThrown); 
		}
	})
}


function actualizarArea(boton){
	let id = boton.getAttribute('id'); 
	let tablaResultados = document.getElementById('tablaResultados');
	$.ajax({
		url: 'servlet-area',
		dataType: 'text',
		type: 'post',
		data: {
			operacion: ACTUALIZAR_AREAS, 
			codigo: id, 
			descripcion: document.getElementById('descripcionArea').value
       } ,
	   success: function( response){
			ocultarComponentes(); 
			tablaResultados.style.display = 'block';
			tablaResultados.innerHTML = response; 
		},
		error: function( jqXhr, textStatus, errorThrown ){
			alertify.error(errorThrown); 
		}
	})
}

function eliminarArea(boton){
	let id = boton.getAttribute('id'); 
	let tablaResultados = document.getElementById('tablaResultados');
	$.ajax({
		url: 'servlet-area',
		dataType: 'text',
		type: 'post',
		data: {
			operacion: BORRAR_AREAS, 
			codigo: id
       } ,
	   success: function( response){
			ocultarComponentes(); 
			tablaResultados.style.display = 'block';
			tablaResultados.innerHTML = response; 
		},
		error: function( jqXhr, textStatus, errorThrown ){
			alertify.error(errorThrown); 
		}
	})
}

$('#consultarCirugias').click(() => {
	$.ajax({
		url: 'administrador',
		dataType: 'text',
		type: 'get',
		data: {
			operacion: CONSULTAR_CIRUGIAS
       } ,
	   success: function( response){
			ocultarComponentes(); 
			tablaResultados.style.display = 'block';
			tablaResultados.innerHTML = response; 
		},
		error: function( jqXhr, textStatus, errorThrown ){
			alertify.error(errorThrown); 
		}
	})
});

function seleccionarCirugia(boton){
	$.ajax({
		url: 'administrador',
		dataType: 'text',
		type: 'get',
		data: {
			operacion: CONSULTAR_CIRUGIA, 
			codigoCirugia : boton.getAttribute('id')
       } ,
	   success: function( response){
			ocultarComponentes(); 
			tablaResultados.style.display = 'block';
			tablaResultados.innerHTML = response; 
		},
		error: function( jqXhr, textStatus, errorThrown ){
			alertify.error(errorThrown); 
		}
	})
};

function actualizarCirugia(){
	$.ajax({
		url: 'administrador',
		dataType: 'text',
		type: 'post',
		data: {
			operacion: ACTUALIZAR_CIRUGIA, 
			codigoCirugia : document.getElementById('codigoCirugia').value,
			descripcionCirugia : document.getElementById('descripcionCirugia').value,
			costoCirugia : document.getElementById('costoCirugia').value,
			precioCirugia : document.getElementById('precioCirugia').value,
			tarifaEspecialista : document.getElementById('tarifaEspecialista').value
       } ,
	   success: function( response){
			ocultarComponentes(); 
			tablaResultados.style.display = 'block';
			tablaResultados.innerHTML = response; 
		},
		error: function( jqXhr, textStatus, errorThrown ){
			alertify.error(errorThrown); 
		}
	})
}