package br.com.fiap.fintechflow.model;

import java.time.LocalDateTime; // Importado para LocalDateTime

public class Transacao {
    private int id; // Corresponde à coluna ID em TB_TRANSACOES
    private Integer idContaOrigem; // FK para TB_CONTAS, pode ser NULL para depósitos/saques
    private Integer idContaDestino; // FK para TB_CONTAS, pode ser NULL para saques
    private String tipo; // Corresponde à coluna TIPO (ex: "DEPOSITO", "SAQUE", "TRANSFERENCIA")
    private double valor; // Corresponde à coluna VALOR
    private LocalDateTime dataHora; // Corresponde à coluna DATA_HORA (TIMESTAMP)
    private String descricao; // Corresponde à coluna DESCRICAO

    // Construtor padrão
    public Transacao() {
    }

    // Construtor completo
    public Transacao(int id, Integer idContaOrigem, Integer idContaDestino, String tipo, double valor, LocalDateTime dataHora, String descricao) {
        this.id = id;
        this.idContaOrigem = idContaOrigem;
        this.idContaDestino = idContaDestino;
        this.tipo = tipo;
        this.valor = valor;
        this.dataHora = dataHora;
        this.descricao = descricao;
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

    @Override
    public String toString() {
        return "Transacao{" +
                "id=" + id +
                ", idContaOrigem=" + idContaOrigem +
                ", idContaDestino=" + idContaDestino +
                ", tipo='" + tipo + '\'' +
                ", valor=" + valor +
                ", dataHora=" + dataHora +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}