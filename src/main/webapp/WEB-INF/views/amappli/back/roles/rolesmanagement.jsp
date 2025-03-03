<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>Gestion des roles</title>
            <link href="https://api.mapbox.com/mapbox-gl-js/v2.15.0/mapbox-gl.css" rel="stylesheet" />
            <link rel="stylesheet" href="<c:url value='/resources/bootstrap/bootstrap.min.css' />">
            <link rel="stylesheet" href="<c:url value='/resources/css/amap/rolesmanagement.css' />">
        </head>

        <body class="${cssStyle} light ${font}-title ${font}-button">

            <header class="fc-main bg-main">
                <c:choose>
                    <c:when test="${amappli == true}">
                        <jsp:include page="../../common/header.jsp" />
                    </c:when>
                    <c:otherwise>
                        <jsp:include page="../../../amap/back/common/headerAdmin.jsp" />
                    </c:otherwise>
                </c:choose>
            </header> 

            <div id="map"></div>

            <div class="fc-main content col d-flex">
                <jsp:include page="../../../amap/back/common/sidebarAdmin.jsp" />

                <div class="container-fluid row mt-2">
                    <div class="col-8 mx-auto justify-content-center">
                        <h2 class="mb-3">Rôles et Permissions</h2>

                        <table id="rolesTable" class="table table-hover table-header-rotated">
                            <thead>
                                <tr>
                                    <th class="col-2">Rôle</th>
                                    <c:forEach var="permission" items="${permissionsToManage}">
                                        <th class="rotate-45">
                                            <div>
                                                <span class="rotate-45__label m-3">
                                                    ${fn:toUpperCase(fn:substring(permission.name, 0, 1))}${fn:substring(permission.name, 1, fn:length(permission.name))}
                                                </span>
                                            </div>
                                        </th>
                                    </c:forEach>
                                </tr>
                            </thead>
                            <tbody>
                                <!-- Default Roles -->
                                <c:forEach var="role" items="${rolesNoModif}">
                                    <tr class="table-active">
                                        <th class="row-header">${role.name}</th>
                                        <c:forEach var="permission" items="${permissionsToManage}">
                                            <td>
                                                <c:choose>
                                                    <c:when
                                                        test="${roleNoModifPermissionsMap[role.roleId] != null && roleNoModifPermissionsMap[role.roleId].contains(permission.permissionId)}">&#x2713;</c:when>
                                                    <c:otherwise></c:otherwise>
                                                </c:choose>
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </c:forEach>
                                <!-- Roles to Manage-->
                                <c:forEach var="role" items="${roles}">
                                    <tr data-role-id="${role.roleId}" data-role-name="${role.name}">
                                        <th class="row-header">${role.name}</th>
                                        <c:forEach var="permission" items="${permissionsToManage}">
                                            <td data-permission-id="${permission.permissionId}">
                                                <c:choose>
                                                    <c:when
                                                        test="${rolePermissionsMap[role.roleId] != null && rolePermissionsMap[role.roleId].contains(permission.permissionId)}">&#x2713;</c:when>
                                                    <c:otherwise></c:otherwise>
                                                </c:choose>
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <div class="row justify-content-center">
                        <div class="col-md-8">
                            <h2 class="text-center mb-4">Créer ou Modifier un Rôle</h2>

                            <c:choose>
                                <c:when test="${amappli == true}">
                                    <form action="${pageContext.request.contextPath}/amappli/roles/manage" method="post">
                                </c:when>
                                <c:otherwise>
                                    <form action="${pageContext.request.contextPath}/amap/${tenancyAlias}/roles/manage" method="post">
                                </c:otherwise>
                            </c:choose>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <input type="hidden" id="roleId" name="roleId">
                                <div class="mb-3">
                                    <label class="form-label" for="roleName">Nom du rôle</label>
                                    <input class="form-control" type="text" class="form-control" id="roleName" name="roleName"
                                        placeholder="Enter role name" required>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label" for="permissions">Permissions disponibles</label>
                                    <div id="permissions" class="row">
                                        <c:forEach var="permission" items="${permissionsToManage}">
                                            <div class="col-12 col-md-4 mb-2 form-check">
                                                <input class="form-check-input" type="checkbox" id="perm-${permission.permissionId}" name="permissions"
                                                    value="${permission.permissionId}">
                                                <label for="perm-${permission.permissionId}" class="form-check-label">
                                                    ${fn:toUpperCase(fn:substring(permission.name, 0, 1))}${fn:substring(permission.name, 1, fn:length(permission.name))}
                                                </label>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>

                                <button type="submit" class="btn btn-900">Sauvegarder</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            
            <footer class="container-fluid fc-main bg-main">
                <c:choose>
                    <c:when test="${amappli == true}">
                        <jsp:include page="../../common/footer.jsp" />
                    </c:when>
                    <c:otherwise>
                        <jsp:include page="../../../amap/front/common/footer-amap.jsp" />
                    </c:otherwise>
                </c:choose>
            </footer> 

            <script src="<c:url value='/resources/bootstrap/bootstrap.bundle.min.js' />"></script>

        <script>
            var styleMapboxLight = "${mapStyleLight}"
            var styleMapboxDark = "${mapStyleDark}"
            var latitude = "${latitude}"
            var longitude = "${longitude}"
        </script>
 
	<script src="<c:url value='/resources/js/common/mapbox/mapbox-gl.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/common/mapbox/map.js' />"></script>
    <script src="<c:url value='/resources/js/common/theme-swap.js' />" type="text/javascript"></script>
    <script src="<c:url value='/resources/js/amap/admin/bg-table.js' />" type="text/javascript"></script>
	<script src="<c:url value='/resources/js/amap/admin/sidebar.js' />" type="text/javascript"></script>
    <script src="<c:url value='/resources/js/amap/admin/rolesmanagement.js' />" type="text/javascript"></script>

        </body>

        </html>