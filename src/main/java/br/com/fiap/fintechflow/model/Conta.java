package br.com.fiap.fintechflow.model;

import java.time.LocalDateTime; // Importado para LocalDateTime

public class Conta {
    private int id; // Corresponde à coluna ID em TB_CONTAS
    private int idUsuario; // Corresponde à coluna ID_USUARIO em TB_CONTAS (FK)
    private String numeroConta; // Corresponde à coluna NUMERO_CONTA
    private String agencia; // Corresponde à coluna AGENCIA
    private double saldo; // Corresponde à coluna SALDO
    private LocalDateTime dataCriacao; // Corresponde à coluna DATA_CRIACAO (TIMESTAMP)

    // Construtor padrão
    public Conta() {
    }

    // Construtor completo
    public Conta(int id, int idUsuario, String numeroConta, String agencia, double saldo, LocalDateTime dataCriacao) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.numeroConta = numeroConta;
        this.agencia = agencia;
        this.saldo = saldo;
        this.dataCriacao = dataCriacao;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", numeroConta='" + numeroConta + '\'' +
                ", agencia='" + agencia + '\'' +
                ", saldo=" + saldo +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}