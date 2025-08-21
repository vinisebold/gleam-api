package com.gleam.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "itens_vendidos")
public class ItemVendido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long produtoOriginalId;
    private String nome;
    private BigDecimal precoCusto;
    private BigDecimal precoVenda;
    private String codigoFornecedor;
    private String categoria;
    private Integer acabamento;
    private Long idFornecedor;
    @Column(nullable = false)
    private BigDecimal lucro;
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataVenda;

    // CORREÇÃO AQUI: Adiciona a relação de volta para a RegistrarVenda
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registrar_venda_id", nullable = false)
    private RegistrarVenda registrarVenda;

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