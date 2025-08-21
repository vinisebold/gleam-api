package com.gleam.backend.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class RegistrarVendaDTO {
    // --- Campos para ENTRADA (o que você envia no Postman) ---

    private String nomeCliente;
    private Integer situacao;
    private Integer formaPagamento;// 1 pix, 2 dinheiro, 3 credito, 4 debito
    private Integer numeroParcelas;
    private List<Long> produtoIds;

    // --- Campos para SAÍDA (o que a API retorna) ---
    private Long id;
    private String nome;
    private BigDecimal precoTotalVenda;
    private List<ItemVendidoDTO> itens;
    private LocalDateTime dataCriacao;
}