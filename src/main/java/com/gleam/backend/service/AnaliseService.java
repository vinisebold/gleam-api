package com.gleam.backend.service;

import com.gleam.backend.dto.AnaliseLucroDTO;
import com.gleam.backend.model.enums.PeriodoAnalise;
import com.gleam.backend.repository.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

@Service
@RequiredArgsConstructor
public class AnaliseService {

    private final VendaRepository vendaRepository;

    @Transactional(readOnly = true)
    public AnaliseLucroDTO calcularLucroPorPeriodo(PeriodoAnalise periodo) {
        LocalDateTime hoje = LocalDateTime.now(); // Baseado em: 26 de Agosto de 2025
        LocalDateTime inicio;
        LocalDateTime fim;

        switch (periodo) {
            case DIA:
                // Lucro de hoje (do início do dia até o fim do dia)
                inicio = hoje.with(LocalTime.MIN); // 2025-08-26 00:00:00
                fim = hoje.with(LocalTime.MAX);    // 2025-08-26 23:59:59.999...
                break;

            case SEMANA:
                // Lucro da semana atual (de segunda-feira até o fim de domingo)
                inicio = hoje.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).with(LocalTime.MIN);
                fim = hoje.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).with(LocalTime.MAX);
                break;

            case MES:
                // Lucro do mês atual (do dia 1 até o último dia do mês)
                inicio = hoje.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
                fim = hoje.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
                break;

            default:
                throw new IllegalArgumentException("Período de análise inválido: " + periodo);
        }

        BigDecimal totalLucro = vendaRepository.sumLucroByDataCriacaoBetween(inicio, fim);

        return new AnaliseLucroDTO(totalLucro);
    }
}