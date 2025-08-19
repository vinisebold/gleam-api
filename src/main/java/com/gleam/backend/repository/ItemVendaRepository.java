package com.gleam.backend.repository;

import com.gleam.backend.model.ItemVenda;
import com.gleam.backend.model.ItemVendaId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface  ItemVendaRepository extends JpaRepository<ItemVenda, ItemVendaId> {

    // Método para encontrar todos os itens de uma venda específica
    List<ItemVenda> findByVendaId(Long idVenda);
}
