package br.com.fiap.fintechflow.controller;

import br.com.fiap.fintechflow.dao.DAOFactory;
import br.com.fiap.fintechflow.dao.UsuarioDAO;
import br.com.fiap.fintechflow.model.Usuario;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String senha = request.getParameter("senha"); // Em um sistema real, use hash da senha

        UsuarioDAO usuarioDAO = null;
        try {
            usuarioDAO = DAOFactory.getUsuarioDAO();
            Usuario usuarioLogado = usuarioDAO.autenticar(login, senha);

            if (usuarioLogado != null) {
                // Autenticação bem-sucedida
                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogado", usuarioLogado); // Armazena o usuário na sessão

                // Redireciona para a página principal (HomeServlet)
                response.sendRedirect(request.getContextPath() + "/home");
            } else {
                // Autenticação falhou
                request.setAttribute("mensagemErro", "Login ou senha inválidos.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log do erro no console do servidor
            request.setAttribute("mensagemErro", "Erro ao conectar ao banco de dados: " + e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } finally {
            DAOFactory.closeAll(); // Fecha a conexão do DAOFactory
        }
    }

}