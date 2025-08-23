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
 * Controller REST para gerir a entidade Cliente.
 * Expõe os endpoints da API para todas as operações de CRUD relacionadas a clientes.
 */
@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    /**
     * Endpoint para criar um novo cliente.
     * @param dto O corpo da requisição com os dados do cliente.
     * @return Resposta 201 Created com o DTO do novo cliente no corpo e a URI no cabeçalho Location.
     */
    @PostMapping
    public ResponseEntity<ClienteDTO> createCliente(@RequestBody ClienteDTO dto) {
        ClienteDTO novoCliente = clienteService.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoCliente.id()).toUri();
        return ResponseEntity.created(uri).body(novoCliente);
    }

    /**
     * Endpoint para listar todos os clientes de forma paginada.
     * @param pageable Parâmetros de paginação (ex: ?page=0&size=10&sort=nome,asc).
     * @return Uma página (Page) de ClienteDTOs.
     */
    @GetMapping
    public ResponseEntity<Page<ClienteDTO>> getAllClientes(Pageable pageable) {
        Page<ClienteDTO> clientes = clienteService.findAll(pageable);
        return ResponseEntity.ok(clientes);
    }

    /**
     * Endpoint para buscar um cliente específico pelo seu ID.
     * @param id O ID do cliente.
     * @return O ClienteDTO correspondente.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Long id) {
        ClienteDTO cliente = clienteService.findById(id);
        return ResponseEntity.ok(cliente);
    }

    /**
     * Endpoint para atualizar um cliente existente.
     * @param id O ID do cliente a ser atualizado.
     * @param dto O corpo da requisição com os novos dados.
     * @return O ClienteDTO atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> updateCliente(@PathVariable Long id, @RequestBody ClienteDTO dto) {
        ClienteDTO clienteAtualizado = clienteService.update(id, dto);
        return ResponseEntity.ok(clienteAtualizado);
    }

    /**
     * Endpoint para apagar um cliente.
     * @param id O ID do cliente a ser apagado.
     * @return Resposta 204 No Content, indicando sucesso sem corpo de resposta.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}