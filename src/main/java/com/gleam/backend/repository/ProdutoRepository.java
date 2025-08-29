package com.gleam.backend.repository;

import com.gleam.backend.dto.EstoqueCategoriaDTO;
import com.gleam.backend.model.Produto;
import com.gleam.backend.model.StatusProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.gleam.backend.dto.EstoqueGlobalDTO;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto> {

    long countByStatus(StatusProduto status);

    @Query("SELECT new com.gleam.backend.dto.EstoqueCategoriaDTO(" +
            "p.categoria, " +
            "COUNT(p.id), " +
            "COALESCE(SUM(p.precoCusto), 0)) " +
            "FROM Produto p " +
            "WHERE p.status = com.gleam.backend.model.StatusProduto.EM_ESTOQUE " +
            "GROUP BY p.categoria " +
            "ORDER BY p.categoria")
    List<EstoqueCategoriaDTO> getResumoEstoquePorCategoria();

    /**
     * Busca um resumo global de todo o estoque.
     * Calcula a quantidade total de itens e a soma total do valor de custo.
     * Considera apenas produtos com o status EM_ESTOQUE.
     * @return Um DTO com os valores totais do inventário.
     */
    @Query("SELECT new com.gleam.backend.dto.EstoqueGlobalDTO(" +
            "COUNT(p.id), " +
            "COALESCE(SUM(p.precoCusto), 0)) " + // <-- Linha do precoVenda REMOVIDA
            "FROM Produto p " +
            "WHERE p.status = com.gleam.backend.model.StatusProduto.EM_ESTOQUE")
    EstoqueGlobalDTO getResumoGlobalEstoque();

}