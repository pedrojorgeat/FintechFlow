package br.com.fiap.fintechflow.dao;

import br.com.fiap.fintechflow.model.Conta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ContaDAO {

    private Connection connection;

    public ContaDAO(Connection connection) { // Construtor agora recebe a conexão
        this.connection = connection;
    }

    public void inserir(Conta conta) throws SQLException {
        String sql = "INSERT INTO TB_CONTAS (ID_USUARIO, NUMERO_CONTA, AGENCIA, SALDO, DATA_CRIACAO) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, conta.getIdUsuario());
            stmt.setString(2, conta.getNumeroConta());
            stmt.setString(3, conta.getAgencia());
            stmt.setDouble(4, conta.getSaldo());
            stmt.setTimestamp(5, java.sql.Timestamp.valueOf(conta.getDataCriacao()));
            stmt.executeUpdate();
        }
    }

    public Conta buscarContaPorUsuario(int idUsuario) throws SQLException {
        String sql = "SELECT ID, NUMERO_CONTA, AGENCIA, SALDO, DATA_CRIACAO FROM TB_CONTAS WHERE ID_USUARIO = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Conta(rs.getInt("ID"),
                            idUsuario,
                            rs.getString("NUMERO_CONTA"),
                            rs.getString("AGENCIA"),
                            rs.getDouble("SALDO"),
                            rs.getTimestamp("DATA_CRIACAO").toLocalDateTime());
                }
            }
        }
        return null;
    }

    public Conta buscarContaPorNumeroConta(String numeroConta) throws SQLException {
        String sql = "SELECT ID, ID_USUARIO, AGENCIA, SALDO, DATA_CRIACAO FROM TB_CONTAS WHERE NUMERO_CONTA = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, numeroConta);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Conta(rs.getInt("ID"),
                            rs.getInt("ID_USUARIO"),
                            numeroConta,
                            rs.getString("AGENCIA"),
                            rs.getDouble("SALDO"),
                            rs.getTimestamp("DATA_CRIACAO").toLocalDateTime());
                }
            }
        }
        return null;
    }

    public void atualizarSaldo(Conta conta) throws SQLException {
        String sql = "UPDATE TB_CONTAS SET SALDO = ? WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, conta.getSaldo());
            stmt.setInt(2, conta.getId());
            stmt.executeUpdate();
        }
    }
}