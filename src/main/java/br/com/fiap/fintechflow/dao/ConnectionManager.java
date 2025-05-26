package br.com.fiap.fintechflow.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    // Usar ThreadLocal para gerenciar conexões por thread,
    // garantindo que cada request/thread tenha sua própria conexão e seja fechada corretamente.
    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    private ConnectionManager() {
    }

    // Método para obter uma conexão para a thread atual
    public static Connection getConnection() throws SQLException {
        Connection connection = connectionHolder.get();
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                connection = DriverManager.getConnection(
                        "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL",
                        "RM553602",
                        "120984");
                connectionHolder.set(connection); // Armazena a conexão na thread local
            } catch (ClassNotFoundException e) {
                System.err.println("Driver JDBC não encontrado: " + e.getMessage());
                throw new SQLException("Erro ao carregar o driver JDBC.", e);
            } catch (SQLException e) {
                System.err.println("Erro ao obter conexão com o banco de dados: " + e.getMessage());
                throw e;
            } catch (Exception e) {
                e.printStackTrace();
                throw new SQLException("Erro inesperado ao obter conexão.", e);
            }
        }
        return connection;
    }

    // Metodo para fechar a conexão da thread atual
    public static void closeConnection() {
        Connection connection = connectionHolder.get();
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
                e.printStackTrace();
            } finally {
                connectionHolder.remove(); // Remove a conexão da ThreadLocal
            }
        }
    }
}