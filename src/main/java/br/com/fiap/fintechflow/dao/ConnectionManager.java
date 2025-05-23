package br.com.fiap.fintechflow.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static ConnectionManager connectionManager;

    private ConnectionManager() {
    }

    public static ConnectionManager getInstance() {
        if (connectionManager == null) {
            connectionManager = new ConnectionManager();
        }
        return connectionManager;
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL",
                    "RM553602",
                    "120984");

        } catch (ClassNotFoundException e) { // Tratar especificamente ClassNotFoundException
            System.err.println("Driver JDBC não encontrado: " + e.getMessage());
            throw new SQLException("Erro ao carregar o driver JDBC.", e);
        } catch (SQLException e) { // Captura SQLException
            System.err.println("Erro ao obter conexão com o banco de dados: " + e.getMessage());
            throw e; // Relança para que o DAOFactory possa tratar
        } catch (Exception e) { // Captura outras exceções inesperadas
            e.printStackTrace();
            throw new SQLException("Erro inesperado ao obter conexão.", e);
        }
        return connection;
    }

}
