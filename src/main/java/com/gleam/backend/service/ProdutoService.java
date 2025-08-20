package com.gleam.backend.service;

import com.gleam.backend.dto.ProdutoDTO;
import com.gleam.backend.model.Fornecedor;
import com.gleam.backend.model.Produto;
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
        return produtoRepository.findAll(pageable).map(this::convertToDto);
    }

    public Page<ProdutoDTO> findAllDisponiveis(Pageable pageable) {
        // Agora busca por status 0
        return produtoRepository.findByStatus(Produto.STATUS_DISPONIVEL, pageable).map(this::convertToDto);
    }

    public ProdutoDTO findById(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));
        return convertToDto(produto);
    }

    public Page<ProdutoDTO> findByFornecedor(Long fornecedorId, Pageable pageable) {
        return produtoRepository.findByFornecedorId(fornecedorId, pageable).map(this::convertToDto);
    }

    public long countDisponiveis() {
        // Agora conta produtos com status 0
        return produtoRepository.countByStatus(Produto.STATUS_DISPONIVEL);
    }

    public ProdutoDTO save(ProdutoDTO produtoDTO) {
        Fornecedor fornecedor = fornecedorRepository.findById(produtoDTO.getIdFornecedor())
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado com o ID: " + produtoDTO.getIdFornecedor()));

        Produto produto = new Produto();
        mapDtoToEntity(produtoDTO, produto, fornecedor);
        produto.setCodigoFornecedor(produtoDTO.getCodigoFornecedor());

        Produto produtoSalvo = produtoRepository.save(produto);
        return convertToDto(produtoSalvo);
    }

    public ProdutoDTO update(Long id, ProdutoDTO produtoDTO) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));

        // Agora verifica pelo status 1
        if (produtoExistente.getStatus().equals(Produto.STATUS_VENDIDO)) {
            throw new IllegalStateException("Não é possível alterar um produto que já foi vendido.");
        }

        Fornecedor fornecedor = fornecedorRepository.findById(produtoDTO.getIdFornecedor())
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado com o ID: " + produtoDTO.getIdFornecedor()));

        String prefixo = getPrefixoPorCategoria(fornecedor, produtoDTO.getCategoria());
        if (prefixo == null || prefixo.trim().isEmpty()) {
            throw new EntityNotFoundException(
                    "O fornecedor '" + fornecedor.getNome() + "' não possui um prefixo de código definido para a categoria '" + produtoDTO.getCategoria() + "'."
            );
        }

        mapDtoToEntity(produtoDTO, produtoExistente, fornecedor);
        produtoExistente.setCodigoFornecedor(prefixo);

        Produto produtoSalvo = produtoRepository.save(produtoExistente);
        return convertToDto(produtoSalvo);
    }

    public void delete(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));

        // Agora verifica pelo status 1
        if (produto.getStatus().equals(Produto.STATUS_VENDIDO)) {
            throw new IllegalStateException("Não é possível excluir um produto que já foi vendido. Ele faz parte do histórico de vendas.");
        }
        produtoRepository.deleteById(id);
    }

    private ProdutoDTO convertToDto(Produto produto) {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setPrecoVenda(produto.getPrecoVenda());
        dto.setPrecoCusto(produto.getPrecoCusto());
        dto.setAcabamento(produto.getAcabamento());
        dto.setCodigoFornecedor(produto.getCodigoFornecedor());
        dto.setCategoria(produto.getCategoria());
        dto.setStatus(produto.getStatus());
        dto.setIdFornecedor(produto.getFornecedor() != null ? produto.getFornecedor().getId() : null);
        dto.setDataCriacao(produto.getDataCriacao());
        dto.setDataAtualizacao(produto.getDataAtualizacao());
        return dto;
    }

    private void mapDtoToEntity(ProdutoDTO dto, Produto produto, Fornecedor fornecedor) {
        produto.setNome(dto.getNome());
        produto.setPrecoVenda(dto.getPrecoVenda());
        produto.setPrecoCusto(dto.getPrecoCusto());
        produto.setAcabamento(dto.getAcabamento());
        produto.setCategoria(dto.getCategoria());
        produto.setFornecedor(fornecedor);
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