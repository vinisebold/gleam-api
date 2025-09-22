package com.gleam.backend.dto;

import com.gleam.backend.model.Fornecedor;

import java.time.LocalDateTime;

/**
 * DTO para a entidade Fornecedor.
 * Representa os dados de um fornecedor na API.
 *
 * @param id              O identificador único do fornecedor.
 * @param nome            O nome do fornecedor.
 * @param cnpj            O CNPJ do fornecedor.
 * @param telefone        O telefone de contato.
 * @param descricao       Anotações sobre o fornecedor.
 * @param dataCriacao     A data de registo do fornecedor.
 * @param dataAtualizacao A data da última atualização.
 */
public record FornecedorDTO(
        Long id,
        String nome,
        String cnpj,
        String telefone,
        String descricao,
        String codigoAnel,
        String codigoBracelete,
        String codigoColar,
        String codigoBrinco,
        String codigoPulseira,
        String codigoPingente,
        String codigoConjunto,
        String codigoBerloque,
        String codigoPiercing,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao
) {
    /**
     * Construtor de conversão para criar um DTO a partir de uma entidade Fornecedor.
     *
     * @param fornecedor A entidade {@link Fornecedor} de origem.
     */
    public FornecedorDTO(Fornecedor fornecedor) {
        this(
                fornecedor.getId(),
                fornecedor.getNome(),
                fornecedor.getCnpj(),
                fornecedor.getTelefone(),
                fornecedor.getDescricao(),
                fornecedor.getCodigoAnel(),
                fornecedor.getCodigoBracelete(),
                fornecedor.getCodigoColar(),
                fornecedor.getCodigoBrinco(),
                fornecedor.getCodigoPulseira(),
                fornecedor.getCodigoPingente(),
                fornecedor.getCodigoConjunto(),
                fornecedor.getCodigoBerloque(),
                fornecedor.getCodigoPiercing(),
                fornecedor.getDataCriacao(),
                fornecedor.getDataAtualizacao()
        );
    }
}