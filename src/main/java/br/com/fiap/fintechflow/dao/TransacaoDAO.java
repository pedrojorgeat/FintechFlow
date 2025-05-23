package br.com.fiap.fintechflow.dao;

import br.com.fiap.fintechflow.model.Transacao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransacaoDAO {

    private Connection connection;

    public TransacaoDAO(Connection connection) { // Construtor agora recebe a conexão
        this.connection = connection;
    }

    public void inserir(Transacao transacao) throws SQLException {
        String sql = "INSERT INTO TB_TRANSACOES (ID_CONTA_ORIGEM, ID_CONTA_DESTINO, TIPO, VALOR, DATA_HORA, DESCRICAO) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, transacao.getIdContaOrigem());
            stmt.setObject(2, transacao.getIdContaDestino());
            stmt.setString(3, transacao.getTipo());
            stmt.setDouble(4, transacao.getValor());
            stmt.setTimestamp(5, java.sql.Timestamp.valueOf(transacao.getDataHora()));
            stmt.setString(6, transacao.getDescricao());
            stmt.executeUpdate();
        }
    }

    public List<Transacao> buscarTransacoesPorConta(int idConta) throws SQLException {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT ID, ID_CONTA_ORIGEM, ID_CONTA_DESTINO, TIPO, VALOR, DATA_HORA, DESCRICAO FROM TB_TRANSACOES WHERE ID_CONTA_ORIGEM = ? OR ID_CONTA_DESTINO = ? ORDER BY DATA_HORA DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idConta);
            stmt.setInt(2, idConta);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Transacao transacao = new Transacao(
                            rs.getInt("ID"),
                            rs.getObject("ID_CONTA_ORIGEM", Integer.class),
                            rs.getObject("ID_CONTA_DESTINO", Integer.class),
                            rs.getString("TIPO"),
                            rs.getDouble("VALOR"),
                            rs.getTimestamp("DATA_HORA").toLocalDateTime(),
                            rs.getString("DESCRICAO")
                    );
                    transacoes.add(transacao);
                }
            }
        }
        return transacoes;
    }
}