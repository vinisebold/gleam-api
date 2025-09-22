package com.gleam.backend.service;

import com.gleam.backend.dto.EstoqueCategoriaDTO;
import com.gleam.backend.dto.EstoqueGlobalDTO;
import com.gleam.backend.dto.ProdutoDTO;
import com.gleam.backend.model.Fornecedor;
import com.gleam.backend.model.Produto;
import com.gleam.backend.model.StatusProduto;
import com.gleam.backend.repository.FornecedorRepository;
import com.gleam.backend.repository.ProdutoRepository;
import com.gleam.backend.repository.VendaRepository;
import com.gleam.backend.repository.specification.ProdutoSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Responsável pelas operações de CRUD, filtros, e lógicas complexas como
 * a geração de ‘ID’ de referência e a gestão de resumos de estoque.
 */
@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final VendaRepository vendaRepository;
    private final FornecedorRepository fornecedorRepository;

    /**
     * Lista produtos de forma paginada com base em filtros opcionais.
     *
     * @param fornecedorId O ID do fornecedor para filtrar os produtos (opcional).
     * @param status O status do produto para filtrar (ex: EM_ESTOQUE) (opcional).
     * @param pageable Objeto com as informações de paginação.
     * @return Uma {@link Page} de {@link ProdutoDTO} com os produtos filtrados.
     */
    @Transactional(readOnly = true)
    public Page<ProdutoDTO> listarProdutosComFiltros(Long fornecedorId, StatusProduto status, Pageable pageable) {
        Specification<Produto> spec = ProdutoSpecification.filtrarPor(fornecedorId, status);
        return produtoRepository.findAll(spec, pageable).map(ProdutoDTO::new);
    }

    /**
     * Busca um único produto pelo seu ‘ID’.
     *
     * @param id O ID do produto a ser procurado.
     * @return O {@link ProdutoDTO} correspondente ao produto encontrado.
     * @throws EntityNotFoundException se nenhum produto for encontrado com o ‘ID’ fornecido.
     */
    @Transactional(readOnly = true)
    public ProdutoDTO findById(Long id) {
        return produtoRepository.findById(id)
                .map(ProdutoDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));
    }

    /**
     * Cria e persiste um novo produto, gerando o seu ‘ID’ de referência com base
     * no prefixo do fornecedor.
     *
     * @param produtoDTO O {@link ProdutoDTO} com os dados do novo produto.
     * @return O {@link ProdutoDTO} do produto recém-criado.
     * @throws EntityNotFoundException se o fornecedor associado não for encontrado.
     * @throws IllegalArgumentException se o fornecedor não tiver um prefixo de código
     * definido para a categoria do produto.
     */
    @Transactional
    public ProdutoDTO save(ProdutoDTO produtoDTO) {
        Fornecedor fornecedor = fornecedorRepository.findById(produtoDTO.idFornecedor())
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado com o ID: " + produtoDTO.idFornecedor()));

        String prefixo = getPrefixoPorCategoria(fornecedor, produtoDTO.categoria());
        if (prefixo == null || prefixo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "O fornecedor '" + fornecedor.getNome() + "' não possui um prefixo de código definido para a categoria '" + produtoDTO.categoria() + "'."
            );
        }

        Produto produto = new Produto();
        mapDtoToEntity(produtoDTO, produto, fornecedor);
        produto.setIdReferencia(prefixo + produtoDTO.idReferencia());

        Produto produtoSalvo = produtoRepository.save(produto);
        return new ProdutoDTO(produtoSalvo);
    }

    /**
     * Atualiza os dados de um produto existente.
     *
     * @param id O ‘ID’ do produto a ser atualizado.
     * @param produtoDTO O {@link ProdutoDTO} com os novos dados.
     * @return O {@link ProdutoDTO} do produto atualizado.
     * @throws EntityNotFoundException se o produto ou o fornecedor não forem encontrados.
     * @throws IllegalStateException se for feita uma tentativa de alterar um produto já vendido.
     * @throws IllegalArgumentException se o fornecedor não tiver um prefixo de código definido.
     */
    @Transactional
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

        mapDtoToEntity(produtoDTO, produtoExistente, fornecedor);
        produtoExistente.setIdReferencia(prefixo + produtoDTO.idReferencia());

        Produto produtoSalvo = produtoRepository.save(produtoExistente);
        return new ProdutoDTO(produtoSalvo);
    }

    /**
     * Remove um produto do sistema.
     * Antes de apagar, o produto é desvinculado de qualquer registo de venda para
     * manter a integridade do histórico.
     *
     * @param id O ID do produto a ser removido.
     * @throws EntityNotFoundException se o produto não for encontrado.
     */
    @Transactional
    public void delete(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));

        vendaRepository.desvincularProdutoDeVendas(id);
        produtoRepository.deleteById(id);
    }

    /**
     * Obtém um resumo do estoque, agregado por categoria.
     *
     * @return Uma lista de {@link EstoqueCategoriaDTO}, cada um representando
     * os totais de uma categoria.
     */
    @Transactional(readOnly = true)
    public List<EstoqueCategoriaDTO> getResumoEstoquePorCategoria() {
        return produtoRepository.getResumoEstoquePorCategoria();
    }

    /**
     * Obtém um resumo global do inventário EM estoque.
     *
     * @return Um {@link EstoqueGlobalDTO} com a quantidade total de itens
     * e o valor total de custo.
     */
    @Transactional(readOnly = true)
    public EstoqueGlobalDTO getResumoGlobalEstoque() {
        return produtoRepository.getResumoGlobalEstoque();
    }

    /**
     * Mapeia os dados de um DTO para uma entidade Produto.
     *
     * @param dto O DTO de origem.
     * @param produto A entidade de destino.
     * @param fornecedor A entidade Fornecedor a ser associada.
     */
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

    /**
     * Retorna o prefixo de código correto de um fornecedor com base na categoria do produto.
     *
     * @param fornecedor O fornecedor.
     * @param nomeCategoria O nome da categoria (ex: "Anel").
     * @return O prefixo de código correspondente ou null se não for encontrado.
     */
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