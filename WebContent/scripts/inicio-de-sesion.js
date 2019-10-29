const enviar=  document.getElementById("enviar");
enviar.addEventListener("click", iniciarSession);

const correoVar = document.getElementById("email");
const passwordVar = document.getElementById("password");

function iniciarSession(){
	alert(correoVar.value + passwordVar.value);
	$.post('inicioDeSesion', {
		correo : correoVar.value,
		password: passwordVar.value
	}, function(responseText) {
		$('#tablaDeResultados').html(responseText);
	});
}