package com.gleam.backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal; // ALTERAÇÃO: Import para valores monetários

@Entity
@Data
public class ItemVenda {
    @EmbeddedId
    private ItemVendaId id;

    @ManyToOne
    @MapsId("idVenda")
    @JoinColumn(name = "id_venda")
    private Venda venda;

    @ManyToOne
    @MapsId("idProduto")
    @JoinColumn(name = "id_produto")
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false, precision = 10, scale = 2) // ALTERAÇÃO: Mapeamento para DECIMAL
    private BigDecimal precoUnitario; // ALTERAÇÃO: de Double para BigDecimal
}