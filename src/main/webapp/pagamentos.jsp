<%--
  Created by IntelliJ IDEA.
  User: Pedro Jorge
  Date: 25/05/2025
  Time: 22:42
  To change this template use File | Settings | File Templates.
--%>

<%-- Inclui o cabeçalho e define o título da página --%>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Pagamentos" />
</jsp:include>

<div class="container content-container mt-5 mb-5 text-center">
    <h1 class="mb-4">Página de Pagamentos</h1>
    <p class="lead">Esta é uma página de exemplo para o serviço de Pagamentos. Funcionalidade em desenvolvimento.</p>
    <p>Em breve, você poderá pagar suas contas, boletos e muito mais diretamente por aqui!</p>
    <a href="${pageContext.request.contextPath}/home" class="btn btn-primary mt-3">Voltar para Home</a>
</div>

<jsp:include page="footer.jsp" />