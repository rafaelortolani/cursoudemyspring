package com.exemplo.service;

import com.exemplo.model.Cliente;
import com.exemplo.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientesServices {
    private ClientesRepository repository;

    @Autowired
    public ClientesServices(ClientesRepository repository){
        this.repository = repository;
    }

    public void salvarCliente (Cliente cliente){
        validarCliente(cliente);
        repository.persistir(cliente);
    }

    public void validarCliente(Cliente cliente){
        //Validações
    }
}
