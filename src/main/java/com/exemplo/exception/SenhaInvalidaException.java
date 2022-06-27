package com.exemplo.exception;

public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException(){
        super("Senha Inválida");
    }
}
