package com.gleam.backend.controller;

import com.gleam.backend.dto.FornecedorDTO;
import com.gleam.backend.service.FornecedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Controller REST para a gestão de Fornecedores.
 * Expõe os endpoints da API para as operações de CRUD relacionadas a fornecedores.
 */
@RestController
@RequestMapping("/api/fornecedores")
@RequiredArgsConstructor
public class FornecedorController {

    private final FornecedorService fornecedorService;

    /**
     * POST /api/fornecedores: cria um fornecedor.
     *
     * @param dto O corpo da requisição com os dados do fornecedor.
     * @return {@link ResponseEntity} com status 201 (Created) e o DTO do novo fornecedor no corpo.
     */
    @PostMapping
    public ResponseEntity<FornecedorDTO> createFornecedor(@RequestBody FornecedorDTO dto) {
        FornecedorDTO novoFornecedor = fornecedorService.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoFornecedor.id()).toUri();
        return ResponseEntity.created(uri).body(novoFornecedor);
    }

    /**
     * GET /api/fornecedores: lista todos os fornecedores de forma paginada.
     *
     * @param pageable Parâmetros de paginação.
     * @return {@link ResponseEntity} com status 200 (OK) e uma página de FornecedorDTOs.
     */
    @GetMapping
    public ResponseEntity<Page<FornecedorDTO>> getAllFornecedores(Pageable pageable) {
        Page<FornecedorDTO> fornecedores = fornecedorService.findAll(pageable);
        return ResponseEntity.ok(fornecedores);
    }

    /**
     * GET /api/fornecedores/{‘id’} : busca um fornecedor pelo seu ‘ID’.
     *
     * @param id O ID do fornecedor.
     * @return {@link ResponseEntity} com status 200 (OK) e o FornecedorDTO no corpo.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FornecedorDTO> getFornecedorById(@PathVariable Long id) {
        FornecedorDTO fornecedor = fornecedorService.findById(id);
        return ResponseEntity.ok(fornecedor);
    }

    /**
     * PUT /api/fornecedores/{id} : atualiza um fornecedor existente.
     *
     * @param id O ‘ID’ do fornecedor a ser atualizado.
     * @param dto O corpo da requisição com os novos dados.
     * @return {@link ResponseEntity} com status 200 (OK) e o FornecedorDTO atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FornecedorDTO> updateFornecedor(@PathVariable Long id, @RequestBody FornecedorDTO dto) {
        FornecedorDTO fornecedorAtualizado = fornecedorService.update(id, dto);
        return ResponseEntity.ok(fornecedorAtualizado);
    }

    /**
     * DELETE /api/fornecedores/{id}: apaga um fornecedor.
     *
     * @param id O ID do fornecedor a ser apagado.
     * @return {@link ResponseEntity} com status 204 (No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFornecedor(@PathVariable Long id) {
        fornecedorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}