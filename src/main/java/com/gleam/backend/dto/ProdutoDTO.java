package com.gleam.backend.dto;

import com.gleam.backend.model.AcabamentoProduto;
import com.gleam.backend.model.Produto;
import com.gleam.backend.model.StatusProduto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Record para representar um Produto na API.
 * Usado tanto para criar/atualizar (Request) quanto para exibir (Response).
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
     * Construtor para converter uma entidade Produto em um ProdutoDTO.
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