
const NUEVA_CONSULTA= 1;
const REGISTRAR_OPERACION= 2;
const REGISTRAR_CONSULTA= 3;  //Registro completo de la consulta
const CONSULTAR_MEDICAMENTOS= 4; 
const CONSULTAR_ENFERMERAS= 5;
const CONSULTAR_MEDICOS = 6;
const AGREGAR_MEDICAMENTO = 7;  
const CONSULTAR_HABITACIONES=8;
const CONSULTAR_PACIENTES_INTERNADOS= 9;

var idPacienteSeleccionado=0;
var numeroDeHabitacion = 0;

let btnNuevaConsulta = document.getElementById('btnNuevaConsulta');
let btnRegistrarCirugia = document.getElementById('btnRegistrarCirugia');
let btnAsignarMedicamento = document.getElementById('btnAsignarMedicamento');

let tablaResultados = document.getElementById('tablaResultados');
let tablaMedicos = document.getElementById('tablaDeMedicos');
let tablaEnfermeras = document.getElementById('tablaDeEnfermeras');
let habitaciones = document.getElementById('habitacion');
let internado = document.getElementById('internado');
let controlFecha = document.getElementById('fecha-consulta');

let codigosMedicamentos = []; 
let cantidadesMedicamentos = []; 
let medicosAsignados = [];
let enfermerasAsignadas =[]; 

//formulario de consulta
let formConsulta = document.getElementById('formConsulta');
let botonTerminarConsulta = document.getElementById('botonAgregarMedicamento');

//formulario de asignacion de medicamentos 
let botonTerminarAsingacionMedicamentos = document.getElementById('btonAsignarMedicamentos');

window.onload= ocultarComponentes(); 

//al cargar la pagina 
function ocultarComponentes(){
	tablaResultados.style.display='none';
	formConsulta.style.display='none';
	tablaEnfermeras.style.display='none'; 
	tablaMedicos.style.display='none'; 
	habitaciones.style.display='none';
	botonTerminarConsulta.style.display='none';
    controlFecha.style.display='none';
    botonTerminarAsingacionMedicamentos.style.display='none';
}

/*
*Evento del boton del encabezado Nueva Consulta
*/

btnNuevaConsulta.addEventListener('click', () => {
    codigosMedicamentos = []; 
    cantidadesMedicamentos = []; 
    medicosAsignados = [];
    enfermerasAsignadas =[]; 
    controlFecha.style.display='block';
    formConsulta.style.display='block';
    $.post('medico', {
        operacion: NUEVA_CONSULTA
    }).done(
        function (response){
            tablaResultados.style.display='block';
            botonTerminarConsulta.style.display='block';
            tablaResultados.innerHTML = response; 
        }
    ).fail(
        function (xhr, status, error){
            alert('estado del registros'+ status+ ' error '+ error); 
        }
    )
});

btnAsignarMedicamento.addEventListener('click', () =>{
    alertify.message('Asingacion de medicamentos', 2);
    botonTerminarAsingacionMedicamentos.style.display='block';
    $.ajax({
        url: 'medico',
        dataType: 'html',
        type: 'get',
        data: {
            operacion: CONSULTAR_PACIENTES_INTERNADOS
       },
       success: function( response){
            tablaResultados.style.display='block';
            tablaResultados.innerHTML = response;
        },
        error: function( jqXhr, textStatus, errorThrown ){
        	alertify.error('estado del registros'+ textStatus+ ' error '+ errorThrown); 
        	console.log('estado del registros'+ textStatus+ ' error '+ errorThrown);
        }
    })
    
});


/*
*Evento del boton del encabezado Registrar Cirugia 
*/
btnRegistrarCirugia.addEventListener('click', () =>{
    
});


/**
 * funcion del boton de agregar medicamentos a pacientes internados
 */
botonTerminarAsingacionMedicamentos.addEventListener('click', () =>{
    $.ajax({
        url: 'medico',
        dataType: 'text',
        type: 'post',
        data: {
            operacion: AGREGAR_MEDICAMENTO, 
    	    cantidades: cantidadesMedicamentos,
            codigos: codigosMedicamentos,
            internado: idPacienteSeleccionado
       },
       success: function( response){
        	alertify.message(response, 2);
        	ocultarComponentes();
        },
        error: function( jqXhr, textStatus, errorThrown ){
        	alertify.error('estado del registros'+ textStatus+ ' error '+ errorThrown); 
        	console.log('estado del registros'+ textStatus+ ' error '+ errorThrown);
        }
    })
});

