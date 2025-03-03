<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<ul class="navbar-nav flex-row justify-content-center text-center">
    <li class="nav-item mx-3">
        <a href="<c:url value='/amappli/legal'/>" class="nav-link">Mentions légales</a>
    </li>
    <li class="nav-item mx-3">
        <a href="<c:url value='/amappli/gcu'/>" class="nav-link">Conditions Générales d'Utilisation</a>
    </li>
    <li class="nav-item mx-3">
        <a href="<c:url value='/amappli/contact'/>" class="nav-link">Contact</a>
    </li>
</ul>