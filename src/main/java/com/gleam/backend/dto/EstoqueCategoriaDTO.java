package com.gleam.backend.dto;

import java.math.BigDecimal;

public record EstoqueCategoriaDTO(
        String categoria,
        long quantidade,
        BigDecimal valorTotalCusto
) {}