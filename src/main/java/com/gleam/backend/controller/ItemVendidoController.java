package com.gleam.backend.controller;

import com.gleam.backend.dto.ItemVendidoDTO;
import com.gleam.backend.dto.RegistrarVendaDTO;
import com.gleam.backend.service.ItemVendidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/itens-vendidos")
@RequiredArgsConstructor
public class ItemVendidoController {

    private final ItemVendidoService itemVendidoService;

    /**
     * Endpoint para REGISTRAR a venda de um único produto.
     * Recebe o ID do produto na URL e os detalhes da venda no corpo da requisição.
     * Exemplo: POST /api/itens-vendidos/vender/1
     */
    @PostMapping("/vender/{produtoId}")
    public ResponseEntity<RegistrarVendaDTO> venderProduto(
            @PathVariable Long produtoId,
            @RequestBody RegistrarVendaDTO detalhesVendaDTO) { // <-- Aceita o corpo da requisição

        // Chama o novo método do serviço, passando ambos os parâmetros
        RegistrarVendaDTO reciboDaVenda = itemVendidoService.registrarVendaDeProdutoUnico(produtoId, detalhesVendaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(reciboDaVenda);
    }

    /**
     * Endpoint para LISTAR todos os itens vendidos.
     */
    @GetMapping
    public ResponseEntity<Page<ItemVendidoDTO>> listarItensVendidos(Pageable pageable) {
        Page<ItemVendidoDTO> paginaDeItens = itemVendidoService.findAllItensVendidos(pageable);
        return ResponseEntity.ok(paginaDeItens);
    }
}