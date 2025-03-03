<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="<c:url value='/resources/bootstrap/bootstrap.min.css' />">
	
	<link rel="stylesheet"
	href="<c:url value='/resources/css/amap/login-amap.css' />">
	<title>Connexion</title>
</head>
<body class="theme-1 light bg-main fc-main">
<div class="d-flex flex-column min-vh-100">
 <header class="fc-main bg-main">
			<jsp:include page="../common/header.jsp" />
		</header> 
		 <div id="map"></div>  




	<div class="container py-5 flex-grow-1 form-container">
		<div class="d-flex justify-content-between align-items-center">
    <h1 class="h3 fw-bold fc-300">Se connecter</h1>
    <a href="<c:url value='/amappli/home'/>" class="btn-close" aria-label="Retour"></a>
</div>
<hr class="bg-main fc-main mb-4">

	
		

		<form
			action="<c:url value='/login'/>"
			method="post">

			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        	<input type="hidden" name="origin" value="${tenancyAlias}">
			<!-- Affichage des erreurs générales -->
			<c:if test="${not empty error}">
				<div class="alert alert-danger">
					<strong>Erreur :</strong> ${error}
				</div>
			</c:if>

			<!-- Champs pour le formulaire -->
			<div class="mb-3">
				<input type="email" class="form-control" id="email" name="username"
					placeholder="Adresse mail" required>
			</div>

			<div class="mb-3">
				<input type="password" class="form-control" id="motDePasse"
					name="password" placeholder="Mot de passe" required>
			</div>

			<div class="text-center">
				<button type="submit" class="btn bg-500 rounded-pill">Se connecter</button>
				
			</div>
		</form>
		<br>
		<div class="text-center mt-4">
			<p class="fc-300 ms-4" >Pas encore inscrit.e ?</p>
			<hr class="bg-main fc-main mb-4">
			<a
				href="${pageContext.request.contextPath}/amappli/start/signup"
				class="btn btn-500 px-4 rounded-pill">S'inscrire</a>
		</div>

	</div>
	 <footer class="fc-main bg-main">
			<jsp:include page="../common/footer.jsp" />
		</footer> 
		</div>
	<script src="<c:url value='/resources/bootstrap/bootstrap.bundle.min.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/mapbox/mapbox-gl.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/mapbox/map.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/theme-swap.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/autofill.js' />" type="text/javascript"></script>
</body>
</html>
