package com.exemplo.rest.controller;

import com.exemplo.exception.PedidoNaoEncontradoException;
import com.exemplo.exception.RegraNegocioException;
import com.exemplo.rest.ApiErrors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handlesRegraNegocioException(RegraNegocioException ex){
        String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro);
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiErrors handlesPedidoNotFoundException(PedidoNaoEncontradoException ex){
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handlesMethodNotValidException (MethodArgumentNotValidException ex){
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(erro -> erro.getDefaultMessage()).collect(Collectors.toList());

        return new ApiErrors(errors);
    }
}
