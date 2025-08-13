package com.gleam.backend.model;

import com.gleam.backend.enums.StatusVenda; // ALTERAÇÃO: Import do novo enum
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal; // ALTERAÇÃO: Import para valores monetários
import java.time.LocalDateTime;

@Entity
@Data
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataVenda;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @Column(nullable = false, precision = 10, scale = 2) // ALTERAÇÃO: Mapeamento para DECIMAL
    private BigDecimal valorTotal; // ALTERAÇÃO: de Double para BigDecimal

    @Enumerated(EnumType.STRING) // ALTERAÇÃO: Anotação para usar o enum
    @Column(nullable = false)
    private StatusVenda status; // ALTERAÇÃO: de Integer para o enum StatusVenda
}