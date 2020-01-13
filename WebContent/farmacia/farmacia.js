
//constantes
const REGISTRAR_MEDICAMENTO=1;
const CONSULTAR_MEDICAMENTOS=2;
const COMPRAR_MEDICAMENTOS = 3; 
const ACTUALIZAR_INVENTARIO= 4;
const REGISTRAR_ACTUALIZACION = 5;
const ACTUALIZAR_MEDICAMENTO = 6; 

const LEER_MEDICAMENTOS= 1;  
const LEER_MEDICAMENTO= 2; 


const REPORTE_MEDICAMENTOS = 1; 
const REPORTE_GANANCIAS_MEDICAMENTOS= 2;
const REPORTE_VENTAS_POR_EMPLEADO=3;

const TIPO_HTML =1; 
const TIPO_PDF = 2; 

 var fechaInicial; 
 var fechaFinal; 

//botones del panel superior
let botonActualiarInventario= document.getElementById('btnActualizarInventario');
let botonComprarMedicamento= document.getElementById('btnComprarMedicamento');
//let botonVenderMedicamento= document.getElementById('btnVenderMedicamento');
let botonRegistrarMedicamento= document.getElementById('btnRegistrarMedicamento');
let botonBuscarMedicamento= document.getElementById('btnBuscar');

//botones de los formularios
let registrarMedicamento= document.getElementById('registrarMedicamento');

//Formularios
let formMedicamento = document.getElementById('form-medicamento');


//tablas ocultas
let tablaDeMedicamentos = document.getElementById('tablaResultados');
let medicamentosSeleccionados = document.getElementById('medicamentosSeleccionados');

//funcion para comprar medicamento
botonComprarMedicamento.addEventListener('click', actualizarTablaMedicamentos); 

window.onload = function (){
	ocultarComponentes(); 
	acutalizarControlFecha();
}

function ocultarComponentes(){
		//document.getElementById('formularioFiltros').style.display = 'none';
		$("#formularioFiltros").css('display', 'none');
		$('#filtrosReporteGanancias').css('display','none'); 
		$('#filtrosReporteVentas').css('display','none');
		formMedicamento.style.display='none';
		$('#form-fecha').css('display', 'none'); 
}

$("#reporteMedicamentos").click(function(){
	ocultarComponentes();
	$("#formularioFiltros").css('display', 'block');
})

$("#reporteGananciasMedicamentos").click(function(){
	ocultarComponentes();
	$("#filtrosReporteGanancias").css('display', 'block');
})

$("#reporteVentasPorEmpleado").click(function(){
	ocultarComponentes();
	$("#filtrosReporteVentas").css('display', 'block');
})


$('#generarReporteVentas').click(function(){
	fechaInicial = new Date(document.getElementById('controlFechaInicialVentas').value); 
	fechaFinal = new Date(document.getElementById('controlFechaFinalVentas').value); 
	$.ajax({
        url: 'reportes',
        dataType: 'text',
        type: 'post',
        data: {
			tipoDeReporte: REPORTE_VENTAS_POR_EMPLEADO,
			filtroCui: document.getElementById('filtroCuiEmpleadoVentas').value,
			filtroNombrePersona : document.getElementById('filtroNombreEmpleadoVentas').value,
			filtroNombreMedicamento: document.getElementById('filtroNombreMedicamentoVentas').value,
			fechaInicial: fechaInicial.getTime(), 
			fechaFinal: fechaFinal.getTime(),
			tipo: TIPO_HTML
       },
       success: function(response){
		   document.getElementById('reporte').innerHTML = response; 
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error(' error '+ errorThrown);
        }
    })
})

$('#btnExportarReporteVentas').click(function(){
	$.ajax({
        url: 'reportes',
        dataType: 'text',
        type: 'post',
        data: {
			tipoDeReporte: REPORTE_VENTAS_POR_EMPLEADO,
			filtroCui: document.getElementById('filtroCuiEmpleadoVentas').value,
			filtroNombrePersona : document.getElementById('filtroNombreEmpleadoVentas').value,
			filtroNombreMedicamento: document.getElementById('filtroNombreMedicamentoVentas').value,
			fechaInicial: fechaInicial.getTime(), 
			fechaFinal: fechaFinal.getTime(),
			tipo: TIPO_PDF
       },
       success: function(response){
		   document.getElementById('reporte').innerHTML = response; 
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error(' error '+ errorThrown);
        }
    })
})



$('#generarReporteGanancias').click(function(){
	fechaInicial = new Date(document.getElementById('controlFechaInicial').value); 
	fechaFinal = new Date(document.getElementById('controlFechaFinal').value); 
	$.ajax({
        url: 'reportes',
        dataType: 'text',
        type: 'post',
        data: {
			tipoDeReporte: REPORTE_GANANCIAS_MEDICAMENTOS,
			filtroNombre: document.getElementById('filtroNombreGanancias').value, 
			fechaInicial: fechaInicial.getTime(), 
			fechaFinal: fechaFinal.getTime(),
			tipo: TIPO_HTML
       },
       success: function(response){
		   document.getElementById('reporte').innerHTML = response; 
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error(' error '+ errorThrown);
        }
    })
})

$('#btnExportarReporteGanancias').click(function(){
	$.ajax({
        url: 'reportes',
        dataType: 'text',
        type: 'post',
        data: {
			tipoDeReporte: REPORTE_GANANCIAS_MEDICAMENTOS,
			filtroNombre: document.getElementById('filtroNombreGanancias').value,
			fechaInicial: fechaInicial.getTime(),
			fechaFinal: fechaFinal.getTime(),
			tipo: TIPO_PDF
       },
       success: function(response){
		   document.getElementById('reporte').innerHTML = response; 
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error(' error '+ errorThrown);
        }
    })
})


