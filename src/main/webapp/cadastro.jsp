<%--
  Created by IntelliJ IDEA.
  User: Pedro Jorge
  Date: 20/05/2025
  Time: 19:17
  To change this template use File | Settings | File Templates.
--%>
<%-- Inclui o cabeçalho e define o título da página --%>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Cadastro" />
</jsp:include>

<div class="container content-container mt-5 mb-5">
    <h1 class="text-center mb-4">Cadastre-se</h1>

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

    <form action="${pageContext.request.contextPath}/cadastro" method="post">
        <h2 class="mb-3">Dados Pessoais</h2>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="nome" class="form-label">Nome Completo:</label>
                <input type="text" class="form-control" id="nome" name="nome" required value="${param.nome}">
            </div>
            <div class="col-md-6 mb-3">
                <label for="email" class="form-label">E-mail:</label>
                <input type="email" class="form-control" id="email" name="email" required value="${param.email}">
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="login" class="form-label">Login:</label>
                <input type="text" class="form-control" id="login" name="login" required value="${param.login}">
            </div>
            <div class="col-md-6 mb-3">
                <label for="senha" class="form-label">Senha:</label>
                <input type="password" class="form-control" id="senha" name="senha" required>
            </div>
        </div>

        <h2 class="mb-3 mt-4">Endereço</h2>
        <div class="row">
            <div class="col-md-8 mb-3">
                <label for="logradouro" class="form-label">Logradouro:</label>
                <input type="text" class="form-control" id="logradouro" name="logradouro" required value="${param.logradouro}">
            </div>
            <div class="col-md-4 mb-3">
                <label for="numero" class="form-label">Número:</label>
                <input type="text" class="form-control" id="numero" name="numero" required value="${param.numero}">
            </div>
        </div>
        <div class="mb-3">
            <label for="complemento" class="form-label">Complemento (Opcional):</label>
            <input type="text" class="form-control" id="complemento" name="complemento" value="${param.complemento}">
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="bairro" class="form-label">Bairro:</label>
                <input type="text" class="form-control" id="bairro" name="bairro" required value="${param.bairro}">
            </div>
            <div class="col-md-4 mb-3">
                <label for="cidade" class="form-label">Cidade:</label>
                <input type="text" class="form-control" id="cidade" name="cidade" required value="${param.cidade}">
            </div>
            <div class="col-md-2 mb-3">
                <label for="estado" class="form-label">Estado (UF):</label>
                <input type="text" class="form-control" id="estado" name="estado" maxlength="2" required value="${param.estado}">
            </div>
        </div>
        <div class="mb-4">
            <label for="cep" class="form-label">CEP:</label>
            <input type="text" class="form-control" id="cep" name="cep" maxlength="9" pattern="[0-9]{5}-?[0-9]{3}" placeholder="Ex: 12345-678" required value="${param.cep}">
        </div>

        <div class="d-grid gap-2">
            <button type="submit" class="btn btn-custom btn-lg">Cadastrar</button>
        </div>
    </form>

    <div class="text-center mt-3">
        <p>Já tem uma conta? <a href="${pageContext.request.contextPath}/login" class="link-custom">Faça Login</a></p>
    </div>
</div>

<jsp:include page="footer.jsp" />