package com.gleam.backend.repository;

import com.gleam.backend.dto.EstoqueCategoriaDTO;
import com.gleam.backend.dto.EstoqueGlobalDTO;
import com.gleam.backend.model.Produto;
import com.gleam.backend.model.StatusProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório Spring Data JPA para a entidade {@link Produto}.
 * <p>
 * Estende {@link JpaSpecificationExecutor} para permitir a criação de
 * consultas dinâmicas com base em critérios (Specifications).
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto> {

    long countByStatus(StatusProduto status);

    /**
     * Agrega os dados de todos os produtos em estoque e os agrupa por categoria.
     * Para cada categoria, calcula a quantidade de itens e a soma total do preço de custo.
     *
     * @return Uma lista de {@link EstoqueCategoriaDTO} com o resumo do estoque por categoria.
     */
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
     * Calcula um resumo global de todo o inventário.
     * A consulta soma a quantidade total de itens e o valor total de custo de todos
     * os produtos com o status EM_ESTOQUE.
     *
     * @return Um {@link EstoqueGlobalDTO} com os valores totais consolidados.
     */
    @Query("SELECT new com.gleam.backend.dto.EstoqueGlobalDTO(" +
            "COUNT(p.id), " +
            "COALESCE(SUM(p.precoCusto), 0)) " +
            "FROM Produto p " +
            "WHERE p.status = com.gleam.backend.model.StatusProduto.EM_ESTOQUE")
    EstoqueGlobalDTO getResumoGlobalEstoque();
}