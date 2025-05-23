package br.com.fiap.fintechflow.controller;

import br.com.fiap.fintechflow.dao.DAOFactory;
import br.com.fiap.fintechflow.dao.ContaDAO;
import br.com.fiap.fintechflow.model.Conta;
import br.com.fiap.fintechflow.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection; // Importa Connection para o try/catch/finally

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuarioLogado") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        Connection connection = null; // Declare a conexão aqui
        try {
            connection = DAOFactory.getConnection(); // Obtém a conexão
            ContaDAO contaDAO = DAOFactory.getContaDAO(); // Obtém o DAO com a conexão injetada

            Conta contaUsuario = contaDAO.buscarContaPorUsuario(usuarioLogado.getId());

            request.setAttribute("conta", contaUsuario);
            request.getRequestDispatcher("home.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensagemErro", "Erro ao carregar dados da conta: " + e.getMessage());
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } finally {
            DAOFactory.closeAll(); // Fecha a conexão da thread
        }
    }
}