<%--
  Created by IntelliJ IDEA.
  User: Pedro Jorge
  Date: 25/05/2025
  Time: 22:39
  To change this template use File | Settings | File Templates.
--%>

<%-- Inclui o cabeçalho e define o título da página --%>
<jsp:include page="header.jsp">
  <jsp:param name="title" value="Login" />
</jsp:include>

<div class="container content-container mt-5 mb-5">
  <h1 class="text-center mb-4">Acesse Sua Conta</h1>

  <c:if test="${not empty requestScope.mensagemErro}">
    <div class="alert alert-danger text-center" role="alert">
        ${requestScope.mensagemErro}
    </div>
  </c:if>
  <c:if test="${not empty requestScope.mensagemSucesso}">
    <div class="alert alert-success text-center" role="alert">
        ${requestScope.mensagemSucesso}
    </div>
  </c:if>

  <div class="form-container">
    <form action="${pageContext.request.contextPath}/login" method="post">
      <div class="mb-3">
        <label for="login" class="form-label">Login:</label>
        <input type="text" class="form-control" id="login" name="login" required placeholder="Digite seu login" value="${param.login}">
      </div>
      <div class="mb-4">
        <label for="senha" class="form-label">Senha:</label>
        <input type="password" class="form-control" id="senha" name="senha" required placeholder="Digite sua senha">
      </div>
      <div class="d-grid gap-2">
        <button type="submit" class="btn btn-custom btn-lg">Entrar</button>
      </div>
    </form>

    <div class="text-center mt-3">
      <p>Não tem uma conta? <a href="${pageContext.request.contextPath}/cadastro" class="link-custom">Cadastre-se aqui</a></p>
    </div>
  </div>
</div>

<jsp:include page="footer.jsp" />
