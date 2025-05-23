package br.com.fiap.fintechflow.dao;

import br.com.fiap.fintechflow.model.Endereco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EnderecoDAO {

    private Connection connection;

    public EnderecoDAO(Connection connection) { // Construtor agora recebe a conexão
        this.connection = connection;
    }

    public int inserir(Endereco endereco) throws SQLException {
        String sql = "INSERT INTO TB_ENDERECOS (LOGRADOURO, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, ESTADO, CEP) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        int idGerado = 0;

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, endereco.getLogradouro());
            stmt.setString(2, endereco.getNumero());
            stmt.setString(3, endereco.getComplemento());
            stmt.setString(4, endereco.getBairro());
            stmt.setString(5, endereco.getCidade());
            stmt.setString(6, endereco.getEstado());
            stmt.setString(7, endereco.getCep());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    idGerado = rs.getInt(1);
                    endereco.setId(idGerado);
                }
            }
            System.out.println("Endereço cadastrado com sucesso. ID: " + idGerado);
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar endereço: " + e.getMessage());
            throw e;
        }
        return idGerado;
    }

    public Endereco buscarPorId(int id) throws SQLException {
        Endereco endereco = null;
        String sql = "SELECT ID, LOGRADOURO, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, ESTADO, CEP " +
                "FROM TB_ENDERECOS WHERE ID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    endereco = new Endereco(
                            rs.getInt("ID"),
                            rs.getString("LOGRADOURO"),
                            rs.getString("NUMERO"),
                            rs.getString("COMPLEMENTO"),
                            rs.getString("BAIRRO"),
                            rs.getString("CIDADE"),
                            rs.getString("ESTADO"),
                            rs.getString("CEP")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar endereço por ID: " + e.getMessage());
            throw e;
        }
        return endereco;
    }

    public Endereco buscarPorEndereco(Endereco endereco) throws SQLException {
        Endereco enderecoExistente = null;
        String sql = "SELECT ID FROM TB_ENDERECOS WHERE LOGRADOURO = ? AND NUMERO = ? AND " +
                "NVL(COMPLEMENTO, 'NULL_COMPLEMENTO') = NVL(?, 'NULL_COMPLEMENTO') AND " +
                "BAIRRO = ? AND CIDADE = ? AND ESTADO = ? AND CEP = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, endereco.getLogradouro());
            stmt.setString(2, endereco.getNumero());
            stmt.setString(3, endereco.getComplemento());
            stmt.setString(4, endereco.getBairro());
            stmt.setString(5, endereco.getCidade());
            stmt.setString(6, endereco.getEstado());
            stmt.setString(7, endereco.getCep());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    enderecoExistente = new Endereco();
                    enderecoExistente.setId(rs.getInt("ID"));
                    enderecoExistente.setLogradouro(endereco.getLogradouro());
                    enderecoExistente.setNumero(endereco.getNumero());
                    enderecoExistente.setComplemento(endereco.getComplemento());
                    enderecoExistente.setBairro(endereco.getBairro());
                    enderecoExistente.setCidade(endereco.getCidade());
                    enderecoExistente.setEstado(endereco.getEstado());
                    enderecoExistente.setCep(endereco.getCep());
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar endereço por campos: " + e.getMessage());
            throw e;
        }
        return enderecoExistente;
    }

    public void atualizar(Endereco endereco) throws SQLException {
        String sql = "UPDATE TB_ENDERECOS SET LOGRADOURO = ?, NUMERO = ?, COMPLEMENTO = ?, BAIRRO = ?, CIDADE = ?, ESTADO = ?, CEP = ? " +
                "WHERE ID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, endereco.getLogradouro());
            stmt.setString(2, endereco.getNumero());
            stmt.setString(3, endereco.getComplemento());
            stmt.setString(4, endereco.getBairro());
            stmt.setString(5, endereco.getCidade());
            stmt.setString(6, endereco.getEstado());
            stmt.setString(7, endereco.getCep());
            stmt.setInt(8, endereco.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Endereço ID: " + endereco.getId() + " atualizado com sucesso.");
            } else {
                System.out.println("Nenhum endereço encontrado com o ID: " + endereco.getId() + " para atualizar.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar endereço: " + e.getMessage());
            throw e;
        }
    }

    public void deletar(int idEndereco) throws SQLException {
        String sql = "DELETE FROM TB_ENDERECOS WHERE ID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEndereco);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Endereço ID: " + idEndereco + " deletado com sucesso.");
            } else {
                System.out.println("Nenhum endereço encontrado com o ID: " + idEndereco + " para deletar.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar endereço: " + e.getMessage());
            throw e;
        }
    }
}