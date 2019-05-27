<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<form id="loginClave" action="loginClaveSimulado.html" method="post">
		IDP (ANONIMO/CLAVE_CERTIFICADO/CLAVE_PIN/CLAVE_PERMANENTE): <input type="text" name="idp" value="CLAVE_CERTIFICADO"/><br/>
		NIF: <input type="text" name="nif" value="00000000T"/><br/>
		NOMBRE: <input type="text" name="nombre" value="PRUEBASPF"/><br/>
		APELLIDOS: <input type="text" name="apellidos" value="APELLIDOUNOPF APELLIDODOSPF"/><br/>
		APELLIDO 1: <input type="text" name="apellido1" value="APELLIDOUNOPF"/><br/>
		APELLIDO 2: <input type="text" name="apellido2" value="APELLIDODOSPF"/><br/>
		REPRESENTANTE NIF: <input type="text" name="rnif" value=""/><br/>
		REPRESENTANTE NOMBRE: <input type="text" name="rnombre" value=""/><br/>
		REPRESENTANTE APELLIDOS: <input type="text" name="rapellidos" value=""/><br/>
		REPRESENTANTE APELLIDO 1: <input type="text" name="rapellido1" value=""/><br/>
		REPRESENTANTE APELLIDO 2: <input type="text" name="rapellido2" value=""/><br/>

		<input type="hidden" name="idSesion" value="<c:out value="${simularClave.idSesion}"/>"/>
		<input type="submit" value="enviar"/>
	</form>
</body>
</html>
