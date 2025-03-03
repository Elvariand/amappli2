<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
String currentMainMenu = "users"; // Détermine la rubrique active
String currentPage = "suppliers"; // Détermine la sous-rubrique active
request.setAttribute("currentMainMenu", currentMainMenu);
request.setAttribute("currentPage", currentPage);
%>
<!DOCTYPE html>
<html lang="fr">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Ajouter un adhérent</title>
	<link href="<c:url value='/resources/bootstrap/bootstrap.min.css' />" rel="stylesheet" />
	<link href="<c:url value='/resources/css/common/utils.css' />" rel="stylesheet">
	<link href="<c:url value='/resources/bootstrap/bootstrap-icons.min.css' />" rel="stylesheet" />
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
							<a href="<c:url value='/amap/${tenancyAlias}/admin/users/list' />" class="${font} text-decoration-none rounded-pill btn btn-outline-300 border border-1 fw-bold fc-300 fch-900">
								<i class="bi bi-arrow-left"></i> Liste<span class="d-none d-md-inline"> des adhérents</span>
							</a>
							<h2 class="my-4 fw-bold">Ajouter un adhérent</h2>
						</div>
						<form:form method="POST" action="/Amappli/amap/${tenancyAlias}/admin/users/add" enctype="multipart/form-data" modelAttribute="user">
                            <div class="row">
                                <!-- Première colonne -->
                                <div class="col-md-4">
                                    <div class="mb-3">
                                        <label class="form-label">Nom de famille</label>
                                        <form:input path="contactInfo.name" class="form-control ${nameError != null ? 'is-invalid' : ''}" required="true" />
                                        <div class="invalid-feedback"> ${nameError} </div>
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Prénom</label>
                                        <form:input path="contactInfo.firstName" class="form-control ${firstNameError != null ? 'is-invalid' : ''}" required="true" />
                                        <div class="invalid-feedback"> ${firstNameError} </div>
                                    </div>
                                    <div class="mb-3 has-validation">
                                        <label class="form-label">E-mail</label>
                                        <form:input path="email" class="form-control ${emailError != null ? 'is-invalid' : ''}" required="true" />
                                        <div class="invalid-feedback"> ${emailError} </div>
                                    </div>
                                    <div class="mb-3">
										<label class="form-label">Mot de passe</label>
										<form:password showPassword="true" class="form-control ${passwordError != null ? 'is-invalid' : ''}" path="password" />
                                        <div class="invalid-feedback"> ${passwordError} </div>
									</div>
									<div class="mb-3">
										<label class="form-label">Confirmer le mot de passe</label>
                                        <form:password showPassword="true" class="form-control ${confirmPasswordError != null ? 'is-invalid' : ''}" path="confirmPassword" />
                                        <div class="invalid-feedback"> ${confirmPasswordError} </div>
									</div>
                                    <div class="mb-3">
                                        <label class="form-label">Balance crédit</label>
                                        <form:input path="creditBalance" type="number" class="form-control ${creditBalanceError != null ? 'is-invalid' : ''}" value="0" />
                                        <div class="invalid-feedback"> ${creditBalanceError} </div>
                                    </div>
                                </div>

								<!-- Deuxième colonne -->
                                <div class="col-md-4">
                                    <div class="mb-3">
                                        <label class="form-label">Adresse</label>
                                        <form:input path="address.line2" class="form-control ${line2Error != null ? 'is-invalid' : ''}" placeholder="151 avenue de la République" />
                                        <div class="invalid-feedback"> ${line2Error} </div>
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Complément d'adresse</label>
                                        <form:input path="address.line1" class="form-control ${line1Error != null ? 'is-invalid' : ''}" placeholder="Appartement 9bis"/>
                                        <div class="invalid-feedback"> ${line1Error} </div>
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Code Postal</label>
                                        <form:input path="address.postCode" class="form-control ${postCodeError != null ? 'is-invalid' : ''}"/>
                                        <div class="invalid-feedback"> ${postCodeError} </div>
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Ville</label>
                                        <form:input path="address.city" class="form-control ${cityError != null ? 'is-invalid' : ''}"/>
                                        <div class="invalid-feedback"> ${cityError} </div>
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Numéro de téléphone</label>
                                        <form:input path="contactInfo.phoneNumber" class="form-control ${phoneNumberError != null ? 'is-invalid' : ''}"/>
                                        <div class="invalid-feedback"> ${phoneNumberError} </div>
                                    </div>
                                </div>

								<!-- Troisième colonne -->
                                <div class="col-md-4">
                                    <div class="mb-3 text-center">
                                        <div class="mb-3">
                                            <label class="form-label">Roles :</label><br/>
                                            <c:forEach var="role" items="${allRoles }" >
												<label class="form-label" for="role-box-${role.name }">${role.name}</label>
												<c:choose>
													<c:when test="${role.name.equals('Adherent')}">
                                                		<form:checkbox id="role-box-${role.name }" class="me-3 ${rolesError != null ? 'is-invalid' : ''}" path="roles" value="${role.roleId}" checked="true" />
                                                	</c:when>
                                                	<c:otherwise>
                                                		<form:checkbox id="role-box-${role.name }" class="me-3 ${rolesError != null ? 'is-invalid' : ''}" path="roles" value="${role.roleId}" />
                                                	</c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                            <div class="invalid-feedback"> ${rolesError} </div>
                                        </div>
                                        <section id="supplier-section" class="d-none">
                                            <div class="mb-3">
                                                <label class="form-label">Exploitation</label>
                                                <form:input path="companyDetails.companyName" class="form-control ${companyNameError != null ? 'is-invalid' : ''}" placeholder="Le verger exotique"/>
                                                <div class="invalid-feedback"> ${companyNameError} </div>
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label">N° Siret</label>
                                                <form:input path="companyDetails.siretNumber" class="form-control ${siretNumberError != null ? 'is-invalid' : ''}" placeholder="14 chiffres"/>
                                                <div class="invalid-feedback"> ${siretNumberError} </div>
                                            </div>
                                        </section>
                                    </div>
                                </div>
                            </div>
                            <div class="d-flex justify-content-evenly my-5">
								<div class="col text-center">
									<button id="submit-button" type="submit" class="btn btn-300 rounded-pill" 
									<c:if test="${isAdmin }">onclick="return confirm('Vous êtes sur le point d'enregistrer un utilisateur avec les droits d'administrateur, êtes vous sûr ?');"</c:if> >Ajouter l'adhérent</button>
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
		var latitude = "${latitude}";
		var longitude = "${longitude}"; 
	</script>
	<script	src="<c:url value='/resources/bootstrap/bootstrap.bundle.min.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/mapbox/mapbox-gl.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/mapbox/map.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/theme-swap.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/autofill.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/amap/admin/sidebar.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/amap/admin/user-details.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/amap/admin/user-form.js' />" type="text/javascript"></script>
</body>
</html>
