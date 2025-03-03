<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
.navbar {
	position: sticky; /* Rendre sticky */
	top: 0; /* Fixer au sommet lors du défilement */
	z-index: 1020; /* S'assurer qu'elle reste devant les autres éléments */
	overflow-y: auto;
	/* Pour gérer le contenu s'il dépasse la hauteur de la fenêtre */
	max-height: 100vh;
	/* Limite la hauteur pour éviter de dépasser l'écran */
}

.border-top-rounded {
	border-radius: 5px 5px 0 0;
}
</style>
<nav class="navbar h-50 py-5 my-5 ms-5 ps-5 w-auto">
	<div
		class="bg-100 border border-1 border-500 border-top-rounded d-flex flex-column justify-content-center">
		<div id="sidebar" class="flex-grow-1">
			<ul class="nav flex-column text-start">
				<!-- Paniers maraîchers -->
				<li><a
					href="<c:url value='/amap/${tenancyAlias}/shop/contracts'/>"
					class="ps-2 pe-5 pt-3 d-flex align-items-left border-bottom border-top-rounded text-decoration-none ${currentMainMenu.equals('contracts') ? 'active bg-500 fc-100' : ' fc-500 text-decoration-none'}">
						<i class="bi bi-basket me-2"></i>
						<h4 class="fw-bold">Paniers</h4>
				</a>
					<ul class="list-unstyled ps-2">
						<li class="d-flex justify-content-between pt-2"><a
							id="allBaskets"
							href="<c:url value='/amap/${tenancyAlias}/shop/contracts?contractType=ALL'/>"
							class="text-decoration-none flex-grow-1 ${currentMainMenu.equals('contracts') && param.contractType == 'ALL' ? 'text-decoration-underline fc-600' : 'fc-300 fch-600'}">Tous
								les paniers</a> <span class="badge bg-none fc-300 rounded-pill ms-5">${contracts.size()}</span>
						</li>
						<li class="d-flex justify-content-between"><a
							id="vegetableBaskets"
							href="<c:url value='/amap/${tenancyAlias}/shop/contracts?contractType=VEGETABLES_CONTRACT'/>"
							class="text-decoration-none flex-grow-1 ${currentMainMenu.equals('contracts') && param.contractType == 'VEGETABLES_CONTRACT' ? 'text-decoration-underline fc-600' : 'fc-300 fch-600'}">
								Paniers légumes </a> <span
							class="badge bg-none fc-300 rounded-pill ms-5">${vegetableCount}</span>
						</li>
						<li class="d-flex justify-content-between"><a
							id="fruitBaskets"
							href="<c:url value='/amap/${tenancyAlias}/shop/contracts?contractType=FRUITS_CONTRACT'/>"
							class="text-decoration-none flex-grow-1 ${currentMainMenu.equals('contracts') && param.contractType == 'FRUITS_CONTRACT' ? 'text-decoration-underline fc-600' : 'fc-300 fch-600'}">
							Paniers	fruits</a> <span class="badge bg-none fc-300 rounded-pill ms-5">${fruitCount}</span>
						</li>
						<li class="d-flex justify-content-between border-bottom pb-3">
							<a id="mixedBaskets"
							href="<c:url value='/amap/${tenancyAlias}/shop/contracts?contractType=MIX_CONTRACT'/>"
							class="text-decoration-none flex-grow-1 ${currentMainMenu.equals('contracts') && param.contractType == 'MIX_CONTRACT' ? 'text-decoration-underline fc-600' : 'fc-300 fch-600'}">Paniers
								mixtes</a> <span class="badge bg-none fc-300 rounded-pill ms-5">${mixedCount}</span>
						</li>
					</ul> <!-- Épicerie --> <c:if test="${options.option1Active}">
						<li><a href="<c:url value='/amap/${tenancyAlias}/shop/products'/>"
							class="ps-2 pe-5 pt-3 pb-2 d-flex align-items-left border-bottom text-decoration-none ${currentMainMenu.equals('products') ? 'active bg-500 fc-100' : 'fc-500 text-decoration-none'}">
								<i class="bi bi-shop-window me-2"></i>
								<h4 class="fw-bold">Épicerie</h4>
						</a>
					</c:if>
					<!-- Ateliers -->
					<c:if test="${options.option1Active}">
						<li><a
							href="<c:url value='/amap/${tenancyAlias}/shop/workshops'/>"
							class="ps-2 pe-5 pt-3 pb-2 d-flex align-items-left border-bottom text-decoration-none ${currentMainMenu.equals('workshops') ? 'active bg-500 fc-100' : 'fc-500 text-decoration-none'}">
								<i class="bi bi-people me-2"></i>
								<h4 class="fw-bold">Ateliers</h4>
						</a>
					</c:if>
			</ul>
		</div>
	</div>
</nav>
