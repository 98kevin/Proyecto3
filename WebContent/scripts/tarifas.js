const CREAR = 1; 

let botonEnviarTarifa = document.getElementById('crearTarifaFormulario');

let formTarifa= document.getElementById('formTarifa');

botonEnviarTarifa.addEventListener('click', enviarTarifa);

function mostrarFormTarifa() {
    formTarifa.style.display="block";
}

formTarifa.style.display="none";  //ocultar el formulario al mostrar la pantalla

function enviarTarifa(){
    let descripVar = document.getElementById('descripcionTarifa').value;
    let costoVar = document.getElementById('costoHospital').value;
    let especialistaVar = document.getElementById('costoEspecialista').value;
    let precioVar = document.getElementById('precioCliente').value;
    $.post('tarifa', {
		descipcion : descripVar,
		costoHospital :costoVar,
        pagoEspecialista : especialistaVar,
        precioCliente : precioVar,
        operacion: CREAR
	}).done(
		function(msg){
        alert(msg);
	}).fail(
		function(xhr, status, error){
			alert('estado del registro '+ status + '\n'+ error);
		}
    );
}