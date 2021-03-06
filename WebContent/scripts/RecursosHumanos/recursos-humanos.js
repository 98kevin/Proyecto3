//post
const DESPIDO_DE_EMPLEADO = 2;
const RECONTRATACION_DE_EMPLEADO = 3;
const ACTUALIZAR_EMPLEADO = 4;
//get
const CONSULTAR_EMPLEADOS_ACTIVOS = 1;
const CONSULTAR_EMPLEADOS_NO_ACTIVOS = 2;
const CONSULTAR_EMPLEADOS = 3;
const CONSULTAR_EMPLEADO = 4;
//constantes de reportes

const REPORTE_EMPLEADOS_CONTRATADOS= 4;
const REPORTE_EMPLEADOS_RETIRADOS= 5;
const REPORTE_DE_MEDICOS= 6;
const REPORTE_DE_MEDICOS_CON_PACIENTES= 7;
const REPORTE_DE_MEDICOS_SIN_PACIENTES= 8;

//crud de credenciales
const LEER_USUARIOS = 1; 
const ACTUALIZAR_CREDENCIALES = 2; 
const BORRAR_CREDENCIALES = 3; 
const LEER_CREDENCIAL = 4; 

const TIPO_HTML =1; 
const TIPO_PDF = 2; 

var botonDespedir=      document.getElementById('despedirEmpleado').addEventListener('click', mostrarEmpleadosActivos);
var botonRecontratar=   document.getElementById('recontratarEmpleado').addEventListener('click', mostrarEmpleadosDesactivados);

let idContratoSeleccionado = 0;
let botonContatacion = document.getElementById('contratarEmpleado'); 
let formContratacion = document.getElementById('formContratacion');
let tablaEmpleados   = document.getElementById('empleados'); 

var tipoReporteMedicos=0; 

botonContatacion.addEventListener('click', () =>{
    formContratacion.style.display='block'; //mostrar formulario
});

const REGISTRO_MEDICO_ESPECIALISTA = 1;

//botones
let botonContratarEmpleado = document.getElementById('contratarNuevoEmpleado');
let botonRegistrarMedicoEspecialista= document.getElementById('registrarMedicoEspecialista');
let botonRegistroMedicoEspecialista=document.getElementById('registrarNuevoEspecialista');

//formularios
let formEspecialista=document.getElementById('formMedicoEspecialista');
formEspecialista.style.display='none';

//eventos y llamadas a las funciones
botonRegistrarMedicoEspecialista.addEventListener('click', mostrarFormularioMedicoEspecialista)



//Funciones

$('#leerUsuarios').click(() => {
    tablaEmpleados.style.display= 'block'; 
    $.ajax({
        url: 'credenciales',
        dataType: 'text',
        type: 'post',
        data: {
			operacion: LEER_USUARIOS
       },
       success: function(response){

           document.getElementById('empleados').innerHTML = response;
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error(' error '+ errorThrown);
        }
    })
})


function seleccionarCredenciales(boton){
    $.ajax({
        url: 'credenciales',
        dataType: 'text',
        type: 'post',
        data: {
            operacion: LEER_CREDENCIAL, 
            codigo : boton.getAttribute('id')
       },
       success: function(response){
           document.getElementById('empleados').innerHTML = response;
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error(' error '+ errorThrown);
        }
    })
}

function actualizarCredencial(boton){
    $.ajax({
        url: 'credenciales',
        dataType: 'text',
        type: 'post',
        data: {
            operacion: ACTUALIZAR_CREDENCIALES, 
            codigo : boton.getAttribute('id'), 
            correo: document.getElementById('correo'+boton.getAttribute('id')).value, 
            password: document.getElementById('password'+boton.getAttribute('id')).value
       },
       success: function(response){
           document.getElementById('empleados').innerHTML = response;
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error(' error '+ errorThrown);
        }
    })
}

function borrarCredencial(boton){
    $.ajax({
        url: 'credenciales',
        dataType: 'text',
        type: 'post',
        data: {
            operacion: BORRAR_CREDENCIALES, 
            codigo : boton.getAttribute('id')
       },
       success: function(response){
           document.getElementById('empleados').innerHTML = response;
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error(' error '+ errorThrown);
        }
    })
}


function mostrarFormularioMedicoEspecialista(){
	formEspecialista.style.display='block';
}

