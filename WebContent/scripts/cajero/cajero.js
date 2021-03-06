//constantes del metodo GET
const CONSULTAR_CUENTAS_DE_INTERNADOS = 1; 
const CONSULTAR_DETALLE_CUENTA = 2 ; 

//constate del metodo POST 
const PAGAR_CUENTA_CLIENTE = 1; 

//componentes 
let tablaPacientes = document.getElementById('tablaPacientes'); 
let pagarCuenta = document.getElementById('botonPagarCuenta'); 

window.onload = function() {
    ocultarComponentes(); 
}

function ocultarComponentes(){
    pagarCuenta.style.display = 'none';
    tablaPacientes.style.display ='none';
}

//funciones 
pagarCuenta.addEventListener('click', pagarCuentaCliente); 

/**
 * Consulta los pacientes que contienen una cuenta por pagar en el hospital
 */

 $('#btnConsultarCuentas').click(() => {
    $.ajax({
		url: 'cajero',
		dataType: 'text',
		type: 'get',
		data: {
			operacion: 1
       } ,
	   success: function( response){
			ocultarComponentes(); 
			tablaPacientes.style.display = 'block';
            tablaPacientes.innerHTML = response; 
		},
		error: function( jqXhr, textStatus, errorThrown ){
			alertify.error(errorThrown); 
		}
	})
 }); 


/**
 * Consulta el detalle de la cuenta de un cliente especifico
 */
function consultarCuentaCliente(boton){
    idPacienteSeleccionado = boton.getAttribute('id');
    $.ajax({
        url: 'cajero',
        dataType: 'text',
        type: 'get',
        data: {
            operacion: CONSULTAR_DETALLE_CUENTA, 
            cuiPaciente : idPacienteSeleccionado
       },
       success: function( response){
            tablaPacientes.innerHTML = response; 
            tablaPacientes.style.display = 'block'; 
            pagarCuenta.style.display = 'block'; 
        }, 
        error: function( jqXhr, textStatus, errorThrown ){ 
        	alertify.error('estado del registros'+ textStatus+ ' error '+ errorThrown); 
        	console.log('estado del registros'+ textStatus+ ' error '+ errorThrown);
        } 
    }) 
} 

/**
 * Envia la peticion al servidor para pagar la cuenta del paciente seleccionado 
 */
function pagarCuentaCliente(){
    $.ajax({
        url: 'cajero',
        dataType: 'text',
        type: 'post',
        data: {
            operacion: PAGAR_CUENTA_CLIENTE, 
            cuiPaciente : idPacienteSeleccionado
       },
       success: function( response){
        	alertify.message(response, 2); 
        },
        error: function( jqXhr, textStatus, errorThrown ){
        	alertify.error('estado del registros'+ textStatus+ ' error '+ errorThrown); 
        	console.log('estado del registros'+ textStatus+ ' error '+ errorThrown);
        }
    })
}