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
<title>Détail de ma commande n°${order.orderId}</title>

<link href="https://api.mapbox.com/mapbox-gl-js/v2.15.0/mapbox-gl.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="<c:url value='/resources/bootstrap/bootstrap.min.css'/>" />
<link rel="stylesheet"
	href="<c:url value='/resources/css/amap/user-profile/my-orders.css' />">
</head>


<body class="row ${cssStyle} light ${font}-title ${font}-button">

	<header class="fc-main bg-main border-1 border-alt">
		<!-- Inclusion du header -->
		<jsp:include page="../common/headerAdmin.jsp" />
	</header>
	<!-- Inclusion de la sidebar -->
	<jsp:include page="../common/sidebarAdmin.jsp" />

	<div id="map"></div>
	<div class="content col fc-main">
		<div class="container-fluid mt-2">
			<div class="row justify-content-center">
				<div class="col-12">
					<div
						class="search-bar d-flex align-items-center justify-content-between mb-3">
						<div class="mx-3">
							<label class="fc-main">Opacité du tableau</label> <input
								type="range" class="form-range" min="0" max="100" value="100"
								id="bg-range">
						</div>
						<a class="btn btn-100"
							href="<c:url value='/amap/${tenancyAlias}/admin/orders/list' />">Retourner
							à la liste des commandes</a>
					</div>
					<div class="order-head">
						<div class="order-details">
							<h3>Commande n°${order.orderId}</h3>
							<p>Montant total : ${order.totalAmount}€</p>
							<p>Date de la commande : ${order.orderDate}</p>
							<p>Etat : ${order.orderStatus.displayName}</p>
							<p>
								Type de Paiement :
								<c:if test="${order.orderPaid}">
									<c:forEach var="payment" items="${order.payments}">${payment.paymentType.displayName} </c:forEach>
								</c:if>
								<c:if test="${!order.orderPaid}">
										Paiement sur place
									</c:if>
							</p>
						</div>
						<c:if test="${order.orderStatus.displayName != 'Récupérée'}">
								<form class="order-details" method="post"
									action="${pageContext.request.contextPath}/amap/${tenancyAlias}/order/updateOrder">
									<div>
										<h3>Valider la récupération</h3>
										<c:if test="${order.orderPaid}">
											<p>L'adhérent a déjà réglé sa commande en ligne.</p>
											<input type="hidden" name="paymentType" value="" />
										</c:if>
										<input type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" />
										<c:if test="${!order.orderPaid}">
											<p>Sélectionnez le type de paiement de l'adhérent</p>
											<select name="paymentType" class="form-select form-control">
												<option value="">-- Sélectionnez un mode de
													paiement --</option>
												<option value="Carte bleue">Carte bleue</option>
												<option value="Chèque">Chèque</option>
												<option value="Espèces">Espèces</option>
											</select>
										</c:if>
										<input type="hidden" name="orderId" value="${order.orderId}" />
									</div>
									<div class="order-button">
										<button type="submit" class="btn btn-100">Valider</button>
									</div>
							</form>
						</c:if>
					</div>
					<table class="table">
						<h3>Détails des produits</h3>
						<thead>
							<tr>
								<th></th>
								<th>Nom de l'article</th>
								<th>Prix à l'unité</th>
								<th>Quantité</th>
								<th>Total</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${order.orderItems}">
								<tr>
									<td></td>
									<td>${item.shoppable.getInfo()}</td>
									<td>${item.unitPrice}€</td>
									<td>${item.quantity}</td>
									<td>${item.total}€</td>
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
	

	<script src="<c:url value='/resources/js/amap/admin/bg-table.js' />"
		type="text/javascript"></script>
</body>
</html>