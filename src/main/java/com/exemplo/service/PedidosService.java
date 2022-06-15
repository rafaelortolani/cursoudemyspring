package com.exemplo.service;

import com.exemplo.domain.entity.Pedido;
import com.exemplo.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidosService {
    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);
}
