<%--
  Created by IntelliJ IDEA.
  User: Pedro Jorge
  Date: 20/05/2025
  Time: 19:22
  To change this template use File | Settings | File Templates.
--%>
<%-- Inclui o cabeçalho e define o título da página --%>
<jsp:include page="header.jsp">
  <jsp:param name="title" value="Extrato da Conta" />
</jsp:include>

<div class="container content-container mt-5 mb-5">
  <h1 class="text-center mb-4">Extrato da Conta</h1>

  <c:if test="${not empty requestScope.mensagemErro}">
    <div class="alert alert-danger text-center" role="alert">
        ${requestScope.mensagemErro}
    </div>
  </c:if>

  <c:if test="${not empty requestScope.saldoAtual}">
    <p class="current-balance text-center mb-4">Saldo Atual: <fmt:formatNumber value="${requestScope.saldoAtual}" type="currency" currencyCode="BRL"/></p>
  </c:if>

  <c:choose>
    <c:when test="${not empty requestScope.extrato}">
      <div class="table-responsive">
        <table class="table table-striped table-hover">
          <thead>
          <tr>
            <th>Data/Hora</th>
            <th>Tipo</th>
            <th>Descrição</th>
            <th>Valor</th>
            <th>De (ID Conta Origem)</th>
            <th>Para (ID Conta Destino)</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="transacao" items="${requestScope.extrato}">
            <tr>
              <td><fmt:formatDate value="${transacao.dataHora}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
                <%-- Adiciona classes CSS para estilizar entradas e saídas --%>
              <td class="transaction-type-<c:out value="${transacao.tipo.toLowerCase().contains('saque') || transacao.tipo.toLowerCase().contains('transferencia') ? 'saida' : 'entrada'}"/>">${transacao.tipo}</td>
              <td>${transacao.descricao}</td>
              <td><fmt:formatNumber value="${transacao.valor}" type="currency" currencyCode="BRL"/></td>
              <td>
                <c:choose>
                  <c:when test="${not empty transacao.idContaOrigem}">${transacao.idContaOrigem}</c:when>
                  <c:otherwise>N/A</c:otherwise>
                </c:choose>
              </td>
              <td>
                <c:choose>
                  <c:when test="${not empty transacao.idContaDestino}">${transacao.idContaDestino}</c:when>
                  <c:otherwise>N/A</c:otherwise>
                </c:choose>
              </td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </c:when>
    <c:otherwise>
      <p class="no-transactions text-center lead">Nenhuma transação encontrada para esta conta.</p>
    </c:otherwise>
  </c:choose>

  <div class="text-center mt-4">
    <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-primary">Voltar para Home</a>
  </div>
</div>

<jsp:include page="footer.jsp" />