/**
 * Funcion de seleccion de paciente
 * @param {*} boton 
 * El boton que 
 */
function seleccionarPaciente(boton){
    idPacienteSeleccionado= boton.getAttribute('id');
    alertify.success('Seleccion correcta',2); 
    $.get('medico', {
        operacion: CONSULTAR_MEDICAMENTOS
    }).done(
        function (response){
            tablaResultados.style.display='block';
            tablaResultados.innerHTML = response;
        }
    ).fail(
        function (xhr, status, error){
            alert('estado del registros'+ status+ ' error '+ error); 
        }
    )
}

//funcion que lee las cajas de texto al momento de agregar medicamentos al paciente
function agregarMedicamento(botonAgregar){
    let varIdMedicamento = botonAgregar.getAttribute('id'); 
    let varCantidad = document.getElementById('cant'+ varIdMedicamento).value;
    if(varCantidad > 0 ){
        codigosMedicamentos.push(varIdMedicamento);
        cantidadesMedicamentos.push(varCantidad);
        alertify.success('Medicamento agregado', 2);
    } else{
        alertify.error('No se pudo agregar el medicamento', 2);
    }
}


/*
*  Boton para terminar una consulta
*/
botonTerminarConsulta.addEventListener('click', () => {
    valueFecha = document.getElementById('fecha-consulta').value;
    let fechaActual = new Date(valueFecha);
    let habitacionSeleccionada = document.getElementById('habitaciones').value;
    $.ajax({
        url: 'medico',
        dataType: 'text',
        type: 'post',
        data: {
            operacion: REGISTRAR_CONSULTA, 
    	    idPaciente : idPacienteSeleccionado,
    	    fecha: fechaActual.getTime(),
    	    cantidades: cantidadesMedicamentos,
    	    codigos: codigosMedicamentos,
    	    isInternado: internado.checked,
    	    medicos: medicosAsignados,
    	    enfermeras: enfermerasAsignadas,
    	    habitacion: habitacionSeleccionada
       },
       success: function( response){
        	alertify.message(response, 2);
        	ocultarComponentes();
        },
        error: function( jqXhr, textStatus, errorThrown ){
        	alertify.error('estado del registros'+ textStatus+ ' error '+ errorThrown); 
        	console.log('estado del registros'+ textStatus+ ' error '+ errorThrown);
        }
    })   
});


/**
 * Manejo del boton check para evaluar si esta internado 
 */
internado.addEventListener('click', ()=>{
    let varInternado = document.getElementById('internado');
    if(varInternado.checked){
    	//consulta de las enfermeras
        $.get('medico', { operacion: CONSULTAR_ENFERMERAS})
        .done(
            function (response){
                tablaEnfermeras.innerHTML= response;
                tablaEnfermeras.style.display='block'; 
            })
        //consulta de los medicos
        $.get('medico', { operacion: CONSULTAR_MEDICOS  })
        .done(
            function (response){
                tablaMedicos.innerHTML= response;
                tablaMedicos.style.display='block'; 
            } )
        //consulta de las habitaciones
        $.get('medico', {
            operacion: CONSULTAR_HABITACIONES})
            .done(
            function (response){
                habitaciones.innerHTML= response;
                habitaciones.style.display='block'; 
            }
        )
    } else{
        tablaEnfermeras.style.display='none'; 
        tablaMedicos.style.display='none'; 
        habitaciones.style.display='none'; 
    }
});


/**
 * 
 * @param {*} boton 
 */
function agregarMedico(boton){
    varIdMedico= boton.getAttribute('id');
    if(medicosAsignados.includes(varIdMedico)){
        alertify.message('El medico ya ha sido agregado al paciente', 2);
    }else{
        medicosAsignados.push(varIdMedico); 
        alertify.success('Medico agregado' , 2 );
    }
}

function agregarEnfermera(boton){
    varIdEnfermera= boton.getAttribute('id');
    if(enfermerasAsignadas.includes(varIdEnfermera)){
        alertify.message('La enfermera ya ha sido agregada al paciente', 2);
    }else{
        enfermerasAsignadas.push(varIdEnfermera); 
        alertify.success('Enfermera agregada' , 2 );
    }
}
