package br.com.fiap.fintechflow.controller;

import br.com.fiap.fintechflow.exception.BusinessException;
import br.com.fiap.fintechflow.model.Usuario;
import br.com.fiap.fintechflow.service.ContaService;
import br.com.fiap.fintechflow.service.impl.ContaServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/transferir")
public class TransferenciaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ContaService contaService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.contaService = new ContaServiceImpl();
        } catch (SQLException e) {
            throw new ServletException("Erro ao inicializar ContaService: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuarioLogado") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        int idContaOrigem = usuarioLogado.getConta().getId(); // Assumindo que a conta do usuário já está no objeto Usuario da sessão

        String numeroContaDestino = request.getParameter("contaDestino");
        double valor = 0.0;
        String descricao = request.getParameter("descricao");

        try {
            valor = Double.parseDouble(request.getParameter("valor"));

            // Realiza a transferência através da camada de serviço
            contaService.realizarTransferencia(idContaOrigem, numeroContaDestino, valor, descricao);

            request.setAttribute("mensagemSucesso", "Transferência realizada com sucesso!");
            request.getRequestDispatcher("transferencias.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("mensagemErro", "Valor de transferência inválido.");
            request.getRequestDispatcher("transferencias.jsp").forward(request, response);
        } catch (BusinessException e) {
            request.setAttribute("mensagemErro", e.getMessage());
            request.getRequestDispatcher("transferencias.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensagemErro", "Erro ao realizar transferência: " + e.getMessage());
            request.getRequestDispatcher("transferencias.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogado") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.getRequestDispatcher("transferencias.jsp").forward(request, response);
    }
}