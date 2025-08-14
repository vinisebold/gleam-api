package com.gleamTeste.Gleamteste.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Gleam")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm_produtos", nullable = false) // garante que não pode ser nulo
    private String nm_produtos;

    @Column(nullable = false)
    private Double preco;

    // Getters e Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNm_produtos() {
        return nm_produtos;
    }
    public void setNm_produtos(String nm_produtos) {
        this.nm_produtos = nm_produtos;
    }
    public Double getPreco() {
        return preco;
    }
    public void setPreco(Double preco) {
        this.preco = preco;
    }
}

