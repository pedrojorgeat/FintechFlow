package br.com.fiap.fintechflow.controller;

import br.com.fiap.fintechflow.factory.DAOFactory;
import br.com.fiap.fintechflow.dao.UsuarioDAO;
import br.com.fiap.fintechflow.model.Usuario;
import br.com.fiap.fintechflow.model.Endereco;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection; // Importa Connection para o try/catch/finally

@WebServlet("/cadastro")
public class CadastroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("cadastro.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String senha = request.getParameter("senha"); // Em um sistema real, HASHE a senha antes de salvar!
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");

        String logradouro = request.getParameter("logradouro");
        String numero = request.getParameter("numero");
        String complemento = request.getParameter("complemento");
        String bairro = request.getParameter("bairro");
        String cidade = request.getParameter("cidade");
        String estado = request.getParameter("estado");
        String cep = request.getParameter("cep");

        Connection connection = null; // Declare a conexão aqui para o bloco finally
        try {
            connection = DAOFactory.getConnection(); // Obtém a conexão do DAOFactory
            UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO(); // Obtém o DAO com a conexão injetada

            Endereco novoEndereco = new Endereco();
            novoEndereco.setLogradouro(logradouro);
            novoEndereco.setNumero(numero);
            novoEndereco.setComplemento(complemento);
            novoEndereco.setBairro(bairro);
            novoEndereco.setCidade(cidade);
            novoEndereco.setEstado(estado);
            novoEndereco.setCep(cep);

            Usuario novoUsuario = new Usuario();
            novoUsuario.setLogin(login);
            novoUsuario.setSenha(senha); // Lembre-se do hash!
            novoUsuario.setNome(nome);
            novoUsuario.setEmail(email);
            novoUsuario.setEndereco(novoEndereco); // Associa o objeto Endereco ao Usuario

            usuarioDAO.inserir(novoUsuario); // Agora o método 'inserir' existe no UsuarioDAO!

            request.setAttribute("mensagemSucesso", "Cadastro realizado com sucesso! Faça login para continuar.");
            request.getRequestDispatcher("login.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            // Tratamento de erros de constraint UNIQUE para login/email
            if (e.getErrorCode() == 1) { // Oracle error code for unique constraint violation
                if (e.getMessage().contains("UK_TB_USUARIOS_LOGIN")) {
                    request.setAttribute("mensagemErro", "Login já cadastrado. Por favor, escolha outro.");
                } else if (e.getMessage().contains("UK_TB_USUARIOS_EMAIL")) {
                    request.setAttribute("mensagemErro", "E-mail já cadastrado. Por favor, escolha outro.");
                } else {
                    request.setAttribute("mensagemErro", "Erro de violação de dado único. Detalhes: " + e.getMessage());
                }
            } else {
                request.setAttribute("mensagemErro", "Erro ao cadastrar: " + e.getMessage());
            }
            request.getRequestDispatcher("cadastro.jsp").forward(request, response);
        } finally {
            DAOFactory.closeAll(); // Fechar a conexão
        }
    }
}