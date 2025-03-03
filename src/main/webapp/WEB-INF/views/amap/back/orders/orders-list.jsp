<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
	String currentMainMenu="products" ; // Détermine la rubrique active
	String currentPage="orders" ; // Détermine la sous-rubrique active
	request.setAttribute("currentMainMenu", currentMainMenu);
	request.setAttribute("currentPage", currentPage);
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Liste des commandes</title>
	<link href="<c:url value='/resources/bootstrap/bootstrap.min.css' />" rel="stylesheet">
	<link href="<c:url value='/resources/css/common/utils.css' />" rel="stylesheet">
	<link href="<c:url value='/resources/bootstrap/bootstrap-icons.min.css' />" rel="stylesheet">
</head>
<body class="row ${cssStyle} light ${font}-title ${font}-button">
	<header class="fc-main bg-main border-1 border-alt">
	<!-- Inclusion du header -->
			<jsp:include page="../common/headerAdmin.jsp" />
	</header>
	<!-- Inclusion de la sidebar -->
		<jsp:include page="../common/sidebarAdmin.jsp" />

<div id="map"></div>

	<!-- Contenu principal -->
	<div class="content col">
		<div class="container-fluid mt-2">
			<div class="row justify-content-center">
				<div class="col-12">
					<div class="search-bar d-flex align-items-center justify-content-between mb-3">
						<!-- Dropdown pour trier -->
						<div class="d-flex align-items-center me-4">
							<label for="sortBy" class="me-2 fw-400 fs-3 text-nowrap fc-main">Trié par</label>
							<select id="sortBy" class="form-select custom-select border-main">
								<option value="name">Nom</option>
								<option value="statut">Statut</option>
							</select>
						</div>
						<!-- Barre de recherche -->
						<div>
							<input type="text" id="searchBar" class="form-control custom-input border-main"
								placeholder="Rechercher...">
						</div>
						<div class="mx-3">
							<label class="fc-main">Opacité du tableau</label>
    						<input type="range" class="form-range" min="0" max="100" value="100" id="bg-range">
						</div>
					</div>
					<div class="table-container d-flex justify-content-between align-items-center">
						<h2 class="fw-bold fc-main">Liste des commandes</h2>
					</div>
					<!-- Nombre total d'adhérents -->
					<div class="mb-2 fs-5 fc-main d-none d-md-block">
						<span>${orders.size()} éléments</span>
					</div>
					<!-- Mode tableau -->
					<table class="table table-hover fc-main" style="--bs-table-bg: color-mix(in srgb, #ffffff, transparent 100%);">
						<thead>
							<tr>
								<th>Numéro de commande</th>
								<th>Montant</th>
								<th>Nom</th>
								<th>Date</th>
								<th>Statut</th>
								<th>Type de paiement</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="order" items="${orders}">
								<tr>
									<td class="text-break">${order.orderId}</td>
									<td>${order.totalAmount}€</td>
									 <td>${order.user.contactInfo.firstName}
										${order.user.contactInfo.name}</td> 
									<td>${order.orderDate}</td>
									<td>${order.orderStatus.displayName}</td>
									<td><c:if test="${order.orderPaid}">
										<c:forEach var="payment" items="${order.payments}">${payment.paymentType.displayName} </c:forEach> 
									</c:if> <c:if test="${!order.orderPaid}">
										Paiement sur place
									</c:if></td>
									<td>
										<div class='d-flex justify-content-start align-items-center'>
											<a href="<c:url value='/amap/${tenancyAlias}/admin/order-details/${order.orderId}' />"
												class="btn rounded-circle border-2 border-300 fc-main px-1 py-0 mx-1"> <i class="bi bi-eye"></i>
											</a>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
		<script>
		var styleMapboxLight = "${mapStyleLight}";
		var styleMapboxDark = "${mapStyleDark}";
		var latitude = "${latitude}"
		var longitude = "${longitude}"
	</script>
	<script src="<c:url value='/resources/bootstrap/bootstrap.bundle.min.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/amap/admin/user-list.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/mapbox/mapbox-gl.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/mapbox/map.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/theme-swap.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/amap/admin/bg-table.js' />" type="text/javascript"></script>
</body>

</html>