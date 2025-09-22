package com.gleam.backend.controller;

import com.gleam.backend.dto.ProdutoDTO;
import com.gleam.backend.model.StatusProduto;
import com.gleam.backend.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Controller REST para a gestão de Produtos e Inventário.
 */
@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    /**
     * GET /api/produtos: lista produtos com filtros opcionais.
     *
     * @param fornecedorId ID do fornecedor para filtrar os resultados (opcional).
     * @param status       Status do produto para filtrar os resultados (opcional).
     * @param pageable     Parâmetros de paginação.
     * @return {@link ResponseEntity} com status 200 (OK) e uma página de ProdutoDTOs.
     */
    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> listarProdutos(
            @RequestParam(required = false) Long fornecedorId,
            @RequestParam(required = false) StatusProduto status,
            Pageable pageable) {

        Page<ProdutoDTO> produtos = produtoService.listarProdutosComFiltros(fornecedorId, status, pageable);
        return ResponseEntity.ok(produtos);
    }

    /**
     * POST /api/produtos: cria um produto.
     *
     * @param produtoDTO O corpo da requisição com os dados do produto.
     * @return {@link ResponseEntity} com status 201 (Created) e o DTO do novo produto.
     */
    @PostMapping
    public ResponseEntity<ProdutoDTO> createProduto(@RequestBody ProdutoDTO produtoDTO) {
        ProdutoDTO novoProduto = produtoService.save(produtoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoProduto.id()).toUri();
        return ResponseEntity.created(uri).body(novoProduto);
    }

    /**
     * GET /api/produtos/{‘id’}: busca um produto pelo seu ‘ID’.
     *
     * @param id O ID do produto.
     * @return {@link ResponseEntity} com status 200 (OK) e o ProdutoDTO no corpo.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> getProdutoById(@PathVariable Long id) {
        ProdutoDTO produto = produtoService.findById(id);
        return ResponseEntity.ok(produto);
    }

    /**
     * PUT /api/produtos/{‘id’}: atualiza um produto existente.
     *
     * @param id         O ‘ID’ do produto a ser atualizado.
     * @param produtoDTO O corpo da requisição com os novos dados.
     * @return {@link ResponseEntity} com status 200 (OK) e o ProdutoDTO atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> updateProduto(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        ProdutoDTO produtoAtualizado = produtoService.update(id, produtoDTO);
        return ResponseEntity.ok(produtoAtualizado);
    }

    /**
     * DELETE /api/produtos/{‘id’}: apaga um produto.
     *
     * @param id O ID do produto a ser apagado.
     * @return {@link ResponseEntity} com status 204 (No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}