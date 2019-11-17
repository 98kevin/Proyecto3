
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

function mostrarFormularioMedicoEspecialista(){
	formEspecialista.style.display='block';
}


//carga de las areas en el formulario
window.onload = function() {
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
  };


  //scripts para contratar empleado

botonContratarEmpleado.addEventListener('click', () => {
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
	let varTipoEmpleado = document.getElementById('areaDeTrabajo').value;

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
    })

//boton del formulario de registro de especialista
botonRegistroMedicoEspecialista.addEventListener('click', () =>{
	console.log('ingreso');
	$.post('creacion-de-medico-especialista', {
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