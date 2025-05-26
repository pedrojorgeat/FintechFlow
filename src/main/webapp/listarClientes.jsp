<%--
  Created by IntelliJ IDEA.
  User: Pedro Jorge
  Date: 25/05/2025
  Time: 22:38
  To change this template use File | Settings | File Templates.
--%>

<%-- Inclui o cabeçalho e define o título da página --%>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Lista de Clientes" />
</jsp:include>

<div class="container content-container mt-5 mb-5">
    <h1 class="text-center mb-4">Lista de Clientes</h1>

    <c:if test="${not empty requestScope.mensagemErro}">
        <div class="alert alert-danger text-center" role="alert">
                ${requestScope.mensagemErro}
        </div>
    </c:if>

    <c:choose>
        <c:when test="${not empty requestScope.clientes}">
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Login</th>
                        <th>E-mail</th>
                        <th>Endereço</th>
                        <th>CEP</th>
                        <th>Cidade/Estado</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="cliente" items="${requestScope.clientes}">
                        <tr>
                            <td>${cliente.id}</td>
                            <td>${cliente.nome}</td>
                            <td>${cliente.login}</td>
                            <td>${cliente.email}</td>
                            <td>
                                <c:if test="${not empty cliente.endereco}">
                                    ${cliente.endereco.logradouro}, ${cliente.endereco.numero}
                                    <c:if test="${not empty cliente.endereco.complemento}">
                                        - ${cliente.endereco.complemento}
                                    </c:if>
                                    , ${cliente.endereco.bairro}
                                </c:if>
                                <c:if test="${empty cliente.endereco}">
                                    N/A
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${not empty cliente.endereco}">
                                    ${cliente.endereco.cep}
                                </c:if>
                                <c:if test="${empty cliente.endereco}">
                                    N/A
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${not empty cliente.endereco}">
                                    ${cliente.endereco.cidade}/${cliente.endereco.estado}
                                </c:if>
                                <c:if test="${empty cliente.endereco}">
                                    N/A
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:when>
        <c:otherwise>
            <p class="no-transactions text-center lead">Nenhum cliente encontrado.</p>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="footer.jsp" />
