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

    /**
     * Cria e salva um novo produto no banco de dados.
     * @param produtoDTO Os dados do novo produto.
     * @return A entidade Produto salva.
     */
    public Produto save(ProdutoDTO produtoDTO) {
        // 1. Busca o Fornecedor para garantir que ele existe.
        Fornecedor fornecedor = fornecedorRepository.findById(produtoDTO.getIdFornecedor())
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado com o ID: " + produtoDTO.getIdFornecedor()));

        // 2. Obtém o prefixo do código com base na categoria informada.
        String prefixo = getPrefixoPorCategoria(fornecedor, produtoDTO.getCategoria());
        if (prefixo == null || prefixo.trim().isEmpty()) {
            throw new EntityNotFoundException(
                    "O fornecedor '" + fornecedor.getNome() + "' não possui um prefixo de código definido para a categoria '" + produtoDTO.getCategoria() + "'."
            );
        }

        Produto produto = new Produto();
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setPrecoVenda(produtoDTO.getPrecoVenda());
        produto.setPrecoCusto(produtoDTO.getPrecoCusto());
        produto.setImagem(produtoDTO.getImagem());
        produto.setCodigoFornecedor(prefixo);

        // 3. Define o campo de texto da categoria diretamente com o valor do DTO.
        produto.setCategoria(produtoDTO.getCategoria());

        // 4. Converte o índice do acabamento para o enum correspondente.
        Integer acabamentoIndex = produtoDTO.getAcabamento();
        if (acabamentoIndex != null && acabamentoIndex >= 0 && acabamentoIndex < Acabamento.values().length) {
            produto.setAcabamento(Acabamento.values()[acabamentoIndex]);
        } else {
            throw new IllegalArgumentException("Índice de acabamento inválido: " + acabamentoIndex);
        }

        produto.setFornecedor(fornecedor);

        return produtoRepository.save(produto);
    }

    /**
     * Atualiza um produto existente.
     * @param id O ID do produto a ser atualizado.
     * @param produtoDTO Os novos dados para o produto.
     * @return A entidade Produto atualizada.
     */
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
        produtoExistente.setDescricao(produtoDTO.getDescricao());
        produtoExistente.setPrecoVenda(produtoDTO.getPrecoVenda());
        produtoExistente.setPrecoCusto(produtoDTO.getPrecoCusto());
        produtoExistente.setImagem(produtoDTO.getImagem());
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

    /**
     * Apaga um produto pelo seu ID.
     * @param id O ID do produto a ser apagado.
     */
    public void delete(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new EntityNotFoundException("Produto não encontrado com o ID: " + id);
        }
        produtoRepository.deleteById(id);
    }

    /**
     * Calcula a quantidade total em estoque para um produto.
     * @param idProduto O ID do produto.
     * @return A quantidade em estoque.
     */
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

    /**
     * Método auxiliar privado para obter o prefixo de código correto do fornecedor
     * com base no nome da categoria.
     * @param fornecedor A entidade Fornecedor.
     * @param nomeCategoria O nome da categoria (ex: "Anel", "Colar").
     * @return O prefixo do código como uma String.
     */
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
