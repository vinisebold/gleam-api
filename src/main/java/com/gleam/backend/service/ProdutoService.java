package com.gleam.backend.service;

import com.gleam.backend.dto.ProdutoDTO;
import com.gleam.backend.enums.Acabamento;
import com.gleam.backend.enums.TipoMovimentacao;
import com.gleam.backend.model.Categoria;
import com.gleam.backend.model.Fornecedor;
import com.gleam.backend.model.MovimentacaoEstoque;
import com.gleam.backend.model.Produto;
import com.gleam.backend.repository.CategoriaRepository;
import com.gleam.backend.repository.FornecedorRepository;
import com.gleam.backend.repository.MovimentacaoEstoqueRepository;
import com.gleam.backend.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private FornecedorRepository fornecedorRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private MovimentacaoEstoqueRepository movimentacaoEstoqueRepository; // << DEPENDÊNCIA ADICIONADA

    public Produto save(ProdutoDTO produtoDTO) {
        // Busca as entidades relacionadas para garantir que existem
        Fornecedor fornecedor = fornecedorRepository.findById(produtoDTO.getIdFornecedor())
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado com o ID: " + produtoDTO.getIdFornecedor()));
        Categoria categoria = categoriaRepository.findById(produtoDTO.getIdCategoria())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com o ID: " + produtoDTO.getIdCategoria()));

        // Lógica para selecionar o prefixo correto com base na categoria
        String prefixo = getPrefixoPorCategoria(fornecedor, categoria.getNome());
        if (prefixo == null || prefixo.trim().isEmpty()) {
            throw new EntityNotFoundException(
                    "O fornecedor '" + fornecedor.getNome() + "' não possui um prefixo de código definido para a categoria '" + categoria.getNome() + "'."
            );
        }

        Produto produto = new Produto();
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setPrecoVenda(produtoDTO.getPrecoVenda());
        produto.setPrecoCusto(produtoDTO.getPrecoCusto());
        produto.setImagem(produtoDTO.getImagem());

        // Atribui o prefixo encontrado automaticamente
        produto.setCodigoFornecedor(prefixo);

        // Lógica para converter o acabamento (continua a mesma)
        Integer acabamentoIndex = produtoDTO.getAcabamento();
        if (acabamentoIndex != null && acabamentoIndex >= 0 && acabamentoIndex < Acabamento.values().length) {
            produto.setAcabamento(Acabamento.values()[acabamentoIndex]);
        } else {
            throw new IllegalArgumentException("Índice de acabamento inválido: " + acabamentoIndex);
        }

        produto.setCategoria(categoria);
        produto.setFornecedor(fornecedor);

        return produtoRepository.save(produto);
    }

    // << MÉTODO ADICIONADO >>
    public Long getQuantidadeTotal(Long idProduto) {
        // Valida se o produto existe antes de calcular o estoque
        if (!produtoRepository.existsById(idProduto)) {
            throw new EntityNotFoundException("Produto não encontrado com o ID: " + idProduto);
        }

        List<MovimentacaoEstoque> entradas = movimentacaoEstoqueRepository.findByProdutoIdAndTipo(idProduto, TipoMovimentacao.ENTRADA);
        List<MovimentacaoEstoque> saidas = movimentacaoEstoqueRepository.findByProdutoIdAndTipo(idProduto, TipoMovimentacao.SAIDA);

        long totalEntradas = entradas.stream().mapToLong(MovimentacaoEstoque::getQuantidade).sum();
        long totalSaidas = saidas.stream().mapToLong(MovimentacaoEstoque::getQuantidade).sum();

        return totalEntradas - totalSaidas;
    }

    // Método auxiliar privado para escolher o prefixo correto
    private String getPrefixoPorCategoria(Fornecedor fornecedor, String nomeCategoria) {
        // A comparação é feita em minúsculas para evitar erros (ex: "Anel" vs "anel")
        switch (nomeCategoria.toLowerCase()) {
            case "anel": return fornecedor.getCodigoAnel();
            case "bracelete": return fornecedor.getCodigoBracelete();
            case "colar": return fornecedor.getCodigoColar();
            case "brinco": return fornecedor.getCodigoBrinco();
            case "pulseira": return fornecedor.getCodigoPulseira();
            case "pingente": return fornecedor.getCodigoPingente();
            case "conjunto": return fornecedor.getCodigoConjunto();
            case "berloque": return fornecedor.getCodigoBerloque();
            case "piercing": return fornecedor.getCodigoPiercing();
            default: return null; // Retorna nulo se a categoria não tiver um prefixo mapeado
        }
    }
}