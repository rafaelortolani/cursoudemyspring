package com.exemplo.domain.repository;

import com.exemplo.domain.entity.Cliente;
import com.exemplo.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface Pedidos extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByCliente(Cliente cliente);
}
