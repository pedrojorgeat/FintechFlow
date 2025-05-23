<%--
  Created by IntelliJ IDEA.
  User: Pedro Jorge
  Date: 18/05/2025
  Time: 19:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>${param.title}</title>

    <%-- Links para os arquivos CSS do Bootstrap localmente --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-grid.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-reboot.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-utilities.css" />
    <%-- Removido bootstrap-rtl.css pois não parece ser necessário para pt-BR --%>

    <%-- Link para o seu arquivo style.css personalizado --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" />

    <%-- Font Awesome para ícones --%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">


</head>
<body class="d-flex flex-column min-vh-100">
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/index">FintechFlow</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <c:if test="${not empty sessionScope.usuarioLogado}">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="${pageContext.request.contextPath}/home">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/extrato">Extrato</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/transferir">Transferência</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/pagamentos">Pagamentos</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/investimentos">Investimentos</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/perfil">Perfil</a>
                    </li>
                </c:if>
            </ul>
            <c:if test="${not empty sessionScope.usuarioLogado}">
                <div class="d-flex align-items-center">
                        <span class="navbar-text me-3">
                            Olá, ${sessionScope.usuarioLogado.nome}!
                        </span>
                    <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light">Sair</a>
                </div>
            </c:if>
            <c:if test="${empty sessionScope.usuarioLogado}">
                <div class="d-flex">
                    <a href="${pageContext.request.contextPath}/login" class="btn btn-outline-light me-2">Login</a>
                    <a href="${pageContext.request.contextPath}/cadastro" class="btn btn-custom">Cadastrar</a>
                </div>
            </c:if>
        </div>
    </div>
</nav>
<main class="flex-grow-1">