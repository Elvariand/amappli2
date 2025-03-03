<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
String currentMainMenu = "orders"; // Détermine la rubrique active
String currentPage = "orders"; // Détermine la sous-rubrique active
request.setAttribute("currentMainMenu", currentMainMenu);
request.setAttribute("currentPage", currentPage);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mes commandes</title>

<link href="https://api.mapbox.com/mapbox-gl-js/v2.15.0/mapbox-gl.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="<c:url value='/resources/bootstrap/bootstrap.min.css'/>" />
<link rel="stylesheet"
	href="<c:url value='/resources/css/amap/user-profile/my-orders.css' />">
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
					<div class="search-bar d-flex align-items-center mb-3">
						<div class="mx-3">
							<label class="fc-main">Opacité du tableau</label> <input
								type="range" class="form-range" min="0" max="100" value="100"
								id="bg-range">
						</div>
					</div>
						<h3 class="fc-main fw-bold title">Liste de vos dernières commandes</h3>
					<table class="table">
						<thead>
							<tr>
								<th>Numéro de commande</th>
								<th>Date</th>
								<th>Montant</th>
								<th>Paiement</th>
								<th>Statut</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="order" items="${orders}">
								<tr>
									<td>${order.orderId}</td>
									<td>${order.orderDate}</td>
									<td>${order.totalAmount}€</td>
									<td><c:if test="${order.orderPaid}">
										<c:forEach var="payment" items="${order.payments}">${payment.paymentType.displayName} </c:forEach> 
									</c:if> <c:if test="${!order.orderPaid}">
										Paiement sur place
									</c:if></td>
									<td>${order.orderStatus.displayName}</td>
									<td><a class="btn btn-100" href="<c:url value='/amap/${tenancyAlias}/account/my-orders/order-details/${order.orderId}' />">Détails </a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

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
	<script src="<c:url value='/resources/js/amap/admin/bg-table.js' />"
		type="text/javascript"></script>
</body>
</html>