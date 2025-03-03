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
<title>Ajouter un Contrat</title>
<link href="<c:url value='/resources/bootstrap/bootstrap.min.css' />"
	rel="stylesheet" />
<link href="<c:url value='/resources/css/common/utils.css' />"
	rel="stylesheet">
<link
	href="<c:url value='/resources/bootstrap/bootstrap-icons.min.css' />"
	rel="stylesheet" />
<link href="<c:url value='/resources/css/amap/image-format.css' />"
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
							<i class="bi bi-arrow-left"></i><span class="d-none d-md-inline">
								Liste des paniers</span>
						</a>
						<h2 class="my-4 fw-bold">Ajouter un panier</h2>
					</div>
					<form:form method="POST"
						action="${pageContext.request.contextPath}/amap/${tenancyAlias}/admin/contracts/add"
						enctype="multipart/form-data">
						<input type="hidden" id="tenancyAlias" name="tenancyAlias"
							value="${tenancyAlias}">
						<div class="row">
							<!-- Première colonne -->
							<div class="col-md-4">
								<div class="mb-3">
									<label for="contractName" class="form-label">Nom du
										panier</label> <input type="text" class="form-control"
										id="contractName" name="contractName" required
										placeholder="Exemple : Panier d'hiver">
									<div class="invalid-feedback"></div>
								</div>
								<div class="mb-3">
									<label for="contractType" class="form-label">Type de
										panier</label> <select class="form-select form-control"
										id="contractType" name="contractType" required>
										<option selected disabled></option>
										<option value="FRUITS_CONTRACT">Panier de fruits</option>
										<option id="select-1" value="VEGETABLES_CONTRACT">Panier de légumes</option>
										<option value="MIX_CONTRACT">Panier mixte</option>
									</select>
									<div class="invalid-feedback"></div>
								</div>
								<div class="mb-3">
									<label for="contractWeight" class="form-label">Taille
										du panier</label> <select class="form-select form-control"
										id="contractWeight" name="contractWeight" required>
										<option selected disabled></option>
										<option id="select-2" value="SMALL">Petit panier (2-4kg)</option>
										<option value="AVERAGE">Moyen panier (5-8kg)</option>
										<option value="BIG">Grand panier (9kg et +)</option>
									</select>
									<div class="invalid-feedback"></div>
								</div>

								<div class="mb-3">
									<label for="userId" class="form-label">Fournisseur :</label> <select
										id="userId" name="userId" class="form-select" required>
										<option value="" selected></option>
										<c:forEach var="user" items="${users}">
											<option value="${user.userId}">
												${user.companyDetails.companyName}</option>
										</c:forEach>
									</select>
									<div class="invalid-feedback"></div>
								</div>

								<div class="mb-3">
									<label for="startDate" class="form-label">Date de début
										du contrat</label> <input type="date" class="form-control"
										id="startDate" name="startDate" min="${currentDate}" required>
								</div>
								<div class="mb-3">
									<label for="endDate" class="form-label">Date de fin du
										contrat</label> <input type="date" class="form-control" id="endDate"
										name="endDate" required>
								</div>
							</div>

							<!-- Deuxième colonne -->
							<div class="col-md-4">
								<div class="mb-3">
									<label for="contractDescription" class="form-label">Produits
										composant le panier</label>
									<textarea class="form-control" id="contractDescription"
										name="contractDescription" rows="5" required
										placeholder="Pommes, carottes, oignons..."></textarea>
									<div class="invalid-feedback"></div>
								</div>
								<div class="mb-3">
									<label for="contractPrice" class="form-label">Prix de
										vente</label>
									<div class="input-group">
										<input type="number" class="form-control" id="contractPrice"
											name="contractPrice" step="0.10" placeholder="0.00" min="1"
											required> <span class="input-group-text">€</span>
									</div>
									<div class="invalid-feedback"></div>
								</div>
								<div class="mb-3">
									<label for="deliveryRecurrence" class="form-label">Fréquence
										de livraison au point de collecte</label> <select
										class="form-select form-control" id="deliveryRecurrence"
										name="deliveryRecurrence" required>
										<option selected disabled></option>
										<option id="select-3" value="WEEKLY">Hebdomadaire</option>
										<option value="BIMONTHLY">Bimensuel</option>
										<option value="MONTHLY">Mensuel</option>
									</select>
									<div class="invalid-feedback"></div>
								</div>
								<div class="mb-3">
									<label for="quantity" class="form-label">Quantité
										disponible entre chaque livraison</label>
									<div class="input-group">
										<input type="number" class="form-control" id="quantity"
											name="quantity" step="1.00" required placeholder="0" min="1">
										<span class="input-group-text">paniers</span>
									</div>
									<div class="invalid-feedback"></div>
								</div>
							</div>

							<!-- Troisième colonne -->
							<div class="col-md-4">
								<div class="mb-3">
									<label for="image" class="form-label">Photo du panier</label> <input
										type="file" class="form-control" id="imageInput" name="image"
										accept="image/png,image/jpeg,image/svg" required>
									<div class="invalid-feedback"></div>
								</div>
								<div class="mb-3 text-center">
									<img src="https://via.placeholder.com/150"
										alt="Aperçu du contrat" class="image-preview"
										id="imagePreview">
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
										création</button>
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
		src="<c:url value='/resources/js/amap/admin/contract-form.js' />"></script>
	<script
		src="<c:url value='/resources/bootstrap/bootstrap.bundle.min.js' />"
		type="text/javascript"></script>
	<script
		src="<c:url value='/resources/js/common/mapbox/mapbox-gl.js' />"></script>
	<script src="<c:url value='/resources/js/common/mapbox/map.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/theme-swap.js' />"
		type="text/javascript"></script>
	
	<script src="<c:url value='/resources/js/common/autofill.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/amap/admin/sidebar.js' />"
		type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/image-format.js' />"
		type="text/javascript"></script>

</body>
</html>
