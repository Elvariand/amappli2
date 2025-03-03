<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
	rel="stylesheet">
<div class="container fc-main mt-4 justify-content-center">
    <div class="row">
        <!-- Colonne "Notre histoire" -->
        <div class="col-12 col-md">
            <h5 class="fw-bold">Notre histoire</h5>
            <ul class="list-unstyled">
                <li><a href="<c:url value='/amap/${tenancyAlias}/home'/>" class="nav-link">A propos</a></li>
                <li><a href="#" class="nav-link">Partenaires</a></li>
                <li><a href="<c:url value='/amap/${tenancyAlias}/shop/contracts'/>" class="nav-link">Nos produits</a></li>
                <li><a href="<c:url value='/amap/${tenancyAlias}/shop/workshop'/>" class="nav-link">Nos événements</a></li>
            </ul>
        </div>

        <!-- Colonne "Une question ?" -->
        <div class="col-12 col-md">
            <h5 class="fw-bold">Une question ?</h5>
            <ul class="list-unstyled">
                <li><a href="<c:url value='/amap/${tenancyAlias}/contact'/>" class="nav-link">Contact</a></li>
                <li><a href="<c:url value='/amap/${tenancyAlias}/amapPage'/>" class="nav-link">FAQ</a></li>
            </ul>

            <!-- Réseaux sociaux -->
            <h5 class="fw-bold mt-3">Réseaux sociaux :</h5>
           <a href="#" class="nav-link me-2 d-inline"><i class="bi bi-facebook"></i></a>
           <a href="#" class="nav-link me-2 d-inline"><i class="bi bi-instagram"></i></a>
        </div>

        <!-- Colonne "Où nous trouver ?" -->
        <div class="col-12 col-md">
            <h5 class="fw-bold">Où nous trouver ?</h5>
            <ul class="list-unstyled">
                <li>${addressLine1}</li>
                <li>${addressLine2}</li>
                <li>${addressPostCode}&nbsp;${addressCity}</li>
            </ul>
        </div>
    </div>

    <!-- Copyright -->
    <div class="text-center mt-2">
        <p class="mb-0 fc-main">&copy; 2024 Groupe 1 - Projet 3 Isika - Tous droits réservés</p>
    </div>
</div>
