const enviar=  document.getElementById("enviar");

enviar.addEventListener("click", () => {
	$.post('inicioDeSesion', {
		correo : document.getElementById("email").value,
		password: document.getElementById("password").value
	}).done(
		function(response){
		window.location = response;
	}).fail(
		function(xhr, status, error){
			alert('estado del registro '+ status + '\n'+ error);
		}
	);
	});
