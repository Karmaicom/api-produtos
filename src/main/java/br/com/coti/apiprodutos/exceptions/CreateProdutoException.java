package br.com.coti.apiprodutos.exceptions;

public class CreateProdutoException extends RuntimeException {
    public CreateProdutoException(String message) {
        super(message);
    }

    public CreateProdutoException(String message,  Throwable cause) {
        super(message, cause);
    }
}
