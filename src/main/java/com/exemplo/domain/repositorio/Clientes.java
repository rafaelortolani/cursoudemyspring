package com.exemplo.domain.repositorio;

import com.exemplo.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Clientes extends JpaRepository<Cliente, Integer> {

    List<Clientes> findByNomeLike(String nome);

    boolean existsByNome(String nome);
}
