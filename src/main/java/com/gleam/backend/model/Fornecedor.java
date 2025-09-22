package com.gleam.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um fornecedor de produtos.
 * Armazena informações cadastrais e os prefixos de código utilizados para
 * gerar o ‘ID’ de referência de cada produto associado a ele.
 */
@Data
@Entity
@Table(name = "fornecedores")
public class Fornecedor {

    /**
     * Identificador único do fornecedor (chave primária).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome fantasia ou razão social do fornecedor.
     */
    private String nome;

    /**
     * CNPJ do fornecedor.
     */
    private String cnpj;

    /**
     * Telefone de contato principal do fornecedor.
     */
    private String telefone;

    /**
     * Campo para anotações ou descrição do fornecedor.
     */
    private String descricao;

    /**
     * Data e hora do registro do fornecedor no sistema.
     * Gerado automaticamente na criação.
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    /**
     * Data e hora da última atualização dos dados do fornecedor.
     * Atualizado automaticamente em cada modificação.
     */
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    /**
     * Prefixos de código utilizados para gerar o ID de referência dos produtos.
     * Cada prefixo está associado a uma categoria de produto.
     * Exemplo: "AN" para a categoria "Anel".
     */
    @Column(name = "codigo_anel")
    private String codigoAnel;
    @Column(name = "codigo_bracelete")
    private String codigoBracelete;
    @Column(name = "codigo_colar")
    private String codigoColar;
    @Column(name = "codigo_brinco")
    private String codigoBrinco;
    @Column(name = "codigo_pulseira")
    private String codigoPulseira;
    @Column(name = "codigo_pingente")
    private String codigoPingente;
    @Column(name = "codigo_conjunto")
    private String codigoConjunto;
    @Column(name = "codigo_berloque")
    private String codigoBerloque;
    @Column(name = "codigo_piercing")
    private String codigoPiercing;

    /**
     * Lista de produtos associados a este fornecedor.
     * A anotação 'mappedBy' indica que a entidade 'Produto' gerencia este relacionamento.
     * O 'cascade' propaga operações (salvar, apagar) para os produtos associados.
     * O 'orphanRemoval' remove produtos que não estão mais nesta lista.
     * Nota: Evitar expor esta lista diretamente na API para prevenir ‘loops’ de serialização.
     */
    @OneToMany(mappedBy = "fornecedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Produto> produtos = new ArrayList<>();
}