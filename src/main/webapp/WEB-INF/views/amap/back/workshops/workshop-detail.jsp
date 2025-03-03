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
<title>Détails de l'atelier</title>
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
	<!-- Inclusion du header -->
	<header class="fc-main bg-main border-1 border-alt">
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
							<i class="bi bi-arrow-left-circle"></i><span
							class="d-none d-md-inline"> Liste des ateliers</span>
						</a>
						<div class="d-flex align-items-end gap-3">
							<h2 class="my-4 fw-bold mb-0">Détails de l'atelier</h2>
							<label class="form-label mb-0">Créé le ${formattedDate}</label>
						</div>
						<br>
					</div>
					<form:form method="POST"
						action="${pageContext.request.contextPath}/amap/${tenancyAlias}/admin/workshops/update"
						enctype="multipart/form-data" modelAttribute="workshop">
						<form:errors path="*" cssClass="errorBox" />
						<form:hidden path="id" value="${workshop.id }" />
						<div class="row">
							<!-- Première colonne -->
							<div class="col-md-4">
								<div class="mb-3">
									<label class="form-label">Nom de l'atelier</label>
									<form:input path="workshopName" class="form-control"
										value="${workshop.workshopName}" />
								</div>

								<div class="mb-3">
									<label class="form-label">Intervenant</label> <select
										id="userId" name="userId" class="form-select">
										<option value=""
											<c:if test="${workshop.user == null}">selected</c:if>>Choisir
											un intervenant</option>
										<c:forEach var="user" items="${users}">
											<option value="${user.userId}"
												<c:if test="${workshop.user != null && workshop.user.userId == user.userId}">selected</c:if>>
												${user.companyDetails.companyName}</option>
										</c:forEach>
									</select>
								</div>
								<div class="mb-3">
									<p>
										<strong>Lieu de l'atelier :</strong>
										<c:if test="${not empty workshop.address}">
            ${workshop.address.line1}, ${workshop.address.line2}, ${workshop.address.city} (${workshop.address.postCode})
        </c:if>
										<c:if test="${empty workshop.address}">
            Aucune adresse associée.
        </c:if>
									</p>
								</div>
								<div class="mb-3">
									<label class="form-label">Description</label>
									<form:textarea path="workshopDescription" class="form-control"
										rows="3"></form:textarea>
								</div>
							</div>


							<!-- Deuxième colonne -->
							<div class="col-md-4">
								<div class="mb-3">
									<label class="form-label">Date et Heure</label>
									<form:input path="workshopDateTime" type="datetime-local"
										class="form-control" value="${workshopDateTime}" />
								</div>
								<div class="mb-3">
									<label class="form-label">Durée</label>
									<div class="input-group">
										<form:input path="workshopDuration" type="number" step="1"
											class="form-control" />
										<span class="input-group-text">minutes</span>
									</div>
								</div>
								<div class="mb-3">
									<label class="form-label">Nombre de participants
										minimum</label>
									<form:input path="minimumParticipants" type="number"
										class="form-control" />
								</div>
								<div class="mb-3">
									<label class="form-label">Nombre de participants
										maximum</label>
									<form:input path="maximumParticipants" type="number"
										class="form-control" />
								</div>
							</div>

							<!-- Troisième colonne -->
							<div class="col-md-4">
								<div class="mb-3">
									<label for="image" class="form-label">Photo de
										l'atelier</label> <input type="file" class="form-control"
										id="imageInput" name="image"
										accept="image/png,image/jpeg,image/svg">
								</div>
								<div class="mb-3 text-center">
									<c:if test="${not empty workshop.imageData}">
										<img id="imagePreview"
											src="data:${workshop.imageType};base64,${workshop.imageData}"
											alt="Aperçu de l'atelier" class="image-preview">
									</c:if>
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
		src="<c:url value='/resources/bootstrap/bootstrap.bundle.min.js' />"
		type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/image-format.js' />"
		type="text/javascript"></script>

	<script
		src="<c:url value='/resources/js/common/mapbox/mapbox-gl.js' />"></script>
	<script src="<c:url value='/resources/js/common/mapbox/map.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/theme-swap.js' />"
		type="text/javascript"></script>
	

	<script src="<c:url value='/resources/js/amap/admin/sidebar.js' />"
		type="text/javascript"></script>
</body>
</html>
