package com.gleam.backend.repository;

import com.gleam.backend.model.MovimentacaoEstoque;
import com.gleam.backend.enums.TipoMovimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoque, Long> {
    @Query("SELECT m FROM MovimentacaoEstoque m WHERE m.produto.id = :idProduto AND m.tipo = :tipo")
    List<MovimentacaoEstoque> findByProdutoIdAndTipo(Long idProduto, TipoMovimentacao tipo);
}