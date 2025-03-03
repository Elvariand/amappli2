<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="<c:url value='/resources/bootstrap/bootstrap.min.css' />">
<link rel="stylesheet"
	href="<c:url value='/resources/css/amap/homePage.css' />">

<title>Page d'accueil - ${tenancyName}</title>

</head>

<body class="${cssStyle} light ${font}-title ${font}-button">
	<div class="d-flex flex-column min-vh-100">
		<header class="fc-main bg-main">
			<jsp:include page="common/header-amap.jsp" />

		</header>

		<div id="map"></div>


		<main class="hero p-5">
			<div
				class="d-flex flex-column justify-content-center align-items-center py-4 py-md-5">
				<h1 class="hero-text h1 fc-main text-center">Bienvenue sur le
					site de ${tenancyName}</h1>
				<h2 class="hero-text h2 fc-main text-center">${tenancySlogan}</h2>
			</div>
		</main>
		
		

		<!-- Affichage du block de présentation -->
		<c:forEach var="presentationBlock" items="${presentationBlocks}" varStatus="status">
			<div id="presentation-section" class="presentation-wrapper">
				<div class="presentation-section">
					<c:if test="${status.index == 0}"> 
						<h2 class="h3 fw-bold fc-300">Qui sommes-nous ?</h2>
					</c:if>
					<div class="presentation-block fc-main">
					<div class="presentation-image-container col-12 col-lg-6 order-1 order-lg-2 text-center ">
							<c:if test="${not empty presentationBlock.contentImg}">
								<img
									src="data:${presentationBlock.contentImgTypeMIME};base64,${presentationBlock.contentImg}"
									alt="${presentationBlock.contentTitle}"
									class="presentation-image w-100" />
							</c:if>
						</div>
						<div class="presentation-text col-12 col-lg-6 order-2 order-lg-1  ">
							<h3 class="h4 fw-bold fc-300">${presentationBlock.contentTitle}</h3>
							<p class="text-justify">${presentationBlock.contentText}</p>
						</div>
						
					</div>
				</div>
			</div>
		</c:forEach>


		<!-- Affichage des ContentBlock avec isValue == true -->
<c:if test="${not empty valueBlocks}">
    <div class="values-section">
        <h2 class="h3 fw-bold fc-300">Nos Valeurs</h2>
        <div class="row justify-content-evenly">
            <c:forEach items="${valueBlocks}" var="block">
                <div class="col-12 col-md-6 col-lg-3 shadow-sm bg-100 rounded-3 p-3 mb-3">
                    <c:if test="${not empty block.contentImg}">
                        <!-- Affichage de l'image encodée en Base64 pour chaque ContentBlock -->
                        <img class="object-fit-contain col-12"
                            src="data:${block.contentImgTypeMIME};base64,${block.contentImg}"
                            alt="${block.contentTitle}" class="value-image" />
                    </c:if>
                    <h3 class="h4 fw-bold fc-300 wrap">${block.contentTitle}</h3>

                    <p class="text-justify">${block.contentText}</p>
                </div>
            </c:forEach>
        </div>
    </div>
</c:if>


		<footer class="fc-main bg-main">
			<jsp:include page="common/footer-amap.jsp" />
		</footer>



		<script
			src="<c:url value='/resources/bootstrap/bootstrap.bundle.min.js' />"></script>

		<script>
			var styleMapboxLight = "${mapStyleLight}";
			var styleMapboxDark = "${mapStyleDark}";
			var latitude = "${latitude}";
			var longitude = "${longitude}";
		</script>
		<script
			src="<c:url value='/resources/js/common/mapbox/mapbox-gl.js' />"
			type="text/javascript"></script>
		<script src="<c:url value='/resources/js/common/mapbox/map.js' />"
			type="text/javascript"></script>
		<script src="<c:url value='/resources/js/common/theme-swap.js' />"
			type="text/javascript"></script>
</body>
</html>