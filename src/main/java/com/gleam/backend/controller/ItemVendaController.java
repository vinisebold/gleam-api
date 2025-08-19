package com.gleam.backend.controller;

import com.gleam.backend.dto.ItemVendaDTO;
import com.gleam.backend.model.ItemVenda;
import com.gleam.backend.service.ItemVendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itens-venda")
public class ItemVendaController {

    @Autowired
    private ItemVendaService itemVendaService;

    /**
     * Endpoint para ADICIONAR um item a uma venda.
     * HTTP POST /api/itens-venda
     */
    @PostMapping
    public ResponseEntity<?> createItemVenda(@RequestBody ItemVendaDTO dto) {
        try {
            ItemVenda novoItem = itemVendaService.save(dto);
            return ResponseEntity.ok(novoItem);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint para BUSCAR todos os itens de uma venda específica.
     * HTTP GET /api/itens-venda/venda/{idVenda}
     */
    @GetMapping("/venda/{idVenda}")
    public ResponseEntity<List<ItemVenda>> getItensByVenda(@PathVariable Long idVenda) {
        List<ItemVenda> itens = itemVendaService.findByVenda(idVenda);
        return ResponseEntity.ok(itens);
    }
}