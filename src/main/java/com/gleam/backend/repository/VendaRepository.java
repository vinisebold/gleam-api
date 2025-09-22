package com.gleam.backend.repository;

import com.gleam.backend.model.StatusVenda;
import com.gleam.backend.model.Venda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositório Spring Data JPA para a entidade {@link Venda}.
 */
@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    Page<Venda> findByStatus(StatusVenda status, Pageable pageable);

    /**
     * Soma o lucro de todas as vendas (status PAGO ou PENDENTE) num intervalo de datas.
     *
     * @param inicio A data e hora de início do período (inclusivo).
     * @param fim    A data e hora de fim do período (exclusivo).
     * @return A soma do lucro como {@link BigDecimal}. Retorna 0 se não houver vendas no período.
     */
    @Query("SELECT COALESCE(SUM(v.lucro), 0) FROM Venda v " +
            "WHERE v.status IN (com.gleam.backend.model.StatusVenda.PAGO, com.gleam.backend.model.StatusVenda.PENDENTE) " +
            "AND v.dataCriacao >= :inicio AND v.dataCriacao < :fim")
    BigDecimal sumLucroByDataCriacaoBetween(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    /**
     * Busca o lucro total agrupado por dia dentro de um intervalo de datas.
     *
     * @param inicio A data e hora de início do período.
     * @param fim    A data e hora de fim do período.
     * @return Uma lista de arrays de objetos (Object[]), onde cada array contém a data (LocalDate) e o lucro total (BigDecimal) do dia.
     */
    @Query("SELECT CAST(v.dataCriacao AS LocalDate), COALESCE(SUM(v.lucro), 0) " +
            "FROM Venda v " +
            "WHERE v.status IN (com.gleam.backend.model.StatusVenda.PAGO, com.gleam.backend.model.StatusVenda.PENDENTE) " +
            "AND v.dataCriacao >= :inicio AND v.dataCriacao <= :fim " +
            "GROUP BY CAST(v.dataCriacao AS LocalDate)")
    List<Object[]> findLucroAgrupadoPorDia(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    /**
     * Busca o lucro total agrupado por mês dentro de um intervalo de datas.
     *
     * @param inicio A data e hora de início do período.
     * @param fim    A data e hora de fim do período.
     * @return Uma lista de arrays de objetos (Object[]), onde cada array contém o número do mês (Integer) e o lucro total (BigDecimal) do mês.
     */
    @Query("SELECT MONTH(v.dataCriacao), COALESCE(SUM(v.lucro), 0) " +
            "FROM Venda v " +
            "WHERE v.status IN (com.gleam.backend.model.StatusVenda.PAGO, com.gleam.backend.model.StatusVenda.PENDENTE) " +
            "AND v.dataCriacao >= :inicio AND v.dataCriacao <= :fim " +
            "GROUP BY MONTH(v.dataCriacao)")
    List<Object[]> findLucroAgrupadoPorMes(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    /**
     * Desvincula todos os registos de venda de um determinado produto, definindo o campo 'produto_id' como nulo.
     * Utilizado antes de apagar um produto para manter a integridade do histórico de vendas.
     *
     * @param produtoId O ID do produto a ser desvinculado.
     */
    @Modifying
    @Query("UPDATE Venda v SET v.produto = NULL WHERE v.produto.id = :produtoId")
    void desvincularProdutoDeVendas(@Param("produtoId") Long produtoId);
}