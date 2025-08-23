package com.gleam.backend.dto;

import com.gleam.backend.model.FormaPagamento;
import com.gleam.backend.model.StatusVenda;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para receber os dados da requisição de registro de uma nova venda.
 */
public record RegistrarVendaRequestDto(
        Long produtoId,
        Long clienteId,
        BigDecimal precoVenda,
        FormaPagamento formaPagamento,
        Integer totalParcelas,
        StatusVenda status,
        LocalDate dataVencimento // Opcional, para vendas PENDENTE
) {

}
