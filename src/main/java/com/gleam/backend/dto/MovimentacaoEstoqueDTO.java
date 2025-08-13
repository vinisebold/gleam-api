package com.gleam.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MovimentacaoEstoqueDTO {
    private Long idProduto;
    private Integer tipo; // << ALTERADO para Integer (1 para ENTRADA, 2 para SAIDA)
    private Integer quantidade;
    private String observacao;
    private LocalDateTime dataMovimentacao;
}