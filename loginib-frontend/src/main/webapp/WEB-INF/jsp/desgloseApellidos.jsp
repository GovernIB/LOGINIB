<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="es">
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />

    <title>Desglose Apellidos</title>

	<!--[if IE]><link rel="shortcut icon" href="imgs/favicon/favicon.ico" type="image/x-icon" /><![endif]-->
	<link rel="apple-touch-icon-precomposed" href="imgs/favicon/favicon-apple.png" />

    <link rel="icon" href="imgs/favicon/favicon.png" />

	<link rel="stylesheet" media="screen" href="../estilos/imc-loginib.css" />

	<script type="text/javascript">

		function capitalize(element){
			element.value = element.value.toUpperCase();
		}

		function fullName(){
			let nombre = document.getElementById("nombre");
			let ape1 = document.getElementById("apellido1");
			let ape2 = document.getElementById("apellido2");
			let nombreCompleto = document.getElementById("nombreCompleto");

			if(ape2.value === '') {
				nombreCompleto.value=nombre.value.toUpperCase()+' '+ape1.value.toUpperCase();
			} else {
				nombreCompleto.value=nombre.value.toUpperCase()+' '+ape1.value.toUpperCase()+' '+ape2.value.toUpperCase();
			}
		}

		function validate(){

			let error = false;
			/* Nombre introducido por el ciudadano */
			let nombre = document.getElementById("nombre");
			/* Apellido introducido por el ciudadano */
			let apellido1 = document.getElementById("apellido1");
			/* Cogemos el valor que retorna el certificado */
			let nombreCert = document.getElementById("nombreCompletoCert").value;
			/* Se divide el nombre completo del certificado por partículas */
			let particulas = nombreCert.split(' ');
			/* Nombre completo introducido por el ciudadano */
			let nombreCompleto = document.getElementById("nombreCompleto").value;
			console.log("Nombre Completo: " + nombreCompleto);
			/* Se divide el nombre completo introducido por partículas */
			let particulasIntroducidas = nombreCompleto.split(' ');
			console.log("Partículas: " + particulasIntroducidas);

			/* Validamos que los nombres contienen las mismas partículas */
			let particulasOrdenadas = particulas.sort().join(' ');
			console.log("PArticulas Ordenadas: " + particulasOrdenadas);
			let particulasIntroducidasOrdenadas = particulasIntroducidas.sort().join(' ');
			console.log("PArticulas Ordenadas: " + particulasIntroducidasOrdenadas);
			let list = document.getElementById("listVal");
			list.innerHTML="";


			/* Validaciones */
			if(nombre.value.length === 0) {
				nombre.style.borderColor = "red";
				list.innerHTML = "<li><p><fmt:message key="desgloseApellidos.msgValNombre"/></p></li>";
				if (apellido1.value.length === 0) {
					apellido1.style.borderColor = "red";
					list.innerHTML = list.innerHTML + "<li><p><fmt:message key="desgloseApellidos.msgValApellido1"/></p></li>";
				}
				error=true;
			} else if (apellido1.value.length === 0) {
				apellido1.style.borderColor = "red";
				list.innerHTML = list.innerHTML + "<li><p><fmt:message key="desgloseApellidos.msgValApellido1"/></p></li>";
				error=true;
			} else if(particulasOrdenadas !== particulasIntroducidasOrdenadas) {
				error = true;
				console.log("1.-: " + particulasOrdenadas);
				console.log("2.- : " + particulasIntroducidasOrdenadas);
				list.innerHTML = list.innerHTML + "<li><p><fmt:message key="desgloseApellidos.msgValParticulas"/> " +particulas.join(', ') +  "</p></li>";
			}

			if(error){
				list.classList.add('lib-desglose-val');
				list.innerHTML = "<li id='errores'><p><fmt:message key="desgloseApellidos.errores"/></p></li>" + list.innerHTML;
			}
			return !error;
		}

		function cambiarBorde(element){
			element.style.borderColor = "grey";
		}
	</script>


</head>
<body>

		<!-- cabecera -->
	<c:if test="${not empty personalizacion.logourl or not empty personalizacion.titulo or not empty personalizacion.css }">

		<div class="imc-cabecera lib-cabecera">
			<h1>
	    		<c:if test="${not empty personalizacion.logourl}">
		    		<img src="<c:out value = "${personalizacion.logourl}"  escapeXml="false"/>" alt="<c:out value = "${personalizacion.logoalt}"/>">
				</c:if>
	    		<c:if test="${not empty personalizacion.titulo}">
		    		<span><c:out value = "${personalizacion.titulo}" escapeXml="false"/></span>
				</c:if>
	    	</h1>
		</div>
	</c:if>


	<!-- contenidor -->

	<div id="imc-contenidor" class="imc-contenidor">

		<div id="imc-contingut" class="lib-desglose" >
			<div class="imc--c">
					<ul>
						<li class="lib-desglose-list">
							<div class="lib-desglose-info">
								<p>
									<fmt:message key="desgloseApellidos.msg2"/>
								</p>
							</div>
							<ul id="listVal" class=""></ul>
							<div class="lib-desglose-form">
								<form method="post" id="desgloseNombre"  action="${callback}" onsubmit="return validate()">
								  	<label for="nombreCompletoCert"><span><fmt:message key="datosCertificado.nombreCompletoCert"/></span> :</label>
								  	<input id="nombreCompletoCert" type="text" name="nombreCert" value="<c:out value="${nombreDef}"/> <c:out value="${apellido1Def}"/> <c:out value="${apellido2Def}"/>" readonly>
								  	<label for="nombre"><span><fmt:message key="datosCertificado.nombre"/></span> *: </label>
								  	<input id="nombre" type="text" name="nombre" value="<c:out value="${nombre}"/>" onfocus="cambiarBorde(this)" onblur="capitalize(this)" onchange="fullName()">
								  	<label for="apellido1"><span><fmt:message key="datosCertificado.apellido1"/></span> *: </label>
								  	<input id="apellido1" type="text" name="apellido1" value="<c:out value="${apellido1}"/>" onfocus="cambiarBorde(this)" onblur="capitalize(this)" onchange="fullName()">
								  	<label for="apellido2"><span><fmt:message key="datosCertificado.apellido2"/></span>: </label>
								  	<input id="apellido2" type="text" name="apellido2" value="<c:out value="${apellido2}"/>" onfocus="cambiarBorde(this)" onblur="capitalize(this)" onchange="fullName()">
								  	<label for="nombreCompleto"><span><fmt:message key="datosCertificado.nombreCompleto"/></span>: </label>
								  	<input id="nombreCompleto" type="text" name="nombreCompleto" value="<c:out value="${nombre} ${apellido1} ${apellido2}"/>" readonly >
								  	<input type="hidden" name="nif" value="<c:out value="${nif}"/>">
								  	<input type="hidden" id="nombreDef" value="<c:out value="${nombreDef}"/>">
								  	<input type="hidden" id="apellido1Def" value="<c:out value="${apellido1Def}"/>">
								  	<input type="hidden" id="apellido2Def" value="<c:out value="${apellido2Def}"/>">
								  	<input type="hidden" id="ticket" name="ticket" value="<c:out value="${ticket}"/>">
								  	<button id="enviar" type ="submit"><fmt:message key="datosCertificado.btnGuardarCambios"/></button>
								  </form>
							</div>
						</li>
					</ul>
			</div>

		</div>

	</div>
</body>
</html>