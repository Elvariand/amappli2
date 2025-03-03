<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>Liste des Contrats</title>
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

	<div id="map" class="p-0"></div>

	<!-- Contenu principal -->
	<div class="content col">
		<div class="container-fluid mt-2">
			<div class="row justify-content-center">
				<div class="col-12">
					<div class="search-bar d-flex align-items-center justify-content-between mb-3">
						<!-- Dropdown pour trier -->
						<div class="d-flex align-items-center me-4">
							<label for="sortBy" class="me-2 fw-400 fs-3 text-nowrap fc-main">Trier par</label>
							<select id="sortBy" class="form-select custom-select border-main">
								<option value="name">Nom</option>
								<option value="producer">Producteur</option>
								<option value="priceAsc" class="d-none d-md-block">Prix croissant</option>
								<option value="priceDesc" class="d-none d-md-block">Prix décroissant</option>
							</select>
						</div>
						<!-- Barre de recherche -->
						<div>
							<input type="text" id="searchBar"
								class="form-control custom-input border-main"
								placeholder="Rechercher...">
						</div>
						<div class="mx-3 d-none d-md-block">
							<label class="fc-main">Opacité du tableau</label>
    						<input type="range" class="form-range" min="0" max="100" value="100" id="bg-range">
						</div>
					</div>
					<div
						class="table-container d-flex justify-content-between align-items-center my-2">
						<h2 class="fw-bold fc-main my-auto">Liste des paniers</h2>
						<a
							href="<c:url value='/amap/${tenancyAlias}/admin/contracts/form'/>"
							class="btn btn-outline-300 rounded-pill fch-100 fw-bold border-2">
							<span class="icon">+ </span><span class=" d-none d-md-inline">Créer
								un panier</span>
						</a>
					</div>
						<!-- Nombre total de contrats -->
						<div class="mb-2 fs-5 fc-main d-none d-md-block">
							<span>${contracts.size()} éléments</span>
						</div>
					<!-- Mode tableau -->
					<table
						class="table table-hover table-responsive fc-main align-middle"
						style="--bs-table-bg: color-mix(in srgb, #ffffff, transparent 100%);">
						<thead>
							<tr>
								<th class="d-none d-md-table-cell bg-700">Image</th>
								<th class="d-table-cell d-md-none bg-700"></th>
								<th class="bg-700">Nom</th>
								<th class="d-none d-md-table-cell bg-700">Type</th>
								<th class="d-table-cell d-md-none bg-700"></th>
								<th class="bg-700">Producteur</th>
								<th class="d-none d-md-table-cell bg-700">Prix</th>
								<th class="d-table-cell d-md-none bg-700"></th>
								<th class="bg-700">Actions</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="contract" items="${contracts}">
								<tr>
									<td class="d-table-cell d-md-none"></td>
									<td class="text-truncate d-none d-md-table-cell"><c:if test="${not empty contract.imageData}">
											<img src="data:${contract.imageType};base64,${contract.imageData}" alt="Image du contrat"
												style="width: 50px; height: 50px; border-radius: 8px; object-fit: cover;">
										</c:if></td>
									<td class="text-wrap">${contract.contractName}</td>
									<td class="d-table-cell d-md-none"></td>
									<td class="d-none d-md-table-cell">${contract.contractType.displayName}</td>
									<td class="text-wrap">${contract.user.companyDetails.companyName}</td>
									<td class="d-table-cell d-md-none"></td>
									<td class="d-none d-md-table-cell">${contract.contractPrice}€</td>
									<td>
										<div class='d-flex justify-content-start align-items-center'>
											<a
												href="<c:url value='/amap/${tenancyAlias}/admin/contracts/detail/${contract.id}' />"
												class="btn rounded-circle border-2 border-300 fc-main px-1 py-0 mx-1">
												<i class="bi bi-eye"></i>
											</a>

											<form:form method="POST"
												action="${pageContext.request.contextPath}/amap/${tenancyAlias}/admin/contracts/delete/${contract.id}"
												class="d-inline"
												onsubmit="return confirm('Voulez-vous vraiment supprimer le panier ${contract.contractName} ?');">
												<button type="submit"
													class="btn rounded-circle border-2 border-300 fc-main px-1 py-0 mx-1">
													<i class="bi bi-trash"></i>
												</button>
											</form:form>
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
	<script src="<c:url value='/resources/js/common/mapbox/mapbox-gl.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/mapbox/map.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/amap/admin/contract-list.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/theme-swap.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/amap/admin/bg-table.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/amap/admin/sidebar.js' />" type="text/javascript"></script>
</body>
</html>
