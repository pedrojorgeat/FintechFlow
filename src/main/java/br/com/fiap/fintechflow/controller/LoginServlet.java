package br.com.fiap.fintechflow.controller;

import br.com.fiap.fintechflow.exception.BusinessException;
import br.com.fiap.fintechflow.model.Usuario;
import br.com.fiap.fintechflow.service.UsuarioService;
import br.com.fiap.fintechflow.service.impl.UsuarioServiceImpl;

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

    private UsuarioService usuarioService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.usuarioService = new UsuarioServiceImpl();
        } catch (SQLException e) {
            throw new ServletException("Erro ao inicializar UsuarioService: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String senha = request.getParameter("senha"); // Lembre-se: em um sistema real, HASH a senha antes de salvar e compare com o hash armazenado!

        try {
            Usuario usuarioLogado = usuarioService.autenticar(login, senha);

            // Autenticação bem-sucedida
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogado", usuarioLogado); // Armazena o usuário na sessão

            // Redireciona para a página principal (HomeServlet)
            response.sendRedirect(request.getContextPath() + "/home");
        } catch (BusinessException e) {
            // Autenticação falhou por regra de negócio (login/senha inválidos)
            request.setAttribute("mensagemErro", e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (SQLException e) {
            // Erro de acesso ao banco de dados
            e.printStackTrace(); // Log do erro no console do servidor
            request.setAttribute("mensagemErro", "Erro ao conectar ao banco de dados: " + e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}