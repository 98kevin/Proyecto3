
const NUEVA_CONSULTA= 1;
const REGISTRAR_OPERACION= 2;
const REGISTRAR_CONSULTA= 3;  //Registro completo de la consulta
const CONSULTAR_MEDICAMENTOS= 4; 
const CONSULTAR_ENFERMERAS= 5;
const CONSULTAR_MEDICOS = 6;
const AGREGAR_MEDICAMENTO = 7;  
const CONSULTAR_HABITACIONES=8;

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

let codigosMedicamentos = []; 
let cantidadesMedicamentos = []; 
let medicosAsignados = [];
let enfermerasAsignadas =[]; 

//formulario de consulta
let formConsulta = document.getElementById('formConsulta');
let botonTerminarConsulta = document.getElementById('botonAgregarMedicamento');


//al cargar la pagina 
formConsulta.style.display='none';
tablaEnfermeras.style.display='none'; 
tablaMedicos.style.display='none'; 
habitaciones
/*
*Evento del boton del encabezado Nueva Consulta
*/

btnNuevaConsulta.addEventListener('click', () => {
    $.post('medico', {
        operacion: NUEVA_CONSULTA
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
});


/*
*Evento del boton del encabezado Registrar Cirugia 
*/
btnRegistrarCirugia.addEventListener('click', () =>{
    
});


/*
*Evento del boton del encabezado Asignar Medicamento
*/
btnAsignarMedicamento.addEventListener('click', () =>{
    
});

function seleccionarPaciente(boton){
    idPacienteSeleccionado= boton.getAttribute('id');
    alertify.success('Seleccion correcta',2); 
    $.get('medico', {
        operacion: CONSULTAR_MEDICAMENTOS
    }).done(
        function (response){
            tablaResultados.style.display='block';
            tablaResultados.innerHTML = response; 
            formConsulta.style.display='block';
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
    var cantidadesPrueba=[1,2,3,4];
    $.ajax({
    url:"medico",
    type:"POST",
    dataType:'json',
    data: {
        operacion: REGISTRAR_CONSULTA, 
	    cantidades:cantidadesPrueba,
	    operacion: REGISTRAR_CONSULTA,
	    idPaciente : idPacienteSeleccionado,
	    fecha: fechaActual.getTime(),
	    cantidades: cantidadesMedicamentos,
	    codigos: codigosMedicamentos,
	    isInternado: internado.checked,
	    medicos: medicosAsignados,
	    enfermeras: enfermerasAsignadas,
	    habitacion: document.getElementById('habitaciones').value
   },
    success:function(data){
        alertify.success('Consulta realizada con exito', 2); 
        tablaResultados.style.display='none';
    }
});
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
        habitaciones.sytle.display='none;'
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
