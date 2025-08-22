package com.gleam.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) para a entidade Cliente.
 * Define a estrutura de dados (JSON) que é enviada e recebida
 * pela API para todas as operações relacionadas a clientes.
 */
@Data
public class ClienteDTO {

    /**
     * O ID único do cliente.
     * Ignorado na criação, mas retornado pela API e usado para atualizações.
     */
    private Long id;

    /**
     * O nome completo do cliente.
     */
    private String nome;

    /**
     * O número de telefone de contato do cliente.
     */
    private String telefone;

    /**
     * O endereço de e-mail do cliente.
     */
    private String email;

    // --- Campos de Auditoria (Apenas para Resposta da API) ---

    /**
     * A data e hora em que o cliente foi criado.
     * Este campo é apenas para leitura e é retornado pela API.
     */
    private LocalDateTime dataCriacao;

    /**
     * A data e hora da última atualização do cliente.
     * Este campo é apenas para leitura e é retornado pela API.
     */
    private LocalDateTime dataAtualizacao;
}