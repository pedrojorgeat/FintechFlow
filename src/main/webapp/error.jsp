<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Pedro Jorge
  Date: 25/05/2025
  Time: 22:48
  To change this template use File | Settings | File Templates.
--%>

<%-- Inclui o cabeçalho e define o título da página --%>
<jsp:include page="header.jsp">
  <jsp:param name="title" value="Erro" />
</jsp:include>

<div class="container content-container mt-5 mb-5 text-center">
  <h1 class="display-4 text-danger mb-4">Ocorreu um Erro!</h1>

  <p class="lead">Desculpe, algo inesperado aconteceu.</p>

  <c:if test="${not empty exception}">
    <div class="alert alert-warning text-start">
      <p><strong>Mensagem do Erro:</strong></p>
      <p>${exception.message}</p>
      <c:if test="${not empty exception.cause}">
        <p><strong>Causa:</strong> ${exception.cause.message}</p>
      </c:if>
      <p>Por favor, tente novamente mais tarde ou entre em contato com o suporte.</p>
    </div>
  </c:if>

  <c:if test="${empty exception}">
    <p>Não foi possível recuperar os detalhes exatos do erro. Por favor, tente novamente mais tarde.</p>
  </c:if>

  <a href="${pageContext.request.contextPath}/home" class="btn btn-primary mt-3">Voltar para a Página Inicial</a>
</div>

<jsp:include page="footer.jsp" />
