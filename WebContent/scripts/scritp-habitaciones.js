var botonAbrirFormulario = document.getElementById('btnHabitaciones');
var botonCrearHabitacion = document.getElementById('enviarHabitacion');
var formHabitacion = document.getElementById('form-crear-habitacion');

botonAbrirFormulario.addEventListener('click', mostrarFormHabitacion);
botonCrearHabitacion.addEventListener('click', crearHabitacion);


formHabitacion.style.display="none";  //ocultar el formulario al mostrar la pantalla

function crearHabitacion(){
	let varMantenimiento = document.getElementById('mantenimiento');
	$.post('habitacion', {
        precioMantenimiento : varMantenimiento.value,
        operacion:1
	}).done(
		function(msg){
		alert(msg);
	}).fail(
		function(xhr, status, error){
			alert('estado del registro '+ status + '\n'+ error);
		}
    )
    formHabitacion.style.display="none";  //ocultar el formulario al mostrar la pantalla
};

function mostrarFormHabitacion(){
	formHabitacion.style.display="block";
}
