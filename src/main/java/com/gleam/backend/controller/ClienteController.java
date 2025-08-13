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
@CrossOrigin(origins = "http://localhost:4200") // Permite comunicação com o Angular
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Endpoint para criar (cadastrar) um novo cliente
    @PostMapping
    public ResponseEntity<?> createCliente(@RequestBody ClienteDTO dto) {
        try {
            Cliente novoCliente = clienteService.save(dto);
            return ResponseEntity.ok(novoCliente);
        } catch (Exception e) {
            // Retorna uma mensagem de erro clara em caso de falha (ex: nome vazio, email/cpf duplicado)
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint para buscar todos os clientes cadastrados
    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        return ResponseEntity.ok().build(); // Placeholder
    }

    // Endpoint para buscar um cliente específico pelo seu ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        // ClienteRepository clienteRepository = ...
        // return clienteRepository.findById(id)
        //         .map(ResponseEntity::ok)
        //         .orElse(ResponseEntity.notFound().build());
        return ResponseEntity.ok().build(); // Placeholder
    }
}