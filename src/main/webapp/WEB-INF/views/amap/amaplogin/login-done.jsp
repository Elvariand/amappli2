<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="<c:url value='/resources/bootstrap/bootstrap.min.css' />">
    <title>Inscription réussie</title>
    <link rel="stylesheet"
	href="<c:url value='/resources/css/amap/login-done.css' />">
  
</head>
<body  class="${cssStyle} light ${font}-title ${font}-button">
<div class="d-flex flex-column min-vh-100">

       <header class="fc-main bg-main">
			<jsp:include page="../front/common/header-amap.jsp" />
		</header>
		
<div id="map"></div>  
		 
    <div class="container flex-grow-1">
    <div class="container-success ">
    <div class="success">
        <h2 class="h2 fw-bold fc-300">Inscription réussie !</h2>
        <p class="fc-main">Votre compte a été créé avec succès. Vous pouvez maintenant vous connecter.</p>
        <div>
            <a href="login" class="btn btn-500 rounded-pill">Se connecter</a>
        </div>
        </div>
        </div>
    </div>
    
    
    
     <footer class="fc-main bg-main">
			<jsp:include page="../front/common/footer-amap.jsp" />
		</footer> 
    	<script src="<c:url value='/resources/bootstrap/bootstrap.bundle.min.js' />"></script>

	<script>
		var styleMapboxLight = "${mapStyleLight}";
		var styleMapboxDark = "${mapStyleDark}";


		 var latitude = "${latitude}";
		 var longitude = "${longitude}"; 

	</script>

	<script src="<c:url value='/resources/js/common/mapbox/mapbox-gl.js' />" type="text/javascript"></script>
	 <script src="<c:url value='/resources/js/common/mapbox/map.js' />" type="text/javascript"></script>  
	<script src="<c:url value='/resources/js/common/theme-swap.js' />"></script>   
</body>
</html>
