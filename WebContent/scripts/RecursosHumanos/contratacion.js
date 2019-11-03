

let botonContratarEmpleado = document.getElementById('contratarNuevoEmpleado');

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

