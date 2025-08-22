package com.gleam.backend.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) para a entidade ItemVendido.
 * Define a estrutura de dados (JSON) que representa um único item dentro de uma venda.
 * Este DTO é usado principalmente como parte da resposta da API de vendas.
 */
@Data
public class ItemVendidoDTO {

    /**
     * O ID único do registo do item vendido.
     */
    private Long id;

    /**
     * O ID que o produto tinha na tabela de inventário antes de ser vendido.
     * Útil para rastreabilidade.
     */
    private Long produtoOriginalId;

    /**
     * O nome do produto no momento da venda.
     */
    private String nome;

    /**
     * O preço pelo qual este item foi vendido.
     */
    private BigDecimal precoVenda;

    /**
     * O lucro obtido com a venda deste item específico.
     */
    private BigDecimal lucro;

    /**
     * A data e hora exatas em que a venda deste item ocorreu.
     */
    private LocalDateTime dataVenda;

    /**
     * A categoria do produto no momento da venda.
     */
    private String categoria;
}