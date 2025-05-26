package br.com.fiap.fintechflow.factory;

import br.com.fiap.fintechflow.dao.*;

import java.sql.Connection;
import java.sql.SQLException;

public class DAOFactory {

    private static Connection connection;

    // Metodo para obter uma conexão atual
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = ConnectionManager.getConnection();
        }
        return connection;
    }

    public static UsuarioDAO getUsuarioDAO() throws SQLException {
        return new UsuarioDAO(getConnection());
    }

    public static ContaDAO getContaDAO() throws SQLException {
        return new ContaDAO(getConnection());
    }

    public static TransacaoDAO getTransacaoDAO() throws SQLException {
        return new TransacaoDAO(getConnection());
    }

    public static EnderecoDAO getEnderecoDAO() throws SQLException {
        return new EnderecoDAO(getConnection());
    }

    public static void closeAll() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}
