<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="<c:out value="${datos.idioma}"/>" lang="<c:out value="${datos.idioma}"/>">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />

	<c:if test="${empty datos.title}">
    	<title>GOIB</title>
	</c:if>
	<c:if test="${not empty datos.title}">
    	<title><c:out value = "${datos.title}" escapeXml="false"/></title>
	</c:if>

	<!--[if IE]><link rel="shortcut icon" href="imgs/favicon/favicon.ico" type="image/x-icon" /><![endif]-->
	<link rel="apple-touch-icon-precomposed" href="imgs/favicon/favicon-apple.png" />

	<c:if test="${empty datos.favicon}">
    	<link rel="icon" href="imgs/favicon/favicon.png" />
	</c:if>
	<c:if test="${not empty datos.favicon}">
    	<link rel="icon" href="<c:out value = "${datos.favicon}"/>"  escapeXml="false"/>
	</c:if>


	<link rel="stylesheet" media="screen" href="estilos/imc-loginib.css" />
	<style type="text/css">
		/* Personalización */
		<c:out value = "${datos.css}"  escapeXml="false"/>
	</style>
</head>
<body>


	<!-- cabecera -->
	<c:if test="${not empty datos.logourl or not empty datos.titulo or not empty datos.css }">

	<div class="imc-cabecera">
		<h1>
	    	<c:if test="${not empty datos.logourl}">
		    	<img src="<c:out value = "${datos.logourl}"  escapeXml="false"/>" alt="<c:out value = "${datos.logoalt}"/>">
			</c:if>
	    	<c:if test="${not empty datos.titulo}">
		    	<span><c:out value = "${datos.titulo}" escapeXml="false"/></span>
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
					<c:if test = "${datos.clave}">
						<li>
							<a href="redirigirLoginClave.html?idSesion=<c:out value = "${datos.idSesion}"/>" class="imc--clave">
				         		<span><fmt:message key="seleccionAutenticacion.clave"/></span>
				         	</a>
						</li>
					</c:if>
					<c:if test = "${datos.clientCert && !datos.clientCertSegundoPlano }">
						<li>
							<a href="client-cert/login.html?idSesion=<c:out value = "${datos.idSesion}"/>" class="imc--certificat">
				         		<span><fmt:message key="seleccionAutenticacion.clientCert"/></span>
				         	</a>

						</li>
					</c:if>
					<c:if test = "${datos.anonimo}">
						<li>
							<a href="loginAnonimo.html?idSesion=<c:out value = "${datos.idSesion}"/>" class="imc--ciutada">
				         		<span><fmt:message key="seleccionAutenticacion.anonimo"/></span>
				         	</a>

						</li>
					</c:if>

				</ul>

			</div>

			<c:if test = "${datos.clientCert && datos.clientCertSegundoPlano }">
				<div class="imc-txtcc" ><fmt:message key="seleccionAutenticacion.txtenlaceClientCert"/>
					<a href="client-cert/login.html?idSesion=<c:out value = "${datos.idSesion}"/>">
		         		<span><fmt:message key="seleccionAutenticacion.enlaceClientCert"/></span>
		         	</a>
				</div>
			</c:if>
		</div>

	</div>
	<!-- /contenidor -->

</body>
</html>