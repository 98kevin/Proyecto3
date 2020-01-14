//constantes de get
const CONSULTAR_MEDICAMENTOS_PACIENTE = 1;
const CONSULTAR_PACIENTES_DE_ENFERMERA = 2; 
//constantes de post 
const ASINGAR_MEDICAMENTOS_PACIENTE = 1;
const CONSULTAR_MEDICAMENTOS_DE_PACIENTE = 2
var pacienteSeleccionado = 0; 

let botonConsultarMedicamentos  = document.getElementById('suministrarMedicamento');
let botonSuministrarMedicamento = document.getElementById('');
let botonVenderMedicamento      = document.getElementById('venderMedicamento');

//listado de componentes 
let tablaMedicamentos = document.getElementById('tablaMedicamentos'); 
let pacientes = document.getElementById('pacientes');
let controlFecha = document.getElementById('controlFecha');

window.onload = ocultarComponentes();


/**
 * Oculta los componenetes de pantalla
 */
function ocultarComponentes(){
    tablaMedicamentos.style.display = 'none';
    pacientes.style.display = 'none';
    controlFecha.style.display = 'none';
}


/**
 * Se encarga de mostrar los medicamentos recetados de los medicos a los pacientes. 
*/

botonConsultarMedicamentos.addEventListener('click', () => {
    $.ajax({
        url: 'medicamentosDeInternados',
        dataType: 'html',
        type: 'get',
        data: {
            operacion: CONSULTAR_MEDICAMENTOS_DE_PACIENTE,
            cui: pacienteSeleccionado
       },
    success: function( response){
        consultarPacientesDeEnfermera(); 
        tablaMedicamentos.innerHTML = response;
        tablaMedicamentos.style.display = 'block';
        acutalizarControlFecha(); 
        controlFecha.style.display = 'block'; 
        },
    error:  function ( jqXhr, textStatus, errorThrown ){
        alertify.error('estado del registros'+ textStatus+ ' error '+ errorThrown);
        console.log('estado del registros'+ textStatus+ ' error '+ errorThrown);
    }
    })   //finalizacion de la peticion ajax
});

/**
 * Se encarga de mostrar los medicamentos a vender. 
 */
botonVenderMedicamento.addEventListener('click',  ()=>{
    
});


/**
 * Se encarga de suministrar el medicamento al paciente seleccionado
 * @param {*} boton 
 */
function suministrarMedicamento(boton){
    id = boton.getAttribute('id');
    let fecha = new Date(controlFecha.value);
    $.ajax({
        url: 'suministarMedicamento',
        dataType: 'html',
        type: 'post',
        data: {
            operacion: ASINGAR_MEDICAMENTOS_PACIENTE, 
            medicamento : id, 
            cantidad : document.getElementById('cantidad' + id).value, 
            pacienteSeleccionado : pacienteSeleccionado,
            fecha : fecha.getTime()
       },
    success: function(response){
        alertify.message(response);
        },
    error: function(textStatus, errorThrown){
        alertify.error('estado del registros'+ textStatus+ ' error '+ errorThrown);
        console.log('estado del registros'+ textStatus+ ' error '+ errorThrown);
    }
    })   //finalizacion de la peticion ajax
}

/**
 * Consulta los pacientes internado asingados a una enfermera 
 */
function consultarPacientesDeEnfermera(){
    $.ajax({
        url: 'consultarPacientes',
        dataType: 'html',
        type: 'get',
        data: {
            operacion: CONSULTAR_PACIENTES_DE_ENFERMERA
       },
    success: function(response){
        pacientes.innerHTML = response;
        pacientes.style.display = 'block'; 
        },
    error: function( jqXhr, textStatus, errorThrown ){
        alertify.error('estado del registros'+ textStatus+ ' error '+ errorThrown);
        console.log('estado del registros'+ textStatus+ ' error '+ errorThrown);
    }
    })   //finalizacion de la peticion ajax
}

function seleccionarPaciente (){
    var select= document.getElementById("selector");
    pacienteSeleccionado = select.options[select.selectedIndex].value;
    $.ajax({
        url: 'medicamentosDeInternados',
        dataType: 'html',
        type: 'post',
        data: {
            operacion: CONSULTAR_MEDICAMENTOS_DE_PACIENTE, 
            cui: pacienteSeleccionado 
       },
    success: function( response){
        tablaMedicamentos.innerHTML = response;
        tablaMedicamentos.style.display = 'block';
        acutalizarControlFecha(); 
        controlFecha.style.display = 'block'; 
        alertify.message('Codigo del paciente: ' + pacienteSeleccionado);
        },
    error:  function ( jqXhr, textStatus, errorThrown ){
        alertify.error('estado del registros'+ textStatus+ ' error '+ errorThrown);
        console.log('estado del registros'+ textStatus+ ' error '+ errorThrown);
    }
    })  
}



/**
 * Actualiza el control de fechas a la fecha actual 
 */
function acutalizarControlFecha(){
    controlFecha.value = new Date().toLocaleDateString('en-CA');
}