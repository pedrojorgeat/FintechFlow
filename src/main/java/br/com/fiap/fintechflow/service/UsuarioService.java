package br.com.fiap.fintechflow.service;

import br.com.fiap.fintechflow.exception.BusinessException;
import br.com.fiap.fintechflow.exception.EntidadeNaoEncontradaException;
import br.com.fiap.fintechflow.model.Usuario;

import java.sql.SQLException;
import java.util.List;

public interface UsuarioService {

    //Autentica um usuário com base no login e senha.
    Usuario autenticar(String login, String senha) throws BusinessException, SQLException;

    //Cadastra um novo usuário no sistema.
    Usuario cadastrar(Usuario usuario) throws BusinessException, SQLException;

    //Busca um usuário pelo seu ID.
    Usuario buscarPorId(int id) throws EntidadeNaoEncontradaException, SQLException;

    //Lista todos os usuários cadastrados no sistema.
    List<Usuario> listarTodos() throws SQLException, BusinessException;
}
