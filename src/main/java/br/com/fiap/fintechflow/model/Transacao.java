package br.com.fiap.fintechflow.model;

import java.time.LocalDateTime;

public class Transacao {
    private int id;
    private Integer idContaOrigem; // Pode ser null para depósito, por exemplo
    private Integer idContaDestino; // Pode ser null para saque, por exemplo
    private String tipo; // Ex: "Depósito", "Saque", "Transferência", "Recebimento de Transferência"
    private double valor;
    private LocalDateTime dataHora;
    private String descricao;

    public Transacao() {
    }

    public Transacao(int id, Integer idContaOrigem, Integer idContaDestino, String tipo, double valor, LocalDateTime dataHora, String descricao) {
        this.id = id;
        this.idContaOrigem = idContaOrigem;
        this.idContaDestino = idContaDestino;
        this.tipo = tipo;
        this.valor = valor;
        this.dataHora = dataHora;
        this.descricao = descricao;
    }

    // Construtor sem ID para inserção
    public Transacao(Integer idContaOrigem, Integer idContaDestino, String tipo, double valor, LocalDateTime dataHora, String descricao) {
        this(0, idContaOrigem, idContaDestino, tipo, valor, dataHora, descricao);
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIdContaOrigem() {
        return idContaOrigem;
    }

    public void setIdContaOrigem(Integer idContaOrigem) {
        this.idContaOrigem = idContaOrigem;
    }

    public Integer getIdContaDestino() {
        return idContaDestino;
    }

    public void setIdContaDestino(Integer idContaDestino) {
        this.idContaDestino = idContaDestino;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}