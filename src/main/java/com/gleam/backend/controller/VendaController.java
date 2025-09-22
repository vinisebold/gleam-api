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

/**
 * Controller REST para a gestão de Vendas.
 */
@RestController
@RequestMapping("/api/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService vendaService;

    /**
     * POST /api/vendas: regista uma nova venda.
     *
     * @param dto Os detalhes da venda a ser criada.
     * @return {@link ResponseEntity} com status 201 (Created) e o DTO da nova venda.
     */
    @PostMapping
    public ResponseEntity<VendaResponseDto> registrarVenda(@RequestBody RegistrarVendaRequestDto dto) {
        VendaResponseDto novaVenda = vendaService.registrarVenda(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaVenda);
    }

    /**
     * GET /api/vendas: lista as vendas de forma paginada, com filtro opcional por status.
     * Exemplo: GET /api/vendas?status=PENDENTE&page=0&size=10
     *
     * @param status   O status para filtrar as vendas (opcional).
     * @param pageable Parâmetros de paginação.
     * @return {@link ResponseEntity} com status 200 (OK) e uma página de VendaResponseDto.
     */
    @GetMapping
    public ResponseEntity<Page<VendaResponseDto>> listarVendas(
            @RequestParam(required = false) StatusVenda status,
            Pageable pageable) {

        Page<VendaResponseDto> vendas = vendaService.findAll(pageable, status);
        return ResponseEntity.ok(vendas);
    }

    /**
     * POST /api/vendas/{‘id’}/pagar-parcela: regista o pagamento da próxima parcela de uma venda.
     *
     * @param vendaId O ID da venda a ter a parcela paga.
     * @return {@link ResponseEntity} com status 200 (OK) e a venda atualizada.
     */
    @PostMapping("/{id}/pagar-parcela")
    public ResponseEntity<VendaResponseDto> pagarParcela(@PathVariable("id") Long vendaId) {
        VendaResponseDto vendaAtualizada = vendaService.pagarProximaParcela(vendaId);
        return ResponseEntity.ok(vendaAtualizada);
    }

    /**
     * POST /api/vendas/{‘id’}/cancelar: cancela uma venda existente.
     *
     * @param vendaId O ID da venda a ser cancelada.
     * @return {@link ResponseEntity} com status 200 (OK) e a venda com o status CANCELADO.
     */
    @PostMapping("/{id}/cancelar")
    public ResponseEntity<VendaResponseDto> cancelarVenda(@PathVariable("id") Long vendaId) {
        VendaResponseDto vendaCancelada = vendaService.cancelarVenda(vendaId);
        return ResponseEntity.ok(vendaCancelada);
    }
}