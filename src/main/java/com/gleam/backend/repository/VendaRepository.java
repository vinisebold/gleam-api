package com.gleam.backend.repository;

import com.gleam.backend.model.StatusVenda; // Import necessário
import com.gleam.backend.model.Venda;
import org.springframework.data.domain.Page;     // Import necessário
import org.springframework.data.domain.Pageable;  // Import necessário
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    // Método para o card (valor único)
    @Query("SELECT COALESCE(SUM(v.lucro), 0) FROM Venda v " +
            "WHERE v.status IN (com.gleam.backend.model.StatusVenda.PAGO, com.gleam.backend.model.StatusVenda.PENDENTE) " +
            "AND v.dataCriacao >= :inicio AND v.dataCriacao < :fim")
    BigDecimal sumLucroByDataCriacaoBetween(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    // Métodos para o gráfico (série de dados)
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

    // ===== MÉTODO QUE ESTAVA FALTANDO ADICIONADO AQUI =====
    /**
     * Busca vendas de forma paginada, filtrando por um status específico.
     * A consulta é gerada automaticamente pelo Spring Data JPA a partir do nome do método.
     */
    Page<Venda> findByStatus(StatusVenda status, Pageable pageable);
}