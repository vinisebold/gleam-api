package com.gleam.backend.controller;

import com.gleam.backend.dto.AnaliseLucroDTO;
import com.gleam.backend.model.PeriodoAnalise;
import com.gleam.backend.service.AnaliseService;
import com.gleam.backend.service.AnaliseService.PontoGrafico; // Importa o record interno
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/analises")
@RequiredArgsConstructor
public class AnaliseController {

    private final AnaliseService analiseService;

    /**
     * Endpoint para obter o lucro total para cards.
     * Períodos válidos: DIA, SEMANA, MES
     */
    @GetMapping("/lucro-total")
    public ResponseEntity<AnaliseLucroDTO> getLucroTotalPorPeriodo(
            @RequestParam("periodo") PeriodoAnalise periodo) {
        AnaliseLucroDTO resultado = analiseService.calcularLucroTotalPorPeriodo(periodo);
        return ResponseEntity.ok(resultado);
    }

    /**
     * Endpoint para obter uma série de dados de lucro para alimentar um gráfico.
     * Períodos válidos: SEMANA, MES, ANO
     */
    @GetMapping("/lucro-grafico")
    public ResponseEntity<List<PontoGrafico>> getLucroParaGrafico(
            @RequestParam("periodo") PeriodoAnalise periodo) {
        List<PontoGrafico> resultado = analiseService.gerarDadosGraficoLucro(periodo);
        return ResponseEntity.ok(resultado);
    }
}