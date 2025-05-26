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

    <%-- Link para o seu style.css --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" />

    <%-- Font Awesome para ícones --%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">Fintech Flow</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <c:if test="${not empty sessionScope.usuarioLogado}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/home">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/extrato">Extrato</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/transferir">Transferência</a>
                    </li>
                    <%-- Adicionado o botão Administrador --%>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/clientes">Administrador</a>
                    </li>
                    <%-- Outros serviços (exemplos, sem funcionalidade de backend nesta versão) --%>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Mais Serviços
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/pagamentos">Pagamentos</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/investimentos">Investimentos</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/seguros">Seguros</a></li>
                        </ul>
                    </li>
                </c:if>
            </ul>
            <%-- Navbar de login/perfil --%>
            <c:if test="${not empty sessionScope.usuarioLogado}">
                <div class="d-flex align-items-center">
                        <span class="navbar-text me-3">
                            Olá, ${sessionScope.usuarioLogado.nome}!
                        </span>
                    <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light">Sair</a>
                </div>
            </c:if>
            <c:if test="${empty sessionScope.usuarioLogado}">
                <c:if test="${pageContext.request.servletPath ne '/WEB-INF/jsp/login.jsp' and pageContext.request.servletPath ne '/WEB-INF/jsp/cadastro.jsp'}">
                    <div class="d-flex">
                        <a href="${pageContext.request.contextPath}/login" class="btn btn-outline-light me-2">Login</a>
                        <a href="${pageContext.request.contextPath}/cadastro" class="btn btn-custom">Cadastrar</a>
                    </div>
                </c:if>
            </c:if>
        </div>
    </div>
</nav>
<main class="flex-grow-1">