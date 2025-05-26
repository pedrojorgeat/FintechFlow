package br.com.fiap.fintechflow.controller;

import br.com.fiap.fintechflow.exception.BusinessException;
import br.com.fiap.fintechflow.model.Usuario;
import br.com.fiap.fintechflow.model.Endereco;
import br.com.fiap.fintechflow.service.UsuarioService;
import br.com.fiap.fintechflow.service.impl.UsuarioServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/cadastro")
public class CadastroServlet extends HttpServlet {
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
        request.getRequestDispatcher("cadastro.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String senha = request.getParameter("senha"); // Lembre-se: em um sistema real, HASHE a senha antes de salvar!
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");

        String logradouro = request.getParameter("logradouro");
        String numero = request.getParameter("numero");
        String complemento = request.getParameter("complemento");
        String bairro = request.getParameter("bairro");
        String cidade = request.getParameter("cidade");
        String estado = request.getParameter("estado");
        String cep = request.getParameter("cep");

        Endereco novoEndereco = new Endereco(0, logradouro, numero, complemento, bairro, cidade, estado, cep);
        Usuario novoUsuario = new Usuario(0, login, senha, nome, email, novoEndereco);

        try {
            usuarioService.cadastrar(novoUsuario);
            request.setAttribute("mensagemSucesso", "Cadastro realizado com sucesso! Faça login para continuar.");
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        } catch (BusinessException e) {
            request.setAttribute("mensagemErro", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/cadastro.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensagemErro", "Erro ao cadastrar: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/cadastro.jsp").forward(request, response);
        }
    }
}