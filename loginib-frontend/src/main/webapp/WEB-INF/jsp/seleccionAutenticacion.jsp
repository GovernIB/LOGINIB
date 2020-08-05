<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!doctype html>
<html lang="<c:out value="${datos.idioma}"/>">
<head>

	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />

	<c:if test="${empty datos.personalizacion.title}">
    	<title>GOIB</title>
	</c:if>
	<c:if test="${not empty datos.personalizacion.title}">
    	<title><c:out value = "${datos.personalizacion.title}" escapeXml="false"/></title>
	</c:if>

	<!--[if IE]><link rel="shortcut icon" href="imgs/favicon/favicon.ico" type="image/x-icon" /><![endif]-->
	<link rel="apple-touch-icon-precomposed" href="imgs/favicon/favicon-apple.png" />

	<c:if test="${empty datos.personalizacion.favicon}">
    	<link rel="icon" href="imgs/favicon/favicon.png" />
	</c:if>
	<c:if test="${not empty datos.personalizacion.favicon}">
    	<link rel="icon" href="<c:out value = "${datos.personalizacion.favicon}"/>"  escapeXml="false"/>
	</c:if>


	<link rel="stylesheet" media="screen" href="estilos/imc-loginib.css" />

	<style type="text/css">
		/* Personalización */
		<c:out value = "${datos.personalizacion.css}"  escapeXml="false"/>
	</style>

	<script src="js/utils/jquery-3.5.0.min.js"></script>

	<!-- inicia! -->
	<script src="js/imc-loginib--app.js"></script>

</head>

<body>

	<!-- cabecera -->
	<c:if test="${not empty datos.personalizacion.logourl or not empty datos.personalizacion.titulo or not empty datos.personalizacion.css }">

	<div class="imc-cabecera">
		<h1>
	    	<c:if test="${not empty datos.personalizacion.logourl}">
		    	<img src="<c:out value = "${datos.personalizacion.logourl}"  escapeXml="false"/>" alt="<c:out value = "${datos.personalizacion.logoalt}"/>">
			</c:if>
	    	<c:if test="${not empty datos.personalizacion.titulo}">
		    	<span><c:out value = "${datos.personalizacion.titulo}" escapeXml="false"/></span>
			</c:if>

	    </h1>
	</div>

	</c:if>


	<!-- contenidor -->

	<div id="imc-contenidor" class="imc-contenidor">

		<div class="imc-contingut" id="imc-contingut">

			<div class="imc--c">

				<h1><span><fmt:message key="seleccionAutenticacion.texto"/></span></h1>

				<ul>

					<!-- CLAVE -->
					<c:if test = "${datos.clave}">
						<li>
							<a href="redirigirLoginClave.html?idSesion=<c:out value = "${datos.idSesion}"/>" class="imc--clave">
				         		<span><fmt:message key="seleccionAutenticacion.clave"/></span>
				         	</a>
						</li>
					</c:if>

					<!-- CLIENTCERT -->
					<c:if test = "${datos.clientCert && !datos.clientCertSegundoPlano }">
						<li>
							<a href="client-cert/login.html?idSesion=<c:out value = "${datos.idSesion}"/>" class="imc--certificat">
				         		<span><fmt:message key="seleccionAutenticacion.clientCert"/></span>
				         	</a>
						</li>
					</c:if>

					<!-- USUARIO/PASSWORD -->
					<c:if test = "${datos.usuarioPassword}">
						<li>
							<div>
								<span><fmt:message key="seleccionAutenticacion.usuarioPassword"/></span>
								<form id="imc--login" method="post" action="loginUsuarioPassword.html">
									<input name="idSesion" type="hidden" value="<c:out value = "${datos.idSesion}"/>" />
									<input type="text" name="usuario" aria-label="Usuario" placeholder="<fmt:message key="seleccionAutenticacion.usuarioPassword.usuario"/>" />
									<input type="password" name="password" aria-label="Contraseña" placeholder="<fmt:message key="seleccionAutenticacion.usuarioPassword.password"/>" />
									<button type="button" onclick="javascript:document.getElementById('imc--login').submit()" disabled><span><fmt:message key="seleccionAutenticacion.usuarioPassword.login"/></span></button>
								</form>
							</div>
						</li>
					</c:if>

					<!-- ANONIMO -->
					<c:if test = "${datos.anonimo}">
						<li>
							<a href="loginAnonimo.html?idSesion=<c:out value = "${datos.idSesion}"/>" class="imc--ciutada">
				         		<span><fmt:message key="seleccionAutenticacion.anonimo"/></span>
				         	</a>
						</li>
					</c:if>

				</ul>

			</div>

			<!-- CLIENTCERT COMO ENLACE -->
			<c:if test = "${datos.clientCert && datos.clientCertSegundoPlano }">
				<div class="imc-txtcc" >
					<fmt:message key="seleccionAutenticacion.txtenlaceClientCert"/>
					<a href="client-cert/login.html?idSesion=<c:out value = "${datos.idSesion}"/>">
		         		<span><fmt:message key="seleccionAutenticacion.enlaceClientCert"/></span>
		         	</a>
				</div>
			</c:if>

		</div>

	</div>





	<!-- missatges -->

	<div id="imc-missatge" class="imc--missatge" data-tipus="enviant" role="alert" aria-labelledby="missatge-titol" aria-modal="true" aria-hidden="true" tabindex="0">
		<div class="imc--contingut">

			<header>
				<h2 id="missatge-titol"><span><fmt:message key="seleccionAutenticacion.autenticando"/></span></h2>
				<p><fmt:message key="seleccionAutenticacion.espere"/></p>
			</header>

		</div>
	</div>


</body>
</html>