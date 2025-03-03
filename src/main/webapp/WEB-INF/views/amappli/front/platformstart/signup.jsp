<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Inscription sur Amappli</title>
<link rel="stylesheet" href="<c:url value='/resources/bootstrap/bootstrap.min.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/amap/signup-form.css' />">
</head>
<body class="theme-1 light bg-main fc-main">
<div class="d-flex flex-column min-vh-100">
<header class="fc-main bg-main">
	<jsp:include page="../../common/header.jsp" />
</header>
		
<div id="map"></div>

<div class="container py-5 flex-grow-1">
    <div class="col-12 col-md-6 mx-auto py-5">

        <div class="d-flex justify-content-between align-items-center ">
            <h1 class="h3 fw-bold fc-300">S'inscrire</h1>

            <a href="/Amappli/amappli/home"class="btn-close fc-main" aria-label="Retour"></a>

        </div>
        <hr class="bg-main fc-main mt-0 mb-4">

        <form:form action="${pageContext.request.contextPath}/amappli/start/signup" method="post" modelAttribute="newUserDTO">

            <!-- Ajout du token CSRF -->
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

            <!-- Affichage des erreurs générales -->
            <c:if test="${not empty error}">
                <div class="alert alert-danger">
                    <strong>Erreur :</strong> ${error}
                </div>
            </c:if>

            <!-- Champs pour le formulaire -->
            <div class="row mb-3">
                <div class="col-md-6 mb-3 mb-md-0">
                    <form:input path="contactInfo.name" cssClass="form-control" id="nom" placeholder="Votre nom" required="true" />
                    <form:errors path="contactInfo.name" cssClass="text-danger" />
                </div>
                <div class="col-md-6">
                    <form:input path="contactInfo.firstName" cssClass="form-control" id="prenom" placeholder="Votre prénom" required="true" />
                    <form:errors path="contactInfo.firstName" cssClass="text-danger" />
                </div>
            </div>

            <div class="mb-3">
                <form:input path="contactInfo.phoneNumber" cssClass="form-control" id="telephone" placeholder="Votre numéro de téléphone" required="true"/>
                <form:errors path="contactInfo.phoneNumber" cssClass="text-danger" />
            </div>

            <div class="mb-3">
                <form:input path="address.line2" cssClass="form-control" id="adresse" placeholder="Complément d'adresse" />
                <form:errors path="address.line2" cssClass="text-danger" />
            </div>

            <div class="mb-3">
                <form:input path="address.line1" cssClass="form-control" id="complement" placeholder="Numéro et rue" required="true"/>
            </div>

            <div class="row mb-3">
                <div class="col-md-6 mb-3 mb-md-0">
                    <form:input path="address.postCode" cssClass="form-control" id="codePostal" placeholder="Code postal" required="true" />
                    <form:errors path="address.postCode" cssClass="text-danger" />
                </div>
                <div class="col-md-6">
                    <form:input path="address.city" cssClass="form-control" id="ville" placeholder="Ville" required="true" />
                    <form:errors path="address.city" cssClass="text-danger" />
                </div>
            </div>

            <div class="mb-3">
                <form:input path="email" cssClass="form-control" id="email" placeholder="Votre adresse mail" required="true" />
                <form:errors path="email" cssClass="text-danger" />
                <c:if test="${not empty emailError}">
                    <div class="invalid-feedback d-block">${emailError}</div>
                </c:if>
                <div id="email-help" class="form-text ml-5">Cet email sera votre identifiant de connexion. Il ne pourra pas être changé.</div>
            </div>

            <div class="mb-3">
                <form:password path="password" cssClass="form-control" id="motDePasse" placeholder="Votre mot de passe" required="true" />
                <form:errors path="password" cssClass="text-danger" />
            </div>

            <div class="mb-3">
                <form:password path="confirmPassword" cssClass="form-control" id="confirmMotDePasse" placeholder="Confirmez votre mot de passe" required="true" />
                <form:errors path="confirmPassword" cssClass="text-danger" />
            </div>

            <div class="text-center">
                <button type="submit" class="btn btn-500 px-4 rounded-pill">S'inscrire</button>
            </div>
        </form:form>
        <div class="text-center mt-4">
			<p class="fc-300 ms-4" >Déja inscrit.e ?</p>
			<hr class="bg-main fc-main mt-0 mb-4">
			<a href="<c:url value='/amappli/login'/>"
				class="btn bg-500  rounded-pill">Se connecter</a>
		</div>
	</div>
</div>

    <footer class="fc-main bg-main">
		<jsp:include page="../../common/footer.jsp" />
	</footer> 
    </div>
    <script src="<c:url value='/resources/js/amappli/platformsignup.js'/>"></script>
   	<script src="<c:url value='/resources/bootstrap/bootstrap.bundle.min.js' />"></script>
	<script src="<c:url value='/resources/js/common/mapbox/mapbox-gl.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/mapbox/map.js' />" type="text/javascript"></script>  
	<script src="<c:url value='/resources/js/common/theme-swap.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/autofill.js' />" type="text/javascript"></script>
</body>
</html>
