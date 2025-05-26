package br.com.fiap.fintechflow.exception;

// Exceção para quando uma entidade esperada não é encontrada no banco de dados ou sistema
public class EntidadeNaoEncontradaException extends BusinessException {

  private static final long serialVersionUID = 1L;

  public EntidadeNaoEncontradaException(String message) {
    super(message);
  }

  public EntidadeNaoEncontradaException(String message, Throwable cause) {
    super(message, cause);
  }
}
