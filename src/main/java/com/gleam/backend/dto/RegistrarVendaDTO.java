package com.gleam.backend.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class RegistrarVendaDTO {
    // --- Campos para ENTRADA (o que você envia no Postman) ---
    private Long clienteId;
    private Integer situacao;
    private Integer formaPagamento;
    private Integer numeroParcelas;

    // --- Campos para SAÍDA (o que a API retorna) ---
    private Long id;
    private String nome;
    private String nomeCliente;
    private BigDecimal precoTotalVenda;
    private List<ItemVendidoDTO> itens;
    private LocalDateTime dataCriacao;
}