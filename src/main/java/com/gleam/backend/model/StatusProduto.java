package com.gleam.backend.model;

/**
 * <p>Define o ciclo de vida de um produto dentro do sistema de inventário.</p>
 *
 * <p>Este status é a peça central na lógica de negócio, determinando
 * a disponibilidade de um ‘item’ para venda e controlando o fluxo de estoque.</p>
 *
 * <ul>
 * <li><b>EM_ESTOQUE:</b> indica que o produto está fisicamente no inventário
 * e disponível para ser incluído em novas vendas.</li>
 * <li><b>VENDIDO:</b> indica que o produto foi associado a uma venda
 * e não está mais disponível nem no estoque e nem fisicamente.</li>
 * </ul>
 */
public enum StatusProduto {
    EM_ESTOQUE,
    VENDIDO
}