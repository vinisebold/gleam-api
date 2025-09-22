package com.gleam.backend.controller;

import com.gleam.backend.dto.ClienteDTO;
import com.gleam.backend.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Controller REST para a gestão de Clientes.
 * Expõe os endpoints da API para as operações de CRUD (Criar, Ler, Atualizar, Apagar)
 * relacionadas a clientes.
 */
@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    /**
     * POST /api/clientes: cria um novo cliente.
     *
     * @param dto O corpo da requisição com os dados do cliente a ser criado.
     * @return {@link ResponseEntity} com status 201 (Created), o DTO do novo cliente no corpo,
     * e a URI do novo recurso no cabeçalho 'Location'.
     */
    @PostMapping
    public ResponseEntity<ClienteDTO> createCliente(@RequestBody ClienteDTO dto) {
        ClienteDTO novoCliente = clienteService.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoCliente.id()).toUri();
        return ResponseEntity.created(uri).body(novoCliente);
    }

    /**
     * GET /api/clientes: lista todos os clientes de forma paginada.
     *
     * @param pageable Parâmetros de paginação.
     * @return {@link ResponseEntity} com status 200 (OK) e uma página (Page) de ClienteDTOs no corpo.
     */
    @GetMapping
    public ResponseEntity<Page<ClienteDTO>> getAllClientes(Pageable pageable) {
        Page<ClienteDTO> clientes = clienteService.findAll(pageable);
        return ResponseEntity.ok(clientes);
    }

    /**
     * GET /api/clientes/{‘id’}: busca um cliente específico pelo seu ‘ID’.
     *
     * @param id O ID do cliente a ser procurado.
     * @return {@link ResponseEntity} com status 200 (OK) e o ClienteDTO correspondente no corpo.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Long id) {
        ClienteDTO cliente = clienteService.findById(id);
        return ResponseEntity.ok(cliente);
    }

    /**
     * PUT /api/clientes/{id}: atualiza um cliente existente.
     *
     * @param id  O ‘ID’ do cliente a ser atualizado.
     * @param dto O corpo da requisição com os novos dados do cliente.
     * @return {@link ResponseEntity} com status 200 (OK) e o ClienteDTO atualizado no corpo.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> updateCliente(@PathVariable Long id, @RequestBody ClienteDTO dto) {
        ClienteDTO clienteAtualizado = clienteService.update(id, dto);
        return ResponseEntity.ok(clienteAtualizado);
    }

    /**
     * DELETE /api/clientes/{id}: apaga um cliente.
     *
     * @param id O ID do cliente a ser apagado.
     * @return {@link ResponseEntity} com status 204 (No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}