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
</head>
<body>

	<fmt:message key="seleccionAutenticacion.texto"/>

	<c:if test = "${datos.clave}">
         <p>
         	<a href="redirigirLoginClave.html?idSesion=<c:out value = "${datos.idSesion}"/>">
         		<fmt:message key="seleccionAutenticacion.clave"/>
         	</a>
         <p>
     </c:if>

     <c:if test = "${datos.anonimo}">
         <p>
         	<a href="loginAnonimo.html?idSesion=<c:out value = "${datos.idSesion}"/>">
         		<fmt:message key="seleccionAutenticacion.anonimo"/>
         	</a>
         <p>
     </c:if>

</body>
</html>