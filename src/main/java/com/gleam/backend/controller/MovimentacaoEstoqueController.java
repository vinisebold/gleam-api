package com.gleam.backend.controller;

import com.gleam.backend.dto.MovimentacaoEstoqueDTO;
import com.gleam.backend.service.MovimentacaoEstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movimentacoes-estoque")
public class MovimentacaoEstoqueController {
    @Autowired
    private MovimentacaoEstoqueService movimentacaoEstoqueService;

    @PostMapping
    public MovimentacaoEstoqueDTO save(@RequestBody MovimentacaoEstoqueDTO movimentacaoDTO) {
        movimentacaoEstoqueService.save(movimentacaoDTO);
        return movimentacaoDTO;
    }
}