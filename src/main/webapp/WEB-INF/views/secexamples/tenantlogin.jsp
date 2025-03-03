<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@taglib uri="jakarta.tags.core" prefix="c" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login for tenant</title>
</head>
<body>
<h1>This is the login page for tenant ${tenancyAlias} with ${tenancyAlias} style</h1>
<c:if test="${error}">
    <p style="color:red;">Invalid username or password. Please try again.</p>
</c:if>
<p>${error}</p>
<form action="<c:url value='/login'/>" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <input type="hidden" name="origin" value="${tenancyAlias}">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username"/><br><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password"/><br><br>        
        <input type="submit" value="Login" />
</form>
</body>
</html>