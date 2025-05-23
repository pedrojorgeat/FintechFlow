package br.com.fiap.fintechflow.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; // Importado para Statement.RETURN_GENERATED_KEYS

import br.com.fiap.fintechflow.model.Usuario;
import br.com.fiap.fintechflow.model.Endereco; // Importado para lidar com o objeto Endereco

public class UsuarioDAO {

    private Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    public Usuario autenticar(String login, String senha) throws SQLException {
        // SQL ajustado para nome da tabela do seu DDL: TB_USUARIOS e colunas
        String sql = "SELECT ID, NOME, EMAIL, ID_ENDERECO FROM TB_USUARIOS WHERE LOGIN = ? AND SENHA = ?";
        // Usar try-with-resources para PreparedStatement e ResultSet
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, login);
            stmt.setString(2, senha); // Lembre-se: em um sistema real, compare com o hash da senha armazenado
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("ID"));
                    usuario.setNome(rs.getString("NOME"));
                    usuario.setEmail(rs.getString("EMAIL"));
                    usuario.setLogin(login);

                    // Carregar o endereço associado
                    int idEndereco = rs.getInt("ID_ENDERECO");
                    if (!rs.wasNull()) { // Verifica se o ID_ENDERECO não era NULL no BD
                        EnderecoDAO enderecoDAO = new EnderecoDAO(connection); // Passa a mesma conexão
                        usuario.setEndereco(enderecoDAO.buscarPorId(idEndereco));
                    }
                    return usuario;
                }
            }
        }
        return null;
    }

    public int inserir(Usuario usuario) throws SQLException {
        int idUsuarioGerado = 0;
        Integer idEnderecoAssociado = null;

        // 1. Lidar com o Endereço primeiro
        Endereco enderecoDoUsuario = usuario.getEndereco();
        if (enderecoDoUsuario != null) {
            EnderecoDAO enderecoDAO = new EnderecoDAO(connection); // Reutiliza a conexão
            // Tenta buscar o endereço existente
            Endereco enderecoExistente = enderecoDAO.buscarPorEndereco(enderecoDoUsuario);

            if (enderecoExistente != null && enderecoExistente.getId() != 0) {
                idEnderecoAssociado = enderecoExistente.getId();
                System.out.println("Endereço já existe no BD. Usando ID: " + idEnderecoAssociado);
            } else {
                idEnderecoAssociado = enderecoDAO.inserir(enderecoDoUsuario); // Insere novo e obtém o ID
                System.out.println("Novo endereço inserido no BD. ID: " + idEnderecoAssociado);
            }
        }

        // 2. Inserir o Usuário
        String sqlUsuario = "INSERT INTO TB_USUARIOS (LOGIN, SENHA, NOME, EMAIL, ID_ENDERECO) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmtUsuario = connection.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
            stmtUsuario.setString(1, usuario.getLogin());
            stmtUsuario.setString(2, usuario.getSenha()); // Lembre-se de implementar o hash da senha!
            stmtUsuario.setString(3, usuario.getNome());
            stmtUsuario.setString(4, usuario.getEmail());

            if (idEnderecoAssociado != null) {
                stmtUsuario.setInt(5, idEnderecoAssociado);
            } else {
                stmtUsuario.setNull(5, java.sql.Types.NUMERIC);
            }

            stmtUsuario.executeUpdate();

            try (ResultSet rsGeneratedKeys = stmtUsuario.getGeneratedKeys()) {
                if (rsGeneratedKeys.next()) {
                    idUsuarioGerado = rsGeneratedKeys.getInt(1);
                    usuario.setId(idUsuarioGerado);
                }
            }
            System.out.println("Usuário cadastrado com sucesso. ID: " + idUsuarioGerado);
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
            throw e;
        }
        return idUsuarioGerado;
    }
}