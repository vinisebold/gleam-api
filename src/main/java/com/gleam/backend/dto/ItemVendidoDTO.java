package com.gleam.backend.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ItemVendidoDTO {
    private Long id;
    private Long produtoOriginalId;
    private String nome;
    private BigDecimal precoVenda;
    private BigDecimal lucro;
    private LocalDateTime dataVenda;
    private String categoria;
}