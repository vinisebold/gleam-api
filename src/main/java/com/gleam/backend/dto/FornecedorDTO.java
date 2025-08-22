package com.gleam.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) para a entidade Fornecedor.
 * Define a estrutura de dados (JSON) que é enviada e recebida
 * pela API para todas as operações relacionadas a fornecedores.
 */
@Data
public class FornecedorDTO {

    /**
     * O ID único do fornecedor.
     * Ignorado na criação, mas retornado pela API e usado para atualizações.
     */
    private Long id;

    // --- Campos Cadastrais ---

    /**
     * O nome fantasia ou razão social do fornecedor.
     */
    private String nome;

    /**
     * O CNPJ do fornecedor.
     */
    private String cnpj;

    /**
     * O telefone de contato principal.
     */
    private String telefone;

    /**
     * Um campo de texto livre para descrições ou anotações sobre o fornecedor.
     */
    private String descricao;

    // --- Campos de Prefixo de Código ---

    /**
     * O prefixo de código para produtos da categoria "Anel".
     */
    private String codigoAnel;

    /**
     * O prefixo de código para produtos da categoria "Bracelete".
     */
    private String codigoBracelete;

    /**
     * O prefixo de código para produtos da categoria "Colar".
     */
    private String codigoColar;

    /**
     * O prefixo de código para produtos da categoria "Brinco".
     */
    private String codigoBrinco;

    /**
     * O prefixo de código para produtos da categoria "Pulseira".
     */
    private String codigoPulseira;

    /**
     * O prefixo de código para produtos da categoria "Pingente".
     */
    private String codigoPingente;

    /**
     * O prefixo de código para produtos da categoria "Conjunto".
     */
    private String codigoConjunto;

    /**
     * O prefixo de código para produtos da categoria "Berloque".
     */
    private String codigoBerloque;

    /**
     * O prefixo de código para produtos da categoria "Piercing".
     */
    private String codigoPiercing;

    // --- Campos de Auditoria (Apenas para Resposta da API) ---

    /**

     * A data e hora em que o fornecedor foi criado.
     * Este campo é apenas para leitura e é retornado pela API.
     */
    private LocalDateTime dataCriacao;

    /**
     * A data e hora da última atualização do fornecedor.
     * Este campo é apenas para leitura e é retornado pela API.
     */
    private LocalDateTime dataAtualizacao;
}