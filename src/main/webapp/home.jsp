<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            </c:if>
            <c:if test="${empty requestScope.conta}">
                <p class="card-text text-danger">Não foi possível carregar as informações da sua conta. Por favor, tente novamente.</p>
            </c:if>
        </div>
    </div>

    <h2 class="text-center mb-4">Serviços Financeiros</h2>
    <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
        <div class="col">
            <div class="card h-100 text-center">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title"><i class="fas fa-exchange-alt fa-2x text-info mb-3"></i></h5>
                    <p class="card-text">Transfira dinheiro para outras contas de forma rápida e segura.</p>
                    <a href="${pageContext.request.contextPath}/transferir" class="btn btn-outline-success w-100 mt-auto">Fazer Transferência</a>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card h-100 text-center">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title"><i class="fas fa-list-alt fa-2x text-primary mb-3"></i></h5>
                    <p class="card-text">Visualize todas as suas transações e movimentações da conta.</p>
                    <a href="${pageContext.request.contextPath}/extrato" class="btn btn-outline-success w-100 mt-auto">Ver Extrato</a>
                </div>
            </div>
        </div>
        <%-- Novos serviços sugeridos --%>
        <div class="col">
            <div class="card h-100 text-center">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title"><i class="fas fa-money-bill-wave fa-2x text-success mb-3"></i></h5>
                    <p class="card-text">Faça depósitos na sua conta de maneira simples e prática.</p>
                    <a href="${pageContext.request.contextPath}/pagamentos" class="btn btn-outline-success w-100 mt-auto">Depositar</a>
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
                    <h5 class="card-title"><i class="fas fa-credit-card fa-2x text-secondary mb-3"></i></h5>
                    <p class="card-text">Gerencie seus cartões de crédito e débito, e solicite novos.</p>
                    <a href="${pageContext.request.contextPath}/cartoes" class="btn btn-outline-success w-100 mt-auto">Cartões</a>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card h-100 text-center">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title"><i class="fas fa-shield-alt fa-2x text-danger mb-3"></i></h5>
                    <p class="card-text">Proteja seu futuro com nossas diversas opções de seguros.</p>
                    <a href="${pageContext.request.contextPath}/seguros" class="btn btn-outline-success w-100 mt-auto">Seguros</a>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />