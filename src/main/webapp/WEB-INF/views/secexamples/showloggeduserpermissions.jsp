<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Details</title>
</head>
<body>
    <p>Principal: ${principal}</p>
    <p>The Logged User id: ${userId}</p>
    <p>The Logged User First Name: ${firstName}</p>

    <c:choose>
        <c:when test="${not empty principal}">
            <h1>Welcome, ${principal.username}!</h1>

            <h2>Your Authorities</h2>
            <ul>
                <c:forEach var="authority" items="${authorities}">
                    <li>${authority}</li>
                </c:forEach>
            </ul>
        </c:when>
        <c:otherwise>
            <h1>You are not logged in.</h1>
        </c:otherwise>
    </c:choose>
</body>
</html>
