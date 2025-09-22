package com.gleam.backend.dto;

import java.math.BigDecimal;

/**
 * DTO para representar o resumo do estoque para uma única categoria.
 *
 * @param categoria       O nome da categoria.
 * @param quantidade      A quantidade total de itens em estoque nesta categoria.
 * @param valorTotalCusto A soma do preço de custo de todos os itens nesta categoria.
 */
public record EstoqueCategoriaDTO(
        String categoria,
        long quantidade,
        BigDecimal valorTotalCusto
) {
}