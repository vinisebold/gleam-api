package com.gleam.backend.repository;

import com.gleam.backend.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório Spring Data JPA para a entidade {@link Fornecedor}.
 * <p>
 * Fornece todos os métodos de CRUD para a entidade Fornecedor.
 */
@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
}