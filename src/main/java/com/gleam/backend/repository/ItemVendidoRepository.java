package com.gleam.backend.repository;

import com.gleam.backend.model.ItemVendido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade ItemVendido.
 * Esta interface estende JpaRepository, o que permite ao Spring Data JPA
 * criar e implementar automaticamente os métodos básicos de acesso a dados (CRUD)
 * para a entidade ItemVendido, que tem uma chave primária do tipo Long.
 */
@Repository
public interface ItemVendidoRepository extends JpaRepository<ItemVendido, Long> {
    // Nenhum método personalizado é necessário no momento.
    // Futuramente, métodos para relatórios (ex: findByDataVendaBetween) podem ser adicionados aqui.
}