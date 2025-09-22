package com.gleam.backend.dto;

import java.math.BigDecimal;

/**
 * DTO para encapsular o resultado de uma análise de lucro total.
 *
 * @param totalLucro O valor total do lucro consolidado.
 */
public record AnaliseLucroDTO(BigDecimal totalLucro) {
}