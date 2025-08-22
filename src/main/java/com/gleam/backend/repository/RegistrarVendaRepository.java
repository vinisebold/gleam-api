package com.gleam.backend.repository;

import com.gleam.backend.model.RegistrarVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade RegistrarVenda.
 * Esta interface estende JpaRepository, o que permite ao Spring Data JPA
 * criar e implementar automaticamente os métodos básicos de acesso a dados (CRUD)
 * para a entidade RegistrarVenda, que tem uma chave primária do tipo Long.
 */
@Repository
public interface RegistrarVendaRepository extends JpaRepository<RegistrarVenda, Long> {
    // Nenhum método personalizado é necessário no momento, pois o JpaRepository
    // já fornece todas as funcionalidades de que precisamos (save, findById, findAll, etc.).
}