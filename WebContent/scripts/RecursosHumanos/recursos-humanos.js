//post
const DESPIDO_DE_EMPLEADO = 2;
const RECONTRATACION_DE_EMPLEADO = 3;

//get
const CONSULTAR_EMPLEADOS_ACTIVOS = 1;
const CONSULTAR_EMPLEADOS_NO_ACTIVOS = 2;

var botonDespedir=      document.getElementById('despedirEmpleado').addEventListener('click', mostrarEmpleadosActivos);
var botonRecontratar=   document.getElementById('recontratarEmpleado').addEventListener('click', mostrarEmpleadosDesactivados);

let idContratoSeleccionado = 0;
let botonContatacion = document.getElementById('contratarEmpleado'); 
let formContratacion = document.getElementById('formContratacion');
let tablaEmpleados   = document.getElementById('empleados'); 

botonContatacion.addEventListener('click', () =>{
    formContratacion.style.display='block'; //mostrar formulario
});

formContratacion.style.display='none';  //ocutlar formulario

/**
 * Funcion para recontratar a un empleado. 
 * @param {*} boton 
 */
function recontratarEmpleado(boton){
    let salarioIngresado =0;
    idContratoSeleccionado = boton.getAttribute('id');
    alertify.prompt("Ingrese el nuevo salario", "0.00",
    function(evt, value ){
        $.ajax({
            url: 'recursos-humanos',
            dataType: 'text',
            type: 'post',
            data: {
                operacion: RECONTRATACION_DE_EMPLEADO,
                contrato: idContratoSeleccionado, 
                salario: value
           },
           success: function(response){
                alertify.message('Contrato ' + idContratoSeleccionado+' recontratado', 2);
                mostrarEmpleadosDesactivados();
            },
            error: function( jqXhr, textStatus, errorThrown ){
                alertify.error('estado del registros'+ textStatus+ ' error '+ errorThrown); 
                console.log('estado del registros'+ textStatus+ ' error '+ errorThrown);
            }
        })
    },
    function(){
        alertify.error('Cancelado');
    });
}

/**
 * Funcion para despedir a un empleado 
 * @param {*} boton 
 */
function despedirEmpleado(boton){
    idContratoSeleccionado = boton.getAttribute('id');
    $.ajax({
        url: 'recursos-humanos',
        dataType: 'text',
        type: 'post',
        data: {
            operacion: DESPIDO_DE_EMPLEADO,
            contrato: idContratoSeleccionado
       },
       success: function( response){
            alertify.message('Contrato ' + idContratoSeleccionado+' despedido', 2);
            mostrarEmpleadosActivos();
        },
        error: function( jqXhr, textStatus, errorThrown ){
        	alertify.error('estado del registros'+ textStatus+ ' error '+ errorThrown); 
        	console.log('estado del registros'+ textStatus+ ' error '+ errorThrown);
        }
    })
}

/**
 * Oculta todos los componentes que poseen la clase de ocultable
 */
function ocultarComponentes(){
    tablaEmpleados.style.display='none'; 
}


/**
 * Consulta la base de datos en busca de los empleados activos
 */
function mostrarEmpleadosActivos (){
    ocultarComponentes(); 
    $.ajax({
        url: 'recursos-humanos',
        dataType: 'text',
        type: 'get',
        data: {
            operacion: CONSULTAR_EMPLEADOS_ACTIVOS 
       },
       success: function( response){
            tablaEmpleados.style.display='block'; 
            document.getElementById('empleados').innerHTML = response;
        },
        error: function( jqXhr, textStatus, errorThrown ){
        	alertify.error('estado del registros'+ textStatus+ ' error '+ errorThrown); 
        	console.log('estado del registros'+ textStatus+ ' error '+ errorThrown);
        }
    })
}

/**
 * Consulta las base de datos en busca de los empleados que han sido despedidos. 
 */
function mostrarEmpleadosDesactivados (){
    ocultarComponentes(); 
    $.ajax({
        url: 'recursos-humanos',
        dataType: 'text',
        type: 'get',
        data: {
            operacion: CONSULTAR_EMPLEADOS_NO_ACTIVOS
       },
       success: function( response){
            tablaEmpleados.style.display='block'; 
            document.getElementById('empleados').innerHTML = response;
        },
        error: function( jqXhr, textStatus, errorThrown ){
        	alertify.error('estado del registros'+ textStatus+ ' error '+ errorThrown); 
        	console.log('estado del registros'+ textStatus+ ' error '+ errorThrown);
        }
    })
}