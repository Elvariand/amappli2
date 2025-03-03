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

<title>Mentions Légales</title>

</head>

<body class="theme-1 dark bg-main fc-main">
<div class="d-flex flex-column min-vh-100">

   <header class="fc-main bg-main">
		<jsp:include page="../../common/header.jsp" />
	</header>

   <div id="map"></div>   
    
<section class="container py-5">
  <div class="row justify-content-center">
    <!-- Carte Potager -->
    <div class="col-8 mb-4 bg-300 fc-900 rounded-5 px-5">
          <h1 class="fw-bold text-center mb-5 mt-3">Mention de droits réservés</h1>
	<p class="text-justify fs-6 pb-3">Conformément à la Loi « Informatique et Libertés » n° 78-17 du 6 janvier 1978, les informations vous concernant sont destinées à Amappli, responsable du traitement. Vous disposez d'un droit d'accès, de rectification et de suppression des données qui vous concernent. Vous pouvez exercer ce droit en adressant un e-mail à l'adresse électronique suivante : contact@amappli.fr
Les informations fournies seront exploitées de manière anonyme et non nominatives à des fins d'analyse et de statistique
En vous connectant à ce site édité et mis en ligne par Amappli, vous accédez à un contenu protégé par la loi, notamment par les dispositions du Code de la propriété intellectuelle.
Les marques de l'éditeur du site Amappli et de ses partenaires, ainsi que les logos figurant sur le site sont des marques déposées. Le téléchargement de tout ou partie des textes, images, sons, photographies, données, marques et tout autre élément contenu est autorisé à des fins strictement privées, dans le cadre scolaire ou pour un usage non commercial.
Amappli n'autorise qu'un usage strictement personnel des données, informations ou contenus auxquels vous accédez, limité à un enregistrement temporaire sur votre ordinateur aux fins d'affichage sur un seul écran ainsi que la reproduction, en un unique exemplaire, pour copie de sauvegarde ou impression sur papier.
Toute autre utilisation est soumise à notre autorisation expresse préalable. En poursuivant votre visite de notre site vous acceptez de respecter les restrictions ci-dessus.
Le contenu généré par des tiers (utilisateurs, partenaires) est sous leur entière responsabilité.
Amappli se réserve le droit de modifier, à tout moment et sans préavis, le contenu de ce site. En outre, Amappli décline toute responsabilité en cas de retard, d'erreur ou d'omission quant au contenu des présentes pages de même qu'en cas d'interruption ou de non-disponibilité du service.
La création de liens hypertextes vers le site de Amappli ainsi que les liens hypertextes établis en direction d'autres sites à partir du site de Amappli n'engagent pas la responsabilité de Amappli.
<br><br>© Droits de reproduction et de diffusion réservés.</p>
        </div>
      </div>
</section>
<section class="container py-5">
  <div class="row justify-content-center">
    <!-- Carte Potager -->
    <div class="col-8 mb-4 bg-300 fc-900 rounded-5 px-5">
          <h1 class="fw-bold text-center mb-5 mt-3">Mentions légales</h1>
          <h3 class="fw-bold mb-3 mt-3">Amappli</h3>
          <p class="fs-6">
				Siège social : 5 Chemin de l'amour, 12543, Villecity<br/>
				SAS au capital de 584 449,73 €<br/>
				RCS Bordeaux 510 918 683<br/>
				Représentant Légal : Pauline Romarin<br/></p>
          <h3 class="fw-bold mb-3 mt-3">Hébergeur</h3>
          <p class="fs-6">
				Microsoft Corporation<br/>
				One Microsoft Way<br/>
				Redmond, WA 98052-6399<br/>
				USA<br/></p>
	<p class="text-justify fs-6">Le site <a href="<c:url value='/amappli/home'/>">www.amappli.fr</a> est hébergé par Microsoft Azure. Les données de l'application sont hébergées sur l’offre Cloud de Microsoft (Azure).
Il est géographiquement localisé dans les centre d’hébergement de Microsoft en Europe de l’Ouest situé au Pays-Bas, et en Europe du Nord, situé en Irlande.
Pour plus d’informations, veuillez vous référer à : <a href="https://www.microsoft.com/en-us/trust-center/privacy" >https://www.microsoft.com/en-us/trust-center/privacy</a></p>
<p class="fs-6 fst-italic">Site déclaré à la CNIL - Commission Nationale Informatique et Libertés</p>
        </div>
      </div>
</section>
    
   <footer class="container-fluid fc-main bg-main">
		<jsp:include page="../../common/footer.jsp" />
	</footer>
</div>
	<script	src="<c:url value='/resources/bootstrap/bootstrap.bundle.min.js' />"></script>
	<script	src="<c:url value='/resources/js/amappli/loading-when-visible.js' />"></script>
	<script	src="<c:url value='/resources/js/common/theme-swap.js' />"></script>
	<script src="<c:url value='/resources/js/common/mapbox/mapbox-gl.js' />" type="text/javascript"></script>
	<script	src="<c:url value='/resources/js/common/mapbox/map.js' />"></script>  
</body>
</html>