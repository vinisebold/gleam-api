package com.gleam.backend.controller;

import com.gleam.backend.dto.EstoqueCategoriaDTO;
import com.gleam.backend.dto.EstoqueGlobalDTO;
import com.gleam.backend.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller REST para fornecer dados e resumos sobre o inventário.
 */
@RestController
@RequestMapping("/api/estoque")
@RequiredArgsConstructor
public class EstoqueController {

    private final ProdutoService produtoService;

    /**
     * GET /api/estoque/resumo-por-categoria: obtém um resumo do inventário agrupado por categoria.
     *
     * @return {@link ResponseEntity} com status 200 (OK) e uma lista com o resumo de cada categoria.
     */
    @GetMapping("/resumo-por-categoria")
    public ResponseEntity<List<EstoqueCategoriaDTO>> getResumoPorCategoria() {
        List<EstoqueCategoriaDTO> resumo = produtoService.getResumoEstoquePorCategoria();
        return ResponseEntity.ok(resumo);
    }

    /**
     * GET /api/estoque/resumo-global: obtém um resumo global do inventário.
     *
     * @return {@link ResponseEntity} com status 200 (OK) e um DTO com os totais de quantidade e custo.
     */
    @GetMapping("/resumo-global")
    public ResponseEntity<EstoqueGlobalDTO> getResumoGlobal() {
        EstoqueGlobalDTO resumoGlobal = produtoService.getResumoGlobalEstoque();
        return ResponseEntity.ok(resumoGlobal);
    }
}