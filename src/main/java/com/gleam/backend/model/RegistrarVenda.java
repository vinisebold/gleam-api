package com.gleam.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "registrar_vendas")
public class RegistrarVenda {

    // ... (Constantes)
    public static final Integer SITUACAO_PENDENTE = 0;
    public static final Integer SITUACAO_PAGO = 1;
    public static final Integer PAGAMENTO_PIX = 1;
    public static final Integer PAGAMENTO_DINHEIRO = 2;
    public static final Integer PAGAMENTO_CREDITO = 3;
    public static final Integer PAGAMENTO_DEBITO = 4;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    // A RELAÇÃO CORRETA COM O CLIENTE
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // ... (outros campos como situacao, formaPagamento, etc.)
    @Column(nullable = false)
    private Integer situacao;

    @Column(nullable = false)
    private Integer formaPagamento;

    @Column(nullable = false)
    private BigDecimal precoTotalVenda;

    @Column(name = "numero_parcelas")
    private Integer numeroParcelas;

    @OneToMany(mappedBy = "registrarVenda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemVendido> itens = new ArrayList<>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
}