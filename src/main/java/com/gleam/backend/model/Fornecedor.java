package com.gleam.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "fornecedores") // Boa prática: definir o nome da tabela explicitamente
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cnpj;
    private String telefone;
    private String descricao;

    // --- DATAS AUTOMÁTICAS ---
    @CreationTimestamp // Define a data/hora no momento da criação
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao; // Renomeado para consistência com as outras entidades

    @UpdateTimestamp // Atualiza a data/hora em qualquer modificação
    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    // --- CÓDIGOS DE PRODUTO ---
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

    // --- RELACIONAMENTO ---
    // Útil para poder buscar todos os produtos de um fornecedor diretamente
    @OneToMany(mappedBy = "fornecedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Produto> produtos;
}