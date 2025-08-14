package com.gleamTeste.Gleamteste.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gleamTeste.Gleamteste.model.Produto;
import com.gleamTeste.Gleamteste.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Gleam")
@CrossOrigin(origins = "*") // Libera requisições de qualquer origem
public class ProdutoController {

    @Autowired
    private ProdutoRepository repo;

    // LISTAR TODOS
    @GetMapping
    public ResponseEntity<List<Produto>> listar() {
        return ResponseEntity.ok(repo.findAll());
    }

    // ADICIONAR
    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Produto produto) {
        try {
            Produto novoProduto = repo.save(produto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao adicionar produto: " + e.getMessage());
        }
    }

    // ATUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Produto produto) {
        Optional<Produto> existente = repo.findById(id);
        if (existente.isPresent()) {
            Produto prod = existente.get();
            prod.setNm_produtos(produto.getNm_produtos());
            prod.setPreco(produto.getPreco());
            repo.save(prod);
            return ResponseEntity.ok(prod);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Produto com ID " + id + " não encontrado.");
        }
    }

    // DELETAR
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Produto com ID " + id + " não encontrado.");
        }
    }
}
