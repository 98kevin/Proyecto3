
var box1 = document.getElementById("pass1").focusout(evaluarPassword());
var box2 = document.getElementById("pass2").focusout(evaluarPassword());

function evaluarPassword(){
	var p1 = box1.value;
	var p2 = box2.value;
	var espacios = false;
	var cont = 0;
	
	while (!espacios && (cont < p1.length)) {
		  if (p1.charAt(cont) == " ")
		    espacios = true;
		  cont++;
	}
 
	if (espacios) {
		alert ("La contraseña no puede contener espacios en blanco");
	}

	if (p1.length == 0 || p2.length == 0) {
	    	alert("Los campos de la password no pueden quedar vacios");
	  }

	if (p1 != p2) {
	    alert("Las passwords deben de coincidir");
	  }
}