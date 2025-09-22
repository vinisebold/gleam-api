package com.gleam.backend.dto;

import com.gleam.backend.model.Cliente;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) para a entidade Cliente.
 * Define a representação de um cliente na API, expondo os dados de forma segura.
 *
 * @param id              O identificador único do cliente.
 * @param nome            O nome completo do cliente.
 * @param telefone        O telefone de contato.
 * @param cpf             O CPF do cliente.
 * @param descricao       Anotações ou observações sobre o cliente.
 * @param email           O e-mail do cliente.
 * @param dataCriacao     A data de registo do cliente.
 * @param dataAtualizacao A data da última atualização.
 */
public record ClienteDTO(
        Long id,
        String nome,
        String telefone,
        String cpf,
        String descricao,
        String email,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao
) {
    /**
     * Construtor de conversão para criar um DTO a partir de uma entidade Cliente.
     *
     * @param cliente A entidade {@link Cliente} de origem.
     */
    public ClienteDTO(Cliente cliente) {
        this(
                cliente.getId(),
                cliente.getNome(),
                cliente.getTelefone(),
                cliente.getCpf(),
                cliente.getDescricao(),
                cliente.getEmail(),
                cliente.getDataCriacao(),
                cliente.getDataAtualizacao()
        );
    }
}