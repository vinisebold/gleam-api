package com.gleam.backend.controller;

import com.gleam.backend.dto.EstoqueCategoriaDTO;
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
     * Endpoint para obter um resumo do inventário, com quantidade e valores totais
     * de custo e venda, agrupados por categoria.
     *
     * @return Uma lista com o resumo de cada categoria de produto em estoque.
     */
    @GetMapping("/resumo-por-categoria")
    public ResponseEntity<List<EstoqueCategoriaDTO>> getResumoPorCategoria() {
        List<EstoqueCategoriaDTO> resumo = produtoService.getResumoEstoquePorCategoria();
        return ResponseEntity.ok(resumo);
    }
}