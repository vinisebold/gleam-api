package com.gleam.backend.dto;

import java.math.BigDecimal;

/**
 * DTO para representar o resumo do estoque para uma categoria de produto.
 * @param categoria O nome da categoria (ex: "Anel", "Pulseira").
 * @param quantidade A quantidade total de itens em estoque para esta categoria.
 * @param valorTotalCusto A soma do preço de custo de todos os itens em estoque.
 * @param valorTotalVenda A soma do preço de venda de todos os itens em estoque.
 */
public record EstoqueCategoriaDTO(
        String categoria,
        long quantidade,
        BigDecimal valorTotalCusto,
        BigDecimal valorTotalVenda
) {}