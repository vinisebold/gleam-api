package com.gleam.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "produtos")
public class Produto {

    // --- CONSTANTES DE STATUS (CORRIGIDO) ---
    public static final Integer STATUS_DISPONIVEL = 0;
    public static final Integer STATUS_VENDIDO = 1;

    // --- CONSTANTES DE ACABAMENTO (RECOMENDADO) ---
    public static final Integer ACABAMENTO_DOURADO = 0;
    public static final Integer ACABAMENTO_BANHO_DOURADO = 1;
    public static final Integer ACABAMENTO_PRATA = 2;
    public static final Integer ACABAMENTO_BANHO_PRATA = 3;
    public static final Integer ACABAMENTO_ACO = 4;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private BigDecimal precoVenda;
    private BigDecimal precoCusto;
    private String codigoFornecedor;
    private String categoria;
    private Integer acabamento;
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_fornecedor")
    private Fornecedor fornecedor;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    @PrePersist
    public void prePersist() {
        if (status == null) {

            status = STATUS_DISPONIVEL;
        }
    }
}