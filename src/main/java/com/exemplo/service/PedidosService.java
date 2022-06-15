package com.exemplo.service;

import com.exemplo.domain.entity.Pedido;
import com.exemplo.rest.dto.PedidoDTO;

public interface PedidosService {
    Pedido salvar(PedidoDTO dto);
}
