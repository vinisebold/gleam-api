package com.gleam.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade que representa um Produto no inventário.
 * Cada registo nesta tabela é um item único e disponível para venda.
 */
@Data
@Entity
@Table(name = "produtos")
public class Produto {

    // --- Constantes de Domínio ---

    /** Status do produto: 0 indica que está disponível no stock. */
    public static final Integer STATUS_DISPONIVEL = 0;
    /** Status do produto: 1 indica que o produto já foi vendido. */
    public static final Integer STATUS_VENDIDO = 1;

    /** Acabamento do produto: 0 para Dourado. */
    public static final Integer ACABAMENTO_DOURADO = 0;
    /** Acabamento do produto: 1 para Banho Dourado. */
    public static final Integer ACABAMENTO_BANHO_DOURADO = 1;
    /** Acabamento do produto: 2 para Prata. */
    public static final Integer ACABAMENTO_PRATA = 2;
    /** Acabamento do produto: 3 para Banho de Prata. */
    public static final Integer ACABAMENTO_BANHO_PRATA = 3;
    /** Acabamento do produto: 4 para Aço. */
    public static final Integer ACABAMENTO_ACO = 4;

    // --- Campos da Tabela ---

    /**
     * Identificador único do produto, gerado automaticamente pelo banco de dados.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome descritivo do produto. Ex: "Colar de Prata com Pedra da Lua".
     */
    private String nome;

    /**
     * Preço pelo qual o produto é vendido ao cliente final.
     */
    private BigDecimal precoVenda;

    /**
     * Preço que a loja pagou pelo produto ao fornecedor.
     */
    private BigDecimal precoCusto;

    /**
     * Código interno ou referência do produto fornecido pelo fornecedor.
     */
    private String codigoFornecedor;

    /**
     * Categoria do produto. Ex: "Anel", "Colar", "Brinco".
     */
    private String categoria;

    /**
     * Tipo de acabamento da peça, representado por um número inteiro.
     * Utiliza as constantes ACABAMENTO_* desta classe.
     */
    private Integer acabamento;

    /**
     * Situação atual do produto no stock, representado por um número inteiro.
     * Utiliza as constantes STATUS_* desta classe.
     */
    private Integer status;

    // --- Relacionamentos ---

    /**
     * Ligação com a entidade Fornecedor. Muitos produtos podem pertencer a um fornecedor.
     * `FetchType.LAZY` otimiza a performance, carregando os dados do fornecedor apenas quando necessário.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_fornecedor") // Define a coluna de chave estrangeira na tabela 'produtos'.
    private Fornecedor fornecedor;

    // --- Campos de Auditoria ---

    /**
     * Data e hora exatas em que o produto foi criado.
     * O valor é gerado automaticamente pelo Hibernate na primeira vez que o registo é salvo.
     * `updatable = false` impede que este campo seja alterado em atualizações.
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    /**
     * Data e hora da última atualização do produto.
     * O valor é atualizado automaticamente pelo Hibernate sempre que o registo é modificado.
     */
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    // --- Métodos de Ciclo de Vida (JPA Callbacks) ---

    /**
     * Este método é executado automaticamente pelo JPA antes de um novo produto ser persistido (salvo) pela primeira vez.
     * Garante que todo novo produto seja criado com o status "Disponível".
     */
    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = STATUS_DISPONIVEL;
        }
    }
}