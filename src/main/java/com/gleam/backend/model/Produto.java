package com.gleam.backend.model;

import com.gleam.backend.enums.Acabamento;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precoVenda;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precoCusto;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Acabamento acabamento;

    @Column(name = "codigofornecedor", unique = true) // << ALTERADO AQUI
    private String codigoFornecedor; // << ALTERADO AQUI

    private String imagem;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_fornecedor")
    private Fornecedor fornecedor;
}