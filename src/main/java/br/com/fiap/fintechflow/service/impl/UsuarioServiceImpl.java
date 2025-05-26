package br.com.fiap.fintechflow.service.impl;

import br.com.fiap.fintechflow.dao.UsuarioDAO;
import br.com.fiap.fintechflow.exception.BusinessException;
import br.com.fiap.fintechflow.exception.EntidadeNaoEncontradaException;
import br.com.fiap.fintechflow.factory.DAOFactory;
import br.com.fiap.fintechflow.model.Usuario;
import br.com.fiap.fintechflow.service.UsuarioService;

import java.sql.SQLException;
import java.util.List;

public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioDAO usuarioDAO;

    // Construtor que recebe um UsuarioDAO, ideal para injeção de dependência ou testes
    public UsuarioServiceImpl(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    // Construtor padrão que obtém o DAO via Factory
    public UsuarioServiceImpl() throws SQLException {
        this.usuarioDAO = DAOFactory.getUsuarioDAO();
    }

    @Override
    public Usuario autenticar(String login, String senha) throws BusinessException, SQLException {
        try {
            Usuario usuario = usuarioDAO.autenticar(login, senha);
            if (usuario == null) {
                throw new BusinessException("Login ou senha inválidos.");
            }
            return usuario;
        } finally {
            DAOFactory.closeAll();
        }
    }

    @Override
    public Usuario cadastrar(Usuario usuario) throws BusinessException, SQLException {
        try {
            // Verifica se o login já existe
            if (usuarioDAO.buscarPorLogin(usuario.getLogin()) != null) {
                throw new BusinessException("Login já cadastrado. Por favor, escolha outro.");
            }
            // Verifica se o email já existe
            if (usuarioDAO.buscarPorEmail(usuario.getEmail()) != null) {
                throw new BusinessException("E-mail já cadastrado. Por favor, escolha outro.");
            }

            usuarioDAO.inserir(usuario);
            return usuario;
        } catch (SQLException e) {
            // Exemplo de tratamento de erro de constraint UNIQUE em banco de dados
            if (e.getErrorCode() == 1) { // Oracle error code for unique constraint violation
                if (e.getMessage().contains("UK_TB_USUARIOS_LOGIN")) {
                    throw new BusinessException("Login já cadastrado. Por favor, escolha outro.", e);
                } else if (e.getMessage().contains("UK_TB_USUARIOS_EMAIL")) {
                    throw new BusinessException("E-mail já cadastrado. Por favor, escolha outro.", e);
                }
            }
            throw new SQLException("Erro ao cadastrar usuário.", e); // Re-lança como SQLException se não for um erro de negócio específico
        } finally {
            DAOFactory.closeAll();
        }
    }

    @Override
    public Usuario buscarPorId(int id) throws EntidadeNaoEncontradaException, SQLException {
        try {
            Usuario usuario = usuarioDAO.buscarPorId(id);
            if (usuario == null) {
                throw new EntidadeNaoEncontradaException("Usuário com ID " + id + " não encontrado.");
            }
            return usuario;
        } finally {
            DAOFactory.closeAll();
        }
    }

    @Override
    public List<Usuario> listarTodos() throws SQLException, BusinessException { // Adicionado BusinessException aqui
        try {
            return usuarioDAO.listarTodos();
        } finally {
            DAOFactory.closeAll();
        }
    }
}