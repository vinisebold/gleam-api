package com.gleam.backend.controller;

import com.gleam.backend.dto.ProdutoDTO;
import com.gleam.backend.dto.RegistrarVendaDTO;
import com.gleam.backend.service.ProdutoService;
import com.gleam.backend.service.RegistrarVendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Controller REST para gerir a entidade Produto.
 * Expõe os endpoints da API para todas as operações relacionadas a produtos e ao ato de vender um produto.
 */
@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;
    private final RegistrarVendaService registrarVendaService;

    /**
     * Endpoint para criar um novo produto.
     * @param produtoDTO O corpo da requisição com os dados do produto.
     * @return Resposta 201 Created com o DTO do novo produto no corpo e a URI no cabeçalho Location.
     */
    @PostMapping
    public ResponseEntity<ProdutoDTO> createProduto(@RequestBody ProdutoDTO produtoDTO) {
        ProdutoDTO novoProduto = produtoService.save(produtoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoProduto.getId()).toUri();
        return ResponseEntity.created(uri).body(novoProduto);
    }

    /**
     * Endpoint para listar todos os produtos de forma paginada.
     * @param pageable Parâmetros de paginação (ex: ?page=0&size=10&sort=nome,asc).
     * @return Uma página (Page) de ProdutoDTOs.
     */
    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> getAllProdutosPaginado(Pageable pageable) {
        Page<ProdutoDTO> produtos = produtoService.findAll(pageable);
        return ResponseEntity.ok(produtos);
    }

    /**
     * Endpoint para listar apenas os produtos disponíveis no stock.
     * @param pageable Parâmetros de paginação.
     * @return Uma página de ProdutoDTOs com status "Disponível".
     */
    @GetMapping("/disponiveis")
    public ResponseEntity<Page<ProdutoDTO>> getAllProdutosDisponiveis(Pageable pageable) {
        Page<ProdutoDTO> produtos = produtoService.findAllDisponiveis(pageable);
        return ResponseEntity.ok(produtos);
    }

    /**
     * Endpoint para buscar produtos de um fornecedor específico.
     * @param fornecedorId O ID do fornecedor.
     * @param pageable Parâmetros de paginação.
     * @return Uma página de ProdutoDTOs do fornecedor especificado.
     */
    @GetMapping("/fornecedor/{fornecedorId}")
    public ResponseEntity<Page<ProdutoDTO>> getProdutosByFornecedor(@PathVariable Long fornecedorId, Pageable pageable) {
        Page<ProdutoDTO> produtos = produtoService.findByFornecedor(fornecedorId, pageable);
        return ResponseEntity.ok(produtos);
    }

    /**
     * Endpoint para buscar um produto específico pelo seu ID.
     * @param id O ID do produto.
     * @return O ProdutoDTO correspondente.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> getProdutoById(@PathVariable Long id) {
        ProdutoDTO produto = produtoService.findById(id);
        return ResponseEntity.ok(produto);
    }

    /**
     * Endpoint para atualizar um produto existente.
     * @param id O ID do produto a ser atualizado.
     * @param produtoDTO O corpo da requisição com os novos dados.
     * @return O ProdutoDTO atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> updateProduto(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        ProdutoDTO produtoAtualizado = produtoService.update(id, produtoDTO);
        return ResponseEntity.ok(produtoAtualizado);
    }

    /**
     * Endpoint para apagar um produto.
     * @param id O ID do produto a ser apagado.
     * @return Resposta 204 No Content, indicando sucesso sem corpo de resposta.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- Endpoint de Venda ---

    /**
     * Endpoint para registar a venda de um único produto.
     * A ação de vender um produto está logicamente localizada dentro do recurso "produtos".
     * @param produtoId O ID do produto a ser vendido, passado na URL.
     * @param detalhesVendaDTO O corpo da requisição com os detalhes da venda (cliente, pagamento).
     * @return Resposta 201 Created com o "recibo" (RegistrarVendaDTO) da venda.
     */
    @PostMapping("/vender/{produtoId}")
    public ResponseEntity<RegistrarVendaDTO> venderProduto(
            @PathVariable Long produtoId,
            @RequestBody RegistrarVendaDTO detalhesVendaDTO) {

        RegistrarVendaDTO novaVenda = registrarVendaService.registrarVenda(produtoId, detalhesVendaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaVenda);
    }
}