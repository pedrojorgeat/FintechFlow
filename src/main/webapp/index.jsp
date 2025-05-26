<%--
  Created by IntelliJ IDEA.
  User: Pedro Jorge
  Date: 23/05/2025
  Time: 16:24
  To change this template use File | Settings | File Templates.
--%>
<%-- Inclui o cabeçalho e define o título da página --%>
<jsp:include page="header.jsp">
  <jsp:param name="title" value="Bem-vindo à Fintech" />
</jsp:include>

<div class="d-flex align-items-center justify-content-center flex-column text-center content-container" style="min-height: calc(100vh - 100px);"> <%-- Ajuste a altura para centralizar o conteúdo --%>
  <img src="${pageContext.request.contextPath}/resources/images/logo com nome.png" alt="Logo Fintech" class="mb-4" height="250" width="250" />
  <h1 class="display-4 mb-4">Bem-vindo à sua Fintech!</h1>
  <p class="lead mb-5">Sua nova experiência financeira, simples e segura.</p>

  <c:if test="${empty sessionScope.usuarioLogado}">
    <div class="d-grid gap-3 col-6 mx-auto">
      <a href="${pageContext.request.contextPath}/login" class="btn btn-custom btn-lg">Fazer Login</a>
      <a href="${pageContext.request.contextPath}/cadastro" class="btn btn-outline-success btn-lg">Criar Conta</a>
    </div>
  </c:if>
  <c:if test="${not empty sessionScope.usuarioLogado}">
    <div class="d-grid gap-3 col-6 mx-auto">
      <a href="${pageContext.request.contextPath}/home" class="btn btn-custom btn-lg">Acessar Dashboard</a>
    </div>
  </c:if>
</div>

<jsp:include page="footer.jsp" />