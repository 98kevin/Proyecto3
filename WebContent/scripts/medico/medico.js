//constantes del metodo GET
const CONSULTAR_MEDICAMENTOS= 4;
const CONSULTAR_ENFERMERAS= 5;
const CONSULTAR_MEDICOS = 6;
const CONSULTAR_HABITACIONES=8;
const CONSULTAR_PACIENTES_INTERNADOS= 9;
const CONSUTLAR_CIRUGIAS=10;
const CONSULTAR_CIRUGIAS_PENDIENTES=13;
const CONSULTAR_MEDICOS_ESPECIALISTAS=14;
const CONSULTAR_INTERNADOS = 15;

// constantes del metodo POST
const CONSULTAR_PACIENTES_REGISTRADOS= 1;
const REGISTRAR_OPERACION= 2;
const REGISTRAR_CONSULTA= 3;  //Registro completo de la consulta
const AGREGAR_MEDICAMENTO = 7;
const ASINGAR_CIRUGIA=11;
const TERMINAR_CIRUGIA=12;
const DAR_DE_ALTA = 16; 

var idPacienteSeleccionado=0;
var numeroDeHabitacion = 0;

let btnNuevaConsulta = document.getElementById('btnNuevaConsulta');
let btnAsignarMedicamento = document.getElementById('btnAsignarMedicamento');
let btnRecetarCirugia = document.getElementById('btnRecetarCirugia');
let btnTerminarCirugia = document.getElementById('btnTerminarCirugia');
let btnConsultarInternados = document.getElementById('btnConsultarInternados'); 

let tablaPacientes = document.getElementById('tablaResultados');
let tablaMedicamentos = document.getElementById('tablaMedicamentos');
let tablaMedicos = document.getElementById('tablaDeMedicos');
let tablaMedicosEspecialistas = document.getElementById('tablaDeMedicosEspecialistas');
let tablaEnfermeras = document.getElementById('tablaDeEnfermeras');
let tablaCirugias = document.getElementById('tablaDeCirugias');
let habitaciones = document.getElementById('habitacion');
let internado = document.getElementById('internado');
let controlFecha = document.getElementById('fecha-consulta');
let formConsulta = document.getElementById('formConsulta');

let codigosMedicamentos = []; 
let cantidadesMedicamentos = []; 
let medicosAsignados = [];
let medicosEspecialistasSeleccionados=[];
let enfermerasAsignadas =[]; 


let botonTerminarConsulta = document.getElementById('botonAgregarMedicamento');
let botonTerminarAsingacionMedicamentos = document.getElementById('btonAsignarMedicamentos');
let recetarCirugia= document.getElementById('btnAsignarCirugia');


window.onload= ocultarComponentes(); 

//al cargar la pagina 
function ocultarComponentes(){
	tablaPacientes.style.display='none';
	tablaEnfermeras.style.display='none'; 
    tablaMedicos.style.display='none'; 
    tablaMedicamentos.style.display='none'; 
    tablaMedicosEspecialistas.style.display='none'; 
    formConsulta.style.display='none';
	habitaciones.style.display='none';
	botonTerminarConsulta.style.display='none';
    controlFecha.style.display='none';
    botonTerminarAsingacionMedicamentos.style.display='none';
    tablaCirugias.style.display='none';
    recetarCirugia.style.display='none';
}


btnRecetarCirugia.addEventListener('click', nuevaCirugia);
btnTerminarCirugia.addEventListener('click', consultarCirugiasPendientes);
recetarCirugia.addEventListener('click', asignarCirugia);
btnConsultarInternados.addEventListener('click',  consultarInternados); 

function consultarInternados(){
    $.ajax({
        url: 'medico',
        dataType: 'text',
        type: 'get',
        data: {
            operacion: CONSULTAR_INTERNADOS
       },
       success: function(response){
           ocultarComponentes();
            tablaPacientes.innerHTML = response; 
            tablaPacientes.style.display = 'block'; 
            acutalizarControlFecha(); 
            controlFecha.style.display = 'block'; 
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error('estado del registros'+ textStatus+ ' error '+ errorThrown); 
            console.log('estado del registros'+ textStatus+ ' error '+ errorThrown);
        }
    })
}

function darDeAlta(boton){
    let cuiPaciente = boton.getAttribute('id'); 
    acutalizarControlFecha(); 
    let fecha = new Date(controlFecha.value); 
    $.ajax({
        url: 'medico',
        dataType: 'text', 
        type: 'post',
        data: {
            operacion: DAR_DE_ALTA, 
            cuiPaciente : cuiPaciente, 
            fecha : fecha.getTime()
       },
       success: function(response){
            alertify.message(response);
            consultarInternados();
            ocultarComponentes();
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error('estado del registros'+ textStatus+ ' error '+ errorThrown); 
            console.log('estado del registros'+ textStatus+ ' error '+ errorThrown);
        }
    })
}


