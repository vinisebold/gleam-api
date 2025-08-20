package com.gleam.backend.controller;

import com.gleam.backend.dto.ProdutoDTO;
import com.gleam.backend.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor // Substitui @Autowired por injeção via construtor
public class ProdutoController {

    private final ProdutoService produtoService;

    /**
     * Endpoint para CRIAR um novo produto.
     * Retorna 201 Created com o DTO do novo produto.
     */
    @PostMapping
    public ResponseEntity<ProdutoDTO> createProduto(@RequestBody ProdutoDTO produtoDTO) {
        ProdutoDTO novoProduto = produtoService.save(produtoDTO);

        // Cria a URI do novo recurso criado
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoProduto.getId()).toUri();

        return ResponseEntity.created(uri).body(novoProduto);
    }

    /**
     * Endpoint para LER (buscar) produtos de forma paginada.
     * Exemplo: /api/produtos?page=0&size=10
     */
    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> getAllProdutosPaginado(Pageable pageable) {
        Page<ProdutoDTO> produtos = produtoService.findAll(pageable);
        return ResponseEntity.ok(produtos);
    }

    /**
     * NOVO: Endpoint para LER (buscar) apenas produtos DISPONÍVEIS de forma paginada.
     */
    @GetMapping("/disponiveis")
    public ResponseEntity<Page<ProdutoDTO>> getAllProdutosDisponiveis(Pageable pageable) {
        Page<ProdutoDTO> produtos = produtoService.findAllDisponiveis(pageable);
        return ResponseEntity.ok(produtos);
    }

    /**
     * NOVO: Endpoint para CONTAR a quantidade de produtos disponíveis.
     */
    @GetMapping("/disponiveis/count")
    public ResponseEntity<Long> countProdutosDisponiveis() {
        return ResponseEntity.ok(produtoService.countDisponiveis());
    }

    /**
     * Endpoint para LER (buscar) um produto específico pelo seu ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> getProdutoById(@PathVariable Long id) {
        ProdutoDTO produto = produtoService.findById(id);
        return ResponseEntity.ok(produto);
    }

    /**
     * Endpoint para LER (buscar) produtos filtrados por fornecedor, de forma paginada.
     */
    @GetMapping("/fornecedor/{fornecedorId}")
    public ResponseEntity<Page<ProdutoDTO>> getProdutosByFornecedor(
            @PathVariable Long fornecedorId,
            Pageable pageable) {

        Page<ProdutoDTO> produtos = produtoService.findByFornecedor(fornecedorId, pageable);
        return ResponseEntity.ok(produtos);
    }

    /**
     * Endpoint para ATUALIZAR (editar) um produto existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> updateProduto(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        ProdutoDTO produtoAtualizado = produtoService.update(id, produtoDTO);
        return ResponseEntity.ok(produtoAtualizado);
    }

    /**
     * Endpoint para APAGAR um produto.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}