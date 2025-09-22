package com.gleam.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Representa o registo histórico e financeiro de uma transação de venda.
 * Vincula um produto a um cliente e armazena os detalhes da transação.
 */
@Data
@Entity
@Table(name = "vendas")
public class Venda {

    /**
     * Identificador único da venda (chave primária).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * O produto vendido.
     * O relacionamento é opcional (nullable) para que o registo da venda
     * permaneça mesmo que o produto seja eventualmente apagado.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = true)
    private Produto produto;

    /**
     * O cliente que realizou a compra.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    /**
     * O valor final pelo qual o produto foi vendido.
     */
    private BigDecimal precoVenda;

    /**
     * O lucro obtido na venda (precoVenda - precoCusto).
     * Armazenado para otimizar a geração de relatórios financeiros.
     */
    private BigDecimal lucro;

    /**
     * A forma de pagamento utilizada na transação.
     */
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    /**
     * O número total de parcelas, se aplicável (ex: 1 para à vista).
     */
    private Integer totalParcelas;

    /**
     * O número de parcelas que já foram pagas.
     */
    private Integer parcelasPagas;

    /**
     * O status financeiro atual da venda (ex: PENDENTE, PAGO).
     *
     * @see StatusVenda
     */
    @Enumerated(EnumType.STRING)
    private StatusVenda status;

    /**
     * Data de vencimento para pagamentos futuros, aplicável a vendas PENDENTE.
     */
    private LocalDate dataVencimento;

    /**
     * Data e hora em que a venda foi registada.
     * Gerado automaticamente na criação.
     */
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dataCriacao;
}