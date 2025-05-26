package br.com.fiap.fintechflow.controller;

import br.com.fiap.fintechflow.exception.BusinessException;
import br.com.fiap.fintechflow.exception.EntidadeNaoEncontradaException; // Manter import, pode ser útil
import br.com.fiap.fintechflow.model.Conta;
import br.com.fiap.fintechflow.model.Transacao;
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
import java.util.List;

@WebServlet("/extrato")
public class ExtratoServlet extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuarioLogado") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        try {
            Conta contaUsuario = contaService.buscarContaPorUsuario(usuarioLogado.getId());
            List<Transacao> extrato = contaService.buscarExtratoPorConta(contaUsuario.getId());

            request.setAttribute("extrato", extrato);
            request.setAttribute("saldoAtual", contaUsuario.getSaldo());
            request.getRequestDispatcher("extrato.jsp").forward(request, response);

        } catch (BusinessException e) {
            request.setAttribute("mensagemErro", e.getMessage());
            request.getRequestDispatcher("extrato.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensagemErro", "Erro ao carregar o extrato: " + e.getMessage());
            request.getRequestDispatcher("extrato.jsp").forward(request, response);
        }
    }
}