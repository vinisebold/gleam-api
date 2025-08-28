package com.gleam.backend.repository;

import com.gleam.backend.model.StatusVenda;
import com.gleam.backend.model.Venda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    Page<Venda> findByStatus(StatusVenda status, Pageable pageable);

    @Query("SELECT COALESCE(SUM(v.lucro), 0) FROM Venda v " +
            "WHERE v.status IN (com.gleam.backend.model.StatusVenda.PAGO, com.gleam.backend.model.StatusVenda.PENDENTE) " +
            "AND v.dataCriacao >= :inicio AND v.dataCriacao < :fim")
    BigDecimal sumLucroByDataCriacaoBetween(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT CAST(v.dataCriacao AS LocalDate), COALESCE(SUM(v.lucro), 0) " +
            "FROM Venda v " +
            "WHERE v.status IN (com.gleam.backend.model.StatusVenda.PAGO, com.gleam.backend.model.StatusVenda.PENDENTE) " +
            "AND v.dataCriacao >= :inicio AND v.dataCriacao <= :fim " +
            "GROUP BY CAST(v.dataCriacao AS LocalDate)")
    List<Object[]> findLucroAgrupadoPorDia(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT MONTH(v.dataCriacao), COALESCE(SUM(v.lucro), 0) " +
            "FROM Venda v " +
            "WHERE v.status IN (com.gleam.backend.model.StatusVenda.PAGO, com.gleam.backend.model.StatusVenda.PENDENTE) " +
            "AND v.dataCriacao >= :inicio AND v.dataCriacao <= :fim " +
            "GROUP BY MONTH(v.dataCriacao)")
    List<Object[]> findLucroAgrupadoPorMes(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}