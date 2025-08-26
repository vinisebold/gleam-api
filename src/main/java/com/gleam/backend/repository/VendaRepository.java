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

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    Page<Venda> findByStatus(StatusVenda status, Pageable pageable);

    /**
     * Calcula a soma total do lucro de todas as vendas dentro de um intervalo de datas específico.
     * A consulta considera apenas vendas com status PAGO ou PENDENTE.
     *
     * @param inicio Data e hora de início do período (inclusivo).
     * @param fim    Data e hora de fim do período (exclusivo).
     * @return A soma do lucro como BigDecimal. Retorna 0 se não houver vendas.
     */
    @Query("SELECT COALESCE(SUM(v.lucro), 0) FROM Venda v " +
            "WHERE v.status IN (com.gleam.backend.model.StatusVenda.PAGO, com.gleam.backend.model.StatusVenda.PENDENTE) " +
            "AND v.dataCriacao >= :inicio AND v.dataCriacao < :fim")
    BigDecimal sumLucroByDataCriacaoBetween(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );
}