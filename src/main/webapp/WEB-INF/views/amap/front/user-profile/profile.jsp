<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
String currentMainMenu = "profile"; // Détermine la rubrique active
String currentPage = "profile"; // Détermine la sous-rubrique active
request.setAttribute("currentMainMenu", currentMainMenu);
request.setAttribute("currentPage", currentPage);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://api.mapbox.com/mapbox-gl-js/v2.15.0/mapbox-gl.css" rel="stylesheet" />
<link rel="stylesheet" href="<c:url value='/resources/bootstrap/bootstrap.min.css'/>" />
<link rel="stylesheet" href="<c:url value='/resources/css/amap/user-profile/profile.css' />">

<title>Mon profil</title>


</head>
<body class="row ${cssStyle} light ${font}-title ${font}-button">

	<header class="fc-main bg-main">
		<jsp:include page="common/header-user-account.jsp" />
	</header>
	<jsp:include page="common/sidebar-user-account.jsp" />


	<div id="map"></div>
	
    <div class="form-container mt-5  ms-5 fc-main centered-form">
        <!-- Affichage du message de succès -->
        <c:if test="${not empty success}">
            <div class="alert alert-success text-center">${success}</div>
        </c:if>

        <!-- Affichage du message d'erreur -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger text-center">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/amap/${tenancyAlias}/account/profile" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <!-- Nom et Prénom -->
            <div class="row mb-3">
                <div class="col">
                    <input type="text" id="name" name="contactInfo.name" class="form-control" value="${updateProfileDTO.contactInfo.name}" placeholder="Nom">
                    <c:if test="${not empty errorName}">
                        <div class="text-danger">${errorName}</div>
                    </c:if>
                </div>
                <div class="col">
                    <input type="text" id="firstName" name="contactInfo.firstName" class="form-control" value="${updateProfileDTO.contactInfo.firstName}" placeholder="Prénom">
                    <c:if test="${not empty errorFirstName}">
                        <div class="text-danger">${errorFirstName}</div>
                    </c:if>
                </div>
            </div>

            <!-- Email -->
            <div class="mb-3">
                <input type="email" id="email" name="email" class="form-control" value="${updateProfileDTO.email}" required placeholder="Email">
                <c:if test="${not empty errorEmail}">
                    <div class="text-danger">${errorEmail}</div>
                </c:if>
            </div>

            <!-- Numéro de téléphone -->
            <div class="mb-3">
                <input type="text" id="phoneNumber" name="contactInfo.phoneNumber" class="form-control" value="${updateProfileDTO.contactInfo.phoneNumber}" placeholder="Numéro de téléphone">
                <c:if test="${not empty errorPhoneNumber}">
                    <div class="text-danger">${errorPhoneNumber}</div>
                </c:if>
            </div>

            <!-- Adresse ligne 1 -->
            <div class="mb-3">
                <input type="text" id="line1" name="address.line1" class="form-control" value="${updateProfileDTO.address.line1}" placeholder="Adresse ligne 1">
                <c:if test="${not empty errorLine1}">
                    <div class="text-danger">${errorLine1}</div>
                </c:if>
            </div>

            <!-- Adresse ligne 2 -->
            <div class="mb-3">
                <input type="text" id="line2" name="address.line2" class="form-control" value="${updateProfileDTO.address.line2}" placeholder="Bâtiment / Appartement">
                <c:if test="${not empty errorLine2}">
                    <div class="text-danger">${errorLine2}</div>
                </c:if>
            </div>

            <!-- Code postal et Ville -->
            <div class="row mb-3">
                <div class="col">
                    <input type="text" id="postCode" name="address.postCode" class="form-control" value="${updateProfileDTO.address.postCode}" placeholder="Code postal">
                    <c:if test="${not empty errorPostCode}">
                        <div class="text-danger">${errorPostCode}</div>
                    </c:if>
                </div>
                <div class="col">
                    <input type="text" id="city" name="address.city" class="form-control" value="${updateProfileDTO.address.city}" placeholder="Ville">
                    <c:if test="${not empty errorCity}">
                        <div class="text-danger">${errorCity}</div>
                    </c:if>
                </div>
            </div>
            <br>

          
            <div class="btn-container">
                <button type="submit" class="btn  bg-500  rounded-pill">Mettre à jour</button>
            </div>
        </form>
    </div>
    <script
		src="<c:url value='/resources/bootstrap/bootstrap.bundle.min.js' />"></script>

	<script>
		var styleMapboxLight = "${mapStyleLight}"
		var styleMapboxDark = "${mapStyleDark}"
		var latitude = "${latitude}"
		var longitude = "${longitude}"
	</script>

	<script
		src="<c:url value='/resources/js/common/mapbox/mapbox-gl.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/mapbox/map.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/theme-swap.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/palette-swap.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/amap/admin/sidebar.js' />" type="text/javascript"></script>
</body>
</html>
