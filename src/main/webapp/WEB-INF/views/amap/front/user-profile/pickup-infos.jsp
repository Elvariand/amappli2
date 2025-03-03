<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
String currentMainMenu = "orders"; // Détermine la rubrique active
String currentPage = "pickup"; // Détermine la sous-rubrique active
request.setAttribute("currentMainMenu", currentMainMenu);
request.setAttribute("currentPage", currentPage);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mon point de collecte</title>

<link href="https://api.mapbox.com/mapbox-gl-js/v2.15.0/mapbox-gl.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="<c:url value='/resources/bootstrap/bootstrap.min.css'/>" />
<link rel="stylesheet"
	href="<c:url value='/resources/css/amap/user-profile/pickup-infos.css' />">
<!-- Leaflet CSS -->
<link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" />
</head>


<body class="row ${cssStyle} light ${font}-title ${font}-button">

	<header class="fc-main bg-main">
		<jsp:include page="common/header-user-account.jsp" />
	</header>
	<jsp:include page="common/sidebar-user-account.jsp" />


	<div id="map"></div>
	<div class="content col">
		<div class="container-fluid mt-2">
			<div class="row justify-content-center">
				<div class="col-12">
					<div>
						<h3 class="fc-main fw-bold title">Où et quand récupérer votre commande ?</h3>
						<h5 class="fc-main">Prochaine permanence : </h5>
						<p class="fc-main ">${nextPickUpDate}</p>
						<h5 class="fc-main">Adresse de votre amap : </h5>
						<p class="fc-main ">${tenancy.address.line1} ${tenancy.address.line2}<br>${tenancy.address.postCode} ${tenancy.address.city}</p>
						<div class="map-container">
							<div class="map" id="dynamic-map"></div>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>


	<script
		src="<c:url value='/resources/bootstrap/bootstrap.bundle.min.js' />"></script>

	<script>
		var styleMapboxLight = "${mapStyleLight}";
		var styleMapboxDark = "${mapStyleDark}";
		var latitude = "${latitude}"
		var longitude = "${longitude}"
	</script>

	<script
		src="<c:url value='/resources/js/common/mapbox/mapbox-gl.js' />"></script>
	<script src="<c:url value='/resources/js/common/mapbox/map.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/theme-swap.js' />"></script>
	<script src="<c:url value='/resources/js/amap/admin/sidebar.js' />" type="text/javascript"></script>
	<!-- Leaflet JS -->
<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
<script src="<c:url value='/resources/js/common/map-contact-page.js' />"></script>
</body>
</html>