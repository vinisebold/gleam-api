package com.gleam.backend.dto;


import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class VendaDTO {
    private Long idCliente;
    private BigDecimal valorTotal;
    private String status; // Ex: "PENDENTE", "CONCLUIDA", "CANCELADA"
    private LocalDateTime dataVenda;

}