package com.exemplo.domain.repository;

import com.exemplo.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;


public interface Pedidos extends JpaRepository<Pedido, Integer> {
}
