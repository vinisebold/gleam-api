package com.gleam.backend.repository;

import com.gleam.backend.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade Produto.
 * Esta interface estende JpaRepository, fornecendo automaticamente métodos CRUD (Create, Read, Update, Delete)
 * e a capacidade de criar consultas personalizadas baseadas no nome dos métodos.
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    /**
     * Busca uma página de produtos pertencentes a um fornecedor específico.
     * O Spring Data JPA cria a consulta automaticamente com base no nome do método.
     *
     //@param fornecedorId O ID do fornecedor pelo qual filtrar.
     // @param pageable     O objeto contendo as informações de paginação (número da página, tamanho, ordenação).
     * @return Uma página (Page) de produtos que correspondem ao critério.
     */
    Page<Produto> findByFornecedorId(Long fornecedorId, Pageable pageable);

    /**
     * Busca uma página de produtos com base no seu status (ex: Disponível ou Vendido).

     // @param status   O status do produto para filtrar (utilizando as constantes de Produto.java).
     // @param pageable O objeto de paginação.
     * @return Uma página (Page) de produtos com o status especificado.
     */
    Page<Produto> findByStatus(Integer status, Pageable pageable);

    /**
     * Conta o número total de produtos que possuem um determinado status.
     * Útil para dashboards e relatórios rápidos.

     // @param status O status do produto para contar.
     * @return A contagem total (long) de produtos com o status especificado.
     */
    long countByStatus(Integer status);
}