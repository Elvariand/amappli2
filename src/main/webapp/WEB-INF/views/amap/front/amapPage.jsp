<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="<c:url value='/resources/bootstrap/bootstrap.min.css' />">
<link rel="stylesheet"
	href="<c:url value='/resources/css/amap/amapPage.css' />">
	
<title>C'est quoi une Amap ?</title>
</head>

<body class="${cssStyle} light ${font}-title ${font}-button">
<div class="d-flex flex-column min-vh-100">
		<header class="fc-main bg-main">
			<jsp:include page="common/header-amap.jsp" />
			
		</header>

<div id="map"></div>  
	

 <div class="container my-5  flex-grow-1">
        <div class="row ">
            <div class="col-lg-6 col-md-12 text-center">
                <img src="<c:url value='/resources/img/amapien.png' />" alt="Schéma explicatif AMAP" class="img-fluid circle-diagram">
            </div>
            <div class="col-lg-6 col-md-12 ">
                <h1 class=" mt-3 h2 fc-500" >AMAP ? </h1>
                <div class="amap-text ">
                    <p class="fc-alt"><strong>A</strong> comme <strong>Association</strong><br>
                        En AMAP, pas d'intermédiaire commercial. C'est le seul système qui reverse 100% du montant du panier à l'agriculteur·rice.</p>
                    <p class="fc-alt"><strong>M</strong> comme <strong>Maintien</strong></p>
                    <p class="fc-alt"><strong>AP</strong> comme <strong>Agriculture Paysanne</strong></p>
                    <p class="fc-alt">L'AMAP soutient une agriculture de proximité qui concilie protection de la nature et emploi local agricole.</p>
                </div>
                <div class="text-end mt-4">
                    <img src="<c:url value='/resources/img/logoAB.png' />"  alt="LogoAB" class="ab-logo">
                </div>
            </div>
        </div>
    </div>
   <div class="container my-0 ">
    <h2 class="text-center mt-1 h2 fc-500">Histoire des AMAP</h2> 
    <div class="row g-4">
        <!-- Texte principal -->
        <div class="col-lg-6 col-md-12 d-flex align-items-center secondtext"> 
            <div>
                <p class="fc-alt">
                    L'historique des AMAP est très bien raconté sur le 
                    <a href="https://www.reseau-amap.org/historique.php" target="_blank">site National du réseau AMAP France</a>.
                </p>
                <p class="fc-alt">
                    C'est donc du Japon que vient cette démarche collaborative entre un ou plusieurs producteurs et des consommateurs 
                    qui établissent un partenariat sous forme de contrat. Ensemble, ils définissent la diversité et la quantité de denrées à produire pour la saison. 
                    Ces denrées peuvent être aussi bien des fruits, des légumes, des œufs, du fromage, de la viande...
                </p>
                <p class="fc-alt">
                    La diversité est très importante car elle permet aux partenaires de l'AMAP de consommer une grande variété d'aliments,
                    d'étendre la durée de la saison, et de limiter les risques dus aux aléas climatiques et aux éventuels problèmes sanitaires.
                </p>
            </div>
        </div>

        <!-- Image explicative avec le texte intégré -->
        <div class="col-lg-6 col-md-12 d-flex align-items-center justify-content-center">
            <img src="<c:url value='/resources/img/carotte.png' />" alt="Ceci n'est pas une carotte" class="img-fluid rounded">
        </div>
    </div>
</div>



    
      <div class="container my-5">
        <div class="row">
            <!-- Carte des AMAP -->
            <div class="col-12 text-center">
                
                <img src="<c:url value='/resources/img/amap-carte.png' />" alt="Carte des réseaux AMAP en France" class="img-fluid rounded">
            </div>
        </div>

        <div class="row mt-4">
            
            <div class="col-12 text-center">
                <a href="https://amap-idf.org/medias/files/2charte_des_amap/charte_des_amap_mars_20142.pdf" class="btn btn-100 rounded-pill btn-lg">
                    Découvrir la charte des AMAP
                </a>
            </div>
        </div>
    </div>
    
    


<footer class="fc-main bg-main">
			<jsp:include page="common/footer-amap.jsp" />
		</footer>
		</div>
		
		
	<script src="<c:url value='/resources/bootstrap/bootstrap.bundle.min.js' />"></script>

	<script>
		var styleMapboxLight = "${mapStyleLight}";
		var styleMapboxDark = "${mapStyleDark}";
		var latitude = "${latitude}";
		var longitude = "${longitude}";
	</script>
	<script src="<c:url value='/resources/js/common/mapbox/mapbox-gl.js' />" type="text/javascript"></script>
    <script src="<c:url value='/resources/js/common/mapbox/map.js' />" type="text/javascript"></script>  
	<script src="<c:url value='/resources/js/common/theme-swap.js' />"></script> 
</body>
</html>