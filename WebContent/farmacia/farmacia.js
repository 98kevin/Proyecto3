
//constantes
const REGISTRAR_MEDICAMENTO=1;
const CONSULTAR_MEDICAMENTOS=2;
const COMPRAR_MEDICAMENTOS = 3; 


//botones del panel superior
let botonActualiarInventario= document.getElementById('btnActualizarInventario');
let botonComprarMedicamento= document.getElementById('btnComprarMedicamento');
let botonVenderMedicamento= document.getElementById('btnVenderMedicamento');
let botonRegistrarMedicamento= document.getElementById('btnRegistrarMedicamento');
let botonBuscarMedicamento= document.getElementById('btnBuscar');

//botones de los formularios
let registrarMedicamento= document.getElementById('registrarMedicamento');

//Formularios
let formMedicamento = document.getElementById('form-medicamento');
formMedicamento.style.display='none';


//tablas ocultas
let tablaDeMedicamentos = document.getElementById('tablaResultados');
let medicamentosSeleccionados = document.getElementById('medicamentosSeleccionados');


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


//funcion para actualizar inventario
botonActualiarInventario.addEventListener('click', () => {});

//funcion para comprar medicamento
botonComprarMedicamento.addEventListener('click', actualizarTablaMedicamentos);

//funcion para vender medicamento
botonVenderMedicamento.addEventListener('click', () => {});

//funcion para registrar medicamento
botonRegistrarMedicamento.addEventListener('click', () => {
	formMedicamento.style.display='block';
}); 

//funcion para buscar un medicamento
botonBuscarMedicamento.addEventListener('click', () => {});

function actualizarTablaMedicamentos () {
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
	let varId= boton.getAttribute('id');
	let varCantidad = document.getElementById('cant'+varId).value;
	$.post('medicamento', {
		operacion: COMPRAR_MEDICAMENTOS,
		idMedicamento : varId, 
		cantidad: varCantidad
	}).done(
		function(msg){
		alert(msg);
		actualizarTablaMedicamentos();
	}).fail(
		function(xhr, status, error){
			alert('estado del registro '+ status + '\n'+ error);
		}
    );
}
