function fullName(element){
	capitalize(element);
	let nombre = document.getElementById("dialogDefinicionVersionDesgloses:nombre");
	let ape1 = document.getElementById("dialogDefinicionVersionDesgloses:apellido1");
	let ape2 = document.getElementById("dialogDefinicionVersionDesgloses:apellido2");
	let nombreCompleto = document.getElementById("dialogDefinicionVersionDesgloses:nombreComp");
	let apellidos = document.getElementById("dialogDefinicionVersionDesgloses:apellidoComp");

	nombreCompleto.value=nombre.value.toUpperCase()+' '+ape1.value.toUpperCase()+' '+ape2.value.toUpperCase();
	apellidos.value = ape1.value.toUpperCase()+' '+ape2.value.toUpperCase();
}

function capitalize(element){
	element.value = element.value.toUpperCase();
}