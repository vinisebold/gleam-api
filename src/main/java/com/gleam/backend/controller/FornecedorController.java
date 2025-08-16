package com.gleam.backend.controller;

import com.gleam.backend.dto.FornecedorDTO;
import com.gleam.backend.model.Fornecedor;
import com.gleam.backend.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    /**
     * Endpoint para CRIAR um novo fornecedor.
     * HTTP POST /api/fornecedores
     */
    @PostMapping
    public ResponseEntity<Fornecedor> createFornecedor(@RequestBody FornecedorDTO dto) {
        Fornecedor novoFornecedor = fornecedorService.save(dto);
        return ResponseEntity.ok(novoFornecedor);
    }

    /**
     * Endpoint para LER todos os fornecedores.
     * HTTP GET /api/fornecedores
     * Chama o método do serviço para aproveitar o cache.
     */
    @GetMapping
    public ResponseEntity<List<Fornecedor>> getAllFornecedores() {
        List<Fornecedor> fornecedores = fornecedorService.findAll();
        return ResponseEntity.ok(fornecedores);
    }

    /**
     * Endpoint para ATUALIZAR um fornecedor existente.
     * HTTP PUT /api/fornecedores/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFornecedor(@PathVariable Long id, @RequestBody FornecedorDTO dto) {
        try {
            Fornecedor fornecedorAtualizado = fornecedorService.update(id, dto);
            return ResponseEntity.ok(fornecedorAtualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint para APAGAR um fornecedor.
     * HTTP DELETE /api/fornecedores/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFornecedor(@PathVariable Long id) {
        try {
            fornecedorService.delete(id);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
