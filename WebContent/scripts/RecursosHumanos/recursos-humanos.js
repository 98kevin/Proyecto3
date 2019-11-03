
let botonContatacion = document.getElementById('contratarEmpleado'); 

let formContratacion = document.getElementById('formContratacion');

botonContatacion.addEventListener('click', () =>{
    formContratacion.style.display='block'; //mostrar formulario
});

formContratacion.style.display='none';  //ocutlar formulario