$('#generarReporteMedicamentos').click(function(){
	$.ajax({
        url: 'reportes',
        dataType: 'text',
        type: 'post',
        data: {
			tipoDeReporte: REPORTE_MEDICAMENTOS,
			filtroNombre: document.getElementById('filtroNombre').value,
			tipo: TIPO_HTML
       },
       success: function(response){
		   document.getElementById('reporte').innerHTML = response; 
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error(' error '+ errorThrown);
        }
    })
})

$('#btnExportarReporteMedicamentos').click(() => {
	$.ajax({
        url: 'reportes',
        dataType: 'text',
        type: 'post',
        data: {
			tipoDeReporte: REPORTE_MEDICAMENTOS,
			filtroNombre: document.getElementById('filtroNombre').value,
			tipo: TIPO_PDF
       },
       success: function(response){
		   alertify.success('Reporte Generado exitosamente', 2 ); 
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error(' error '+ errorThrown);
        }
    })
})




registrarMedicamento.addEventListener('click', () => {
	let varNombre= document.getElementById('nombreMedicamento').value;
	let varCosto= document.getElementById('costoMedicamento').value;
	let varPrecio= document.getElementById('precioMedicamento').value;
	let varCantidadMinima= document.getElementById('unidadesMinimas').value;
	$.post('medicamento', {
		operacion: REGISTRAR_MEDICAMENTO,
		nombre: varNombre,
		costo: varCosto,
		precio: varPrecio,
		minimo: varCantidadMinima
	}).done(
		function(msg){
		alert(msg);
	}).fail(
		function(xhr, status, error){
			alert('estado del registro '+ status + '\n'+ error);
		}
    );
});

function actualizarTablaMedicamentos () {
	$('#form-fecha').css('display', 'block'); 
	$.post('medicamento', {
		operacion: CONSULTAR_MEDICAMENTOS
	}).done(
		function(response){
		tablaDeMedicamentos.innerHTML=response;
	}).fail(
		function(xhr, status, error){
			alert('estado del registro '+ status + '\n'+ error);
		}
    );
}

function botonComprar (boton) {
	let fecha = new Date(document.getElementById('controlFecha').value);
	let varId= boton.getAttribute('id');
	let varCantidad = document.getElementById('caja'+varId).value;
	$.post('medicamento', {
		operacion: COMPRAR_MEDICAMENTOS,  //3
		idMedicamento : varId, 
		cantidad: varCantidad, 
		fecha: fecha.getTime()
	}).done(
		function(msg){
		alertify.message(msg); 
		actualizarTablaMedicamentos();
	}).fail(
		function(xhr, status, error){
			alertify.alert('estado del registro '+ status + '\n'+ error);
		}
    );
}

//funcion para actualizar inventario
botonActualiarInventario.addEventListener('click', () => {
	$.post('medicamento', {
		operacion: ACTUALIZAR_INVENTARIO  //4
	}).done(
		function(response){
		tablaDeMedicamentos.innerHTML=response;
	}).fail(
		function(xhr, status, error){
			alert('estado del registro '+ status + '\n'+ error);
		}
    );
});


 function botonActualizar (boton){
	let varId= boton.getAttribute('id');
	let varCantidad = document.getElementById('caja'+varId).value;
	$.post('medicamento', {
		operacion: REGISTRAR_ACTUALIZACION, //5
		idMedicamento: varId, 
		cantidad: varCantidad
	}).done(
		function(response){
		tablaDeMedicamentos.innerHTML=response;
	}).fail(
		function(xhr, status, error){
			alert('estado del registro '+ status + '\n'+ error);
		}
    );
};


function acutalizarControlFecha(){
	let controlFecha = document.getElementById('controlFecha');  
	controlFecha.value = new Date().toLocaleDateString('en-CA');
}

$('#btnConsultarMedicamentos').click(() => {
	$.ajax({
        url: 'medicamento',
        dataType: 'text',
        type: 'get',
        data: {
			operacion : LEER_MEDICAMENTOS
       },
       success: function(response){
		   document.getElementById('tablaResultados').style.display = 'block';
		   document.getElementById('tablaResultados').innerHTML = response; 
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error(errorThrown);
        }
    })
});

function seleccionarMedicamento(boton){
	let id = boton.getAttribute ('id'); 
	$.ajax({
        url: 'medicamento',
        dataType: 'text',
        type: 'get',
        data: {
			operacion : LEER_MEDICAMENTO, 
			codigo: id
       },
       success: function(response){
		   document.getElementById('tablaResultados').style.display = 'block';
		   document.getElementById('tablaResultados').innerHTML = response; 
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error(errorThrown);
        }
    })
}

function actualizarMedicamento (boton){
	$.ajax({
        url: 'medicamento',
        dataType: 'text',
        type: 'post',
        data: {
			operacion : ACTUALIZAR_MEDICAMENTO, 
			codigo: document.getElementById('codigo').value,
			nombre: document.getElementById('nombre').value,
			costo: document.getElementById('costo').value,
			precio: document.getElementById('precio').value,
			cantidadMinima: document.getElementById('cantidadMinima').value
       },
       success: function(response){
		   document.getElementById('tablaResultados').style.display = 'block';
		   document.getElementById('tablaResultados').innerHTML = response; 
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error(errorThrown);
        }
    })
}