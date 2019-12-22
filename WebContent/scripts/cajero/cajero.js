//constantes del metodo GET
const CONSULTAR_CUENTAS_DE_INTERNADOS = 1; 
const CONSULTAR_DETALLE_CUENTA = 2 ; 


/**
 * Consulta los pacientes que contienen una cuenta por pagar en el hospital
 */
function consultarPacientesConCuenta(){
    $.ajax({
        url: 'cajero',
        dataType: 'text',
        type: 'get',
        data: {
            operacion : CONSULTAR_CUENTAS_DE_INTERNADOS
       },
       success: function( response){
            ocultarComponentes();
            tablaPacientes.innerHTML = response; 
            tablaPacientes.style.display = block;
        },
        error: function( jqXhr, textStatus, errorThrown ){
        	alertify.error('estado del registros'+ textStatus+ ' error '+ errorThrown); 
        	console.log('estado del registros'+ textStatus+ ' error '+ errorThrown);
        }
    })
}


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
        	alertify.message(response, 2);
        	ocultarComponentes();
        },
        error: function( jqXhr, textStatus, errorThrown ){
        	alertify.error('estado del registros'+ textStatus+ ' error '+ errorThrown); 
        	console.log('estado del registros'+ textStatus+ ' error '+ errorThrown);
        }
    })
}