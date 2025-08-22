package com.gleam.backend.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) para a entidade Produto.
 * Esta classe define a estrutura de dados (JSON) que é enviada e recebida
 * pela API para todas as operações relacionadas a produtos.
 */
@Data
public class ProdutoDTO {

    // --- Campos de Identificação e Resposta ---

    /**
     * O ID único do produto.
     * Este campo é ignorado ao criar um novo produto, mas é retornado pela API após a criação.
     */
    private Long id;

    /**
     * O nome do produto.
     */
    private String nome;

    /**
     * O preço de venda final para o cliente.
     */
    private BigDecimal precoVenda;
    /**
     * O preço de custo do produto para a loja.
     */
    private BigDecimal precoCusto;
    // --- Campos de Classificação e Atributos ---
    /**
     * A categoria à qual o produto pertence (ex: "Anel", "Colar").
     */
    private String categoria;

    /**
     * O tipo de acabamento da peça.
     * 0 = Dourado
     * 1 = Banho dourado
     * 2 = Prata
     * 3 = Banho de prata
     * 4 = Aço
     */
    private Integer acabamento;

    /**
     * O código de referência do produto dado pelo fornecedor.
     */
    private String codigoFornecedor;

    // --- Campos de Relacionamento ---

    /**
     * O ID do Fornecedor ao qual este produto está associado.
     * Essencial para criar ou atualizar um produto.
     */
    private Long idFornecedor;

    // --- Campos de Estado e Auditoria (geralmente para resposta da API) ---

    /**
     * O status atual do produto no inventário.
     * 0 = Disponível
     * 1 = Vendido
     * Este campo é definido automaticamente pelo sistema.
     */
    private Integer status;

    /**
     * A data e hora em que o produto foi criado.
     * Este campo é apenas para leitura e é retornado pela API.
     */
    private LocalDateTime dataCriacao;

    /**
     * A data e hora da última atualização do produto.
     * Este campo é apenas para leitura e é retornado pela API.
     */
    private LocalDateTime dataAtualizacao;
}