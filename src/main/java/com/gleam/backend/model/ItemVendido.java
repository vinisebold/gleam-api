package com.gleam.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade que representa um item individual dentro de uma Venda.
 * Funciona como um registo histórico imutável, uma "cópia" de um Produto
 * no exato momento em que foi vendido.
 */
@Data
@NoArgsConstructor // Construtor sem argumentos exigido pelo JPA
@Entity
@Table(name = "itens_vendidos")
public class ItemVendido {

    /**
     * Identificador único do item vendido.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Campos Copiados do Produto ---

    /**
     * O ID que o produto tinha na tabela 'produtos' antes de ser vendido e apagado.
     * Útil para rastreabilidade.
     */
    @Column(nullable = false)
    private Long produtoOriginalId;

    /**
     * O nome do produto no momento da venda.
     */
    private String nome;

    /**
     * O preço de custo do produto no momento da venda.
     */
    private BigDecimal precoCusto;

    /**
     * O preço de venda do produto no momento da venda.
     */
    private BigDecimal precoVenda;

    /**
     * O código do fornecedor do produto no momento da venda.
     */
    private String codigoFornecedor;

    /**
     * A categoria do produto no momento da venda.
     */
    private String categoria;

    /**
     * O acabamento do produto no momento da venda.
     */
    private Integer acabamento;

    /**
     * O ID do fornecedor do produto no momento da venda.
     */
    private Long idFornecedor;

    // --- Campos Específicos da Venda ---

    /**
     * O lucro obtido com a venda deste item (precoVenda - precoCusto).
     * Calculado no momento da criação para otimizar relatórios.
     */
    @Column(nullable = false)
    private BigDecimal lucro;

    /**
     * A data e hora exatas em que o item foi vendido.
     * `updatable = false` garante que este registo nunca seja alterado.
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataVenda;

    // --- Relacionamentos ---

    /**
     * Ligação com a entidade RegistrarVenda. Muitos itens podem pertencer a uma única venda.
     * `nullable = false` garante que todo item vendido deve estar associado a um "recibo" de venda.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registrar_venda_id", nullable = false)
    private RegistrarVenda registrarVenda;

    /**
     * Construtor que cria um ItemVendido a partir de uma entidade Produto.
     * Este é o principal meio de criação desta entidade, copiando todos os dados relevantes
     * do produto que está a ser movido do stock para o histórico de vendas.
     *
     * @param produto A entidade Produto a ser vendida.
     */
    public ItemVendido(Produto produto) {
        this.produtoOriginalId = produto.getId();
        this.nome = produto.getNome();
        this.precoCusto = produto.getPrecoCusto();
        this.precoVenda = produto.getPrecoVenda();
        this.codigoFornecedor = produto.getCodigoFornecedor();
        this.categoria = produto.getCategoria();
        this.acabamento = produto.getAcabamento();
        if (produto.getFornecedor() != null) {
            this.idFornecedor = produto.getFornecedor().getId();
        }
        this.lucro = produto.getPrecoVenda().subtract(produto.getPrecoCusto());
        this.dataVenda = LocalDateTime.now();
    }
}