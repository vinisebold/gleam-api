package com.gleam.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa um ‘item’ de inventário único no sistema.
 * Esta entidade é a fonte da verdade para cada peça física, rastreando
 * o seu estado, custo, preço e origem.
 */
@Getter
@Setter
@Entity
@Table(name = "produtos")
public class Produto {

    /**
     * Identificador único do produto (chave primária).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome descritivo do produto.
     */
    private String nome;

    /**
     * Preço final de venda ao cliente.
     */
    private BigDecimal precoVenda;

    /**
     * Custo de aquisição do produto junto ao fornecedor.
     */
    private BigDecimal precoCusto;

    /**
     * Código de referência único gerado pelo sistema.
     * Combina o prefixo do fornecedor com um identificador específico do produto.
     */
    private String idReferencia;

    /**
     * Categoria à qual o produto pertence (ex: "Anel", "Colar").
     */
    private String categoria;

    /**
     * Tipo de acabamento do material do produto (ex: PRATA, BANHO_DOURADO).
     *
     * @see AcabamentoProduto
     */
    @Enumerated(EnumType.STRING)
    private AcabamentoProduto acabamento;

    /**
     * Status atual do produto no ciclo de vida do inventário.
     *
     * @see StatusProduto
     */
    @Enumerated(EnumType.STRING)
    private StatusProduto status;

    /**
     * O fornecedor que originou este produto.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_fornecedor", nullable = false)
    private Fornecedor fornecedor;

    /**
     * Data e hora do registro do produto no sistema.
     * Gerado automaticamente na criação.
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    /**
     * Data e hora da última atualização dos dados do produto.
     * Atualizado automaticamente em cada modificação.
     */
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    /**
     * Data e hora em que o produto foi vendido.
     * Permanece nulo enquanto o produto estiver em estoque.
     */
    private LocalDateTime dataVenda;

    /**
     * Garante que o status padrão de um novo produto seja sempre 'EM_ESTOQUE'
     * antes de ser persistido no banco de dados.
     */
    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = StatusProduto.EM_ESTOQUE;
        }
    }
}