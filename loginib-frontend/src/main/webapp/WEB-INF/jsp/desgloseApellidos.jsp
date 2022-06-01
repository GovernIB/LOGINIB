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

    <c:if test="${empty personalizacion.title}">
    	<title>GOIB</title>
	</c:if>
	<c:if test="${not empty personalizacion.title}">
    	<title><c:out value = "${personalizacion.title}" escapeXml="false"/></title>
	</c:if>

	<!--[if IE]><link rel="shortcut icon" href="imgs/favicon/favicon.ico" type="image/x-icon" /><![endif]-->
	<link rel="apple-touch-icon-precomposed" href="imgs/favicon/favicon-apple.png" />

    <c:if test="${empty personalizacion.favicon}">
    	<link rel="icon" href="imgs/favicon/favicon.png" />
	</c:if>
	<c:if test="${not empty personalizacion.favicon}">
    	<link rel="icon" href="<c:out value = "${personalizacion.favicon}"/>"  escapeXml="false"/>
	</c:if>

	<link rel="stylesheet" media="screen" href="../estilos/imc-loginib.css" />

	<style type="text/css">
		/* Personalización */
		<c:out value = "${personalizacion.css}"  escapeXml="false"/>
	</style>

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
			//console.log("Nombre Completo: " + nombreCompleto);
			/* Se divide el nombre completo introducido por partículas */
			let particulasIntroducidas = nombreCompleto.split(' ');
			//console.log("Partículas: " + particulasIntroducidas);

			/* Validamos que los nombres contienen las mismas partículas */
			let particulasOrdenadas = particulas.sort().join(' ');
			//console.log("PArticulas Ordenadas: " + particulasOrdenadas);
			let particulasIntroducidasOrdenadas = particulasIntroducidas.sort().join(' ');
			//console.log("PArticulas Ordenadas: " + particulasIntroducidasOrdenadas);
			let validacions = document.getElementById("validacions");
			let list = document.getElementById("listVal");
			list.innerHTML="";

			/*Check para verificar que el ciudadano se fija*/
			let check = document.getElementById('checkDesglo');


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
				//console.log("1.-: " + particulasOrdenadas);
				//console.log("2.- : " + particulasIntroducidasOrdenadas);
				list.innerHTML = list.innerHTML + "<li><p><fmt:message key="desgloseApellidos.msgValParticulas"/> " +particulas.join(', ') +  "</p></li>";
			}

			if(!check.checked) {
				error = true;
				list.innerHTML = list.innerHTML + "<li><p><fmt:message key="desgloseApellidos.msgCheck"/></p></li>";
			}

			if(error){
				validacions.classList.add('lib-desglose-val');
				validacions.classList.add('lib-desglose-err');
				list.innerHTML = "<li><h2 id='errores'><fmt:message key="desgloseApellidos.errores"/></h2></li>" + list.innerHTML;
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
							<h1 id="titulo"><fmt:message key="desgloseApellidos.titulo"/></h1>
							<div class="lib-desglose-info">
								<p>
									<fmt:message key="desgloseApellidos.msg2"/>
								</p>
							</div>
							<div id="validacions">
								<ul id="listVal" class=""></ul>
							</div>
							<div class="lib-desglose-form">
								<form method="post" id="desgloseNombre"  action="${callback}" onsubmit="return validate()">
								  	<label for="nombreCompletoCert"><span><fmt:message key="datosCertificado.nombreCompletoCert"/></span>:</label>
								  	<input id="nombreCompletoCert" type="text" name="nombreCert" value="<c:out value="${nombreDef}"/> <c:out value="${apellido1Def}"/> <c:out value="${apellido2Def}"/>" readonly>
								  	<label for="nombre"><span><fmt:message key="datosCertificado.nombre"/></span>*:</label>
								  	<input id="nombre" type="text" name="nombre" value="<c:out value="${nombre}"/>" onfocus="cambiarBorde(this)" onblur="capitalize(this)" onchange="fullName()">
								  	<label for="apellido1"><span><fmt:message key="datosCertificado.apellido1"/></span>*:</label>
								  	<input id="apellido1" type="text" name="apellido1" value="<c:out value="${apellido1}"/>" onfocus="cambiarBorde(this)" onblur="capitalize(this)" onchange="fullName()">
								  	<label for="apellido2"><span><fmt:message key="datosCertificado.apellido2"/></span>: </label>
								  	<input id="apellido2" type="text" name="apellido2" value="<c:out value="${apellido2}"/>" onfocus="cambiarBorde(this)" onblur="capitalize(this)" onchange="fullName()">
								  	<label for="nombreCompleto"><span><fmt:message key="datosCertificado.nombreCompleto"/></span>:</label>
								  	<input id="nombreCompleto" type="text" name="nombreCompleto" value="<c:out value="${nombre} ${apellido1} ${apellido2}"/>" readonly >
								  	<input type="hidden" name="nif" value="<c:out value="${nif}"/>">
								  	<input type="hidden" id="nombreDef" value="<c:out value="${nombreDef}"/>">
								  	<input type="hidden" id="apellido1Def" value="<c:out value="${apellido1Def}"/>">
								  	<input type="hidden" id="apellido2Def" value="<c:out value="${apellido2Def}"/>">
								  	<input type="hidden" id="ticket" name="ticket" value="<c:out value="${ticket}"/>">
								  	<div class="lib-desglose-check">
								  		<div class="imc-check">
								  			<input id="checkDesglo" type="checkbox" />
								  			<span></span>
								  		</div>
								  		<span><fmt:message key="datosCertificado.textoCheck"/></span>
								  	</div>
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