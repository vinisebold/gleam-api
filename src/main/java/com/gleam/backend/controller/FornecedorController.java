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
 * Controller REST para gerir a entidade Fornecedor.
 * Expõe os endpoints da API para todas as operações de CRUD relacionadas a fornecedores.
 */
@RestController
@RequestMapping("/api/fornecedores")
@RequiredArgsConstructor
public class FornecedorController {

    private final FornecedorService fornecedorService;

    /**
     * Endpoint para criar um novo fornecedor.
     * @param dto O corpo da requisição com os dados do fornecedor.
     * @return Resposta 201 Created com o DTO do novo fornecedor no corpo e a URI no cabeçalho Location.
     */
    @PostMapping
    public ResponseEntity<FornecedorDTO> createFornecedor(@RequestBody FornecedorDTO dto) {
        FornecedorDTO novoFornecedor = fornecedorService.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoFornecedor.id()).toUri();
        return ResponseEntity.created(uri).body(novoFornecedor);
    }

    /**
     * Endpoint para listar todos os fornecedores de forma paginada.
     * @param pageable Parâmetros de paginação (ex: ?page=0&size=10&sort=nome,asc).
     * @return Uma página (Page) de FornecedorDTOs.
     */
    @GetMapping
    public ResponseEntity<Page<FornecedorDTO>> getAllFornecedores(Pageable pageable) {
        Page<FornecedorDTO> fornecedores = fornecedorService.findAll(pageable);
        return ResponseEntity.ok(fornecedores);
    }

    /**
     * Endpoint para buscar um fornecedor específico pelo seu ID.
     * @param id O ID do fornecedor.
     * @return O FornecedorDTO correspondente.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FornecedorDTO> getFornecedorById(@PathVariable Long id) {
        FornecedorDTO fornecedor = fornecedorService.findById(id);
        return ResponseEntity.ok(fornecedor);
    }

    /**
     * Endpoint para atualizar um fornecedor existente.
     * @param id O ID do fornecedor a ser atualizado.
     * @param dto O corpo da requisição com os novos dados.
     * @return O FornecedorDTO atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FornecedorDTO> updateFornecedor(@PathVariable Long id, @RequestBody FornecedorDTO dto) {
        FornecedorDTO fornecedorAtualizado = fornecedorService.update(id, dto);
        return ResponseEntity.ok(fornecedorAtualizado);
    }

    /**
     * Endpoint para apagar um fornecedor.
     * @param id O ID do fornecedor a ser apagado.
     * @return Resposta 204 No Content, indicando sucesso sem corpo de resposta.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFornecedor(@PathVariable Long id) {
        fornecedorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}