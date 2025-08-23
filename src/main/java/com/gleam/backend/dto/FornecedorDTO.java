package com.gleam.backend.dto;

import com.gleam.backend.model.Fornecedor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO para representar um Fornecedor na API.
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
     * Construtor para converter uma entidade Fornecedor em um FornecedorDTO.
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