package com.gleam.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FornecedorDTO {
    private Long id;
    private String nome;
    private String cnpj;
    private String telefone;
    private String descricao;

    // As datas que serão exibidas no frontend
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    // Códigos de produto
    private String codigoAnel;
    private String codigoBracelete;
    private String codigoColar;
    private String codigoBrinco;
    private String codigoPulseira;
    private String codigoPingente;
    private String codigoConjunto;
    private String codigoBerloque;
    private String codigoPiercing;
}