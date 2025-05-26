package br.com.fiap.fintechflow.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.fintechflow.model.Usuario;
import br.com.fiap.fintechflow.model.Endereco;
import br.com.fiap.fintechflow.model.Conta; // Importar Conta para associação

public class UsuarioDAO {

    private Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    public Usuario autenticar(String login, String senha) throws SQLException {
        // SQL ajustado para nome da tabela do seu DDL: TB_USUARIOS e colunas
        // Adicionado JOIN para buscar dados da conta junto com o usuário
        String sql = "SELECT U.ID AS USUARIO_ID, U.NOME, U.EMAIL, U.ID_ENDERECO, " +
                "C.ID AS CONTA_ID, C.NUMERO_CONTA, C.AGENCIA, C.SALDO, C.DATA_CRIACAO " +
                "FROM TB_USUARIOS U LEFT JOIN TB_CONTAS C ON U.ID = C.ID_USUARIO " +
                "WHERE U.LOGIN = ? AND U.SENHA = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, login);
            stmt.setString(2, senha); // Lembre-se: em um sistema real, compare com o hash da senha armazenado
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("USUARIO_ID"));
                    usuario.setLogin(login); // O login foi usado na query
                    usuario.setSenha(senha); // A senha foi usada na query
                    usuario.setNome(rs.getString("NOME"));
                    usuario.setEmail(rs.getString("EMAIL"));

                    // Associa o endereço se existir
                    int idEndereco = rs.getInt("ID_ENDERECO");
                    if (!rs.wasNull()) {
                        EnderecoDAO enderecoDAO = new EnderecoDAO(connection); // Reusa a mesma conexão
                        Endereco endereco = enderecoDAO.buscarPorId(idEndereco);
                        usuario.setEndereco(endereco);
                    }

                    // Associa a conta se existir
                    if (rs.getObject("CONTA_ID") != null) {
                        Conta conta = new Conta(
                                rs.getInt("CONTA_ID"),
                                usuario.getId(),
                                rs.getString("NUMERO_CONTA"),
                                rs.getString("AGENCIA"),
                                rs.getDouble("SALDO"),
                                rs.getTimestamp("DATA_CRIACAO").toLocalDateTime()
                        );
                        usuario.setConta(conta);
                    }
                    return usuario;
                }
            }
        }
        return null;
    }

    public void inserir(Usuario usuario) throws SQLException {
        // 1. Inserir o Endereço primeiro (se houver) e obter o ID gerado
        Integer idEnderecoAssociado = null;
        if (usuario.getEndereco() != null) {
            EnderecoDAO enderecoDAO = new EnderecoDAO(connection); // Passa a conexão atual para o DAO de Endereço
            idEnderecoAssociado = enderecoDAO.inserir(usuario.getEndereco());
            System.out.println("Novo endereço inserido no BD. ID: " + idEnderecoAssociado);
        }

        // 2. Inserir o Usuário
        String sqlUsuario = "INSERT INTO TB_USUARIOS (LOGIN, SENHA, NOME, EMAIL, ID_ENDERECO) VALUES (?, ?, ?, ?, ?)";
        int idUsuarioGerado = 0;
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

            // 3. Opcional: Criar uma conta padrão para o novo usuário
            // Isso pode ser uma regra de negócio e pode ser movido para o Service
            ContaDAO contaDAO = new ContaDAO(connection);
            Conta novaConta = new Conta();
            novaConta.setIdUsuario(idUsuarioGerado);
            novaConta.setNumeroConta("CC-" + (10000 + idUsuarioGerado)); // Exemplo simples
            novaConta.setAgencia("0001");
            novaConta.setSaldo(0.0);
            novaConta.setDataCriacao(java.time.LocalDateTime.now());
            contaDAO.inserir(novaConta);
            usuario.setConta(novaConta); // Associa a nova conta ao usuário
            System.out.println("Conta padrão criada para o usuário ID: " + idUsuarioGerado);

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
            throw e;
        }
    }

    public Usuario buscarPorLogin(String login) throws SQLException {
        String sql = "SELECT ID, NOME, EMAIL FROM TB_USUARIOS WHERE LOGIN = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("ID"));
                    usuario.setLogin(login);
                    usuario.setNome(rs.getString("NOME"));
                    usuario.setEmail(rs.getString("EMAIL"));
                    return usuario;
                }
            }
        }
        return null;
    }

    public Usuario buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT ID, NOME, LOGIN FROM TB_USUARIOS WHERE EMAIL = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("ID"));
                    usuario.setEmail(email);
                    usuario.setNome(rs.getString("NOME"));
                    usuario.setLogin(rs.getString("LOGIN"));
                    return usuario;
                }
            }
        }
        return null;
    }

    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT ID, LOGIN, SENHA, NOME, EMAIL, ID_ENDERECO FROM TB_USUARIOS WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("ID"));
                    usuario.setLogin(rs.getString("LOGIN"));
                    usuario.setSenha(rs.getString("SENHA"));
                    usuario.setNome(rs.getString("NOME"));
                    usuario.setEmail(rs.getString("EMAIL"));

                    int idEndereco = rs.getInt("ID_ENDERECO");
                    if (!rs.wasNull()) {
                        EnderecoDAO enderecoDAO = new EnderecoDAO(connection);
                        usuario.setEndereco(enderecoDAO.buscarPorId(idEndereco));
                    }
                    return usuario;
                }
            }
        }
        return null;
    }

    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT U.ID, U.LOGIN, U.NOME, U.EMAIL, U.ID_ENDERECO, " +
                "E.LOGRADOURO, E.NUMERO, E.COMPLEMENTO, E.BAIRRO, E.CIDADE, E.ESTADO, E.CEP " +
                "FROM TB_USUARIOS U LEFT JOIN TB_ENDERECOS E ON U.ID_ENDERECO = E.ID ORDER BY U.NOME";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("ID"));
                    usuario.setLogin(rs.getString("LOGIN"));
                    usuario.setNome(rs.getString("NOME"));
                    usuario.setEmail(rs.getString("EMAIL"));

                    // Popula o objeto Endereco
                    int idEndereco = rs.getInt("ID_ENDERECO");
                    if (!rs.wasNull()) {
                        Endereco endereco = new Endereco(
                                idEndereco,
                                rs.getString("LOGRADOURO"),
                                rs.getString("NUMERO"),
                                rs.getString("COMPLEMENTO"),
                                rs.getString("BAIRRO"),
                                rs.getString("CIDADE"),
                                rs.getString("ESTADO"),
                                rs.getString("CEP")
                        );
                        usuario.setEndereco(endereco);
                    }
                    usuarios.add(usuario);
                }
            }
        }
        return usuarios;
    }
}