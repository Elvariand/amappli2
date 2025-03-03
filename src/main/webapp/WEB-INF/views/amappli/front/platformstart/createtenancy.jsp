<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Création de votre espace AMAP</title>
<link href="<c:url value='/resources/bootstrap/bootstrap.min.css'/>" rel="stylesheet">
<link href="<c:url value='/resources/css/amappli/tenancycreation.css'/>" rel="stylesheet">
<!--  Mapbox CSS 
<link href="https://api.mapbox.com/mapbox-gl-js/v2.15.0/mapbox-gl.css" rel="stylesheet" /> -->
</head>
<body class="theme-1 light bg-main fc-main">

<section class="container justify-content-center">
    <!-- Conteneur pour la carte -->
    <div id="map"></div>

    <div class="fc-main d-flex flex-column p-15 fc-main w-100 h-100">

        <div class="title">
            <h1 class="pb-3">Faisons connaissance!</h1>
            <div class="progress" role="progressbar" aria-label="Basic example" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100">
                <div class="progress-bar" style="width: 0%" id="the-progress-bar"></div>
            </div>
        </div>
        <hr>

        <c:if test="${not empty org.springframework.validation.BindingResult.newTenancyDTO}">
    <c:forEach var="error" items="${org.springframework.validation.BindingResult.newTenancyDTO.allErrors}">
        <p class="text-danger">${error.defaultMessage}</p>
    </c:forEach>
