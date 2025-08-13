package com.gleam.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cnpj;
    private String telefone;
    private String descricao;

    @Column(name = "codigo_anel")
    private String codigoAnel;

    @Column(name = "codigo_bracelete")
    private String codigoBracelete;

    @Column(name = "codigo_colar")
    private String codigoColar;

    @Column(name = "codigo_brinco")
    private String codigoBrinco;

    @Column(name = "codigo_pulseira")
    private String codigoPulseira;

    @Column(name = "codigo_pingente")
    private String codigoPingente;

    @Column(name = "codigo_conjunto")
    private String codigoConjunto;

    @Column(name = "codigo_berloque")
    private String codigoBerloque;

    @Column(name = "codigo_piercing")
    private String codigoPiercing;
}