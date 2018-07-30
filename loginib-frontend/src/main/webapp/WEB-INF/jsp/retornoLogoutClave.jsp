<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="<c:out value="es"/>" lang="<c:out value="es"/>">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />		
	
	<script type="text/javascript">
	<!--
	function retornoClave() {
		document.getElementById('retornoClave').submit();
	}
	-->
	</script>
	
</head>
<body onload="retornoClave();">
	<form id="retornoClave" action="<c:out value="${datos.urlCallback}"/>" method="post">	
		<input type="hidden" name="logout" value="<c:out value="${datos.logout}"/>"/>		
	</form>
</body>
</html>