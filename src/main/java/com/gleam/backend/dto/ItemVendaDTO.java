package com.gleam.backend.dto;

import lombok.Data;

@Data
public class ItemVendaDTO {
    private Long idVenda;
    private Long idProduto;
    private Integer quantidade;
}