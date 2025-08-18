package com.gleam.backend.controller;

import com.gleam.backend.dto.ProdutoDTO;
import com.gleam.backend.model.Produto;
import com.gleam.backend.repository.ProdutoRepository;
import com.gleam.backend.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoRepository produtoRepository; // Usado para buscas simples

    /**
     * Endpoint para CRIAR um novo produto.
     * A lógica de gerar o 'codigofornecedor' está dentro do service.
     * HTTP POST /api/produtos
     */
    @PostMapping
    public ResponseEntity<?> createProduto(@RequestBody ProdutoDTO produtoDTO) {
        try {
            Produto novoProduto = produtoService.save(produtoDTO);
            return ResponseEntity.ok(novoProduto);
        } catch (Exception e) {
            // Retorna uma resposta de erro clara para o frontend
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint para LER (buscar) todos os produtos.
     * HTTP GET /api/produtos
     */
    @GetMapping
    public ResponseEntity<List<Produto>> getAllProdutos() {
        List<Produto> produtos = produtoRepository.findAll();
        return ResponseEntity.ok(produtos);
    }

    /**
     * Endpoint para LER (buscar) produtos de forma paginada.
     * Exemplo de chamada: /api/produtos/paginado?page=0&size=10
     * HTTP GET /api/produtos/paginado
     */
    @GetMapping("/paginado")
    public ResponseEntity<Page<Produto>> getAllProdutosPaginado(Pageable pageable) {
        Page<Produto> produtos = produtoRepository.findAll(pageable);
        return ResponseEntity.ok(produtos);
    }

    /**
     * Endpoint para LER (buscar) um produto específico pelo seu ID.
     * HTTP GET /api/produtos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(produto -> ResponseEntity.ok(produto))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para ATUALIZAR (editar) um produto existente.
     * HTTP PUT /api/produtos/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduto(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        try {
            Produto produtoAtualizado = produtoService.update(id, produtoDTO);
            return ResponseEntity.ok(produtoAtualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint para APAGAR um produto.
     * HTTP DELETE /api/produtos/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        try {
            produtoService.delete(id);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
