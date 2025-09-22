package com.gleam.backend.dto;

import java.math.BigDecimal;

/**
 * DTO para representar o resumo global do inventário.
 *
 * @param quantidadeTotal A quantidade total de todos os produtos em estoque.
 * @param valorTotalCusto A soma do preço de custo de todos os produtos em estoque.
 */
public record EstoqueGlobalDTO(
        long quantidadeTotal,
        BigDecimal valorTotalCusto
) {
}