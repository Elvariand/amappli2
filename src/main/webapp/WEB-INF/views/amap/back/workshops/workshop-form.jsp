<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
String currentMainMenu = "products"; // Détermine la rubrique active
String currentPage = "workshops"; // Détermine la sous-rubrique active
request.setAttribute("currentMainMenu", currentMainMenu);
request.setAttribute("currentPage", currentPage);
%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Ajouter un Atelier</title>
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
							href="<c:url value='/amap/${tenancyAlias}/admin/workshops/list' />"
							class="${font} text-decoration-none rounded-pill btn btn-outline-300 border border-1 fw-bold fc-300 fch-900">
							<i class="bi bi-arrow-left"></i><span class="d-none d-md-inline">Liste
								des ateliers</span>
						</a>
						<h2 class="my-4 fw-bold">Ajouter un atelier</h2>
					</div>
					<form:form method="POST"
						action="${pageContext.request.contextPath}/amap/${tenancyAlias}/admin/workshops/add"
						enctype="multipart/form-data">
						<input type="hidden" id="tenancyAlias" name="tenancyAlias"
							value="${tenancyAlias}">
						<div class="row">
							<!-- Première colonne -->
							<div class="col-md-4">
								<div class="mb-3">
									<label for="workshopName" class="form-label">Nom de
										l'atelier</label> <input type="text" class="form-control"
										id="workshopName" name="workshopName" required
										placeholder="Exemple : Atelier cuisine">
									<div class="invalid-feedback"></div>
								</div>
								<div class="mb-3">
									<label for="userId" class="form-label">Intervenant :</label> <select
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
									<label for="workshopDescription" class="form-label">Description
										de l'atelier</label>
									<textarea class="form-control" id="workshopDescription"
										name="workshopDescription" rows="10" required
										placeholder="Décrivez les objectifs et contenu de votre atelier"></textarea>
									<div class="invalid-feedback"></div>
								</div>
							</div>

							<!-- Deuxième colonne -->
							<div class="col-md-4">
								<div class="mb-3">
									<label for="workshopDateTime" class="form-label">Date
										et heure de l'atelier</label> <input type="datetime-local"
										class="form-control" id="workshopDateTime"
										name="workshopDateTime" step="900" required>
									<div class="invalid-feedback"></div>
								</div>

								<div class="mb-3">
									<label for="workshopDuration" class="form-label">Durée
										de l'atelier (en minutes)</label> <input type="number"
										class="form-control" id="workshopDuration"
										name="workshopDuration" placeholder="Durée en minutes"
										step="1" min="1" required>
									<div class="invalid-feedback"></div>
								</div>
								<div class="mb-3">
									<label for="minimumParticipants" class="form-label">Participants
										minimum</label> <input type="number" class="form-control"
										id="minimumParticipants" name="minimumParticipants"
										placeholder="0" min="1" required>
									<div class="invalid-feedback"></div>
								</div>
								<div class="mb-3">
									<label for="maximumParticipants" class="form-label">Participants
										maximum</label> <input type="number" class="form-control"
										id="maximumParticipants" name="maximumParticipants"
										placeholder="0" min="1" required>
									<div class="invalid-feedback"></div>
								</div>
							</div>

							<!-- Troisième colonne -->
							<div class="col-md-4">
								<div class="mb-3">
									<label for="workshopPrice" class="form-label">Prix de
										l'atelier par personne</label>
									<div class="input-group">
										<input type="number" class="form-control" id="workshopPrice"
											name="workshopPrice" step="0.10" placeholder="0.00" min="1"
											required> <span class="input-group-text">€</span>
									</div>
									<div class="invalid-feedback"></div>
								</div>
								<div class="mb-3">
									<label for="image" class="form-label">Photo de
										l'atelier</label> <input type="file" class="form-control"
										id="imageInput" name="image"
										accept="image/png,image/jpeg,image/svg" required>
									<div class="invalid-feedback"></div>
								</div>
								<div class="mb-3 text-center">
									<img src="https://via.placeholder.com/150"
										alt="Aperçu de l'atelier" class="image-preview"
										id="imagePreview">
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
		src="<c:url value='/resources/js/amap/admin/workshop-form.js' />"></script>
	<script
		src="<c:url value='/resources/bootstrap/bootstrap.bundle.min.js' />"
		type="text/javascript"></script>
	<script
		src="<c:url value='/resources/js/common/mapbox/mapbox-gl.js' />"></script>
	<script src="<c:url value='/resources/js/common/mapbox/map.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/theme-swap.js' />"
		type="text/javascript"></script>
	

	<script src="<c:url value='/resources/js/amap/admin/sidebar.js' />"
		type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/image-format.js' />"
		type="text/javascript"></script>
</body>
</html>
