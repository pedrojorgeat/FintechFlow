<%--
  Created by IntelliJ IDEA.
  User: Pedro Jorge
  Date: 25/05/2025
  Time: 22:44
  To change this template use File | Settings | File Templates.
--%>

<%-- Inclui o cabeçalho e define o título da página --%>
<jsp:include page="header.jsp">
  <jsp:param name="title" value="Investimentos" />
</jsp:include>

<div class="container content-container mt-5 mb-5 text-center">
  <h1 class="mb-4">Página de Investimentos</h1>
  <p class="lead">Esta é uma página de exemplo para o serviço de Investimentos. Funcionalidade em desenvolvimento.</p>
  <p>Descubra as melhores opções de investimento para o seu perfil e veja seu dinheiro crescer!</p>
  <a href="${pageContext.request.contextPath}/home" class="btn btn-primary mt-3">Voltar para Home</a>
</div>

<jsp:include page="footer.jsp" />
