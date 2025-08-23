package com.gleam.backend.service;

import com.gleam.backend.dto.ProdutoDTO;
import com.gleam.backend.model.Fornecedor;
import com.gleam.backend.model.Produto;
import com.gleam.backend.model.StatusProduto;
import com.gleam.backend.repository.FornecedorRepository;
import com.gleam.backend.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final FornecedorRepository fornecedorRepository;

    public Page<ProdutoDTO> findAll(Pageable pageable) {
        return produtoRepository.findAll(pageable).map(ProdutoDTO::new);
    }

    public Page<ProdutoDTO> findAllDisponiveis(Pageable pageable) {
        return produtoRepository.findByStatus(StatusProduto.EM_ESTOQUE, pageable).map(ProdutoDTO::new);
    }

    public ProdutoDTO findById(Long id) {
        return produtoRepository.findById(id)
                .map(ProdutoDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));
    }

    public Page<ProdutoDTO> findByFornecedor(Long fornecedorId, Pageable pageable) {
        return produtoRepository.findByFornecedorId(fornecedorId, pageable).map(ProdutoDTO::new);
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

        // Define o ID de referência na entidade
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
        produto.setIdReferencia(dto.idReferencia());
        produto.setFornecedor(fornecedor);
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
}