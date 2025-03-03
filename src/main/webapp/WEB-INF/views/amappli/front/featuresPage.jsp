<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="<c:url value='/resources/bootstrap/bootstrap.min.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/amappli/featuresPage.css' />">
<link href="<c:url value='/resources/bootstrap/bootstrap-icons.min.css' />" rel="stylesheet">

<title>Fonctionnalités</title>

</head>

<body class="theme-1 dark bg-main fc-main">
<div class="d-flex flex-column min-vh-100">

   <header class="fc-main bg-main">
		<jsp:include page="../common/header.jsp" />
	</header>

   <div id="map"></div>   
    
    
     <section class="features-section container flex-grow-1">
    <div class="row justify-content-center">
      <div class="col-md-8 col-lg-6 text-center">
        <h2 class="display-5 fc-300">Fonctionnalités</h2>
        <hr class="bg-300">
        <p class="lead fc-300">Comparez nos offres pour trouver la solution qui<br> correspond à votre amap.</p>
      </div>
    </div>
  </section>
    
    <section class="pricing-section container py-5 ">
  <div class="row justify-content-center">
    <!-- Carte Potager -->
    <div class="col-lg-4 col-md-6 mb-4 ">
      <div class="card shadow-sm border-0 card-potager bg-300">
        <div class="card-body fc-900">
          <h5 class="h4 fw-bold">Potager</h5>
          <hr>
          <p class="card-text">Un site opérationnel avec peu de fonctionnalités</p><br>
          <h2 class="h3 fw-bold">0€/an</h2>
          <p class="fc-600">Ou 0€/mois</p>
          <ul class="list-unstyled text-start">
            <li><i class="bi bi-check-circle-fill fc-900 me-2"></i> <span>Un site vitrine avec les informations de votre amap</span></li>
            <li><i class="bi bi-check-circle-fill fc-900 me-2"></i> <span> La possibilité d’ajouter votre logo, et le choix entre deux thèmes de couleurs et deux typographies</span></li>
            <li><i class="bi bi-check-circle-fill fc-900 me-2"></i> Une boutique pour que vos adhérents s’abonnent aux paniers</li>
            <li><i class="bi bi-check-circle-fill fc-900 me-2"></i>  La gestion des contrats et des stocks avec un paiement hors-ligne</li>
            <li><i class="bi bi-check-circle-fill fc-900 me-2"></i>  La gestion des commandes avec factures pour vos fournisseurs</li>
          </ul>
          <a href="<c:url value='/amappli/start/signup'/>" class="btn btn-100 rounded-pill px-4">Créer mon site</a>
        </div>
      </div>
    </div>

    <!-- Carte Verger -->
    <div class="col-lg-4 col-md-6 mb-4 ">
      <div class="card shadow-sm border-0 card-verger bg-400">
        <div class="card-body fc-900">
          <h5 class="h4 fw-bold fc-900">Verger</h5>
          <hr>
          <p class="card-text fc-900">Plus d’outils avancés pour personnaliser et enrichir votre site.</p>
          <h2 class="h3 fw-bold fc-900">50€/an</h2>
          <p class="fc-600">Ou 5€/mois</p>
          <ul class="list-unstyled text-start">
            <li><i class="bi bi-check-circle-fill fc-900 me-2"></i>  Tout ce qu’il y a dans le potager plus :</li>
            <li><i class="bi bi-check-circle-fill fc-900 me-2"></i> Le paiement en ligne</li>
            <li><i class="bi bi-check-circle-fill fc-900 me-2"></i>  La possibilité d’ajouter des produits à l’unité et des ateliers dans la boutique</li>
            <li><i class="bi bi-check-circle-fill fc-900 me-2"></i>  Des rôles personnalisés pour vos bénévoles et fournisseurs</li>
            <li><i class="bi bi-check-circle-fill fc-900 me-2"></i>  Plus de choix de palettes de couleurs et de typographies</li>
            <li><i class="bi bi-check-circle-fill fc-900 me-2"></i>  Un choix parmi plusieurs modèles de site pour personnaliser encore plus !</li>
            <li><i class="bi bi-check-circle-fill fc-900 me-2"></i>  Choix d’un logo dans une banque de logos</li>
          </ul>
          <a href="<c:url value='/amappli/start/signup'/>" class="btn btn-100 rounded-pill px-4">Créer mon site</a>
        </div>
      </div>
    </div>

    <!-- Carte Ferme -->
    <div class="col-lg-4 col-md-6 mb-4  ">
      <div class="card shadow-sm border-0 card-ferme bg-500">
        <div class="card-body fc-900">
          <h5 class="h4 fw-bold">Ferme</h5>
          <hr>
          <p class="card-text">Toutes les fonctionnalités pour une gestion complète et professionnelle de votre AMAP</p>
          <h2 class="h3 fw-bold">100€/an</h2>
          <p class="fc-600">Ou 10€/mois</p>
          <ul class="list-unstyled text-start">
            <li><i class="bi bi-check-circle-fill fc-900 me-2"></i>  Tout ce qu’il y a dans le verger plus :</li>
            <li><i class="bi bi-check-circle-fill fc-900 me-2"></i>  Une visibilité sur vos statistiques</li>
            <li><i class="bi bi-check-circle-fill fc-900 me-2"></i>  Un système de messagerie pour communiquer avec vos adhérents</li>
            <li><i class="bi bi-check-circle-fill fc-900 me-2"></i>  Un système de mailing automatique en cas d’oubli de commande par un adhérent</li>
            <li><i class="bi bi-check-circle-fill fc-900 me-2"></i>  La possibilité de créer des promotions et des lots, et de rajouter des produits en favoris</li>
            <li><i class="bi bi-check-circle-fill fc-900 me-2"></i>  La personnalisation du montant de la cotisation en fonction des adhérents</li>
            <li><i class="bi bi-check-circle-fill fc-900 me-2"></i>  Un système de newsletter</li>
            <li><i class="bi bi-check-circle-fill fc-900 me-2"></i>  Un accompagnement SEO personnalisé</li>
          </ul>
          <a href="<c:url value='/amappli/start/signup'/>" class="btn btn-100 rounded-pill px-4">Créer mon site</a>
        </div>
      </div>
    </div>
  </div>
</section>
    
   <footer class="container-fluid fc-main bg-main">
		<jsp:include page="../common/footer.jsp" />
	</footer>
</div>
	<script	src="<c:url value='/resources/bootstrap/bootstrap.bundle.min.js' />"></script>
	<script	src="<c:url value='/resources/js/amappli/loading-when-visible.js' />"></script>
	<script src="<c:url value='/resources/js/common/mapbox/mapbox-gl.js' />" type="text/javascript"></script>
	<script	src="<c:url value='/resources/js/common/mapbox/map.js' />"></script>  
	<script	src="<c:url value='/resources/js/common/theme-swap.js' />"></script>
</body>
</html>