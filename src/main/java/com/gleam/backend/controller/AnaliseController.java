package com.gleam.backend.controller;

import com.gleam.backend.dto.AnaliseLucroDTO;
import com.gleam.backend.model.enums.PeriodoAnalise;
import com.gleam.backend.service.AnaliseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analises")
@RequiredArgsConstructor
public class AnaliseController {

    private final AnaliseService analiseService;

    /**
     * Endpoint para obter o lucro total com base em um período (DIA, SEMANA, MES).
     * Exemplo de uso: GET /api/analises/lucro?periodo=SEMANA
     *
     * @param periodo O período para o qual o lucro deve ser calculado.
     * @return Um DTO contendo o lucro total.
     */
    @GetMapping("/lucro")
    public ResponseEntity<AnaliseLucroDTO> getLucroPorPeriodo(
            @RequestParam("periodo") PeriodoAnalise periodo) {
        AnaliseLucroDTO resultado = analiseService.calcularLucroPorPeriodo(periodo);
        return ResponseEntity.ok(resultado);
    }
}