function asignarCirugia(){
    let cirugiaSeleccionada = document.getElementById('selectCirugiasDisponibles').value;
    if(idPacienteSeleccionado==0){
        alertify.error('No se ha seleccionado ningun paciente');
    } else{
        let fecha = new Date(controlFecha.value);
        $.ajax({
            url: 'medico',
            dataType: 'text',
            type: 'post',
            data: {
                operacion: ASINGAR_CIRUGIA,
                fecha: fecha.getTime(),
                paciente: idPacienteSeleccionado,
                cirugia: cirugiaSeleccionada, 
                medicos: medicosAsignados, 
                medicosEspecialistas: medicosEspecialistasSeleccionados
           },
           success: function(response){
                alertify.message(response);
                ocultarComponentes();
            },
            error: function( jqXhr, textStatus, errorThrown ){
                alertify.error('estado del registros'+ textStatus+ ' error '+ errorThrown); 
                console.log('estado del registros'+ textStatus+ ' error '+ errorThrown);
            }
        })
    }
}

/**
 * Muestra la informacion pertinente para la receta de la nueva cirugia
 */
function nuevaCirugia(){
    idPacienteSeleccionado=0;
    acutalizarControlFecha();
    ocultarComponentes();
    alertify.message('Receta de nueva cirugia');
    consultarPacientesRegistrados();
    consultarCirugiasDisponibles();
    consultarMedicos();
    consultarMedicosEspecialistas();
    controlFecha.style.display='block';
    tablaCirugias.style.display='block';
    recetarCirugia.style.display='block';
    tablaPacientes.style.display='block';
}


/**
 * Actualiza el control de fechas a la fecha actual 
 */
function acutalizarControlFecha(){
    let fechaActual = new Date();
    let dias = fechaActual.getDate();
    let mes = fechaActual.getMonth()+1;
    let anio = fechaActual.getFullYear();
    controlFecha.value=anio+'-'+mes+'-'+dias;
}

/**
 * Consulta las cirugias en las que el medico ha estado involucrado
 */
function consultarCirugiasPendientes(){
    ocultarComponentes();
    acutalizarControlFecha();
    $.ajax({
        url: 'medico',
        dataType: 'html',
        type: 'get',
        data: {
            operacion: CONSULTAR_CIRUGIAS_PENDIENTES
       },
       success: function(response){
           tablaCirugias.innerHTML=response;
           tablaCirugias.style.display='block';
           controlFecha.style.display='block';
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alertify.error('estado del registros'+ textStatus+ ' error '+ errorThrown); 
            console.log('estado del registros'+ textStatus+ ' error '+ errorThrown);
        }
    })
}

/**
 * Boton iniciador de una nueva consulta
 */
btnNuevaConsulta.addEventListener('click', () => {
    ocultarComponentes();
    codigosMedicamentos = []; 
    cantidadesMedicamentos = []; 
    medicosAsignados = [];
    medicosEspecialistasSeleccionados = [];
    enfermerasAsignadas =[]; 
    controlFecha.style.display='block';
    formConsulta.style.display='block';
    tablaPacientes.style.display='block';
    tablaMedicamentos.style.display='block';
    botonTerminarConsulta.style.display='block';
    consultarMedicamentos();
    consultarPacientesRegistrados();
});

/*
*Consulta los pacientes registrados en el sistema. 
*/
function consultarPacientesRegistrados(){
    $.post('medico', {
        operacion: CONSULTAR_PACIENTES_REGISTRADOS
    }).done(
        function (response){
            tablaPacientes.innerHTML= response;
        }
    ).fail(
        function (xhr, status, error){
            alert('estado del registros'+ status+ ' error '+ error); 
        }
    )
}

/**
 * Envia la peticion al servidor de consulta de cirugias disponibles e ingresa el resutlado en la tabla de cirugias
 */
function consultarCirugiasDisponibles(){
    $.ajax({
        url: 'medico',
        dataType: 'html',
        type: 'get',
        data: {
            operacion: CONSUTLAR_CIRUGIAS
       },
       success: function(response){
            tablaCirugias.innerHTML = response;
        },
        error: function( jqXhr, textStatus, errorThrown ){
        	alertify.error('estado del registros'+ textStatus+ ' error '+ errorThrown); 
        	console.log('estado del registros'+ textStatus+ ' error '+ errorThrown);
        }
    })
}

/**
 * Consulta los pacientes internados actualmente
 */
