
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

