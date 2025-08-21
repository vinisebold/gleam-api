package com.gleam.backend.controller;

import com.gleam.backend.dto.RegistrarVendaDTO;
import com.gleam.backend.service.RegistrarVendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registrar-vendas")
@RequiredArgsConstructor
public class RegistrarVendaController {

    private final RegistrarVendaService registrarVendaService;

    @PostMapping
    public ResponseEntity<RegistrarVendaDTO> registrarVenda(@RequestBody RegistrarVendaDTO registrarVendaDTO) {
        RegistrarVendaDTO novaVenda = registrarVendaService.registrarVenda(registrarVendaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaVenda);
    }

    @GetMapping
    public ResponseEntity<Page<RegistrarVendaDTO>> listarVendas(Pageable pageable) {
        Page<RegistrarVendaDTO> paginaDeVendas = registrarVendaService.findAll(pageable);
        return ResponseEntity.ok(paginaDeVendas);
    }
}