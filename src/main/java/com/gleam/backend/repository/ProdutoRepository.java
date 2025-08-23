package com.gleam.backend.repository;

import com.gleam.backend.model.Produto;
import com.gleam.backend.model.StatusProduto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Page<Produto> findByFornecedorId(Long fornecedorId, Pageable pageable);

    /**
     * Busca uma página de produtos com base no seu status.
     *
     * @param status   O status do produto para filtrar (agora um Enum).
     * @param pageable O objeto de paginação.
     * @return Uma página (Page) de produtos com o status especificado.
     */
    Page<Produto> findByStatus(StatusProduto status, Pageable pageable);

    /**
     * Conta o número total de produtos que possuem um determinado status.
     *
     * @param status O status do produto para contar.
     * @return A contagem total (long) de produtos com o status especificado.
     */
    long countByStatus(StatusProduto status);
}