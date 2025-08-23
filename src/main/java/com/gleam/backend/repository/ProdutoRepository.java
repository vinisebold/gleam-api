package com.gleam.backend.repository;

import com.gleam.backend.model.Produto;
import com.gleam.backend.model.StatusProduto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Page<Produto> findByFornecedorIdAndStatus(Long fornecedorId, StatusProduto status, Pageable pageable);
    Page<Produto> findByFornecedorId(Long fornecedorId, Pageable pageable);
    Page<Produto> findByStatus(StatusProduto status, Pageable pageable);

    long countByStatus(StatusProduto status);
}