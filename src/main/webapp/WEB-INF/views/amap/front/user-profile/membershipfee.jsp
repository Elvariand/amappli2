<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
String currentMainMenu = "profile"; // Détermine la rubrique active
String currentPage = "membership"; // Détermine la sous-rubrique active
request.setAttribute("currentMainMenu", currentMainMenu);
request.setAttribute("currentPage", currentPage);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://api.mapbox.com/mapbox-gl-js/v2.15.0/mapbox-gl.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="<c:url value='/resources/bootstrap/bootstrap.min.css'/>" />
<link rel="stylesheet"
	href="<c:url value='/resources/css/amap/user-profile/membershipfee.css' />">
<title>Mon adhésion</title>
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
						<h3 class="fc-main fw-bold title">Mon adhésion</h3>
						<p>
							<strong>Statut de l'adhésion :</strong>
							<c:if test="${isActive}">
								<span>Valide</span>
							</c:if>
							<c:if test="${!isActive}">
								<span>Invalide</span>
							</c:if>

						</p>
						<p>
							<strong>Date d'adhésion :</strong>
							${user.membershipFee.dateBeginning}
						</p>
						<p>
							<strong>Date d'expiration :</strong>
							${user.membershipFee.dateEnd}
						</p>
						<p>
							<strong>Montant annuel de la cotisation :</strong>
							${user.membershipFee.price}€
						</p>
					</div>
				</div>

			</div>
		</div>
	</div>




	<script
		src="<c:url value='/resources/bootstrap/bootstrap.bundle.min.js' />"></script>
	<script>
		var styleMapboxLight = "${mapStyleLight}"
		var styleMapboxDark = "${mapStyleDark}"
		var latitude = "${latitude}"
		var longitude = "${longitude}"
	</script>
	<script
		src="<c:url value='/resources/js/common/mapbox/mapbox-gl.js' />"></script>
	<script src="<c:url value='/resources/js/common/mapbox/map.js' />"></script>
	<script src="<c:url value='/resources/js/common/theme-swap.js' />"></script>
	<script src="<c:url value='/resources/js/common/palette-swap.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/amap/admin/sidebar.js' />" type="text/javascript"></script>

</body>
</html>
