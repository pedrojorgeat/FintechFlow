package br.com.fiap.fintechflow.model;

import java.util.Objects; // Importado para Objects.hash e Objects.equals

public class Endereco {
    private int id; // Corresponde à coluna ID em TB_ENDERECOS
    private String logradouro; // Corresponde à coluna LOGRADOURO
    private String numero;     // Corresponde à coluna NUMERO
    private String complemento; // Corresponde à coluna COMPLEMENTO (pode ser NULL)
    private String bairro;     // Corresponde à coluna BAIRRO
    private String cidade;     // Corresponde à coluna CIDADE
    private String estado;     // Corresponde à coluna ESTADO
    private String cep;        // Corresponde à coluna CEP

    // Construtor padrão
    public Endereco() {}

    // Construtor sem ID (para inserção de novo endereço)
    public Endereco(String logradouro, String numero, String complemento, String bairro, String cidade, String estado, String cep) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    // Construtor completo (para leitura do banco)
    public Endereco(int id, String logradouro, String numero, String complemento, String bairro, String cidade, String estado, String cep) {
        this.id = id;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = log(logradouro);
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    private String log(String str) {
        System.out.println("Log: " + str);
        return str;
    }
}