package br.com.fiap.fintechflow.service.impl;

import br.com.fiap.fintechflow.dao.ContaDAO;
import br.com.fiap.fintechflow.dao.TransacaoDAO;
import br.com.fiap.fintechflow.exception.BusinessException;
import br.com.fiap.fintechflow.exception.EntidadeNaoEncontradaException;
import br.com.fiap.fintechflow.factory.DAOFactory;
import br.com.fiap.fintechflow.model.Conta;
import br.com.fiap.fintechflow.model.Transacao;
import br.com.fiap.fintechflow.service.ContaService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class ContaServiceImpl implements ContaService {

    private ContaDAO contaDAO;
    private TransacaoDAO transacaoDAO;

    // Construtor para injeção de dependências (útil para testes)
    public ContaServiceImpl(ContaDAO contaDAO, TransacaoDAO transacaoDAO) {
        this.contaDAO = contaDAO;
        this.transacaoDAO = transacaoDAO;
    }

    // Construtor padrão que obtém os DAOs via Factory (irá obter a mesma conexão para todos)
    public ContaServiceImpl() throws SQLException {
        Connection connection = DAOFactory.getConnection();
        this.contaDAO = new ContaDAO(connection);
        this.transacaoDAO = new TransacaoDAO(connection);
    }


    @Override
    public Conta buscarContaPorUsuario(int idUsuario) throws EntidadeNaoEncontradaException, SQLException {
        try {
            Conta conta = contaDAO.buscarContaPorUsuario(idUsuario);
            if (conta == null) {
                throw new EntidadeNaoEncontradaException("Nenhuma conta encontrada para o usuário com ID: " + idUsuario);
            }
            return conta;
        } finally {
            DAOFactory.closeAll(); // Fecha a conexão associada à thread
        }
    }

    @Override
    public void realizarTransferencia(int idContaOrigem, String numeroContaDestino, double valor, String descricao) throws BusinessException, SQLException {
        Connection connection = null;
        try {
            connection = DAOFactory.getConnection(); // Obtém a conexão
            connection.setAutoCommit(false); // Inicia a transação manual

            // Reinstancia DAOs com a conexão da transação
            ContaDAO contaDAOTransacional = new ContaDAO(connection);
            TransacaoDAO transacaoDAOTransacional = new TransacaoDAO(connection);

            Conta contaOrigem = contaDAOTransacional.buscarContaPorId(idContaOrigem);
            if (contaOrigem == null) {
                throw new EntidadeNaoEncontradaException("Conta de origem não encontrada.");
            }

            Conta contaDestino = contaDAOTransacional.buscarContaPorNumeroConta(numeroContaDestino);
            if (contaDestino == null) {
                throw new BusinessException("Conta de destino inválida ou não encontrada.");
            }

            if (valor <= 0) {
                throw new BusinessException("O valor da transferência deve ser positivo.");
            }

            if (contaOrigem.getSaldo() < valor) {
                throw new BusinessException("Saldo insuficiente para realizar a transferência.");
            }

            // Debitar da conta de origem
            contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
            contaDAOTransacional.atualizarSaldo(contaOrigem);

            // Creditar na conta de destino
            contaDestino.setSaldo(contaDestino.getSaldo() + valor);
            contaDAOTransacional.atualizarSaldo(contaDestino);

            // Registrar transação de débito (saída)
            // CORREÇÃO: Usando o construtor sem o ID, e verificando as contas de origem/destino corretas para a transação
            Transacao transacaoSaida = new Transacao(idContaOrigem, contaDestino.getId(), "Transferência", valor, LocalDateTime.now(), "Transferência para " + numeroContaDestino + ": " + descricao);
            transacaoDAOTransacional.inserir(transacaoSaida);

            // Registrar transação de crédito (entrada)
            // CORREÇÃO: Usando o construtor sem o ID. A origem é a conta que enviou (idContaOrigem) e o destino é a conta que recebeu (contaDestino.getId())
            Transacao transacaoEntrada = new Transacao(idContaOrigem, contaDestino.getId(), "Recebimento de Transferência", valor, LocalDateTime.now(), "Transferência de " + contaOrigem.getNumeroConta() + ": " + descricao);
            transacaoDAOTransacional.inserir(transacaoEntrada);

            connection.commit(); // Confirma a transação
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Desfaz a transação em caso de erro SQL
                    System.err.println("Rollback realizado devido a erro SQL na transferência.");
                } catch (SQLException rbex) {
                    System.err.println("Erro ao realizar rollback: " + rbex.getMessage());
                    rbex.printStackTrace();
                }
            }
            System.err.println("Erro SQL ao realizar transferência: " + e.getMessage());
            throw e; // Re-lança a SQLException para ser tratada no Servlet
        } catch (BusinessException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Desfaz a transação em caso de erro de negócio
                    System.err.println("Rollback realizado devido a erro de negócio na transferência.");
                } catch (SQLException rbex) {
                    System.err.println("Erro ao realizar rollback: " + rbex.getMessage());
                    rbex.printStackTrace();
                }
            }
            throw e; // Re-lança a BusinessException
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true); // Retorna ao modo auto-commit padrão
                } catch (SQLException e) {
                    System.err.println("Erro ao restaurar auto-commit: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            DAOFactory.closeAll(); // Fecha a conexão
        }
    }

    @Override
    public List<Transacao> buscarExtratoPorConta(int idConta) throws SQLException {
        try {
            return transacaoDAO.buscarTransacoesPorConta(idConta);
        } finally {
            DAOFactory.closeAll();
        }
    }
}