package com.gleam.backend.dto;

import java.math.BigDecimal;

/**
 * DTO para retornar o resultado de análises de lucro.
 * @param totalLucro O valor total do lucro calculado.
 */
public record AnaliseLucroDTO(BigDecimal totalLucro) {
}