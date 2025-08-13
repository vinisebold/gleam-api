package com.gleam.backend.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class ItemVendaId implements Serializable {
    private Long idVenda;
    private Long idProduto;

    // Construtor padrão necessário para JPA
    public ItemVendaId() {}

    // Construtor com parâmetros
    public ItemVendaId(Long idVenda, Long idProduto) {
        this.idVenda = idVenda;
        this.idProduto = idProduto;
    }

    // Override equals e hashCode para garantir a correta comparação de chaves compostas
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemVendaId that = (ItemVendaId) o;
        return idVenda.equals(that.idVenda) && idProduto.equals(that.idProduto);
    }

    @Override
    public int hashCode() {
        return 31 * idVenda.hashCode() + idProduto.hashCode();
    }
}