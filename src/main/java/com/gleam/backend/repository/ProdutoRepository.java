package com.gleam.backend.repository;

import com.gleam.backend.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    /**
     * Busca uma página de produtos que pertencem a um fornecedor específico.
     * O Spring Data JPA cria a consulta automaticamente com base no nome do método.
     * @param fornecedorId O ID do Fornecedor.
     * @param pageable As informações de paginação (página, tamanho).
     * @return Uma página de Produtos.
     */
    Page<Produto> findByFornecedorId(Long fornecedorId, Pageable pageable);
}