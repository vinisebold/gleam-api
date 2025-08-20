package com.gleam.backend.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProdutoDTO {
    private Long id;
    private String nome;
    private BigDecimal precoVenda;
    private BigDecimal precoCusto;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    private Integer acabamento; //
    // Acabamento INT 0 Dourado,
    // 1 Banho dourado,
    // 2 Prata,
    // 3 Banho de prata,
    // 4 Aço


    private String codigoFornecedor;
    private Long idFornecedor;
    private String categoria;


    private Integer status;
    //status INT
    // 0 Disponivel
    // 1 Vendido
}