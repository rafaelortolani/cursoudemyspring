package com.exemplo.service.impl;

import com.exemplo.domain.entity.Cliente;
import com.exemplo.domain.entity.ItemPedido;
import com.exemplo.domain.entity.Pedido;
import com.exemplo.domain.entity.Produto;
import com.exemplo.domain.enums.StatusPedido;
import com.exemplo.domain.repository.Clientes;
import com.exemplo.domain.repository.ItensPedido;
import com.exemplo.domain.repository.Pedidos;
import com.exemplo.domain.repository.Produtos;
import com.exemplo.exception.PedidoNaoEncontradoException;
import com.exemplo.exception.RegraNegocioException;
import com.exemplo.rest.dto.ItemPedidoDTO;
import com.exemplo.rest.dto.PedidoDTO;
import com.exemplo.service.PedidosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidosService {

    private final Pedidos repository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItensPedido itensPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository.findById(idCliente).orElseThrow(() -> new RegraNegocioException("Código de cliente inválido"));
        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);
        List<ItemPedido> itemPedidos = converterItens(pedido, dto.getItens());
        repository.save(pedido);
        itensPedidoRepository.saveAll(itemPedidos);
        pedido.setItemPedido(itemPedidos);
        return pedido;
    }

    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> items){
        if (items.isEmpty()){
            throw  new RegraNegocioException("Não é possível realizar um pedido sem itens");
        }
        return  items
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository.findById(idProduto).orElseThrow(() -> new RegraNegocioException("Produto inválido"));
                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());
    }


    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        repository
                .findById(id)
                .map(pedido -> {
                    pedido.setStatus(statusPedido);
                    return repository.save(pedido);
                }).orElseThrow(() -> new PedidoNaoEncontradoException());

    }
}
