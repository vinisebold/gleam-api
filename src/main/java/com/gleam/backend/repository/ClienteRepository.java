package com.gleam.backend.repository;

import com.gleam.backend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório Spring Data JPA para a entidade {@link Cliente}.
 * <p>
 * Fornece todos os métodos de CRUD (Criar, Ler, Atualizar, Apagar)
 * para a entidade Cliente sem a necessidade de implementação explícita.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}