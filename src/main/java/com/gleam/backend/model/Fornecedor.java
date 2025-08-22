package com.gleam.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um Fornecedor de produtos.
 * Contém informações cadastrais e os prefixos de código para cada categoria de produto.
 */
@Data
@Entity
@Table(name = "fornecedores")
public class Fornecedor {

    /**
     * Identificador único do fornecedor, gerado automaticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome fantasia ou razão social do fornecedor.
     */
    private String nome;

    /**
     * CNPJ do fornecedor, para fins fiscais e de identificação.
     */
    private String cnpj;

    /**
     * Telefone de contato principal do fornecedor.
     */
    private String telefone;

    /**
     * Um campo de texto livre para anotações ou descrição do fornecedor.
     */
    private String descricao;

    // --- Campos de Auditoria ---

    /**
     * Data e hora exatas em que o fornecedor foi registado no sistema.
     * Gerado automaticamente pelo Hibernate na criação.
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    /**
     * Data e hora da última atualização dos dados do fornecedor.
     * Atualizado automaticamente pelo Hibernate em cada modificação.
     */
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    // --- Campos de Prefixo de Código ---

    /**
     * Prefixo de código utilizado para a categoria "Anel". Ex: "FORN-AN".
     */
    @Column(name = "codigo_anel")
    private String codigoAnel;

    /**
     * Prefixo de código utilizado para a categoria "Bracelete".
     */
    @Column(name = "codigo_bracelete")
    private String codigoBracelete;

    /**
     * Prefixo de código utilizado para a categoria "Colar".
     */
    @Column(name = "codigo_colar")
    private String codigoColar;

    /**
     * Prefixo de código utilizado para a categoria "Brinco".
     */
    @Column(name = "codigo_brinco")
    private String codigoBrinco;

    /**
     * Prefixo de código utilizado para a categoria "Pulseira".
     */
    @Column(name = "codigo_pulseira")
    private String codigoPulseira;

    /**
     * Prefixo de código utilizado para a categoria "Pingente".
     */
    @Column(name = "codigo_pingente")
    private String codigoPingente;

    /**
     * Prefixo de código utilizado para a categoria "Conjunto".
     */
    @Column(name = "codigo_conjunto")
    private String codigoConjunto;

    /**
     * Prefixo de código utilizado para a categoria "Berloque".
     */
    @Column(name = "codigo_berloque")
    private String codigoBerloque;

    /**
     * Prefixo de código utilizado para a categoria "Piercing".
     */
    @Column(name = "codigo_piercing")
    private String codigoPiercing;

    // --- Relacionamentos ---

    /**
     * Lista de todos os produtos associados a este fornecedor.
     * `mappedBy = "fornecedor"` indica que a entidade Produto é a "dona" da relação.
     * `cascade = CascadeType.ALL` garante que as operações (salvar, apagar) no fornecedor sejam propagadas para os seus produtos.
     * `orphanRemoval = true` garante que um produto seja apagado se for removido desta lista.
     * Nota: É importante ter cuidado ao expor esta lista diretamente na API para evitar loops de serialização.
     */
    @OneToMany(mappedBy = "fornecedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Produto> produtos = new ArrayList<>();
}