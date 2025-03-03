<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="fr_FR" />

<!DOCTYPE html>
<html>
<head>
<title>Votre panier</title>

<link href="https://api.mapbox.com/mapbox-gl-js/v2.15.0/mapbox-gl.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="<c:url value='/resources/bootstrap/bootstrap.min.css'/>" />
<link rel="stylesheet"
	href="<c:url value='/resources/css/amap/shopping-cart.css' />">
</head>


<body class="${cssStyle} light ${font}-title ${font}-button">

	<!-- the bootstrap classes on this div are used to have the footer correctly positioned at the bottom when the page is not full -->
	<div class="d-flex flex-column min-vh-100">

		<header class="fc-main bg-main">
			<jsp:include page="common/header-amap.jsp" />
		</header>
		<div class="fc-main content flex-grow-1">
			<div id="map"></div>
			<div class="main-div">
				<div class="row">
					<div class="col-md-8 table-container">
						<table class="table">
							<thead>
								<tr>
									<th>Produits</th>
									<th></th>
									<th>Prix à l'unité</th>
									<th>Quantité</th>
									<th>Prix</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="item" items="${cart.shoppingCartItems}">
									<tr>
										<td><c:if test="${not empty item.shoppable.getImage()}">
												<img src="${item.shoppable.getImage()}"
													alt="Image du produit"
													style="width: 100px; height: auto; border-radius: 8px;" />
											</c:if></td>
										<td>${item.shoppable.getInfo()}</td>
										<td>${item.shoppable.getPrice()}€</td>
										<td><c:choose>
												<c:when test="${item.shoppable.membershipFee}">
													<span>${item.quantity}</span>
												</c:when>
												<c:otherwise>
													<div class="btn btn-100">
														<form:form method="post"
															action="${pageContext.request.contextPath}/amap/${tenancyAlias}/cart/updateQuantity/${item.shoppingItemId}">
															<button type="submit" name="action" value="decrease">-</button>
															<span>${item.quantity}</span>
															<button type="submit" name="action" value="increase">+</button>
														</form:form>
													</div>
												</c:otherwise>
											</c:choose></td>

										<td>${item.totalPrice}€</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="col-md-4 totals">
						<table>
							<tr>
							<tr>
								<td>Panier</td>
								<td><fmt:formatNumber value="${totalContracts}"
										type="number" minFractionDigits="2" />€</td>
							</tr>
							<tr>
								<td>Produits artisanaux</td>
								<td><fmt:formatNumber value="${totalProducts}"
										type="number" minFractionDigits="2" />€</td>
							</tr>
							<tr>
								<td>Ateliers</td>
								<td><fmt:formatNumber value="${totalWorkshops}"
										type="number" minFractionDigits="2" />€</td>
							</tr>
							<c:if test="${!tenancy.tenancyAlias.equals('terralocal')}">
							<tr>
								<td>Cotisation</td>
								<td><fmt:formatNumber value="${tenancy.membershipFeePrice}"
										type="number" minFractionDigits="2" />€</td>
							</tr>
							</c:if>
							<tr>
								<td><h2 class="fw-bold">TOTAL</h2></td>
								<td><h2 class="fw-bold">
										<fmt:formatNumber value="${total}" type="number"
											minFractionDigits="2" />
										€
									</h2></td>
							</tr>


							<form:form method="post"
								action="${pageContext.request.contextPath}/amap/${tenancyAlias}/order/createOrder">
								<tr>
									<c:if test="${options.option1Active}">
										<td colspan="2"><button class="btn btn-500 btn-order"
												type="submit" name="action" value="OrderWithPayment">Paiement
												en ligne</button></td>
									</c:if>
								</tr>
								<tr>
									<td colspan="2"><button class="btn btn-100 btn-order"
											type="submit" name="action" value="OrderWithoutPayment">Payer
											sur place</button></td>
								</tr>
							</form:form>
						</table>
					</div>
				</div>
			</div>
		</div>
		<footer class="fc-main bg-main">
			<jsp:include page="common/footer-amap.jsp" />
		</footer>
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
		src="<c:url value='/resources/js/common/mapbox/mapbox-gl.js' />"
		type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/mapbox/map.js' />"
		type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/theme-swap.js' />"
		type="text/javascript"></script>
</body>
</html>