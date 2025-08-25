package com.gleam.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidade que representa um Cliente no sistema.
 * Armazena informações cadastrais básicas para identificação e contato.
 */
@Data
@Entity
@Table(name = "clientes")
public class Cliente {

    /**
     * Identificador único do cliente, gerado automaticamente pelo banco de dados.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome completo do cliente.
     */
    private String nome;

    /**
     * Número de telefone de contato do cliente.
     */
    private String telefone;

    /**
     * CPF do cliente. Opcional.
     */
    @Column(nullable = true, length = 14)
    private String cpf;

    /**
     * Uma breve descrição ou observação sobre o cliente.
     * Por exemplo: histórico, preferências ou qualquer informação relevante.
     */
    @Column(length = 255)
    private String descricao;

    // --- Campos de Auditoria ---

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

    // --- Relacionamentos (Opcional Futuro) ---
    // No futuro, se quisermos ver facilmente todas as vendas de um cliente,
    // poderíamos adicionar o seguinte relacionamento:
    // @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    // private List<RegistrarVenda> vendas;
}