</c:if>

        <div class="form-part px-0 my-4">
            <p class="mb-3">Pour vous aider à créer le site de votre AMAP, nous allons vous poser une série de questions pour mieux vous connaître. Cela devrait prendre moins de 10 minutes. Veuillez préparer les informations concernant votre AMAP.</p>
            <h3>Etes-vous prêts ?</h3>
        </div>

        <form:form action="${pageContext.request.contextPath}/amappli/start/creation" method="post" modelAttribute="newTenancyDTO" enctype="multipart/form-data">
            
            <div class="form-part px-0 my-4">

                <div class="mb-3">
                    <form:label for="input-tenancy-name" class="form-label" path="tenancyName">
                        <h3>Quel est le nom de votre AMAP?</h3>
                    </form:label>
                    <form:input type="text" class="form-control" id="input-tenancy-name" aria-describedby="input-tenancy-name" path="tenancyName" required="true" aria-required="true"/>
                    <form:errors path="tenancyName" class="invalid-feedback d-block" />
                    <div class="invalid-feedback d-block" id="input-tenancy-name-error"></div>
                </div>

            </div>

            <div class="form-part px-0 my-4">

                <div class="mb-3">
                    <form:label for="input-tenancy-alias" class="form-label" path="tenancyAlias">
                        <h3>Quelle URL souhaitez-vous pour votre AMAP?</h3>
                    </form:label>
                    <form:input type="text" class="form-control" id="input-tenancy-alias" aria-describedby="input-tenancy-alias" path="tenancyAlias" required="true" aria-required="true"/>
                    <form:errors path="tenancyAlias" class="invalid-feedback d-block" />
                    <c:if test="${not empty aliasError}">
                        <div class="invalid-feedback d-block">${aliasError}</div>
                    </c:if>
                </div>

                <div class="mb-3">
                    Votre AMAP sera accessible à l'adresse <i>www.amappli.fr/<span id="amap-url-example">...</span></i>
                </div>

            </div>

            <div class="form-part px-0 my-4">

                <div class="mb-3">
                    <form:label for="input-tenancy-slogan" class="form-label display-6" path="tenancyName">
                        <h3>Quel est le slogan de votre AMAP?</h3>
                    </form:label>
                    <form:input type="text" class="form-control" id="input-tenancy-slogan" aria-describedby="input-tenancy-slogan" path="tenancySlogan" required="true" aria-required="true"/>
                    <form:errors path="tenancySlogan" class="invalid-feedback d-block" />
                
                </div>

            </div>

            <div class="form-part px-0 my-4">

                <div class="mb-3">
                    <form:label for="input-membership-price" class="form-label display-6" path="membershipFeePrice">
                        <h3>Combien coûte la cotisation annuelle dans votre AMAP ?</h3>
                    </form:label>
                    <form:input type="number" class="form-control" id="input-membership-price" aria-describedby="input-tenancy-name" path="membershipFeePrice" required="true" aria-required="true"/>
                    <form:errors path="membershipFeePrice" class="invalid-feedback d-block" />
                </div>

            </div>

            <div class="form-part px-0 my-4">

                <div class="mb-3">
                    <h3>Quel est le créneau hebdomadaire de collecte de votre AMAP?</h3>

                    <div class="row justify-content-center">

                        <div class="col-12 col-md-6 col-lg-4 mb-3">
                            <label for="dayOfWeek" class="form-label">Jour de la Semaine</label>
                            <form:select class="from-control form-select" path="pickUpSchedule.dayOfWeek" id="dayOfWeek">
                                <form:option value="">Sélectionnez un Jour</form:option>
                                <form:option value="MONDAY">Lundi</form:option>
                                <form:option value="TUESDAY">Mardi</form:option>
                                <form:option value="WEDNESDAY">Mercredi</form:option>
                                <form:option value="THURSDAY">Jeudi</form:option>
                                <form:option value="FRIDAY">Vendredi</form:option>
                                <form:option value="SATURDAY">Samedi</form:option>
                                <form:option value="SUNDAY">Dimanche</form:option>
                            </form:select>
                            <form:errors path="pickUpSchedule.dayOfWeek" class="invalid-feedback d-block"/>
                        </div>
                    
                        <div class="col-12 col-md-6 col-lg-4 mb-3">
                            <label for="startHour" class="form-label">Heure de Début</label>
                            <form:input path="pickUpSchedule.startHour" type="time" class="form-control" id="startHour" required="true" />
                            <form:errors path="pickUpSchedule.startHour" class="invalid-feedback d-block"/>
                        </div>
                        <div class="col-12 col-md-6 col-lg-4 mb-3">
                            <label for="endHour" class="form-label">Heure de Fin</label>
                            <form:input path="pickUpSchedule.endHour" type="time" class="form-control" id="endHour" required="true" />
                            <form:errors path="pickUpSchedule.endHour" class="invalid-feedback d-block"/>
                        </div>
                    </div>
                </div>
            </div>

            <div class="form-part px-0 my-4">

                <h3>Où se situe votre AMAP?</h3>

                <div class="row">
                    <div class="col-12 col-md-6 mb-3">
                        <form:label for="address-line1" class="form-label" path="address.line1">Complément d'adresse</form:label>
                        <form:input type="text" class="form-control" id="address-line1" path="address.line1"/>
                        <form:errors path="address.line1" class="invalid-feedback d-block" />
                    </div>

                    <div class="col-12 col-md-6 mb-3">
                        <form:label for="address-line2" class="form-label" path="address.line2">Numéro et Rue</form:label>
                        <form:input type="text" class="form-control" id="address-line2" path="address.line2" required="true" aria-required="true"/>
                        <form:errors path="address.line2" class="invalid-feedback d-block" />
                    </div>
                </div>
                <div class="row">
                    <div class="col-12 col-md-6 mb-3">
                        <form:label for="address-postcode" class="form-label" path="address.postCode">Code Postal</form:label>
                        <form:input type="text" class="form-control" id="address-postcode" path="address.postCode" required="true" aria-required="true"/>
                        <form:errors path="address.postCode" class="invalid-feedback d-block" />
                    </div>

                    <div class="col-12 col-md-6 mb-3">
                        <form:label for="address-city" class="form-label" path="address.city">Ville</form:label>
                        <form:input type="text" class="form-control" id="address-city" path="address.city" required="true" aria-required="true"/>
                        <form:errors path="address.city" class="invalid-feedback d-block" />
                    </div>
                </div>
            </div>

            <div class="form-part px-0 my-4">

                <h3>Avez-vous un logo?</h3>
                <form:input type="file" class="form-control" id="input-logo" aria-describedby="input-logo" path="logo" accept="image/png,image/jpeg,image/svg"/>

            </div>

            <div class="form-part px-0 my-4">

                <h3>Quelles sont vos valeurs?</h3>
                <p>Elles apparaîtront sur votre page d'accueil.</p>

                <div id="value-form" class="row g-3 justify-content-evenly">
                <c:forEach items="${newTenancyDTO.values}" var="valueDTO" varStatus="status">
                    <div class="col-12 col-md-3">
                        <div class="mb-3 d-flex flex-column">
                            <form:input class="form-control mb-3" id="input-value-name-${status.index}" aria-placeholder="valeur" placeholder="valeur" aria-describedby="input-value-name" path="values[${status.index}].name" />
                            <form:textarea class="form-control mb-3" id="input-value-description-${status.index}" aria-placeholder="description" placeholder="description" aria-describedby="input-value-description" path="values[${status.index}].description" />
                            <form:input type="file" class="form-control mb-3" id="input-value-file-${status.index}" aria-placeholder="une image pour illustrer?" placeholder="une image pour illustrer?" aria-describedby="input-value-file" path="values[${status.index}].file" accept="image/png,image/jpeg,image/svg"/>
                        </div>
                    </div>
                </c:forEach>
                </div>               
            </div>

            <div class="form-part px-0 my-4">

                <h3>Présentez-vous à vos adhérents!</h3>
                <p>Vous pourrez ajouter plus de contenu sur votre page d'accueil par la suite.</p>

                <div class="mb-3 d-flex flex-column">

                    <form:input class="form-control mb-3" id="input-hp-title" aria-placeholder="titre" placeholder="titre" aria-describedby="input-hp-title" path="firstHomePageTitle" />
                    <form:textarea class="form-control mb-3" id="input-hp-text" aria-placeholder="contenu" placeholder="contenu" aria-describedby="input-hp-text" path="firstHomePageText" />
                    <form:input type="file" class="form-control mb-3" id="input-hp-image" aria-describedby="input-hp-image" path="firstHomePagePic" accept="image/png,image/jpeg,image/svg"/>
                
                </div>

            </div>

            <div class="form-part px-0 my-4">

                <h3>Choississez une police pour vos titres et boutons.</h3>

                <div class="mb-3">
                    <div id="font-choices" class="row g-3">
                        <c:forEach items="${fontChoices}" var="font">
                            <div class="col-12 col-md-6 col-lg-4 text-center align-self-center">
                                <input type="radio" id="font-${font}" value="${font}" name="fontChoice"/>
                                <label for="font-${font}" class="font-choices d-flex align-items-center justify-content-center">
                                    <h1 class="${fn:toLowerCase(font)}">${font}</h1>
                                </label>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                
            </div>

            <div class="form-part px-0 my-4">

                <h3>Choississez une palette de couleurs.</h3>

                <div class="mb-3">

                    <div id="palette-choices" class="row g-3">
                        <c:forEach items="${colorPalettes}" var="palette" varStatus="status">
                            <div class="col-2 col-md-6 col-lg-2 text-center">
                                <input type="radio" id="palette-${status.index + 1}" name="colorPalette" value="${palette}" />
                                <label for="palette-${status.index+1}" class="palette-choices">
                                    <img src="<c:url value='/resources/img/palettes_samples/PALETTE${status.index + 1}.svg' />" class="palette-img"/>
                                </label>
                            </div>
                        </c:forEach>
                    </div>

                </div>
                
            </div>

            <div class="form-part px-0 my-4">

                <h3>Des paniers oui, mais quoi d'autre ?</h3>

                <div class="mb-3">
                    <ul class="list-unstyled">
                        <li class="d-flex align-items-center mb-3">
                            <span class="me-auto p-2">Est-ce que vous vendez des produits à l'unité en plus de vos paniers?</span>
                            <div class="d-flex">
                                <input type="radio" value="true" name="question-1" class="btn-check" id="question-1-true" checked/>
                                <label class="btn btn-quizz" for="question-1-true">Oui</label>
                                <input type="radio" value="false" name="question-1" class="btn-check" id="question-1-false"/>
                                <label class="btn btn-quizz" for="question-1-false">Non</label>
                            </div>
                        </li>
                        <li class="d-flex align-items-center mb-3">
                            <span class="me-auto p-2">Organisez-vous des ateliers que vous souhaiteriez mettre à disposition de vos adhérents sur ce site?</span>
                            <div class="d-flex">
                                <input type="radio" value="true" name="question-3" class="btn-check" id="question-3-true" checked/>
                                <label class="btn btn-quizz" for="question-3-true">Oui</label>
                                <input type="radio" value="false" name="question-3" class="btn-check" id="question-3-false"/>
                                <label class="btn btn-quizz" for="question-3-false">Non</label> 
                            </div>
                        </li>
                        <li class="d-flex align-items-center mb-3">
                            <span class="me-auto p-2">Voulez-vous activer le paiement en ligne ?</span>
                            <div class="d-flex">
                                <input type="radio" value="true" name="question-4" class="btn-check" id="question-4-true" checked/>
                                <label class="btn btn-quizz" for="question-4-true">Oui</label>
                                <input type="radio" value="false" name="question-4" class="btn-check" id="question-4-false"/>
                                <label class="btn btn-quizz" for="question-4-false">Non</label>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>

            <div class="form-part px-0 my-4">

                <h3>Des paniers oui, mais quoi d'autre ?</h3>

                <div class="mb-3">
                    <ul class="list-unstyled">
                        <li class="d-flex align-items-center mb-3">
                            <span class="me-auto p-2">Souhaitez-vous que vos producteurs puissent ajouter eux-même leurs produits sur le site?</span>
                            <div class="d-flex">
                                <input type="radio" value="true" name="question-5" class="btn-check" id="question-5-true" checked/>
                                <label class="btn btn-quizz" for="question-5-true">Oui</label>
                                <input type="radio" value="false" name="question-5" class="btn-check" id="question-5-false"/>
                                <label class="btn btn-quizz" for="question-5-false">Non</label>
                            </div>
                        </li>
                        <li class="d-flex align-items-center mb-3">
                            <span class="me-auto p-2">Est-ce que vous souhaitez donner des accès personnalisés à vos bénévoles ? Par exemple leur permettre d’accéder à la liste des commandes de vos adhérents mais sans leur donner accès à leurs informations personnelles.</span>
                            <div class="d-flex">
                                <input type="radio" value="true" name="question-6" class="btn-check" id="question-6-true" checked/>
                                <label class="btn btn-quizz" for="question-6-true">Oui</label>
                                <input type="radio" value="false" name="question-6" class="btn-check" id="question-6-false"/>
                                <label class="btn btn-quizz" for="question-6-false">Non</label> 
                            </div>
                        </li>
                        <li class="d-flex align-items-center">
                            <span class="me-auto p-2">Souhaitez-vous améliorer le quotidien de vos adhérents en leur proposant la possibilité de choisir entre un thème dark et un thème light?</span>
                            <div class="d-flex">
                                <input type="radio" value="true" name="question-8" id="question-8-true" class="btn-check" checked/>
                                <label class="btn btn-quizz" for="question-8-true">Oui</label>
                                <input type="radio" value="false" name="question-8" id="question-8-false" class="btn-check"/>
                                <label class="btn btn-quizz" for="question-8-false">Non</label> 
                            </div>
                        </li>
                    </ul>
                </div>
            </div>

            <div class="form-part px-0 my-4">

                <h3 class="mb-3">Choississez maintenant votre abonnement à Amappli:</h3>
                <br>
                <br>
                <br>
                <br>
                <div class="mb-3">
                    <ul class="row d-flex list-unstyled justify-content-around align-items-stretch">
                        <li class="col-12 col-md-4 d-flex pricing-list">
                            <form:radiobutton path="option" value="option-1" id="option-1" />
                            <label for="option-1" id="option-1-label" class="w-100 flex-fill d-flex flex-column">
                                <div id="pricing-1" class="pricing flex-fill text-center py-3 px-2 rounded-5 align-content-between bg-300 fc-900">
                                    <h2 class="h4 fw-bold">Potager</h2>
                                    <p classe="my-2">Site opérationnel avec les fonctionnalités de base</p>
                                    <h3 class="h5 fw-bold">Gratuit</h3>
                                </div>
                            </label>
                        </li>
                        <li class="col-12 col-md-4 d-flex pricing-list">
                            <form:radiobutton path="option" value="option-2" id="option-2"  />
                            <label for="option-2" id="option-2-label" class="w-100 flex-fill d-flex flex-column">
                                <div id="pricing-2"	class="pricing flex-fill text-center py-3 px-2 rounded-5 align-content-between bg-400 fc-900">
                                <h2 class="h4 fw-bold">Verger</h2>
                                <p classe="my-2">Plus d'outils avancés pour personnaliser et enrichir votre
                                    site</p>
                                <h3 class="h5 fw-bold">50 € /an</h3>
                                </div>
                            </label>
                        </li>
                        <li class="col-12 col-md-4 d-flex pricing-list">
                            <form:radiobutton path="option" value="option-3" id="option-3" />
                            <label for="option-3" id="option-3-label" class="w-100 flex-fill d-flex flex-column">
                                <div id="pricing-3" class="pricing flex-fill text-center py-3 px-2 rounded-5 align-content-between bg-500 fc-900">
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

            <div id="nav-button" class="container-fluid py-3 px-0 d-flex ms-auto">
                <button type="button" class="me-auto btn btn-green rounded-pill" id="go-back-button">
                    Retour
                </button>
                <div class="d-flex">
                    <button type="button" class="btn btn-orange rounded-pill" id="continue-button">
                        Continuer
                    </button>
                    <button type="submit" class="btn btn-submit" id="submit-button">Valider</button>
                </div>
            </div>
        </form:form>

    </div>
    </section>
    <script>
        const errorpresent = "${errorspresent}";
	</script>
    <script src="<c:url value='/resources/bootstrap/bootstrap.bundle.min.js'/>" type="text/javascript"></script>
    <script src="<c:url value='/resources/js/amappli/tenancycreation.js'/>" type="text/javascript"></script>
    <script	src="<c:url value='/resources/js/common/mapbox/mapbox-gl.js' />" type="text/javascript"></script>
	<script	src="<c:url value='/resources/js/common/mapbox/map.js' />" type="text/javascript"></script>
	<script	src="<c:url value='/resources/js/common/headerless-theme-swap.js' />" type="text/javascript"></script>
	<script	src="<c:url value='/resources/js/common/autofill.js' />" type="text/javascript"></script>
</body>
</html>