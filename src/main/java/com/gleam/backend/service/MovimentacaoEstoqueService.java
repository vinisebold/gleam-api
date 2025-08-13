package com.gleam.backend.service;

import com.gleam.backend.dto.MovimentacaoEstoqueDTO;
import com.gleam.backend.enums.TipoMovimentacao;
import com.gleam.backend.model.MovimentacaoEstoque;
import com.gleam.backend.model.Produto;
import com.gleam.backend.repository.MovimentacaoEstoqueRepository;
import com.gleam.backend.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class MovimentacaoEstoqueService {

    @Autowired
    private MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public MovimentacaoEstoque save(MovimentacaoEstoqueDTO movimentacaoDTO) {
        // Busca o produto para garantir que ele existe
        Produto produto = produtoRepository.findById(movimentacaoDTO.getIdProduto())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + movimentacaoDTO.getIdProduto()));

        // Validações
        if (movimentacaoDTO.getTipo() == null) {
            throw new IllegalArgumentException("O tipo da movimentação é obrigatório.");
        }
        if (movimentacaoDTO.getQuantidade() == null || movimentacaoDTO.getQuantidade() <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser um número positivo.");
        }

        MovimentacaoEstoque movimentacao = new MovimentacaoEstoque();
        movimentacao.setProduto(produto);

        // << LÓGICA ALTERADA PARA LIDAR COM O NÚMERO (1 ou 2) >>
        Integer tipoIndex = movimentacaoDTO.getTipo();
        if (tipoIndex == 1) { // 1 = ENTRADA
            movimentacao.setTipo(TipoMovimentacao.ENTRADA);
        } else if (tipoIndex == 2) { // 2 = SAIDA
            movimentacao.setTipo(TipoMovimentacao.SAIDA);
        } else {
            // Lança um erro se o número for inválido
            throw new IllegalArgumentException("Tipo de movimentação inválido. Use 1 para ENTRADA ou 2 para SAIDA.");
        }

        movimentacao.setQuantidade(movimentacaoDTO.getQuantidade());
        movimentacao.setDataMovimentacao(movimentacaoDTO.getDataMovimentacao() != null ? movimentacaoDTO.getDataMovimentacao() : LocalDateTime.now());
        movimentacao.setObservacao(movimentacaoDTO.getObservacao());

        return movimentacaoEstoqueRepository.save(movimentacao);
    }
}