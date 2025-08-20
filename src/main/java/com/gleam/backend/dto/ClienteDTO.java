package com.gleam.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ClienteDTO {
    private Long id;
    private String nome;
    private String telefone;
    private String email;

    // Campos de data adicionados
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
}