package br.com.fiap.fintechflow.exception;

// Exceção genérica para erros de regra de negócio
public class BusinessException extends Exception {

    private static final long serialVersionUID = 1L;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}