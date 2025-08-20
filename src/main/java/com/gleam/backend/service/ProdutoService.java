package com.gleam.backend.service;

import com.gleam.backend.dto.ProdutoDTO;
import com.gleam.backend.enums.Acabamento;
import com.gleam.backend.enums.TipoMovimentacao;
import com.gleam.backend.model.Fornecedor;
import com.gleam.backend.model.MovimentacaoEstoque;
import com.gleam.backend.model.Produto;
import com.gleam.backend.repository.FornecedorRepository;
import com.gleam.backend.repository.MovimentacaoEstoqueRepository;
import com.gleam.backend.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private FornecedorRepository fornecedorRepository;
    @Autowired
    private MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;

    public Produto save(ProdutoDTO produtoDTO) {
        Fornecedor fornecedor = fornecedorRepository.findById(produtoDTO.getIdFornecedor())
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado com o ID: " + produtoDTO.getIdFornecedor()));

        Produto produto = new Produto();
        produto.setNome(produtoDTO.getNome());

        produto.setPrecoVenda(produtoDTO.getPrecoVenda());
        produto.setPrecoCusto(produtoDTO.getPrecoCusto());

        produto.setCodigoFornecedor(produtoDTO.getCodigoFornecedor());
        produto.setCategoria(produtoDTO.getCategoria());
        Integer acabamentoIndex = produtoDTO.getAcabamento();
        if (acabamentoIndex != null && acabamentoIndex >= 0 && acabamentoIndex < Acabamento.values().length) {
            produto.setAcabamento(Acabamento.values()[acabamentoIndex]);
        } else {
            throw new IllegalArgumentException("Índice de acabamento inválido: " + acabamentoIndex);
        }
        produto.setFornecedor(fornecedor);
        return produtoRepository.save(produto);
    }

    public Produto update(Long id, ProdutoDTO produtoDTO) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));
        Fornecedor fornecedor = fornecedorRepository.findById(produtoDTO.getIdFornecedor())
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado com o ID: " + produtoDTO.getIdFornecedor()));
        String prefixo = getPrefixoPorCategoria(fornecedor, produtoDTO.getCategoria());
        if (prefixo == null || prefixo.trim().isEmpty()) {
            throw new EntityNotFoundException(
                    "O fornecedor '" + fornecedor.getNome() + "' não possui um prefixo de código definido para a categoria '" + produtoDTO.getCategoria() + "'."
            );
        }
        produtoExistente.setNome(produtoDTO.getNome());

        produtoExistente.setPrecoVenda(produtoDTO.getPrecoVenda());
        produtoExistente.setPrecoCusto(produtoDTO.getPrecoCusto());
        ;
        produtoExistente.setCodigoFornecedor(prefixo);
        produtoExistente.setCategoria(produtoDTO.getCategoria());
        Integer acabamentoIndex = produtoDTO.getAcabamento();
        if (acabamentoIndex != null && acabamentoIndex >= 0 && acabamentoIndex < Acabamento.values().length) {
            produtoExistente.setAcabamento(Acabamento.values()[acabamentoIndex]);
        } else {
            throw new IllegalArgumentException("Índice de acabamento inválido: " + acabamentoIndex);
        }
        produtoExistente.setFornecedor(fornecedor);
        return produtoRepository.save(produtoExistente);
    }

    public void delete(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new EntityNotFoundException("Produto não encontrado com o ID: " + id);
        }
        produtoRepository.deleteById(id);
    }

    public Long getQuantidadeTotal(Long idProduto) {
        if (!produtoRepository.existsById(idProduto)) {
            throw new EntityNotFoundException("Produto não encontrado com o ID: " + idProduto);
        }
        List<MovimentacaoEstoque> entradas = movimentacaoEstoqueRepository.findByProdutoIdAndTipo(idProduto, TipoMovimentacao.ENTRADA);
        List<MovimentacaoEstoque> saidas = movimentacaoEstoqueRepository.findByProdutoIdAndTipo(idProduto, TipoMovimentacao.SAIDA);
        long totalEntradas = entradas.stream().mapToLong(MovimentacaoEstoque::getQuantidade).sum();
        long totalSaidas = saidas.stream().mapToLong(MovimentacaoEstoque::getQuantidade).sum();
        return totalEntradas - totalSaidas;
    }

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Page<Produto> findAll(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    public Produto findById(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));
    }

    /**
     * Busca uma lista paginada de produtos por ID do fornecedor.
     * @param fornecedorId O ID do fornecedor para filtrar.
     * @param pageable Informações de paginação.
     * @return Uma página de produtos.
     */
    public Page<Produto> findByFornecedor(Long fornecedorId, Pageable pageable) {
        return produtoRepository.findByFornecedorId(fornecedorId, pageable);
    }

    private String getPrefixoPorCategoria(Fornecedor fornecedor, String nomeCategoria) {
        if (nomeCategoria == null) return null;
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
            default: return null;
        }
    }
}
