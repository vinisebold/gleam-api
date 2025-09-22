package com.gleam.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Representa um cliente no sistema, armazenando a suas informações
 * cadastrais para contato e identificação.
 */
@Data
@Entity
@Table(name = "clientes")
public class Cliente {

    /**
     * Identificador único do cliente (chave primária).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome completo do cliente.
     */
    private String nome;

    /**
     * Telefone de contato do cliente.
     */
    private String telefone;

    /**
     * CPF do cliente (opcional).
     */
    @Column(nullable = true, length = 14)
    private String cpf;

    /**
     * Campo para anotações ou observações relevantes sobre o cliente.
     */
    @Column(nullable = true, length = 255)
    private String descricao;

    /**
     * Endereço eletrónico do cliente (opcional).
     */
    @Column(nullable = true, length = 255)
    private String email;

    /**
     * Data e hora exatas em que o cliente foi registrado no sistema.
     * O valor é gerado automaticamente pelo Hibernate na primeira vez que o registro é salvo.
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    /**
     * Data e hora da última atualização dos dados do cliente.
     * O valor é atualizado automaticamente pelo Hibernate sempre que o registro é modificado.
     */
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;
}