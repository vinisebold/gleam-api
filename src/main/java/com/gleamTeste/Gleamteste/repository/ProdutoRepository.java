package com.gleamTeste.Gleamteste.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gleamTeste.Gleamteste.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}

