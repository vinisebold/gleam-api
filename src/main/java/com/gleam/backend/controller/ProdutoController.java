package com.gleam.backend.controller;

import com.gleam.backend.dto.ProdutoDTO;
import com.gleam.backend.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ProdutoDTO save(@RequestBody ProdutoDTO produtoDTO) {
        produtoService.save(produtoDTO);
        return produtoDTO;
    }

    @GetMapping("/{id}/quantidade-total")
    public Long getQuantidadeTotal(@PathVariable Long id) {
        return produtoService.getQuantidadeTotal(id);
    }
}