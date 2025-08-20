package com.gleam.backend.controller;

import com.gleam.backend.dto.ItemVendidoDTO;
import com.gleam.backend.service.ItemVendidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/itens-vendidos") // Endpoint atualizado
@RequiredArgsConstructor
public class ItemVendidoController {

    private final ItemVendidoService itemVendidoService;

    /**
     * Endpoint para REGISTRAR a venda de um produto.
     * Exemplo: POST /api/itens-vendidos/vender/1
     */
    @PostMapping("/vender/{produtoId}")
    public ResponseEntity<ItemVendidoDTO> venderProduto(@PathVariable Long produtoId) {
        ItemVendidoDTO itemVendidoDTO = itemVendidoService.registrarVenda(produtoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemVendidoDTO);
    }

    /**
     * Endpoint para LISTAR todas as vendas já realizadas.
     * Exemplo: GET /api/itens-vendidos
     */
    @GetMapping
    public ResponseEntity<Page<ItemVendidoDTO>> listarItensVendidos(Pageable pageable) {
        Page<ItemVendidoDTO> paginaDeVendas = itemVendidoService.findAll(pageable);
        return ResponseEntity.ok(paginaDeVendas);
    }
}