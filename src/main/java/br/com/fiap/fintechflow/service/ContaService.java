package br.com.fiap.fintechflow.service;

import br.com.fiap.fintechflow.exception.BusinessException;
import br.com.fiap.fintechflow.exception.EntidadeNaoEncontradaException;
import br.com.fiap.fintechflow.model.Conta;
import br.com.fiap.fintechflow.model.Transacao;

import java.sql.SQLException;
import java.util.List;

public interface ContaService {

    // Busca a conta de um usuário.

    Conta buscarContaPorUsuario(int idUsuario) throws EntidadeNaoEncontradaException, SQLException;

    // Realiza uma transferência entre contas.
    void realizarTransferencia(int idContaOrigem, String numeroContaDestino, double valor, String descricao) throws BusinessException, SQLException;

    //Busca o extrato de transações de uma conta.
     List<Transacao> buscarExtratoPorConta(int idConta) throws SQLException;
}