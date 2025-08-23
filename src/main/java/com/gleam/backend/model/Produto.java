package com.gleam.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade que representa um Produto no sistema, seja em estoque ou vendido.
 * Esta é a fonte única da verdade para cada item físico rastreado.
 */
@Data
@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private BigDecimal precoVenda;

    private BigDecimal precoCusto;

    private String idReferencia;

    private String categoria;

    @Enumerated(EnumType.STRING)
    private AcabamentoProduto acabamento;

    @Enumerated(EnumType.STRING)
    private StatusProduto status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_fornecedor", nullable = false)
    private Fornecedor fornecedor;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    private LocalDateTime dataVenda;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = StatusProduto.EM_ESTOQUE;
        }
    }
}