package com.gleam.backend.repository;

import com.gleam.backend.model.ItemVenda;
import com.gleam.backend.model.ItemVendaId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, ItemVendaId> {
}