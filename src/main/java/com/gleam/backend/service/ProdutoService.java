package com.gleam.backend.service;

import com.gleam.backend.dto.EstoqueCategoriaDTO;
import com.gleam.backend.dto.EstoqueGlobalDTO; // <-- Import necessário
import com.gleam.backend.dto.ProdutoDTO;
import com.gleam.backend.model.Fornecedor;
import com.gleam.backend.model.Produto;
import com.gleam.backend.model.StatusProduto;
import com.gleam.backend.repository.FornecedorRepository;
import com.gleam.backend.repository.ProdutoRepository;
import com.gleam.backend.repository.specification.ProdutoSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final FornecedorRepository fornecedorRepository;

    public Page<ProdutoDTO> listarProdutosComFiltros(Long fornecedorId, StatusProduto status, Pageable pageable) {
        Specification<Produto> spec = ProdutoSpecification.filtrarPor(fornecedorId, status);
        Page<Produto> produtos = produtoRepository.findAll(spec, pageable);
        return produtos.map(ProdutoDTO::new);
    }

    public ProdutoDTO findById(Long id) {
        return produtoRepository.findById(id)
                .map(ProdutoDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));
    }

    public long countDisponiveis() {
        return produtoRepository.countByStatus(StatusProduto.EM_ESTOQUE);
    }

    public ProdutoDTO save(ProdutoDTO produtoDTO) {
        Fornecedor fornecedor = fornecedorRepository.findById(produtoDTO.idFornecedor())
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado com o ID: " + produtoDTO.idFornecedor()));

        String prefixo = getPrefixoPorCategoria(fornecedor, produtoDTO.categoria());
        if (prefixo == null || prefixo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "O fornecedor '" + fornecedor.getNome() + "' não possui um prefixo de código definido para a categoria '" + produtoDTO.categoria() + "'."
            );
        }

        String idReferencia = prefixo + produtoDTO.idReferencia();

        Produto produto = new Produto();
        mapDtoToEntity(produtoDTO, produto, fornecedor);

        produto.setIdReferencia(idReferencia);

        Produto produtoSalvo = produtoRepository.save(produto);
        return new ProdutoDTO(produtoSalvo);
    }

    public ProdutoDTO update(Long id, ProdutoDTO produtoDTO) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));

        if (produtoExistente.getStatus() == StatusProduto.VENDIDO) {
            throw new IllegalStateException("Não é possível alterar um produto que já foi vendido.");
        }

        Fornecedor fornecedor = fornecedorRepository.findById(produtoDTO.idFornecedor())
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado com o ID: " + produtoDTO.idFornecedor()));

        String prefixo = getPrefixoPorCategoria(fornecedor, produtoDTO.categoria());
        if (prefixo == null || prefixo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "O fornecedor '" + fornecedor.getNome() + "' não possui um prefixo de código definido para a categoria '" + produtoDTO.categoria() + "'."
            );
        }

        String idReferencia = prefixo + produtoDTO.idReferencia();

        mapDtoToEntity(produtoDTO, produtoExistente, fornecedor);

        produtoExistente.setIdReferencia(idReferencia);

        Produto produtoSalvo = produtoRepository.save(produtoExistente);
        return new ProdutoDTO(produtoSalvo);
    }

    public void delete(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));

        if (produto.getStatus() == StatusProduto.VENDIDO) {
            throw new IllegalStateException("Não é possível excluir um produto que já foi vendido. Ele faz parte do histórico de vendas.");
        }
        produtoRepository.deleteById(id);
    }

    private void mapDtoToEntity(ProdutoDTO dto, Produto produto, Fornecedor fornecedor) {
        produto.setNome(dto.nome());
        produto.setPrecoVenda(dto.precoVenda());
        produto.setPrecoCusto(dto.precoCusto());
        produto.setAcabamento(dto.acabamento());
        produto.setCategoria(dto.categoria());
        produto.setFornecedor(fornecedor);
        if (dto.status() != null) {
            produto.setStatus(dto.status());
        }
    }

    private String getPrefixoPorCategoria(Fornecedor fornecedor, String nomeCategoria) {
        if (nomeCategoria == null) return null;
        return switch (nomeCategoria.toLowerCase()) {
            case "anel" -> fornecedor.getCodigoAnel();
            case "bracelete" -> fornecedor.getCodigoBracelete();
            case "colar" -> fornecedor.getCodigoColar();
            case "brinco" -> fornecedor.getCodigoBrinco();
            case "pulseira" -> fornecedor.getCodigoPulseira();
            case "pingente" -> fornecedor.getCodigoPingente();
            case "conjunto" -> fornecedor.getCodigoConjunto();
            case "berloque" -> fornecedor.getCodigoBerloque();
            case "piercing" -> fornecedor.getCodigoPiercing();
            default -> null;
        };
    }

    /**
     * Obtém um resumo completo do estoque, agregado por categoria.
     */
    @Transactional(readOnly = true)
    public List<EstoqueCategoriaDTO> getResumoEstoquePorCategoria() {
        return produtoRepository.getResumoEstoquePorCategoria();
    }

    /**
     * Obtém um resumo global de todo o estoque.
     */
    @Transactional(readOnly = true)
    public EstoqueGlobalDTO getResumoGlobalEstoque() {
        return produtoRepository.getResumoGlobalEstoque();
    }
}