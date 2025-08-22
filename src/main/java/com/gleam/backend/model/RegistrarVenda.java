package com.gleam.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa o registo de uma Venda, funcionando como um "recibo" mestre.
 * Cada registo agrupa um ou mais itens vendidos numa única transação.
 */
@Data
@Entity
@Table(name = "registrar_vendas")
public class RegistrarVenda {

    // --- Constantes de Domínio ---

    /** Situação da venda: 0 indica que o pagamento está pendente. */
    public static final Integer SITUACAO_PENDENTE = 0;
    /** Situação da venda: 1 indica que a venda foi paga. */
    public static final Integer SITUACAO_PAGO = 1;

    /** Forma de pagamento: 1 para PIX. */
    public static final Integer PAGAMENTO_PIX = 1;
    /** Forma de pagamento: 2 para Dinheiro. */
    public static final Integer PAGAMENTO_DINHEIRO = 2;
    /** Forma de pagamento: 3 para Cartão de Crédito. */
    public static final Integer PAGAMENTO_CREDITO = 3;
    /** Forma de pagamento: 4 para Cartão de Débito. */
    public static final Integer PAGAMENTO_DEBITO = 4;

    // --- Campos da Tabela ---

    /**
     * Identificador único da venda, gerado automaticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome descritivo da venda, gerado automaticamente a partir dos nomes dos produtos.
     * Ex: "Anel Solitário, Colar de Prata".
     */
    @Column(nullable = false)
    private String nome;

    /**
     * O preço total da venda, somando o preço de todos os itens vendidos.
     */
    @Column(nullable = false)
    private BigDecimal precoTotalVenda;

    /**
     * A situação do pagamento da venda (Pendente ou Pago).
     * Utiliza as constantes SITUACAO_* desta classe.
     */
    @Column(nullable = false)
    private Integer situacao;

    /**
     * A forma de pagamento utilizada na venda.
     * Utiliza as constantes PAGAMENTO_* desta classe.
     */
    @Column(nullable = false)
    private Integer formaPagamento;

    /**
     * O número de parcelas, se aplicável (ex: para cartão de crédito).
     */
    @Column(name = "numero_parcelas")
    private Integer numeroParcelas;

    // --- Relacionamentos ---

    /**
     * Ligação com a entidade Cliente. Muitas vendas podem pertencer a um cliente.
     * `nullable = false` garante que toda venda deve estar associada a um cliente.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    /**
     * Lista de todos os itens individuais que compõem esta venda.
     * `cascade = CascadeType.ALL` garante que os ItensVendidos sejam salvos/apagados junto com a Venda.
     */
    @OneToMany(mappedBy = "registrarVenda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemVendido> itens = new ArrayList<>();

    // --- Campos de Auditoria ---

    /**
     * Data e hora exatas em que a venda foi registada.
     * Gerado automaticamente pelo Hibernate na criação.
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
}