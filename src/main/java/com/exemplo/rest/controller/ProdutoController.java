package com.exemplo.rest.controller;

import com.exemplo.domain.entity.Produto;
import com.exemplo.domain.repository.Produtos;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private Produtos produtos;

    public ProdutoController(Produtos produtos){
        this.produtos = produtos;
    }

    @GetMapping("/{id}")
    public Produto getProdutoById(@PathVariable Integer id){
        return produtos.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid Produto produto){
        produtos.save(produto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void delete (@PathVariable Integer id){
        produtos.findById(id)
                .map(produto -> {
                    produtos.delete(produto);
                    return produto;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid Produto produto){
        produtos.findById(id)
                .map(produtoExistente -> {
                    produto.setId(produtoExistente.getId());
                    produtos.save(produto);
                    return produto;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/")
    public List<Produto> find (Produto filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return produtos.findAll(example);
    }
}
