package com.gleam.backend.repository.specification;

import com.gleam.backend.model.Produto;
import com.gleam.backend.model.StatusProduto;
import org.springframework.data.jpa.domain.Specification;

public class ProdutoSpecification {

    /**
     * Cria uma Specification que combina todos os critérios de filtro.
     */
    public static Specification<Produto> filtrarPor(Long fornecedorId, StatusProduto status) {
        return Specification
                .where(comFiltroDeFornecedor(fornecedorId))
                .and(comFiltroDeStatus(status));
    }

    private static Specification<Produto> comFiltroDeFornecedor(Long fornecedorId) {
        if (fornecedorId == null) {
            return null; // Retorna null para não aplicar este filtro
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("fornecedor").get("id"), fornecedorId);
    }

    private static Specification<Produto> comFiltroDeStatus(StatusProduto status) {
        if (status == null) {
            return null; // Retorna null para não aplicar este filtro
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }
}