window.onload = function (){
    ocultarComponentes(); 
    let areas = this.document.getElementById('areaDeTrabajo');
    $.get('contrataciones', {})
    .done(
            function(response){
                areas.innerHTML=response;
            }
        )
    .fail(
		function(xhr, status, error){
			alert('estado del registro '+ status + '\n'+ error);
		}
    )
}
// medicos
$('#generarReporteMedicos').click(function(){
    var select = document.getElementById("filtroMedicos");
    var tipoReporteMedicos = select.options[select.selectedIndex].value;
	$.ajax({
        url: 'reportes',
        dataType: 'text',
        type: 'post',
        data: {
			tipoDeReporte: tipoReporteMedicos,
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

$('#exportarReporteMedicos').click(function(){
    var select = document.getElementById("filtroMedicos");
    var tipoReporteMedicos = select.options[select.selectedIndex].value;
	$.ajax({
        url: 'reportes',
        dataType: 'text',
        type: 'post',
        data: {
			tipoDeReporte: tipoReporteMedicos,
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

//despedidos
$('#generarReporteDespedidos').click(function(){
	fechaInicial = new Date(document.getElementById('controlFechaInicialDespedidos').value);
	fechaFinal = new Date(document.getElementById('controlFechaFinalDespedidos').value); 
	$.ajax({
        url: 'reportes',
        dataType: 'text',
        type: 'post',
        data: {
			tipoDeReporte: REPORTE_EMPLEADOS_RETIRADOS,
			filtroArea: document.getElementById('filtroAreaDespedidos').value,
			fechaInicial: fechaInicial.getTime(), 
			fechaFinal: fechaFinal.getTime(),
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

$('#exportarReporteDespedidos').click(function(){
	$.ajax({
        url: 'reportes',
        dataType: 'text',
        type: 'post',
        data: {
			tipoDeReporte: REPORTE_EMPLEADOS_RETIRADOS,
			filtroArea: document.getElementById('filtroAreaDespedidos').value,
			fechaInicial: fechaInicial.getTime(), 
			fechaFinal: fechaFinal.getTime(),
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

//contratados
$('#generarReporteContratados').click(function(){
	fechaInicial = new Date(document.getElementById('controlFechaInicialContratados').value);
	fechaFinal = new Date(document.getElementById('controlFechaFinalContratados').value); 
	$.ajax({
        url: 'reportes',
        dataType: 'text',
        type: 'post',
        data: {
			tipoDeReporte: REPORTE_EMPLEADOS_CONTRATADOS,
			filtroArea: document.getElementById('filtroAreaContratados').value,
			fechaInicial: fechaInicial.getTime(), 
			fechaFinal: fechaFinal.getTime(),
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

$('#exportarReporteContratados').click(function(){
	$.ajax({
        url: 'reportes',
        dataType: 'text',
        type: 'post',
        data: {
			tipoDeReporte: REPORTE_EMPLEADOS_CONTRATADOS,
			filtroArea: document.getElementById('filtroAreaContratados').value,
			fechaInicial: fechaInicial.getTime(), 
			fechaFinal: fechaFinal.getTime(),
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


/**
 * Funcion para recontratar a un empleado. 
 * @param {*} boton 
 */
function recontratarEmpleado(boton){
    let salarioIngresado =0;
    idContratoSeleccionado = boton.getAttribute('id');
    alertify.prompt("Ingrese el nuevo salario", "0.00",
    function(evt, value ){
        $.ajax({
            url: 'recursos-humanos',
            dataType: 'text',
            type: 'post',
            data: {
                operacion: RECONTRATACION_DE_EMPLEADO,
                contrato: idContratoSeleccionado, 
                salario: value
           },
           success: function(response){
                alertify.message('Contrato ' + idContratoSeleccionado+' recontratado', 2);
                mostrarEmpleadosDesactivados();
            },
            error: function( jqXhr, textStatus, errorThrown ){
                alertify.error('estado del registros'+ textStatus+ ' error '+ errorThrown); 
                console.log('estado del registros'+ textStatus+ ' error '+ errorThrown);
            }
        })
    },
    function(){
        alertify.error('Cancelado');
    });
}

/**
 * Funcion para despedir a un empleado 
 * @param {*} boton 
 */
function despedirEmpleado(boton){
    idContratoSeleccionado = boton.getAttribute('id');
    $.ajax({
        url: 'recursos-humanos',
        dataType: 'text',
        type: 'post',
        data: {
            operacion: DESPIDO_DE_EMPLEADO,
            contrato: idContratoSeleccionado
       },
       success: function( response){
            alertify.message('Contrato ' + idContratoSeleccionado+' despedido', 2);
            mostrarEmpleadosActivos();
        },
        error: function( jqXhr, textStatus, errorThrown ){
        	alertify.error('estado del registros'+ textStatus+ ' error '+ errorThrown); 
        	console.log('estado del registros'+ textStatus+ ' error '+ errorThrown);
        }
    })
}

/**
 * Oculta todos los componentes que poseen la clase de ocultable
 */
function ocultarComponentes(){
    $('#filtrosReporteEmpleadosContratados').css('display', 'none'); 
    $('#filtrosReporteEmpleadosDespedidos').css('display', 'none'); 
    $('#filtrosReporteMedicos').css('display', 'none');
    $('#formMedicoEspecialista').css('display', 'none');
    $('#reporte').innerHTML = '<div></div>'; 
    tablaEmpleados.style.display='none'; 
    formContratacion.style.display='none';
}

$('#reporteEmpleadosContratados').click(() => {
    ocultarComponentes();
    $('#filtrosReporteEmpleadosContratados').css('display', 'block'); 
})

$('#reporteEmpleadosDespedidos').click(() => {
    ocultarComponentes();
    $('#filtrosReporteEmpleadosDespedidos').css('display', 'block'); 
})

$('#reporteMedicos').click(() => {
    ocultarComponentes();
    $('#filtrosReporteMedicos').css('display', 'block');
})


/**
 * Consulta la base de datos en busca de los empleados activos
 */
function mostrarEmpleadosActivos (){
    ocultarComponentes(); 
    $.ajax({
        url: 'recursos-humanos',
        dataType: 'text',
        type: 'get',
        data: {
            operacion: CONSULTAR_EMPLEADOS_ACTIVOS 
       },
       success: function( response){
            tablaEmpleados.style.display='block'; 
            document.getElementById('empleados').innerHTML = response;
        },
        error: function( jqXhr, textStatus, errorThrown ){
        	alertify.error('estado del registros'+ textStatus+ ' error '+ errorThrown); 
        	console.log('estado del registros'+ textStatus+ ' error '+ errorThrown);
        }
    })
}

/**
 * Consulta las base de datos en busca de los empleados que han sido despedidos. 
 */
function mostrarEmpleadosDesactivados (){
    ocultarComponentes(); 
    $.ajax({
        url: 'recursos-humanos',
        dataType: 'text',
        type: 'get',
        data: {
            operacion: CONSULTAR_EMPLEADOS_NO_ACTIVOS
       },
       success: function( response){
            tablaEmpleados.style.display='block'; 
            document.getElementById('empleados').innerHTML = response;
        },
        error: function( jqXhr, textStatus, errorThrown ){
        	alertify.error('estado del registros'+ textStatus+ ' error '+ errorThrown); 
        	console.log('estado del registros'+ textStatus+ ' error '+ errorThrown);
        }
    })
}


  //scripts para contratar empleado

  botonContratarEmpleado.addEventListener('click', () => {
	$.post('creacionDeEmpleado', {
		cui : document.getElementById('cui').value,
		nombre : document.getElementById('nombre').value,
		mail : document.getElementById('email').value,
		direccion : document.getElementById('direccion').value,
		iggs : document.getElementById('iggs').value,
		irtra : document.getElementById('irtra').value,
		vacaciones : new Date(document.getElementById('vacaciones').value).getTime(),
		contrato : new Date(document.getElementById('contrato').value).getTime(),
		salario : document.getElementById('salario').value,
		tipoEmpleado: document.getElementById('areaDeTrabajo'),
		password : document.getElementById('pass1').value
	}).done(
		function(msg){
		alert(msg);
	}).fail(
		function(xhr, status, error){
			alert('estado del registro '+ status + '\n'+ error);
		}
    ); 
    })

//boton del formulario de registro de especialista
botonRegistroMedicoEspecialista.addEventListener('click', () =>{
	console.log('ingreso');
	$.post('creacion-de-medico-especialista', {
		operacion : REGISTRO_MEDICO_ESPECIALISTA,
		cui : document.getElementById('cuiMedicoEspecilista').value,
		nombre : document.getElementById('nombreMedicoEspecilista').value,
		direccion : document.getElementById('direccionMedicoEspecilista').value,
		colegiado : document.getElementById('colegiadoMedicoEspecilista').value
	}).done(
		function(responseText){
		alertify.message(responseText);
		formEspecialista.style.display='none'; //ocultamos el formulario
	}).fail(
		function(xhr, status, error){
			alert('Ha ocurrido un error. Estado '+ status+ '. Codigo de error '+ error);
		}
	);
});


$('#consultarEmpleados').click(() => {
    $.ajax({
        url: 'recursos-humanos',
        dataType: 'text',
        type: 'get',
        data: {
            operacion: CONSULTAR_EMPLEADOS
       },
       success: function(response){
        document.getElementById('empleados').style.display = 'block';
           document.getElementById('empleados').innerHTML = response;
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error(' error '+ errorThrown);
        }
    })
});

function seleccionarEmpleado(boton){
    let id = boton.getAttribute('id');
    $.ajax({
        url: 'recursos-humanos',
        dataType: 'text',
        type: 'get',
        data: {
            operacion: CONSULTAR_EMPLEADO,
            cuiEmpleado : id
       },
       success: function(response){
        document.getElementById('empleados').style.display = 'block';
           document.getElementById('empleados').innerHTML = response;
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error(' error '+ errorThrown);
        }
    })
}


//terminar esta funcion
function actualizarEmpleado(boton){
    let id = boton.getAttribute('id');
    $.ajax({
        url: 'recursos-humanos',
        dataType: 'text',
        type: 'post',
        data: {
            operacion: ACTUALIZAR_EMPLEADO, 
            cuiActual: document.getElementById('campo-cui-viejo').value, 
            cuiNuevo: document.getElementById('campo-cui').value, 
            nombre: document.getElementById('campo-nombre').value, 
            direccion: document.getElementById('campo-direccion').value, 
            igss: document.getElementById('campo-iggs').value, 
            irtra: document.getElementById('campo-irtra').value 
       },
       success: function(response){
            document.getElementById('empleados').style.display = 'block';
            document.getElementById('empleados').innerHTML = response;
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error(' error '+ errorThrown);
        }
    })
}
