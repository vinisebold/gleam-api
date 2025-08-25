package com.gleam.backend.repository;

import com.gleam.backend.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto> {
    // Os métodos de contagem e busca simples podem ser removidos daqui se não forem usados em outros lugares,
    // pois a Specification cuidará das buscas filtradas.
    // Manterei um para o seu método 'countDisponiveis' que é específico.
    long countByStatus(com.gleam.backend.model.StatusProduto status);
}