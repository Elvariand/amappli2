<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
String currentMainMenu = "products";
String currentPage = "products";
request.setAttribute("currentMainMenu", currentMainMenu);
request.setAttribute("currentPage", currentPage);
%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Détails du produit</title>
<link href="<c:url value='/resources/bootstrap/bootstrap.min.css' />"
	rel="stylesheet">
<link href="<c:url value='/resources/css/common/utils.css' />"
	rel="stylesheet">
<link href="<c:url value='/resources/css/amap/shopping-detail.css' />"
	rel="stylesheet">
<link
	href="<c:url value='/resources/bootstrap/bootstrap-icons.min.css' />"
	rel="stylesheet">
</head>
<body class="row ${cssStyle} light ${font}-title ${font}-button">
	<header class="fc-main bg-main mb-5">
		<jsp:include page="common/header-amap.jsp" />
	</header>
	<jsp:include page="../front/common/sidebarUser.jsp" />
	<div id="map" class="p-0"></div>

	<div class="content col fc-main bg-100 border border-3 border-main pb-3 m-5"
		style="border-radius: 10px 10px 0 0;">
			<div class="header">
				<a href="<c:url value='/amap/${tenancyAlias}/shop/products'/>" class="text-decoration-none">
    <i class="bi bi-arrow-left-circle fs-1 fc-500"></i>
</a>
</div>
		
		<div class="contract-detail-container">
			<!-- Colonne de gauche : image -->
			<div class="contract-image">
				<c:if test="${not empty product.imageData}">
					<img src="data:${product.imageType};base64,${product.imageData}"
						alt="Image du produit">
				</c:if>
			</div>

			<!-- Colonne de droite : informations -->
			<div class="contract-info">
				<h2 style="text-transform: uppercase; margin: 0;">${product.productName}</h2>
				<p class="text-muted"> Fabriqué le ${formattedFabricationDate}
				<p>
					<strong>Producteur :</strong>
					${product.user.companyDetails.companyName}
					(${product.user.contactInfo.firstName}
					${product.user.contactInfo.name})
				</p>
				<p>
					<strong>Détails du produit :<br></strong>
					${product.productDescription}
				</p>
				<p>
					<i class="bi bi-exclamation-octagon"></i> Le producteur vous informe que le produit ne sera plus consommable après le
					${formattedExpirationDate}
				</p><br>
				<h2 class="text-end mt-4">
					<strong>${product.productPrice}&euro;</strong>
				</h2>
			</div>
		</div>

		<!-- Section pour la quantité et le bouton d'ajout au panier -->
		<div class="text-end mt-2">
			<form:form method="post"
				action="${pageContext.request.contextPath}/amap/${tenancyAlias}/cart/add">
				<div class="btn btn-100 mt-0 px-1 py-1 quantity-selector">
					<button type="button" id="decreaseQuantity"
						class="btn p-0 mx-2 border-0 bg-transparent">-</button>
					<span id="currentQuantity">1</span>
					<button type="button" id="increaseQuantity"
						class="btn p-0 mx-2 border-0 bg-transparent">+</button>
				</div>
				<input type="hidden" id="quantity" name="quantity" value="1">
				<input type="hidden" name="shoppableId" value="${product.id}">
				<input type="hidden" name="shoppableType" value="PRODUCT">
				<button type="submit" class="btn btn-500 btn-order ms-2">Ajouter au
					panier</button>
			</form:form>
			</div>
			</div>

	<!-- Footer -->
	<footer class="fc-main bg-main mt-5">
		<jsp:include page="common/footer-amap.jsp" />
	</footer>
	<script>
		var styleMapboxLight = "${mapStyleLight}";
		var styleMapboxDark = "${mapStyleDark}";
		var latitude = "${latitude}"
		var longitude = "${longitude}"
	</script>
	<script
		src="<c:url value='/resources/bootstrap/bootstrap.bundle.min.js' />"
		type="text/javascript"></script>
	<script
		src="<c:url value='/resources/js/common/mapbox/mapbox-gl.js' />"></script>
	<script src="<c:url value='/resources/js/common/mapbox/map.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/theme-swap.js' />"
		type="text/javascript"></script>
	<script src="<c:url value='/resources/js/amap/quantityselector.js' />"
		type="text/javascript"></script>
</body>
</html>
