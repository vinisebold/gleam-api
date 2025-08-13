package com.gleam.backend.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProdutoDTO {
    private String nome;
    private String descricao;
    private BigDecimal precoVenda;
    private BigDecimal precoCusto;
    private Integer acabamento;
    private String codigoFornecedor;
    private String imagem;
    private Long idCategoria;
    private Long idFornecedor;
}