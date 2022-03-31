<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
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
		function copiarAlPortapapeles(id_elemento) {
		  var aux = document.createElement("input");
		  aux.setAttribute("value", document.getElementById(id_elemento).value);
		  document.body.appendChild(aux);
		  aux.select();
		  document.execCommand("copy");
		  document.body.removeChild(aux);
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

					<!-- datos -->
					<ul>
						<li class="lib-desglose-list-cert">
							<div class="lib-desglose-info-cert">
								<h1><fmt:message key="datosCertificado.titulo"/></h1>
							</div>
							<div class="lib-desglose-cert">
								<div>
									<span class="lib-desglose-cert-head"></b><fmt:message key="datosCertificado.idp"/>:</span>
									<span class="lib-desglose-cert-data"><c:out value="${idp}"/></span>
								</div>

								<div>
									<span class="lib-desglose-cert-head"></b><fmt:message key="datosCertificado.nif"/>:</span>
									<span class="lib-desglose-cert-data"><c:out value="${nif}"/></span>
								</div>
								<div>
									<span class="lib-desglose-cert-head"></b><fmt:message key="datosCertificado.nombre"/>:</span>
									<span class="lib-desglose-cert-data"><c:out value="${nombre}"/></span>
								</div>

								<div>
									<span class="lib-desglose-cert-head"></b><fmt:message key="datosCertificado.apellidos"/>:</span>
									<span class="lib-desglose-cert-data"><c:out value="${apellidos}"/></span>
								</div>

				         		<div>
				         			<span class="lib-desglose-cert-head"></b><fmt:message key="datosCertificado.apellido1"/>:</span>
									<span class="lib-desglose-cert-data"><c:out value="${apellido1}"/></span>
				         		</div>

				         		<div>
				         			<span class="lib-desglose-cert-head"></b><fmt:message key="datosCertificado.apellido2"/>:</span>
									<span class="lib-desglose-cert-data"><c:out value="${apellido2}"/></span>
				         		</div>

				         		<div>
				         			<span class="lib-desglose-cert-head"></b><fmt:message key="datosCertificado.qaa"/>:</span>
									<span class="lib-desglose-cert-data"><c:out value="${qaa}"/></span>
				         		</div>

				         		<div>
				         			<span class="lib-desglose-cert-head"></b><fmt:message key="datosCertificado.desglose"/>:</span>
									<span class="lib-desglose-cert-data"><c:out value="${isDesglose}"/></span>
				         		</div>
								<c:if test="${isRepresentante == 'S'}">

									<div>
										<span class="lib-desglose-cert-head"></b><fmt:message key="datosCertificado.nifRepresentante"/>:</span>
										<span class="lib-desglose-cert-data"><c:out value="${representanteNif}"/></span>
									</div>
									<div>
										<span class="lib-desglose-cert-head"></b><fmt:message key="datosCertificado.nombreRepresentante"/>:</span>
										<span class="lib-desglose-cert-data"><c:out value="${representanteNombre}"/></span>
									</div>

									<div>
										<span class="lib-desglose-cert-head"></b><fmt:message key="datosCertificado.apellidosRepresentante"/>:</span>
										<span class="lib-desglose-cert-data"><c:out value="${representanteApellidos}"/></span>
									</div>

					         		<div>
					         			<span class="lib-desglose-cert-head"></b><fmt:message key="datosCertificado.apellido1Representante"/>:</span>
										<span class="lib-desglose-cert-data"><c:out value="${representanteApellido1}"/></span>
					         		</div>

					         		<div>
					         			<span class="lib-desglose-cert-head"></b><fmt:message key="datosCertificado.apellido2Representante"/>:</span>
										<span class="lib-desglose-cert-data"><c:out value="${representanteApellido2}"/></span>
					         		</div>
								</c:if>
							</div>
							<div class="lib-desglose-info-cert">
								<h1><fmt:message key="datosCertificado.tituloLoginIB"/></h1>
							</div>
							<div class="lib-desglose-cert">
								<div>
									<span class="lib-desglose-cert-head"></b><fmt:message key="datosCertificado.nombre"/>:</span>
									<span class="lib-desglose-cert-data"><c:out value="${loginIBnombre}"/></span>
								</div>

				         		<div>
				         			<span class="lib-desglose-cert-head"></b><fmt:message key="datosCertificado.apellido1"/>:</span>
									<span class="lib-desglose-cert-data"><c:out value="${loginIBapellido1}"/></span>
				         		</div>

				         		<div>
				         			<span class="lib-desglose-cert-head"></b><fmt:message key="datosCertificado.apellido2"/>:</span>
									<span class="lib-desglose-cert-data"><c:out value="${loginIBapellido2}"/></span>
				         		</div>

								<div>
				         			<span class="lib-desglose-cert-head"></b><fmt:message key="datosCertificado.fechaCreacion"/>:</span>
									<span class="lib-desglose-cert-data"><c:out value="${loginIBfechaCreacion}"/></span>
				         		</div>

								<div>
				         			<span class="lib-desglose-cert-head"></b><fmt:message key="datosCertificado.fechaModificacion"/>:</span>
									<span class="lib-desglose-cert-data"><c:out value="${loginIBFechaMod}"/></span>
				         		</div>
							</div>
				         	<div class="lib-desglose-copiar">
				         		<p><fmt:message key="datosCertificado.msg"/></p>
				         		<button type="button" class="btn-copiar" onclick="copiarAlPortapapeles('info')" aria-label="<fmt:message key="datosCertificado.boton"/>">
				         			<fmt:message key="datosCertificado.boton"/>
				         		</button>
				         	</div>
				         	<input style="display: none;" id="info" value="<c:out value="${datosB64}"/>" /></input>
						</li>
					</ul>
			</div>

		</div>

	</div>
</body>
</html>