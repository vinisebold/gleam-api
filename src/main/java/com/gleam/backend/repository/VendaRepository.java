package com.gleam.backend.repository;

import com.gleam.backend.model.StatusVenda;
import com.gleam.backend.model.Venda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade Venda.
 * Fornece todos os métodos CRUD básicos para as transações de venda.
 */
@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
    Page<Venda> findByStatus(StatusVenda status, Pageable pageable);
    // Futuramente adicionar aqui métodos para relatórios,
    // como, por exemplo, buscar vendas por cliente ou por período.
}
