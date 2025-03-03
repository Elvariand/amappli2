<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="<c:url value='/resources/bootstrap/bootstrap.min.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/amappli/featuresPage.css' />">
<link href="<c:url value='/resources/bootstrap/bootstrap-icons.min.css' />" rel="stylesheet">

<title>403 - Accès refusé</title>

</head>

<body class=" ${cssStyle == null? 'theme-1' : cssStyle} light ${font}-title ${font}-button">
<div class="d-flex flex-column min-vh-100">

   <header class="fc-main bg-main">
		<jsp:include page="../common/header-amap.jsp" />
	</header>

   <div id="map"></div>   
    
<section class="container py-5">
	<div class="row justify-content-center">
    <!-- Carte Potager -->
		<div class="col-8 mb-4 bg-300 fc-900 rounded-5 px-5">
	        <h1 class="fw-bold text-center mb-5 mt-3">Accès refusé</h1>
			<p class="text-center fs-6 pb-3">Vous n'avez pas les permissions nécessaires pour accéder à cette zone.</p>
		</div>
	</div>
</section>

    
	<footer class="container-fluid fc-main bg-main">
		<jsp:include page="../common/footer-amap.jsp" />
	</footer>
</div>
	<script	src="<c:url value='/resources/bootstrap/bootstrap.bundle.min.js' />" type="text/javascript"></script>
	<script	src="<c:url value='/resources/js/common/theme-swap.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/mapbox/mapbox-gl.js' />" type="text/javascript"></script>
	<script	src="<c:url value='/resources/js/common/mapbox/map.js' />" type="text/javascript"></script>  
</body>
</html>