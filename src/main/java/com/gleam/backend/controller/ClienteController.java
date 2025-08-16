package com.gleam.backend.controller;

import com.gleam.backend.dto.ClienteDTO;
import com.gleam.backend.model.Cliente;
import com.gleam.backend.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    /**
     * Endpoint para CRIAR (cadastrar) um novo cliente.
     * Chama o service, que irá limpar o cache.
     */
    @PostMapping
    public ResponseEntity<?> createCliente(@RequestBody ClienteDTO dto) {
        try {
            Cliente novoCliente = clienteService.save(dto);
            return ResponseEntity.ok(novoCliente);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint para LER (buscar) todos os clientes cadastrados.
     * Chama o service, que irá usar o cache.
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clienteService.findAll();
        return ResponseEntity.ok(clientes);
    }

    /**
     * Endpoint para LER (buscar) um cliente específico pelo seu ID.
     * Chama o service, que irá usar o cache.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getClienteById(@PathVariable Long id) {
        try {
            Cliente cliente = clienteService.findById(id);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint para ATUALIZAR (editar) um cliente existente.
     * Chama o service, que irá limpar o cache.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCliente(@PathVariable Long id, @RequestBody ClienteDTO dto) {
        try {
            Cliente clienteAtualizado = clienteService.update(id, dto);
            return ResponseEntity.ok(clienteAtualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint para APAGAR um cliente.
     * Chama o service, que irá limpar o cache.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        try {
            clienteService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
