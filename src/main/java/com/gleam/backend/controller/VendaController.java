package com.gleam.backend.controller;

import com.gleam.backend.dto.RegistrarVendaRequestDto;
import com.gleam.backend.dto.VendaResponseDto;
import com.gleam.backend.model.StatusVenda;
import com.gleam.backend.service.VendaService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService vendaService;

    /**
     * Endpoint para registrar uma nova venda.
     *
     * @param dto Os detalhes da venda vindos do frontend.
     * @return O DTO da venda recém-criada.
     */
    @PostMapping
    public ResponseEntity<VendaResponseDto> registrarVenda(@RequestBody RegistrarVendaRequestDto dto) {
        VendaResponseDto novaVenda = vendaService.registrarVenda(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaVenda);
    }

    /**
     * Endpoint para listar todas as vendas de forma paginada.
     * Permite filtrar opcionalmente por status (PENDENTE, PAGO, CANCELADA).
     * Exemplo: GET /api/vendas?status=PENDENTE&page=0&size=10
     */
    @GetMapping
    public ResponseEntity<Page<VendaResponseDto>> listarVendas(
            @RequestParam(required = false) StatusVenda status,
            Pageable pageable) {

        // Por enquanto, apenas listamos tudo.
        Page<VendaResponseDto> vendas = vendaService.findAll(pageable, status);
        return ResponseEntity.ok(vendas);
    }

    @PostMapping("/{id}/pagar-parcela")
    public ResponseEntity<VendaResponseDto> pagarParcela(@PathVariable("id") Long vendaId) {
        VendaResponseDto vendaAtualizada = vendaService.pagarProximaParcela(vendaId);
        return ResponseEntity.ok(vendaAtualizada);
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<VendaResponseDto> cancelarVenda(@PathVariable("id") Long vendaId) {
        VendaResponseDto vendaCancelada = vendaService.cancelarVenda(vendaId);
        return ResponseEntity.ok(vendaCancelada);
    }
}
