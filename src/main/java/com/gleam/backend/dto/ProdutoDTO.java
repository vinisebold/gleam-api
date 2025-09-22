package com.gleam.backend.dto;

import com.gleam.backend.model.AcabamentoProduto;
import com.gleam.backend.model.Produto;
import com.gleam.backend.model.StatusProduto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para a entidade Produto.
 * Usado tanto para enviar dados para a API (request) quanto para receber (response).
 *
 * @param id O identificador único do produto.
 * @param nome O nome do produto.
 * @param precoVenda O preço de venda ao cliente.
 * @param precoCusto O preço de custo do produto.
 * @param categoria A categoria do produto (ex: "Anel").
 * @param acabamento O tipo de acabamento do material.
 * @param idReferencia O código de referência único do produto (sem o prefixo do fornecedor).
 * @param idFornecedor O ‘ID’ do fornecedor associado.
 * @param status O status atual do produto no inventário.
 * @param dataCriacao A data de registo do produto.
 * @param dataAtualizacao A data da última atualização.
 */
public record ProdutoDTO(
        Long id,
        String nome,
        BigDecimal precoVenda,
        BigDecimal precoCusto,
        String categoria,
        AcabamentoProduto acabamento,
        String idReferencia,
        Long idFornecedor,
        StatusProduto status,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao
) {
    /**
     * Construtor de conversão para criar um DTO a partir de uma entidade Produto.
     * @param produto A entidade {@link Produto} de origem.
     */
    public ProdutoDTO(Produto produto) {
        this(
                produto.getId(),
                produto.getNome(),
                produto.getPrecoVenda(),
                produto.getPrecoCusto(),
                produto.getCategoria(),
                produto.getAcabamento(),
                produto.getIdReferencia(),
                produto.getFornecedor().getId(),
                produto.getStatus(),
                produto.getDataCriacao(),
                produto.getDataAtualizacao()
        );
    }
}