package com.gleam.backend.repository;

import com.gleam.backend.model.ItemVendido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemVendidoRepository extends JpaRepository<ItemVendido, Long> {
}