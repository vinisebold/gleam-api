package com.gleam.backend.repository;

import com.gleam.backend.model.RegistrarVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrarVendaRepository extends JpaRepository<RegistrarVenda, Long> {
    // Por enquanto, não precisamos de métodos personalizados aqui.
    // O JpaRepository já nos dá tudo o que precisamos (save, findById, findAll, etc.).
}