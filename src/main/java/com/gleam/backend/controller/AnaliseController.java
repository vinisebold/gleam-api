package com.gleam.backend.controller;

import com.gleam.backend.model.PeriodoAnalise;
import com.gleam.backend.service.AnaliseService;
import com.gleam.backend.service.AnaliseService.PontoGrafico;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller REST para fornecer dados para análises financeiras e dashboards.
 */
@RestController
@RequestMapping("/api/analises")
@RequiredArgsConstructor
public class AnaliseController {

    private final AnaliseService analiseService;

    /**
     * GET /api/analises/lucro-total: obtém o lucro total para um dado período.
     * Ideal para cards de resumo (ex: Lucro do Dia, Lucro do Mês).
     *
     * @param periodo Período para o cálculo (DIA, SEMANA, MES).
     * @return {@link ResponseEntity} com status 200 (OK) e o DTO com o lucro total.
     */
    @GetMapping("/lucro-total")
    public ResponseEntity<?> getLucroTotalPorPeriodo(
            @RequestParam("periodo") PeriodoAnalise periodo) {
        return ResponseEntity.ok(analiseService.calcularLucroTotalPorPeriodo(periodo));
    }

    /**
     * GET /api/analises/lucro-grafico: obtém uma série de dados de lucro para alimentar um gráfico.
     *
     * @param periodo Período para a agregação dos dados (SEMANA, MES, ANO).
     * @return {@link ResponseEntity} com status 200 (OK) e uma lista de pontos para o gráfico.
     */
    @GetMapping("/lucro-grafico")
    public ResponseEntity<List<PontoGrafico>> getLucroParaGrafico(
            @RequestParam("periodo") PeriodoAnalise periodo) {
        List<PontoGrafico> resultado = analiseService.gerarDadosGraficoLucro(periodo);
        return ResponseEntity.ok(resultado);
    }
}