package com.exemplo.rest.controller;

import com.exemplo.domain.entity.ItemPedido;
import com.exemplo.domain.entity.Pedido;
import com.exemplo.domain.enums.StatusPedido;
import com.exemplo.rest.dto.AtualizacaoStatusPedidoDTO;
import com.exemplo.rest.dto.InformacaoItemPedidoDTO;
import com.exemplo.rest.dto.InformacoesPedidoDTO;
import com.exemplo.rest.dto.PedidoDTO;
import com.exemplo.service.PedidosService;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.DateFormatter;
import javax.validation.Valid;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public Integer save(@RequestBody @Valid PedidoDTO dto){
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("{id}")
    public InformacoesPedidoDTO getById(@PathVariable  Integer id){
        return service.obterPedidoCompleto(id)
                .map(p ->
                    converter(p)
                )
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    }

    private InformacoesPedidoDTO converter(Pedido pedido){
        return InformacoesPedidoDTO.builder().codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .status(pedido.getStatus().name())
                .total(pedido.getTotal())
                .itens(converter(pedido.getItemPedido()))
                .build();

    }

    private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens){
        if(CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }

        return itens.stream().map(item -> InformacaoItemPedidoDTO.builder()
                .descricaoProduto(item.getProduto().getDescricao())
                .precoUnitario(item.getProduto().getPreco())
                .quantidade(item.getQuantidade())
                .build()).collect(Collectors.toList());

    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable Integer id, @RequestBody AtualizacaoStatusPedidoDTO dto){
        String statusPedido = dto.getNovoStatus();
        service.atualizaStatus(id, StatusPedido.valueOf(statusPedido));
    }
}
