package br.com.fiap.fintechflow.model;

public class Usuario {
    private int id; // Corresponde à coluna ID em TB_USUARIOS
    private String login; // Corresponde à coluna LOGIN
    private String senha; // Corresponde à coluna SENHA (Lembre-se do HASH!)
    private String nome; // Corresponde à coluna NOME
    private String email; // Corresponde à coluna EMAIL
    private Integer idEndereco; // FK para TB_ENDERECOS, pode ser NULL
    private Endereco endereco; // Objeto Endereco associado, para facilidade de uso na camada de aplicação

    // Construtor padrão
    public Usuario() {
    }

    // Construtor para leitura do banco de dados (pode carregar o idEndereco e depois o objeto Endereco)
    public Usuario(int id, String login, String senha, String nome, String email, Integer idEndereco) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.email = email;
        this.idEndereco = idEndereco;
        // O objeto Endereco será populado via DAO (UsuarioDAO.autenticar ou outro método de busca)
    }

    // Construtor para criação/cadastro de um novo usuário com endereço
    public Usuario(String login, String senha, String nome, String email, Endereco endereco) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.email = email;
        this.setEndereco(endereco); // Usa o setter para garantir que idEndereco também seja setado
    }


    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter e Setter para o ID da chave estrangeira do Endereço
    public Integer getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(Integer idEndereco) {
        this.idEndereco = idEndereco;
    }

    // Getter e Setter para o objeto Endereco completo
    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
        // Se o objeto Endereco for setado, também atualiza o idEndereco
        if (endereco != null) {
            this.idEndereco = endereco.getId();
        } else {
            this.idEndereco = null;
        }
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", senha='[PROTECTED]'" + // Não exibir senha em logs!
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", idEndereco=" + idEndereco +
                ", endereco=" + (endereco != null ? endereco.getLogradouro() + ", " + endereco.getNumero() : "null") +
                '}';
    }
}