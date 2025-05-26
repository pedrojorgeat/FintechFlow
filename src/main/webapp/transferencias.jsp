<%--
  Created by IntelliJ IDEA.
  User: Pedro Jorge
  Date: 20/05/2025
  Time: 19:20
  To change this template use File | Settings | File Templates.
--%>
<%-- Inclui o cabeçalho e define o título da página --%>
<jsp:include page="header.jsp">
  <jsp:param name="title" value="Transferências" />
</jsp:include>

<div class="container content-container mt-5 mb-5">
  <h1 class="text-center mb-4">Realizar Transferência</h1>

  <c:if test="${not empty requestScope.mensagemSucesso}">
    <div class="alert alert-success text-center" role="alert">
        ${requestScope.mensagemSucesso}
    </div>
  </c:if>
  <c:if test="${not empty requestScope.mensagemErro}">
    <div class="alert alert-danger text-center" role="alert">
        ${requestScope.mensagemErro}
    </div>
  </c:if>

  <form action="${pageContext.request.contextPath}/transferir" method="post">
    <div class="mb-3">
      <label for="contaDestino" class="form-label">Número da Conta de Destino:</label>
      <input type="text" class="form-control" id="contaDestino" name="contaDestino" required placeholder="Ex: 12345-6" value="${param.contaDestino}">
    </div>
    <div class="mb-3">
      <label for="valor" class="form-label">Valor a Transferir:</label>
      <input type="number" class="form-control" id="valor" name="valor" step="0.01" min="0.01" required placeholder="Ex: 100.50" value="${param.valor}">
    </div>
    <div class="mb-4">
      <label for="descricao" class="form-label">Descrição (Opcional):</label>
      <textarea class="form-control" id="descricao" name="descricao" rows="3" placeholder="Ex: Pagamento aluguel">${param.descricao}</textarea>
    </div>

    <div class="d-grid gap-2">
      <button type="submit" class="btn btn-custom btn-lg">Transferir</button>
    </div>
  </form>

  <div class="text-center mt-4">
    <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-primary">Voltar para Home</a>
  </div>
</div>

<jsp:include page="footer.jsp" />