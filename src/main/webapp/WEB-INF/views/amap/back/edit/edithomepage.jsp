<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifier votre page d'accueil</title>


    <!-- Mapbox CSS -->
    <link href="https://api.mapbox.com/mapbox-gl-js/v2.15.0/mapbox-gl.css" rel="stylesheet" />
    <link href="<c:url value='/resources/bootstrap/bootstrap.min.css'/>" rel="stylesheet">
    <link href="<c:url value='/resources/bootstrap/bootstrap-icons.min.css'/>" rel="stylesheet">
    <link href="<c:url value='/resources/css/amappli/tenancycreation.css'/>" rel="stylesheet">

</head>

<body class="${cssStyle} light ${font}-title ${font}-button">
    <header class="fc-main bg-main">
        <jsp:include page="../../../amap/back/common/headerAdmin.jsp" />
    </header>

    <div id="map"></div>

    <div class="fc-main content col d-flex">
        <jsp:include page="../../../amap/back/common/sidebarAdmin.jsp" />

        <div class="content col container-fluid row mt-2">
            <!-- Main Title -->
            <h1 class="text-center mb-4">Modifier votre AMAP</h1>

             <!-- Accordion for Collapsible Sections -->
            <div class="accordion" id="accordionExample">
                <!-- Nom ou adresse internet -->
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingOne">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
                            <i class="bi bi-chevron-right me-2"></i> Nom ou adresse internet
                        </button>
                    </h2>
                    <div id="collapseOne" class="accordion-collapse collapse" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
                        <div class="accordion-body">
                            <form action="${pageContext.request.contextPath}/amap/${tenancyAlias}/admin/editthenameandalias" method="post">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <div class="mb-3">
                                    <label class="form-label" for="input-tenancyName">Nom de l'AMAP</label>
                                    <input class="form-control" type="text" class="form-control" id="input-tenancyName" name="tenancyName"
                                        value="${tenancyUpdateNameAliasDTO.tenancyName}" required>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label" for="input-tenancyAlias">Adresse internet de l'AMAP</label>
                                    <input class="form-control" type="text" class="form-control" id="input-tenancyAlias" name="tenancyAlias"
                                        value="${tenancyUpdateNameAliasDTO.tenancyAlias}" required>
                                </div>
                                <div class="mb-3">
                                    Votre AMAP sera accessible à l'adresse <i>www.amappli.fr/<span id="amap-url-example">...</span></i>
                                </div>
                                <button type="submit" class="btn btn-700">Sauvegarder</button>
                            </form>
                        </div>
                    </div>
                </div>

                <!-- Slogan -->
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingTwo">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                            <i class="bi bi-chevron-right me-2"></i> Slogan
                        </button>
                    </h2>
                    <div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="headingTwo" data-bs-parent="#accordionExample">
                        <div class="accordion-body">
                            <form action="${pageContext.request.contextPath}/amap/${tenancyAlias}/admin/edittheslogan" method="post">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <div class="mb-3">
                                    <label class="form-label" for="input-tenancySlogan">Slogan de l'AMAP</label>
                                    <input class="form-control" type="text" class="form-control" id="input-tenancySlogan" name="slogan"
                                        value="${tenancyUpdateSloganDTO.slogan}" required>
                                </div>
                                <button type="submit" class="btn btn-700">Sauvegarder</button>
                            </form>
                        </div>
                    </div>
                </div>

                <!-- Logo -->
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingThree">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                            <i class="bi bi-chevron-right me-2"></i> Logo
                        </button>
                    </h2>
                    <div id="collapseThree" class="accordion-collapse collapse" aria-labelledby="headingThree" data-bs-parent="#accordionExample">
                        <div class="accordion-body">
                            <form action="${pageContext.request.contextPath}/amap/${tenancyAlias}/admin/editthelogo" method="post" enctype="multipart/form-data">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <div class="mb-3">
                                    <img id="preview-logo" class="w-30" src="data:${tenancyUpdateLogo.logoImgType};base64,${tenancyUpdateLogo.logoImg}" alt="logo"/>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label" for="input-tenancyLogo">Logo de l'AMAP</label>
                                    <input class="form-control" type="file" class="form-control" id="input-tenancyLogo" name="file"
                                        required accept="image/png,image/jpeg,image/svg">
                                </div>
                                <button type="button" id="cancel-preview-logo" class="btn btn-900" style="display: none;">Annuler</button>
                                <button type="submit" class="btn btn-700">Sauvegarder</button>
                            </form>
                        </div>
                    </div>
                </div>

                <!-- Adresse -->
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingFour">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                            <i class="bi bi-chevron-right me-2"></i> Adresse
                        </button>
                    </h2>
                    <div id="collapseFour" class="accordion-collapse collapse" aria-labelledby="headingFour" data-bs-parent="#accordionExample">
                        <div class="accordion-body">
                            <form action="${pageContext.request.contextPath}/amap/${tenancyAlias}/admin/edittheaddress" method="post">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <p>Cela changera les courbes de niveau en arrière-plan de votre site.</p>
                                <div class="mb-3">
                                    <label class="form-label" for="input-tenancyAddressLine1">Complément d'adresse</label>
                                    <input class="form-control" type="text" class="form-control" id="input-tenancyAddressLine1" name="address.line1"
                                        value="${tenancyUpdateAddressDTO.address.line1}">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label" for="input-tenancyAddressLine2">Numéro et Rue</label>
                                    <input class="form-control" type="text" class="form-control" id="input-tenancyAddressLine2" name="address.line2"
                                        value="${tenancyUpdateAddressDTO.address.line2}" required>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label" for="input-tenancyAddressPostCode">Code Postal</label>
                                    <input class="form-control" type="text" class="form-control" id="input-tenancyAddressPostCode" name="address.postCode"
                                        value="${tenancyUpdateAddressDTO.address.postCode}" required>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label" for="input-tenancyAddressCity">Ville</label>
                                    <input class="form-control" type="text" class="form-control" id="input-tenancyAddressCity" name="address.city"
                                        value="${tenancyUpdateAddressDTO.address.city}" required>
                                </div>
                                <button type="submit" class="btn btn-700">Sauvegarder</button>
                            </form>
                        </div>
                    </div>
                </div>

                <!-- Modifier votre créneau d'ouverture -->
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingFive">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
                            <i class="bi bi-chevron-right me-2"></i> Modifier votre créneau d'ouverture
                        </button>
                    </h2>
                    <div id="collapseFive" class="accordion-collapse collapse" aria-labelledby="headingFive" data-bs-parent="#accordionExample">
                        <div class="accordion-body">
                            <form action="${pageContext.request.contextPath}/amap/${tenancyAlias}/admin/editthepickupschedule" method="post">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <p>Vos utilisateurs seront avertis à partir de leurs prochaines commandes.</p>
                                <div class="mb-3">
                                    <label for="dayOfWeek" class="form-label">Jour de la Semaine</label>
                                    <select class="from-control form-select" name="pickUpSchedule.dayOfWeek" id="dayOfWeek">
                                        <option value="">Sélectionnez un Jour</option>
                                        <option value="a">Test</option>
                                        <c:choose>
                                        <c:when test="${tenancyUpdatePickUpDTO.pickUpSchedule.dayOfWeek.getValue() == 1}">
                                            <option value="MONDAY" selected>Lundi</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="MONDAY">Lundi</option>
                                        </c:otherwise>
                                        </c:choose>

                                        <c:choose>
                                            <c:when test="${tenancyUpdatePickUpDTO.pickUpSchedule.dayOfWeek.getValue() == 2}">
                                                <option value="TUESDAY" selected>Mardi</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="TUESDAY">Mardi</option>
                                            </c:otherwise>
                                        </c:choose>

                                        <c:choose>
                                            <c:when test="${tenancyUpdatePickUpDTO.pickUpSchedule.dayOfWeek.getValue() == 3}">
                                                <option value="WEDNESDAY" selected>Mercredi</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="WEDNESDAY">Mercredi</option>
                                            </c:otherwise>
                                        </c:choose>

                                        <c:choose>
                                            <c:when test="${tenancyUpdatePickUpDTO.pickUpSchedule.dayOfWeek.getValue() == 4}">
                                                <option value="THURSDAY" selected>Jeudi</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="THURSDAY">Jeudi</option>
                                            </c:otherwise>
                                        </c:choose>

                                        <c:choose>
                                            <c:when test="${tenancyUpdatePickUpDTO.pickUpSchedule.dayOfWeek.getValue() == 5}">
                                                <option value="FRIDAY" selected>Vendredi</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="FRIDAY">Vendredi</option>
                                            </c:otherwise>
                                        </c:choose>

                                        <c:choose>
                                            <c:when test="${tenancyUpdatePickUpDTO.pickUpSchedule.dayOfWeek.getValue() == 6}">
                                                <option value="SATURDAY" selected>Samedi</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="SATURDAY">Samedi</option>
                                            </c:otherwise>
                                        </c:choose>

                                        <c:choose>
                                            <c:when test="${tenancyUpdatePickUpDTO.pickUpSchedule.dayOfWeek.getValue() == 7}">
                                                <option value="SUNDAY" selected>Dimanche</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="SUNDAY">Dimanche</option>
                                            </c:otherwise>
                                        </c:choose>
                                     </select>
                                </div>
                            
                                <div class="mb-3">
                                    <label for="startHour" class="form-label">Heure de Début</label>
                                    <input value="${tenancyUpdatePickUpDTO.pickUpSchedule.startHour}" name="pickUpSchedule.startHour" type="time" class="form-control" id="startHour" required="true" />
                                </div>
                                <div class="mb-3">
                                    <label for="endHour" class="form-label">Heure de Fin</label>
                                    <input value="${tenancyUpdatePickUpDTO.pickUpSchedule.endHour}" name="pickUpSchedule.endHour" type="time" class="form-control" id="endHour" required="true" />
                                </div>
                                <button type="submit" class="btn btn-700">Sauvegarder</button>
                            </form>
                        </div>
                    </div>
                </div>

                <!-- Palette de couleurs et police -->
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingSix">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseSix" aria-expanded="false" aria-controls="collapseSix">
                            <i class="bi bi-chevron-right me-2"></i> Palette de couleurs et police d'écriture
                        </button>
                    </h2>
                    <div id="collapseSix" class="accordion-collapse collapse" aria-labelledby="headingSix" data-bs-parent="#accordionExample">
                        <div class="accordion-body">
                            <form action="${pageContext.request.contextPath}/amap/${tenancyAlias}/admin/editthefontandcolor" method="post">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <p>Police de caractères pour vos titres et boutons.</p>
                                <div class="mb-3">
                                    <div id="font-choices" class="row g-3">
                                        <c:forEach items="${fontChoices}" var="font">
                                            <div class="col-12 col-md-6 col-lg-4 text-center align-self-center">
                                                <input type="radio" id="font-${font}" value="${font}" name="fontChoice"/>
                                                <label for="font-${font}" class="font-choices d-flex align-items-center justify-content-center">
                                                    <p style="font-size: 2em; font-family: ${fn:toLowerCase(font)} !important;">${font}</p>
                                                </label>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                                <p>Palette de couleurs de votre page.</p>
                                <div class="mb-3">

                                    <div id="palette-choices" class="row g-3">
                                        <c:forEach items="${colorPalettes}" var="palette" varStatus="status">
                                            <div class="col-12 col-md-6 col-lg-4 text-center">
                                                <input type="radio" id="palette-${status.index + 1}" name="colorPalette" value="${palette}" />
                                                <label for="palette-${status.index+1}" class="palette-choices">
                                                    <img src="<c:url value='/resources/img/palettes_samples/PALETTE${status.index + 1}.svg' />" class="palette-img"/>
                                                </label>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                                <button type="submit" class="btn btn-700">Sauvegarder</button>
                            </form>
                        </div>
                    </div>
                </div>

                <!-- Contenu de la page d'accueil -->
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingSeven">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseSeven" aria-expanded="false" aria-controls="collapseSeven">
                            <i class="bi bi-chevron-right me-2"></i> Contenu de la page d'accueil
                        </button>
                    </h2>
                    <div id="collapseSeven" class="accordion-collapse collapse" aria-labelledby="headingSeven" data-bs-parent="#accordionExample">
                        <div class="accordion-body">
                            <form action="${pageContext.request.contextPath}/amap/${tenancyAlias}/admin/editthecontents" method="post" enctype="multipart/form-data">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <div id="content-form-sections">
                                    <c:forEach var="content" items="${tenancyUpdateHomePageContentDTO.contents}" varStatus="status">
                                        <input type="hidden" name="contents[${status.index}].contentBlockId" value="${content.contentBlockId}" />
                                        <div class="content-form-section" data-index="${status.index}">
                                            <div class="mb-3">
                                                <label for="input-title-${content.contentBlockId}" class="form-label">Titre</label>
                                                <input class="form-control" 
                                                    id="input-title-${content.contentBlockId}" 
                                                    aria-placeholder="titre" 
                                                    placeholder="titre" 
                                                    aria-describedby="input-title" 
                                                    name="contents[${status.index}].contentTitle" 
                                                    value="${content.contentTitle}" />
                                            </div>
                                            <div class="row g-3 align-items-start">
                                                <div class="col-11 col-md-5">
                                                    <label for="input-text-${content.contentBlockId}" class="form-label">Contenu</label>
                                                    <textarea class="form-control" rows="5"
                                                            id="input-text-${content.contentBlockId}" 
                                                            aria-placeholder="contenu" 
                                                            placeholder="contenu" 
                                                            aria-describedby="input-text" 
                                                            name="contents[${status.index}].contentText">${content.contentText}</textarea>
                                                </div>
                                                <div class="col-11 col-md-5 d-flex flex-column justify-content-center">
                                                    <input class="form-control mb-3 file-input" type="file" id="input-img-${content.contentBlockId}" name="contents[${status.index}].image"
                                                    accept="image/png,image/jpeg,image/svg">
                                                    <img class="img-fluid preview-img" style="height: 100%;" src="data:${content.contentImgTypeMIME};base64,${content.contentImg}" alt="image"/>
                                                </div>
                                            </div>
                                            <hr>
                                        </div>
                                    </c:forEach>
                                </div>
                                <button type="button" id="add-more-button" class="btn btn-900">Ajouter un nouveau contenu</button>
                                <button type="submit" class="btn btn-700">Sauvegarder</button>
                            </form>
                        </div>
                    </div>
                </div>

                <!-- Valeurs -->
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingEight">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseEight" aria-expanded="false" aria-controls="collapseEight">
                            <i class="bi bi-chevron-right me-2"></i> Valeurs
                        </button>
                    </h2>
                    <div id="collapseEight" class="accordion-collapse collapse" aria-labelledby="headingEight" data-bs-parent="#accordionExample">
                        <div class="accordion-body">
                            <form action="${pageContext.request.contextPath}/amap/${tenancyAlias}/admin/editthevalues" method="post" enctype="multipart/form-data">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <c:forEach var="content" items="${tenancyUpdateValuesDTO.contents}" varStatus="status">
                                    <input type="hidden" name="contents[${status.index}].contentBlockId" value="${content.contentBlockId}" />
                                    <div class="mb-3">
                                        <label for="input-title-${content.contentBlockId}" class="form-label">Titre</label>
                                        <input class="form-control" 
                                            id="input-title-${content.contentBlockId}" 
                                            aria-placeholder="titre" 
                                            placeholder="titre" 
                                            aria-describedby="input-title" 
                                            name="contents[${status.index}].contentTitle" 
                                            value="${content.contentTitle}" />
                                    </div>
                                    <div class="row g-3 align-items-start">
                                        <div class="col-11 col-md-5">
                                            <label for="input-text-${content.contentBlockId}" class="form-label">Contenu</label>
                                            <textarea class="form-control" rows="5"
                                                    id="input-text-${content.contentBlockId}" 
                                                    aria-placeholder="contenu" 
                                                    placeholder="contenu" 
                                                    aria-describedby="input-text" 
                                                    name="contents[${status.index}].contentText">${content.contentText}</textarea>
                                        </div>
                                        <div class="col-11 col-md-5 d-flex flex-column justify-content-center">
                                            <input class="form-control mb-3 file-input" type="file" id="input-img-${content.contentBlockId}" name="contents[${status.index}].image"
                                            accept="image/png,image/jpeg,image/svg">
                                            <img class="img-fluid preview-img" style="height: 100%;" src="data:${content.contentImgTypeMIME};base64,${content.contentImg}" alt="image"/>
                                        </div>
                                    </div>
                                    <hr>
                                </c:forEach>
                                <button type="submit" class="btn btn-700">Sauvegarder</button>
                            </form>
                        </div>
                    </div>
                </div>

                <!-- Prix de la cotisation -->
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingNine">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseNine" aria-expanded="false" aria-controls="collapseNine">
                            <i class="bi bi-chevron-right me-2"></i> Montant de la cotisation
                        </button>
                    </h2>
                    <div id="collapseNine" class="accordion-collapse collapse" aria-labelledby="headingNine" data-bs-parent="#accordionExample">
                        <div class="accordion-body">
                            <p>Cela s'appliquera aux futures adhésions.</p>
                            <form action="${pageContext.request.contextPath}/amap/${tenancyAlias}/admin/editthemembershipfee" method="post">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <div class="mb-3">
                                    <label class="form-label" for="input-membershipfee">Montant de la cotisation annuelle</label>
                                    <input class="form-control" type="text" class="form-control" id="input-membershipfee" name="membershipFeePrice"
                                        value="${tenancyMembershipDTO.membershipFeePrice}">
                                </div>
                                <button type="submit" class="btn btn-700">Sauvegarder</button>
                            </form>
                        </div>
                    </div>
                </div>

                <!-- Abonnement -->
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingTen">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTen" aria-expanded="false" aria-controls="collapseTen">
                            <i class="bi bi-chevron-right me-2"></i> Abonnement
                        </button>
                    </h2>
                    <div id="collapseTen" class="accordion-collapse collapse" aria-labelledby="headingTen" data-bs-parent="#accordionExample">
                        <div class="accordion-body">
                            <p>Cela changera le montant de votre abonnement Amappli annuel.</p>
                            <p>Pour l'instant, vous avez l'abonnement ${tenancyUpdateOptionsDTO.currentSubscription}</p>
                            <form action="${pageContext.request.contextPath}/amap/${tenancyAlias}/admin/edittheoptions" method="post">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <div class="mb-3">
                                    <div class="mb-3">
                                        <ul class="row d-flex list-unstyled justify-content-around align-items-stretch">
                                            <li class="col-12 col-md-4 d-flex pricing-list">
                                                <input type="radio" name="option" value="option-1" id="option-1" />
                                                <label for="option-1" id="option-1-label" class="w-100 flex-fill d-flex flex-column">
                                                    <div id="pricing-1" class="pricing flex-fill text-center py-3 px-2 rounded-5 align-content-between bg-300">
                                                        <h2 class="h4 fw-bold">Potager</h2>
                                                        <p classe="my-2">Site opérationnel avec les fonctionnalités de base</p>
                                                        <h3 class="h5 fw-bold">Gratuit</h3>
                                                    </div>
                                                </label>
                                            </li>
                                            <li class="col-12 col-md-4 d-flex pricing-list">
                                                <input type="radio" name="option" value="option-2" id="option-2"  />
                                                <label for="option-2" id="option-2-label" class="w-100 flex-fill d-flex flex-column">
                                                    <div id="pricing-2"	class="pricing flex-fill text-center py-3 px-2 rounded-5 align-content-between bg-400">
                                                    <h2 class="h4 fw-bold">Verger</h2>
                                                    <p classe="my-2">Plus d'outils avancés pour personnaliser et enrichir votre
                                                        site</p>
                                                    <h3 class="h5 fw-bold">50 € /an</h3>
                                                    </div>
                                                </label>
                                            </li>
                                            <li class="col-12 col-md-4 d-flex pricing-list">
                                                <input type="radio" name="option" value="option-3" id="option-3" />
                                                <label for="option-3" id="option-3-label" class="w-100 flex-fill d-flex flex-column">
                                                    <div id="pricing-3" class="pricing flex-fill text-center py-3 px-2 rounded-5 align-content-between bg-500">
                                                        <h2 class="h4 fw-bold">Ferme</h2>
                                                        <p classe="my-2">Toutes les fonctionnalités pour une gestion complète et
                                                            professionnelle de votre AMAP</p>
                                                        <h3 class="h5 fw-bold">100 € /an</h3>
                                                    </div>
                                                </label>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <button type="submit" class="btn btn-700">Sauvegarder</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <script src="<c:url value='/resources/bootstrap/bootstrap.bundle.min.js' />"></script>
    <script>
        var styleMapboxLight = "${mapStyleLight}"
        var styleMapboxDark = "${mapStyleDark}"
        var latitude = "${latitude}"
        var longitude = "${longitude}"
        var fontValue = "${tenancyUpdateColorFontDTO.fontChoice}"
        var colorValue = "${tenancyUpdateColorFontDTO.colorPalette}"
    </script>
    <script src="<c:url value='/resources/js/common/mapbox/mapbox-gl.js' />" type="text/javascript"></script>
    <script src="<c:url value='/resources/js/common/mapbox/map.js' />"></script>
    <script src="<c:url value='/resources/js/amap/admin/sidebar.js' />" type="text/javascript"></script>
    <script src="<c:url value='/resources/js/common/theme-swap.js' />" type="text/javascript"></script>
    <script src="<c:url value='/resources/js/amap/admin/homepageedit.js' />" type="text/javascript"></script>
</body>
</html>