package br.com.fiap.fintechflow.controller;

import br.com.fiap.fintechflow.dao.DAOFactory;
import br.com.fiap.fintechflow.dao.ContaDAO;
import br.com.fiap.fintechflow.dao.TransacaoDAO;
import br.com.fiap.fintechflow.model.Conta;
import br.com.fiap.fintechflow.model.Transacao;
import br.com.fiap.fintechflow.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection; // Importa Connection
import java.time.LocalDateTime;

@WebServlet("/transferir")
public class TransferenciaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuarioLogado") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        String numeroContaDestinoStr = request.getParameter("numeroContaDestino");
        String valorStr = request.getParameter("valor");

        Connection connection = null; // Declare a conexão aqui
        try {
            connection = DAOFactory.getConnection(); // Obtém a conexão

            // Iniciar a transação
            connection.setAutoCommit(false); // Desabilita o auto-commit para gerenciar a transação manualmente

            ContaDAO contaDAO = DAOFactory.getContaDAO(); // Obtém DAOs com a mesma conexão
            TransacaoDAO transacaoDAO = DAOFactory.getTransacaoDAO();

            Conta contaOrigem = contaDAO.buscarContaPorUsuario(usuarioLogado.getId());
            if (contaOrigem == null) {
                throw new SQLException("Conta de origem não encontrada.");
            }

            Conta contaDestino = contaDAO.buscarContaPorNumeroConta(numeroContaDestinoStr);
            if (contaDestino == null) {
                throw new SQLException("Conta de destino não encontrada.");
            }

            double valor = Double.parseDouble(valorStr);

            if (contaOrigem.getSaldo() < valor) {
                throw new SQLException("Saldo insuficiente para a transferência.");
            }

            // Atualizar saldos
            contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
            contaDestino.setSaldo(contaDestino.getSaldo() + valor);

            contaDAO.atualizarSaldo(contaOrigem);
            contaDAO.atualizarSaldo(contaDestino);

            // Registrar transação
            Transacao transacao = new Transacao();
            transacao.setIdContaOrigem(contaOrigem.getId());
            transacao.setIdContaDestino(contaDestino.getId());
            transacao.setTipo("TRANSFERENCIA");
            transacao.setValor(valor);
            transacao.setDataHora(LocalDateTime.now());
            transacao.setDescricao("Transferência para " + contaDestino.getNumeroConta());
            transacaoDAO.inserir(transacao);

            connection.commit(); // Confirma todas as operações se tudo deu certo

            request.setAttribute("mensagemSucesso", "Transferência realizada com sucesso!");
            request.getRequestDispatcher("transferencias.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            // Rollback em caso de erro de formato de número
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rbex) {
                    rbex.printStackTrace();
                }
            }
            request.setAttribute("mensagemErro", "Valor da transferência inválido.");
            request.getRequestDispatcher("transferencias.jsp").forward(request, response);
        } catch (SQLException e) {
            // Rollback em caso de erro SQL
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rbex) {
                    rbex.printStackTrace();
                }
            }
            e.printStackTrace();
            request.setAttribute("mensagemErro", "Erro ao realizar transferência: " + e.getMessage());
            request.getRequestDispatcher("transferencias.jsp").forward(request, response);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true); // Retorna ao modo auto-commit
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                DAOFactory.closeAll(); // Fecha a conexão da thread
            }
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