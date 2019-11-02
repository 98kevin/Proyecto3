const enviar=  document.getElementById("enviar");
enviar.addEventListener("click", iniciarSession);

const correoVar = document.getElementById("email");
const passwordVar = document.getElementById("password");

function iniciarSession(){
	$.post('inicioDeSesion', {
		correo : correoVar.value,
		password: passwordVar.value
	}).done(
		function(response){
		window.location = response;
	}).fail(
		function(xhr, status, error){
			alert('estado del registro '+ status + '\n'+ error);
		}
	);
}