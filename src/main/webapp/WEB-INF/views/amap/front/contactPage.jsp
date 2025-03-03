<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="<c:url value='/resources/bootstrap/bootstrap.min.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/amap/contactPage.css' />">

<!-- Leaflet CSS -->
<link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" />

<title>Page de contact</title>
</head>
<body class="${cssStyle} light ${font}-title ${font}-button">
<div class="d-flex flex-column min-vh-100">
<header class="fc-main bg-main">
	<jsp:include page="common/header-amap.jsp" />
</header>
<div id="map"></div>  

<div class="container mt-5 fc-main flex-grow-1">
    <div class="contact-container">
        <!-- Contact Information -->
        <div class="contact-info fc-100 bg-700">
            <h2>Comment nous contacter ?</h2>
            <div class="row">
                <div class="col-6">
                    <p><strong>Téléphone :</strong></p>
                    <p>${phoneNumber}</p>
                </div>
                <div class="col-6">
                    <p><strong>Adresse mail :</strong></p>
                    <p>${email}</p>
                </div>
            </div>
            <div class="row mt-3">
                <div class="col-6">
                    <p><strong>Emplacement :</strong></p>
                    <p>${addressLine1}</p>
                    <p>${addressLine2}</p>
                    <p>${addressPostCode}&nbsp;${addressCity}</p>
                </div>
                <div class="col-6">
                    <p><strong>Réseaux sociaux :</strong></p>
                    <p>
                        <a href="#" class="text-white me-2 "><i class="bi bi-facebook"></i></a>
                        <a href="#" class="text-white me-2"><i class="bi bi-instagram"></i></a>
                    </p>
                </div>
            </div>
            <!-- Carte dynamique -->
            <div class="map mt-4" id="dynamic-map" style="height: 300px; width: 100%;"></div>
        </div>

        <!-- Contact Form -->
        <div class="contact-form fc-100 bg-100">
            <h2 class="fc-main">Contactez-nous</h2>
            <form action="/submit" method="POST">
                <div class="row mb-3 fc-main">
                    <div class="col-6">
                        <label for="nom" class="form-label">Votre nom</label>
                        <input type="text" class="form-control border-bottom border-1 border-main" id="nom" name="nom" placeholder="Lamant" required>
                    </div>
                    <div class="col-6">
                        <label for="prenom" class="form-label">Votre prénom</label>
                        <input type="text" class="form-control border-bottom border-1 border-main" id="prenom" name="prenom" placeholder="Marie" required>
                    </div>
                </div>
                <div class="mb-3 fc-main">
                    <label for="email" class="form-label">Votre email</label>
                    <input type="email" class="form-control border-bottom border-1 border-main" id="email" name="email" placeholder="exemple@mail.fr" required>
                </div>
                <div class="mb-3 fc-main">
                    <label for="message" class="form-label">Votre message</label>
                    <textarea class="form-control border border-1 border-main" id="message" name="message" rows="4" required></textarea>
                </div>
                <button type="submit" class="btn rounded-pill btn-500 ">Envoyer</button>
            </form>
        </div>
    </div>
</div>

<footer class="fc-main bg-main">
	<jsp:include page="common/footer-amap.jsp" />
</footer>
</div>
<!-- Bootstrap -->
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

<!-- Leaflet JS -->
<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
<script src="<c:url value='/resources/js/common/map-contact-page.js' />"></script>




</body>
</html>
