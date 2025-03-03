<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="<c:url value='/resources/bootstrap/bootstrap.min.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/amap/signup-form.css' />">
<title>Formulaire d'inscription</title>
</head>

<body  class="${cssStyle} light ${font}-title ${font}-button">
<div class="d-flex flex-column min-vh-100">
       <header class="fc-main bg-main">
			<jsp:include page="../front/common/header-amap.jsp" />
		</header>
		
<div id="map"></div> 
 
 
    <div class="container py-5 flex-grow-1">
    <div class="form-container py-5">
        <div class="d-flex justify-content-between align-items-center ">
            <h1 class="h3 fw-bold fc-300">S'inscrire</h1>

            <a href="/Amappli/amap/${tenancyAlias}/home"class="btn-close fc-main" aria-label="Retour"></a>

        </div>
        <hr class="bg-main fc-main mb-4 mt-0">

        <form:form 
            modelAttribute="userDTO" 
            method="post" 
            action="${pageContext.request.contextPath}/amap/${tenancyAlias}/signup">

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
                <form:input path="contactInfo.phoneNumber" cssClass="form-control" id="telephone" placeholder="Votre numéro de téléphone" />
                <form:errors path="contactInfo.phoneNumber" cssClass="text-danger" />
            </div>

            <div class="mb-3">
                <form:input path="address.line2" cssClass="form-control" id="adresse" placeholder="Votre adresse" required="true" />
                <form:errors path="address.line2" cssClass="text-danger" />
            </div>

            <div class="mb-3">
                <form:input path="address.line1" cssClass="form-control" id="complement" placeholder="Complément d'adresse" />
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
            
                <button 
                type="submit" class="btn btn-500 px-4 rounded-pill">S'inscrire</button>
            </div>
        </form:form>
        
        <br>
		<div class="text-center mt-4">
			<p class="fc-300 ms-4" >Déja inscrit.e ?</p>
			<hr class="bg-main fc-main mb-4">
			<a
				href="<c:url value='/amap/${tenancyAlias}/login'/>"
				class="btn bg-500  rounded-pill">Se connecter</a>
		</div>
        </div>
    </div>
   <footer class="fc-main bg-main">
		<jsp:include page="../front/common/footer-amap.jsp" />
	</footer>
    </div>
   	<script src="<c:url value='/resources/bootstrap/bootstrap.bundle.min.js' />"></script>
	<script>
		var styleMapboxLight = "${mapStyleLight}";
		var styleMapboxDark = "${mapStyleDark}";
		var latitude = "${latitude}";
		var longitude = "${longitude}"; 
	</script>
	<script src="<c:url value='/resources/js/common/mapbox/mapbox-gl.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/mapbox/map.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/theme-swap.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/autofill.js' />" type="text/javascript"></script>
</body>
</html>
