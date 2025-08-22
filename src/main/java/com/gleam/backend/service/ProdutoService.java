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

/**
 * Serviço que encapsula a lógica de negócio para a entidade Produto.
 * É responsável por todas as operações de CRUD, validações e conversões de dados.
 */
@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final FornecedorRepository fornecedorRepository;

    /**
     * Busca uma lista paginada de todos os produtos, sem filtros.
     * @param pageable Objeto com informações de paginação.
     * @return Uma página de ProdutoDTOs.
     */
    public Page<ProdutoDTO> findAll(Pageable pageable) {
        return produtoRepository.findAll(pageable).map(this::convertToDto);
    }

    /**
     * Busca uma lista paginada apenas dos produtos que estão disponíveis no stock (status 0).
     * @param pageable Objeto com informações de paginação.
     * @return Uma página de ProdutoDTOs disponíveis.
     */
    public Page<ProdutoDTO> findAllDisponiveis(Pageable pageable) {
        return produtoRepository.findByStatus(Produto.STATUS_DISPONIVEL, pageable).map(this::convertToDto);
    }

    /**
     * Busca um único produto pelo seu ID.
     * @param id O ID do produto a ser buscado.
     * @return O ProdutoDTO correspondente.
     * @throws EntityNotFoundException se nenhum produto for encontrado com o ID fornecido.
     */
    public ProdutoDTO findById(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));
        return convertToDto(produto);
    }

    /**
     * Busca uma lista paginada de produtos filtrados por um fornecedor específico.
     * @param fornecedorId O ID do fornecedor.
     * @param pageable Objeto com informações de paginação.
     * @return Uma página de ProdutoDTOs do fornecedor especificado.
     */
    public Page<ProdutoDTO> findByFornecedor(Long fornecedorId, Pageable pageable) {
        return produtoRepository.findByFornecedorId(fornecedorId, pageable).map(this::convertToDto);
    }

    /**
     * Conta o número total de produtos disponíveis no stock.
     * @return A contagem (long) de produtos com status "Disponível".
     */
    public long countDisponiveis() {
        return produtoRepository.countByStatus(Produto.STATUS_DISPONIVEL);
    }

    /**
     * Cria e salva um novo produto no banco de dados.
     * @param produtoDTO O DTO com os dados do novo produto.
     * @return O ProdutoDTO do produto recém-criado, incluindo seu novo ID.
     */
    public ProdutoDTO save(ProdutoDTO produtoDTO) {
        Fornecedor fornecedor = fornecedorRepository.findById(produtoDTO.getIdFornecedor())
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado com o ID: " + produtoDTO.getIdFornecedor()));

        Produto produto = new Produto();
        mapDtoToEntity(produtoDTO, produto, fornecedor);
        // Ao criar, o código do fornecedor é definido diretamente a partir do DTO.
        produto.setCodigoFornecedor(produtoDTO.getCodigoFornecedor());

        Produto produtoSalvo = produtoRepository.save(produto);
        return convertToDto(produtoSalvo);
    }

    /**
     * Atualiza um produto existente.
     * Impede a alteração se o produto já tiver sido vendido.
     * Gera o código do fornecedor automaticamente com base nos prefixos do fornecedor.
     * @param id O ID do produto a ser atualizado.
     * @param produtoDTO O DTO com os novos dados do produto.
     * @return O ProdutoDTO do produto atualizado.
     */
    public ProdutoDTO update(Long id, ProdutoDTO produtoDTO) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));

        // Regra de negócio: não permitir alteração de produto vendido.
        if (produtoExistente.getStatus().equals(Produto.STATUS_VENDIDO)) {
            throw new IllegalStateException("Não é possível alterar um produto que já foi vendido.");
        }

        Fornecedor fornecedor = fornecedorRepository.findById(produtoDTO.getIdFornecedor())
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado com o ID: " + produtoDTO.getIdFornecedor()));

        // Regra de negócio: gerar o código do produto com base no prefixo da categoria no fornecedor.
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

    /**
     * Apaga um produto do banco de dados.
     * Impede a exclusão se o produto já tiver sido vendido, para manter a integridade do histórico.
     * @param id O ID do produto a ser apagado.
     */
    public void delete(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));

        // Regra de negócio: não permitir exclusão de produto vendido.
        if (produto.getStatus().equals(Produto.STATUS_VENDIDO)) {
            throw new IllegalStateException("Não é possível excluir um produto que já foi vendido. Ele faz parte do histórico de vendas.");
        }
        produtoRepository.deleteById(id);
    }

    /**
     * Converte uma entidade Produto para o seu respectivo DTO.
     * @param produto A entidade a ser convertida.
     * @return O DTO preenchido.
     */
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

    /**
     * Mapeia os dados de um ProdutoDTO para uma entidade Produto.
     * Centraliza a lógica de mapeamento comum entre os métodos save e update.
     * @param dto O DTO com os dados de origem.
     * @param produto A entidade de destino.
     * @param fornecedor A entidade Fornecedor a ser associada.
     */
    private void mapDtoToEntity(ProdutoDTO dto, Produto produto, Fornecedor fornecedor) {
        produto.setNome(dto.getNome());
        produto.setPrecoVenda(dto.getPrecoVenda());
        produto.setPrecoCusto(dto.getPrecoCusto());
        produto.setAcabamento(dto.getAcabamento());
        produto.setCategoria(dto.getCategoria());
        produto.setFornecedor(fornecedor);
    }

    /**
     * Retorna o prefixo de código de um fornecedor com base na categoria do produto.
     * @param fornecedor A entidade Fornecedor.
     * @param nomeCategoria O nome da categoria (ex: "Anel").
     * @return O prefixo correspondente (ex: "FORN-AN") ou null se não houver.
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