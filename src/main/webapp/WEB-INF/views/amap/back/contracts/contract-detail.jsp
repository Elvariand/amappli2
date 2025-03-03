<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
String currentMainMenu = "products"; // Détermine la rubrique active
String currentPage = "contracts"; // Détermine la sous-rubrique active
request.setAttribute("currentMainMenu", currentMainMenu);
request.setAttribute("currentPage", currentPage);
%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Détails du panier</title>
<link href="<c:url value='/resources/bootstrap/bootstrap.min.css' />"
	rel="stylesheet">
<link href="<c:url value='/resources/css/common/utils.css' />"
	rel="stylesheet">
<link href="<c:url value='/resources/css/amap/image-format.css' />"
	rel="stylesheet">
<link
	href="<c:url value='/resources/bootstrap/bootstrap-icons.min.css' />"
	rel="stylesheet">
</head>
<body class="row ${cssStyle} light ${font}-title ${font}-button">
	<header class="fc-main bg-main border-1 border-alt">
		<!-- Inclusion du header -->
		<jsp:include page="../common/headerAdmin.jsp" />
	</header>
	<!-- Inclusion de la sidebar -->
	<jsp:include page="../common/sidebarAdmin.jsp" />

	<div id="map" class="p-0"></div>

	<!-- Contenu principal -->
	<div class="content col fc-main">
		<div class="container-fluid mt-2">
			<div class="row justify-content-center">
				<div class="form-container">
					<div class="header-container">
						<a
							href="<c:url value='/amap/${tenancyAlias}/admin/contracts/list' />"
							class="${font} text-decoration-none rounded-pill btn btn-outline-300 border border-1 fw-bold fc-300 fch-900">
							<i class="bi bi-arrow-left-circle"></i><span
							class="d-none d-md-inline"> Liste des contracts</span>
						</a>
						<div class="d-flex align-items-end gap-3">
							<h2 class="my-4 fw-bold mb-0">Détails du panier</h2>
							<label class="form-label mb-0">Créé le ${formattedDate}</label>
						</div>
						<br>
					</div>
					<form:form method="POST"
						action="${pageContext.request.contextPath}/amap/${tenancyAlias}/admin/contracts/update"
						enctype="multipart/form-data" modelAttribute="contract">
						<form:errors path="*" cssClass="errorBox" />
						<form:hidden path="id" value="${contract.id }" />
						<div class="row">
							<!-- Première colonne -->
							<div class="col-md-4">
								<div class="mb-3">
									<label class="form-label">Nom du panier</label>
									<form:input path="contractName" class="form-control"
										value="${contract.contractName}" />
								</div>
								<div class="mb-3">
									<label for="contractType" class="form-label">Type de
										panier</label>
									<form:select path="contractType"
										class="form-select form-control">
										<c:forEach var="type" items="${contractTypes}">
											<form:option value="${type}" label="${type.displayName}" />
										</c:forEach>
									</form:select>
								</div>
								<div class="mb-3">
									<label for="contractWeight" class="form-label">Taille
										du panier</label>
									<form:select path="contractWeight"
										class="form-select form-control">
										<c:forEach var="weight" items="${contractWeights}">
											<form:option value="${weight}" label="${weight.displayName}" />
										</c:forEach>
									</form:select>
								</div>
								<div class="mb-3">
									<label class="form-label">Fournisseur</label> <select
										id="userId" name="userId" class="form-select">
										<option value=""
											<c:if test="${contract.user == null}">selected</c:if>>Choisir
											un fournisseur</option>
										<c:forEach var="user" items="${users}">
											<option value="${user.userId}"
												<c:if test="${contract.user != null && contract.user.userId == user.userId}">selected</c:if>>
												${user.companyDetails.companyName}</option>
										</c:forEach>
									</select>
								</div>
								<div class="mb-3">
									<label class="form-label">Date de début du contrat</label>
									<form:input path="startDate" type="date" class="form-control" />
								</div>
								<div class="mb-3">
									<label class="form-label">Date de fin du contrat</label>
									<form:input path="endDate" type="date" class="form-control" />
								</div>
							</div>

							<!-- Deuxième colonne -->
							<div class="col-md-4">
								<div class="mb-3">
									<label class="form-label">Produits composant le panier</label>
									<form:textarea path="contractDescription" class="form-control"
										rows="3"></form:textarea>
								</div>
								<div class="mb-3">
									<label class="form-label">Prix de vente</label>
									<div class="input-group">
										<form:input path="contractPrice" type="number" step="0.10"
											class="form-control" placeholder="Prix" />
										<span class="input-group-text">€</span>
									</div>
								</div>
								<div class="mb-3">
									<label class="form-label">Fréquence de livraison</label>
									<form:select path="deliveryRecurrence"
										class="form-select form-control">
										<c:forEach var="recurrence" items="${deliveryRecurrence}">
											<form:option value="${recurrence}"
												label="${recurrence.displayName}" />
										</c:forEach>
									</form:select>
								</div>
								<div class="mb-3">
									<label class="form-label">Quantité disponible entre
										chaque livraison</label>
									<div class="input-group">
										<form:input path="quantity" type="number" step="1"
											class="form-control" placeholder="Quantité" />
										<span class="input-group-text">paniers</span>
									</div>
								</div>
							</div>
							<!-- Troisième colonne -->
							<div class="col-md-4">
								<div class="mb-3">
									<label for="image" class="form-label">Photo du panier</label> <input
										type="file" class="form-control" id="imageInput" name="image"
										accept="image/png,image/jpeg,image/svg">
								</div>
								<div class="mb-3 text-center">
									<c:if test="${not empty contract.imageData}">
										<img id="imagePreview"
											src="data:${contract.imageType};base64,${contract.imageData}"
											alt="Aperçu du contrat" class="image-preview">
									</c:if>
								</div>
								<br>
								<div class="mb-3">
									<p>
										<strong>Adresse de livraison du point de collecte :</strong><br>
										<c:if test="${not empty address}">
            ${address.line1} ${address.line2}, ${address.city} (${address.postCode})
        </c:if>
									</p>
								</div>
								<div class="mb-3">
									<p>
										<strong>Jour et heures de livraison :</strong><br>
										<c:if test="${not empty address}">
            ${pickupSchedule.localizedDayOfWeek} de ${pickupSchedule.startHour} à ${pickupSchedule.endHour}
        </c:if>
									</p>
								</div>
							</div>
							<div class="d-flex justify-content-evenly my-5">
								<div class="col text-center">
									<button id="submit-button" type="submit"
										class="btn btn-300 rounded-pill">Valider la
										modification</button>
								</div>
								<div class="col text-center">
									<button type="reset" class="btn btn-900 fc-alt border-alt rounded-pill">Annuler</button>
								</div>
							</div>
						</div>
					</form:form>
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
	<script
		src="<c:url value='/resources/bootstrap/bootstrap.bundle.min.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/image-format.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/amap/admin/contract-edit.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/mapbox/mapbox-gl.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/mapbox/map.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/theme-swap.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/amap/admin/sidebar.js' />" type="text/javascript"></script>
</body>
</html>
