package com.gleam.backend.dto;

import com.gleam.backend.model.FormaPagamento;
import com.gleam.backend.model.StatusVenda;
import com.gleam.backend.model.Venda;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record VendaResponseDto(
        Long id,
        ProdutoDTO produto,
        Long clienteId,
        String nomeCliente,
        BigDecimal precoVenda,
        BigDecimal lucro,
        FormaPagamento formaPagamento,
        Integer totalParcelas,
        Integer parcelasPagas,
        StatusVenda status,
        LocalDate dataVencimento,
        LocalDateTime dataCriacao
) {
    /**
     * Construtor para converter uma entidade Venda em um DTO.
     * Isto é essencial para o VendaService funcionar.
     */
    public VendaResponseDto(Venda venda) {
        this(
                venda.getId(),
                new ProdutoDTO(venda.getProduto()),
                venda.getCliente().getId(),
                venda.getCliente().getNome(),
                venda.getPrecoVenda(),
                venda.getLucro(),
                venda.getFormaPagamento(),
                venda.getTotalParcelas(),
                venda.getParcelasPagas(),
                venda.getStatus(),
                venda.getDataVencimento(),
                venda.getDataCriacao()
        );
    }
}