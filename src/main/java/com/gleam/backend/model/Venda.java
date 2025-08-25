package com.gleam.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidade que representa uma transação de Venda no sistema.
 * Armazena os detalhes financeiros, o cliente e o produto associado.
 */
@Data
@Entity
@Table(name = "vendas")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Relacionamento com a peça vendida.
     * Muitas vendas podem referenciar o mesmo Produto,
     * permitindo histórico de vendas e cancelamentos.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    // Relacionamento com o cliente. Muitas Vendas podem pertencer a um Cliente.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    private BigDecimal precoVenda;

    // Lucro é calculado e armazenado p otimizar relatórios financeiros.
    private BigDecimal lucro;

    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    private Integer totalParcelas;

    private Integer parcelasPagas;

    @Enumerated(EnumType.STRING)
    private StatusVenda status;

    // Data de vencimento para pagamento de vendas pendentes.
    private LocalDate dataVencimento;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dataCriacao;
}