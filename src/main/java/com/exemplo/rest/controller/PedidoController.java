package com.exemplo.rest.controller;

import com.exemplo.domain.entity.Pedido;
import com.exemplo.rest.dto.PedidoDTO;
import com.exemplo.service.PedidosService;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidosService service;

    public PedidoController(PedidosService service){
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody PedidoDTO dto){
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }
}
