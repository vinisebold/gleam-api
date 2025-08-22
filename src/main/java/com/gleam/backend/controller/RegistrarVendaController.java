package com.gleam.backend.controller;

import com.gleam.backend.dto.RegistrarVendaDTO;
import com.gleam.backend.service.RegistrarVendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para consultar os registos de Vendas.
 * Este controller expõe endpoints para listar e buscar os "recibos" de vendas
 * que já foram processadas e estão no histórico.
 */
@RestController
@RequestMapping("/api/registrar-vendas")
@RequiredArgsConstructor
public class RegistrarVendaController {

    private final RegistrarVendaService registrarVendaService;

    /**
     * Endpoint para listar todos os "recibos" de vendas já realizadas, de forma paginada.
     * @param pageable Parâmetros de paginação (ex: ?page=0&size=10&sort=dataCriacao,desc).
     * @return Uma página (Page) de RegistrarVendaDTOs.
     */
    @GetMapping
    public ResponseEntity<Page<RegistrarVendaDTO>> listarVendas(Pageable pageable) {
        Page<RegistrarVendaDTO> paginaDeVendas = registrarVendaService.findAll(pageable);
        return ResponseEntity.ok(paginaDeVendas);
    }

    /**
     * Endpoint para buscar um único recibo de venda pelo seu ID.
     * @param id O ID da venda a ser buscada.
     * @return O RegistrarVendaDTO correspondente.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RegistrarVendaDTO> getVendaById(@PathVariable Long id) {
        RegistrarVendaDTO venda = registrarVendaService.findById(id);
        return ResponseEntity.ok(venda);
    }
}