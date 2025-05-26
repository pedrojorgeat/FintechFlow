<%--
  Created by IntelliJ IDEA.
  User: Pedro Jorge
  Date: 25/05/2025
  Time: 22:44
  To change this template use File | Settings | File Templates.
--%>

<%-- Inclui o cabeçalho e define o título da página --%>
<jsp:include page="header.jsp">
  <jsp:param name="title" value="Seguros" />
</jsp:include>

<div class="container content-container mt-5 mb-5 text-center">
  <h1 class="mb-4">Página de Seguros</h1>
  <p class="lead">Esta é uma página de exemplo para o serviço de Seguros. Funcionalidade em desenvolvimento.</p>
  <p>Conheça nossas opções de seguros para proteger você e sua família.</p>
  <a href="${pageContext.request.contextPath}/home" class="btn btn-primary mt-3">Voltar para Home</a>
</div>

<jsp:include page="footer.jsp" />
