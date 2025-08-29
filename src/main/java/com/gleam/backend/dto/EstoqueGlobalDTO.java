package com.gleam.backend.dto;

import java.math.BigDecimal;

/**
 * DTO para representar o resumo global do estoque.
 * @param quantidadeTotal Quantidade total de todos os produtos em estoque.
 * @param valorTotalCusto Soma do preço de custo de todos os produtos em estoque.
 */
public record EstoqueGlobalDTO(
        long quantidadeTotal,
        BigDecimal valorTotalCusto
) {}