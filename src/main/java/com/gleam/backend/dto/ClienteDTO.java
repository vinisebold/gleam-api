package com.gleam.backend.dto;

import com.gleam.backend.model.Cliente;
import java.time.LocalDateTime;

/**
 * Record para representar um Cliente na API.
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
     * Construtor para converter uma entidade Cliente em um ClienteDTO.
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