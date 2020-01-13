const REGISTRAR_PACIENTE = 1;
const ACTUALIZAR_PACIENTE = 2;

const LEER_PACIENTES = 1;
const LEER_PACIENTE = 2;



let botonPanelRegistro = document.getElementById('registrarPacienteEncabezado');
let formularioPaciente = document.getElementById('form-paciente');
let botonRegistroPaciente = document.getElementById('registrarPaciente');

formularioPaciente.style.display='none'; //ocultamos el formulario inicialmente


//mostramos el formularios
botonPanelRegistro.addEventListener('click', () => {
    formularioPaciente.style.display='block'; 
});


//Enviamos la solicitud
botonRegistroPaciente.addEventListener('click', () => {
    let varCui=document.getElementById('cui');
    let varNombre=document.getElementById('nombre');
    let varDireccion=document.getElementById('direccion');
    let varNit=document.getElementById('nit');
    $.post('secretaria', {
		operacion : REGISTRAR_PACIENTE, 
		cui : varCui.value,
		nombre : varNombre.value,
		direccion : varDireccion.value,
        nit: varNit.value
	}).done(
		function(msg){
		alert(msg);
	}).fail(
		function(xhr, status, error){
			alert('estado del registro '+ status + '\n'+ error);
		}
    );
});

$('#consultarPacientes').click(() => {
	$.ajax({
        url: 'secretaria',
        dataType: 'text',
        type: 'get',
        data: {
            operacion: LEER_PACIENTES, 
       },
       success: function(response){
            document.getElementById('resultado').style.display = 'block';
            document.getElementById('resultado').innerHTML = response;
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error(' error '+ errorThrown);
        }
    })
});

function seleccionarPaciente(boton){
	$.ajax({
        url: 'secretaria',
        dataType: 'text',
        type: 'get',
        data: {
			operacion: LEER_PACIENTE, 
			cui: boton.getAttribute('id')
       },
       success: function(response){
            document.getElementById('resultado').style.display = 'block';
            document.getElementById('resultado').innerHTML = response;
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error(' error '+ errorThrown);
        }
    })
}

function actualizarPaciente(){
	$.ajax({
        url: 'secretaria',
        dataType: 'text',
        type: 'post',
        data: {
			operacion: ACTUALIZAR_PACIENTE, 
			cuiActual: document.getElementById('cuiPacienteActual').value,
			cuiNuevo: document.getElementById('cuiPacienteNuevo').value,
			nombre: document.getElementById('nombrePaciente').value,
			direccion: document.getElementById('direccionPaciente').value,
			nit: document.getElementById('nitPaciente').value,
       },
       success: function(response){
            document.getElementById('resultado').style.display = 'block';
            document.getElementById('resultado').innerHTML = response;
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error(' error '+ errorThrown);
        }
    })
}
