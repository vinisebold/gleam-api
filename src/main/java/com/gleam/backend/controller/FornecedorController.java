package com.gleam.backend.controller;

import com.gleam.backend.dto.FornecedorDTO;
import com.gleam.backend.model.Fornecedor;
import com.gleam.backend.repository.FornecedorRepository;
import com.gleam.backend.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fornecedores")
@CrossOrigin(origins = "http://localhost:4200")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @Autowired
    private FornecedorRepository fornecedorRepository; // Injetar o repositório

    // Endpoint para criar um novo fornecedor
    @PostMapping
    public ResponseEntity<Fornecedor> createFornecedor(@RequestBody FornecedorDTO dto) {
        Fornecedor novoFornecedor = fornecedorService.save(dto);
        return ResponseEntity.ok(novoFornecedor);
    }

    // Endpoint para buscar todos os fornecedores (útil para listagens)
    @GetMapping
    public ResponseEntity<List<Fornecedor>> getAllFornecedores() {

        List<Fornecedor> fornecedores = fornecedorRepository.findAll();
        return ResponseEntity.ok(fornecedores);
    }
}
