package com.gleam.backend.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para a entidade RegistrarVenda.
 * Define a estrutura de dados (JSON) para as operações de venda.
 */
@Data
public class RegistrarVendaDTO {
    // --- Campos de ENTRADA (Enviados no corpo da requisição POST) ---
    private Long clienteId;
    private Integer situacao;
    private Integer formaPagamento;
    private Integer numeroParcelas;

    // --- Campos de SAÍDA (Retornados pela API como resposta) ---
    private Long id;
    private String nome;
    private String nomeCliente;
    private BigDecimal precoTotalVenda;

    /**
     * O item detalhado que foi vendido nesta transação.
     * Alterado de uma lista para um único objeto para refletir a regra de negócio.
     */
    private ItemVendidoDTO item;

    private LocalDateTime dataCriacao;
}