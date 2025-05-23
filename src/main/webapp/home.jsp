<%--
  Created by IntelliJ IDEA.
  User: Pedro Jorge
  Date: 20/05/2025
  Time: 19:18
  To change this template use File | Settings | File Templates.
--%>
<%-- Inclui o cabeçalho e define o título da página --%>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Página Principal" />
</jsp:include>

<div class="container content-container mt-5 mb-5">
    <h1 class="text-center mb-4">Dashboard</h1>

    <c:if test="${not empty requestScope.mensagemErro}">
        <div class="alert alert-danger text-center" role="alert">
                ${requestScope.mensagemErro}
        </div>
    </c:if>

    <div class="card mb-4">
        <div class="card-header bg-primary text-white">
            <h5 class="mb-0">Informações da Sua Conta</h5>
        </div>
        <div class="card-body">
            <c:if test="${not empty requestScope.conta}">
                <p class="card-text"><strong>Número da Conta:</strong> ${requestScope.conta.numeroConta}</p>
                <p class="card-text"><strong>Agência:</strong> ${requestScope.conta.agencia}</p>
                <p class="card-text fs-4 text-success"><strong>Saldo Atual:</strong> <fmt:formatNumber value="${requestScope.conta.saldo}" type="currency" currencyCode="BRL"/></p>
                <p class="card-text"><small class="text-muted">Conta Criada em: <fmt:formatDate value="${requestScope.conta.dataCriacao}" pattern="dd/MM/yyyy HH:mm:ss"/></small></p>
            </c:if>
            <c:if test="${empty requestScope.conta}">
                <p class="text-center">Nenhuma informação de conta encontrada.</p>
            </c:if>
        </div>
    </div>

    <h2 class="text-center mb-4">Serviços</h2>
    <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
        <div class="col">
            <div class="card h-100 text-center">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title"><i class="fas fa-exchange-alt fa-2x text-primary mb-3"></i></h5>
                    <p class="card-text">Envie dinheiro para qualquer banco de forma rápida e segura.</p>
                    <a href="${pageContext.request.contextPath}/transferir" class="btn btn-outline-success w-100 mt-auto">Fazer Transferência</a>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card h-100 text-center">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title"><i class="fas fa-file-invoice-dollar fa-2x text-info mb-3"></i></h5>
                    <p class="card-text">Consulte todas as suas movimentações financeiras.</p>
                    <a href="${pageContext.request.contextPath}/extrato" class="btn btn-outline-success w-100 mt-auto">Ver Extrato</a>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card h-100 text-center">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title"><i class="fas fa-barcode fa-2x text-success mb-3"></i></h5>
                    <p class="card-text">Pague suas contas e boletos com facilidade.</p>
                    <a href="${pageContext.request.contextPath}/pagamentos" class="btn btn-outline-success w-100 mt-auto">Pagar Contas</a>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card h-100 text-center">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title"><i class="fas fa-chart-line fa-2x text-warning mb-3"></i></h5>
                    <p class="card-text">Invista seu dinheiro e veja-o crescer com nossas opções.</p>
                    <a href="${pageContext.request.contextPath}/investimentos" class="btn btn-outline-success w-100 mt-auto">Investimentos</a>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card h-100 text-center">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title"><i class="fas fa-user-circle fa-2x text-secondary mb-3"></i></h5>
                    <p class="card-text">Atualize suas informações pessoais e configurações de segurança.</p>
                    <a href="${pageContext.request.contextPath}/perfil" class="btn btn-outline-success w-100 mt-auto">Gerenciar Perfil</a>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card h-100 text-center">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title"><i class="fas fa-headset fa-2x text-danger mb-3"></i></h5>
                    <p class="card-text">Precisa de ajuda? Entre em contato com nosso suporte especializado.</p>
                    <a href="${pageContext.request.contextPath}/suporte" class="btn btn-outline-success w-100 mt-auto">Suporte</a>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="footer.jsp" %>
