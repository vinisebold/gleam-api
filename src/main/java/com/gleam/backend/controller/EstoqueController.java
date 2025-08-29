package com.gleam.backend.controller;

import com.gleam.backend.dto.EstoqueCategoriaDTO;
import com.gleam.backend.dto.EstoqueGlobalDTO; // <-- Import para o novo DTO
import com.gleam.backend.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/estoque")
@RequiredArgsConstructor
public class EstoqueController {

    private final ProdutoService produtoService;

    /**
     * Endpoint para obter um resumo do inventário, agrupado por categoria.
     */
    @GetMapping("/resumo-por-categoria")
    public ResponseEntity<List<EstoqueCategoriaDTO>> getResumoPorCategoria() {
        List<EstoqueCategoriaDTO> resumo = produtoService.getResumoEstoquePorCategoria();
        return ResponseEntity.ok(resumo);
    }

     //* Endpoint para obter um resumo global do inventário, com os totais de
     //quantidade e valor de custo de todos os produtos em estoque.


    @GetMapping("/resumo-global")
    public ResponseEntity<EstoqueGlobalDTO> getResumoGlobal() {
        EstoqueGlobalDTO resumoGlobal = produtoService.getResumoGlobalEstoque();
        return ResponseEntity.ok(resumoGlobal);
    }
}