btnAsignarMedicamento.addEventListener('click', () =>{
    ocultarComponentes();
    alertify.message('Asingacion de medicamentos', 2);
    consultarMedicamentos();
    botonTerminarAsingacionMedicamentos.style.display='block';
    $.ajax({
        url: 'medico',
        dataType: 'html',
        type: 'get',
        data: {
            operacion: CONSULTAR_PACIENTES_INTERNADOS
       },
       success: function( response){
            tablaPacientes.style.display='block';
            tablaPacientes.innerHTML = response;
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
btnTerminarCirugia.addEventListener('click', () =>{
    
});


/**
 * funcion del boton de agregar medicamentos a pacientes 
 * internados
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
    tablaPacientes.style.display='none';
}

/**
 * Consulta los medicamentos en la base de datos. 
 */
function consultarMedicamentos(){
    let medicamentos;
    $.get('medico', {
        operacion: CONSULTAR_MEDICAMENTOS
    }).done(
        function (response){
            tablaMedicamentos.innerHTML = response;
            tablaMedicamentos.style.display='block';
        }
    ).fail(
        function (xhr, status, error){
            alert('estado del registros'+ status+ ' error '+ error); 
        }
    )
}

/**
 * Agrega un medicamento al arreglo de medicametnos del paciente
 * @param {*} botonAgregar 
 */
function agregarMedicamento(botonAgregar){
    let varIdMedicamento = botonAgregar.getAttribute('id'); 
    let varCantidad = document.getElementById('caja'+ varIdMedicamento).value;
    if(varCantidad > 0 ){
        codigosMedicamentos.push(varIdMedicamento);
        cantidadesMedicamentos.push(varCantidad);
        alertify.success('Medicamento agregado', 2);
    } else{
        alertify.error('No se pudo agregar el medicamento', 2);
    }
}


/*
*  Boton para terminar una consulta y registrarla en el sistema
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
    })   //finalizacion de la peticion ajax
});


/**
 * Manejo del boton check para evaluar si esta internado 
 */
internado.addEventListener('click', ()=>{
    let varInternado = document.getElementById('internado');
    if(varInternado.checked){
        //llamada a las funciones de consulta de informacion
    	consultarEnfermeras();
        consultarMedicos();
        consultarHabitaciones();
    } else{
        tablaEnfermeras.style.display='none'; 
        tablaMedicos.style.display='none'; 
        habitaciones.style.display='none'; 
    }
});

/**
 * Consulta los medicos y los muestra en una tabla
 */
function consultarMedicos(){
    //consulta de los medicos
    $.get('medico', { 
        operacion: CONSULTAR_MEDICOS  
    })
    .done(
        function (response){
            tablaMedicos.style.display='block'; 
            tablaMedicos.innerHTML= response;
        }
    )
}


/**
 * Consulta los medicos especialistas y los muestra en una tabla
 */
function consultarMedicosEspecialistas(){
    //consulta de los medicos especialistas
    $.get('medico', { 
        operacion: CONSULTAR_MEDICOS_ESPECIALISTAS  
    })
    .done(
        function (response){
            tablaMedicosEspecialistas.innerHTML= response;
            tablaMedicosEspecialistas.style.display='block';
        } 
    )
}

function seleccionarMedicoEspecialista(boton){
    varIdMedico= boton.getAttribute('id');
    if(medicosEspecialistasSeleccionados.includes(varIdMedico)){
        alertify.message('El medico ya ha sido agregado a la operacion', 2);
    }else{
        medicosEspecialistasSeleccionados.push(varIdMedico);
        alertify.success('Medico Especialista agregado' , 2 );
    }
}



/**
 * Consulta las enfermeras y las muestra en una tabla
 */
function consultarEnfermeras(){
    //consulta de las enfermeras
    $.get('medico', { 
        operacion: CONSULTAR_ENFERMERAS
    })
    .done(
        function (response){
            tablaEnfermeras.innerHTML= response;
            tablaEnfermeras.style.display='block'; 
        })
}


function consultarHabitaciones(){
    //consulta de las habitaciones
    $.get('medico', {
        operacion: CONSULTAR_HABITACIONES})
        .done(
        function (response){
            habitaciones.innerHTML= response;
            habitaciones.style.display='block'; 
        }
    )
}


/**
 * Agrega el medico al arreglo de medicos seleccionados
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

/**
 * Agrega la enfermera al arreglo de enfermeras seleccionadas
 * @param {*} boton 
 */
function agregarEnfermera(boton){
    varIdEnfermera= boton.getAttribute('id');
    if(enfermerasAsignadas.includes(varIdEnfermera)){
        alertify.message('La enfermera ya ha sido agregada al paciente', 2);
    }else{
        enfermerasAsignadas.push(varIdEnfermera); 
        alertify.success('Enfermera agregada' , 2 );
    }
}


function registrarCirugiaTerminada(boton){
    let idCirugiaTerminada = boton.getAttribute('id');
    let fecha = new Date(controlFecha.value);
    $.ajax({
        url: 'medico',
        dataType: 'text',
        type: 'post',
        data: {
            operacion: TERMINAR_CIRUGIA, 
            idCirugia: idCirugiaTerminada,
            fecha: fecha.getTime()
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
}