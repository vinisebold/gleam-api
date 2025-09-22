package com.gleam.backend.dto;

import com.gleam.backend.model.FormaPagamento;
import com.gleam.backend.model.StatusVenda;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para receber os dados da requisição de registo de uma nova venda.
 * Representa o "payload" que o frontend envia para a API.
 *
 * @param produtoId      O ‘ID’ do produto a ser vendido.
 * @param clienteId      O ID do cliente que está a comprar.
 * @param precoVenda     O preço final da venda.
 * @param formaPagamento A forma de pagamento utilizada.
 * @param totalParcelas  O número total de parcelas.
 * @param status         O status inicial da venda (ex: PAGO, PENDENTE).
 * @param dataVencimento A data de vencimento, caso a venda seja PENDENTE.
 */
public record RegistrarVendaRequestDto(
        Long produtoId,
        Long clienteId,
        BigDecimal precoVenda,
        FormaPagamento formaPagamento,
        Integer totalParcelas,
        StatusVenda status,
        LocalDate dataVencimento
) {
}