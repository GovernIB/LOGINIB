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

	<link href="estilos/imc-loginib.css" rel="stylesheet" type="text/css"/>
	<style type="text/css">
		/* Personalización */
		<c:out value = "${datos.personalizacion.css}"  escapeXml="false"/>
	</style>

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

				<h1 class="imc--atencio"><span><fmt:message key="atencion"/></span></h1>
				<c:if test="${not empty datos.mensajeErrorGeneral}">
				<span><fmt:message key="${datos.mensajeErrorGeneral}"/></span>
				</c:if>
				<c:if test="${not empty datos.mensajeErrorPersonalizado}">
				<span>${datos.mensajeErrorPersonalizado}</span>
				</c:if>
				<c:if test="${not empty datos.urlCallback}">
					<p class="botonVolver">
						<span><a href="${datos.urlCallback}">Volver</a></span>
					</p>
				</c:if>
			</div>
		</div>

	</div>
	<!-- /contenidor -->
</body>
</html>