<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<div class="container-fluid d-flex flex-row-reverse flex-md-row justify-content-between justify-content-md-evenly align-items-center">

    <c:if test="${not empty logoBase64}">
	    <a href="<c:url value='/amap/${tenancyAlias}/home'/>">
			<div class="logo-container d-none d-md-flex align-items-center">
	            <!-- Affichage du logo encodé en Base64 -->
	            <img id="logo-header" 
	                 height="50" 
	                 min-height="50" 
	                 viewBox="0 0 697 726" 
	                 class="my-2 my-sm-0 logo-image me-3" 
	                 src="data:${logoImgType};base64,${logoBase64}" 
	                 alt="Logo de ${tenancyName}" />
			</div>
	    </a>
    </c:if>
	
	<div class="p-0 ms-2 ms-md-0 d-flex justify-content-evenly">
		<svg class="my-auto mx-1" width="15" height="15" viewBox="0 0 30 30"
			xmlns="http://www.w3.org/2000/svg">
	        <path class="fill-300"
				d="M6.458 10.428l-4.33-2.5c-.564-.325-.08-1.2.5-.866l4.33 2.5c.57.33.072 1.197-.5.866zm19.914 11.498l-4.33-2.5c-.564-.326-.08-1.2.5-.866l4.33 2.5c.57.33.072 1.196-.5.866zM9.568 6.952l-2.5-4.33c-.326-.564.53-1.08.866-.5l2.5 4.33c.33.57-.536 1.072-.866.5zm11.497 19.914l-2.5-4.33c-.325-.564.53-1.08.866-.5l2.5 4.33c.33.57-.535 1.072-.865.5zm-2.5-20.415l2.5-4.33c.326-.563 1.2-.08.866.5l-2.5 4.33c-.328.572-1.195.073-.865-.5zM7.068 26.366l2.5-4.33c.325-.564 1.2-.08.866.5l-2.5 4.33c-.33.57-1.196.072-.866-.5zM22.042 9.56l4.33-2.5c.564-.325 1.08.532.5.867l-4.33 2.5c-.57.33-1.072-.536-.5-.866zM2.128 21.06l4.33-2.5c.563-.325 1.08.53.5.866l-4.33 2.5c-.57.33-1.072-.536-.5-.866zM23.5 14h5c.65 0 .67 1 0 1h-5c-.66 0-.66-1 0-1zm-23 0h5c.65 0 .67 1 0 1h-5c-.66 0-.66-1 0-1zM15 23.5v5c0 .65-1 .67-1 0v-5c0-.66 1-.66 1 0zm0-23v5c0 .65-1 .67-1 0v-5c0-.66 1-.66 1 0zM14.5 8C10.916 8 8 10.916 8 14.5s2.916 6.5 6.5 6.5 6.5-2.916 6.5-6.5S18.084 8 14.5 8zm0 1c3.043 0 5.5 2.457 5.5 5.5 0 3.044-2.457 5.5-5.5 5.5S9 17.544 9 14.5C9 11.457 11.457 9 14.5 9z" />
	    </svg>
		<div class="form-switch">
	    	<input id="switch" class="form-check-input" type="checkbox" />
   		</div>
		<svg class="m-auto" width="15" height="15" viewBox="0 0 32 32" version="1.1"
			xmlns="http://www.w3.org/2000/svg">
	        <path class="fill-300"
				d="M9.882 5.052c-0.847 1.717-1.295 3.614-1.295 5.564 0 6.977 5.676 12.653 12.653 12.653 2.052 0 4.035-0.489 5.812-1.412-2.15 3.869-6.248 6.37-10.862 6.37-6.866 0-12.451-5.585-12.451-12.451 0-4.491 2.409-8.533 6.143-10.724zM12.79 2.707c-5.817 1.509-10.118 6.78-10.118 13.069 0 7.465 6.053 13.517 13.518 13.517 6.387 0 11.726-4.435 13.139-10.389-2.087 2.039-4.939 3.298-8.088 3.298-6.399 0-11.587-5.188-11.587-11.587 0-3.061 1.196-5.838 3.137-7.909v0z" />
	    </svg>
	</div>
	
	<nav class="navbar navbar-expand col-offset-1 col-8 align-content-center">
		<div class="collapse navbar-collapse" id="nav-content">
			<ul class="navbar-nav w-100 justify-content-around align-items-center">
				<li class="nav-item d-block d-md-none">
				<button id="button-collapse-2" class="navbar-toggler align-self-start d-block d-md-none m-2" type="button" data-bs-toggle="collapse" role="button" aria-expanded="true" aria-controls="sidebar" data-bs-target="#sidebar">
					<span class="navbar-toggler-icon fill-main"></span>
				</button>
				</li>
	 			<li class="nav-item">
				</li>
				<li class="nav-item">
					<h1 class="${font} fc-300 fw-bold text-center">Espace admin<span class="d-none d-md-inline">istrateur</span></h1>
				</li>
	 			<li class="nav-item">
					<a href="<c:url value='/logout'/>" class="nav-link ${font} text-decoration-none btn p-0 border-0 bg-transparent"><i class="bi bi-box-arrow-left fs-4 fc-300 fch-500"></i></a>
				</li>
			</ul>
		</div>
	</nav>
</div>