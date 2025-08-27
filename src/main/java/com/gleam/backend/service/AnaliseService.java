package com.gleam.backend.service;

import com.gleam.backend.dto.AnaliseLucroDTO;
import com.gleam.backend.model.PeriodoAnalise;
import com.gleam.backend.repository.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class AnaliseService {

    private final VendaRepository vendaRepository;
    private final Locale localeBrasil = new Locale("pt", "BR");

    // DTO interno para os pontos do gráfico, eliminando a necessidade de um novo arquivo.
    public record PontoGrafico(String periodo, BigDecimal lucro) {}

    // ===== LÓGICA PARA OS CARDS (VALOR ÚNICO) =====
    @Transactional(readOnly = true)
    public AnaliseLucroDTO calcularLucroTotalPorPeriodo(PeriodoAnalise periodo) {
        LocalDateTime hoje = LocalDateTime.now();
        LocalDateTime inicio;
        LocalDateTime fim;

        switch (periodo) {
            case DIA:
                inicio = hoje.with(LocalTime.MIN);
                fim = hoje.with(LocalTime.MAX);
                break;
            case SEMANA:
                inicio = hoje.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).with(LocalTime.MIN);
                fim = hoje.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).with(LocalTime.MAX);
                break;
            case MES:
                inicio = hoje.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
                fim = hoje.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
                break;
            default:
                throw new IllegalArgumentException("Período inválido para esta análise: " + periodo);
        }
        BigDecimal totalLucro = vendaRepository.sumLucroByDataCriacaoBetween(inicio, fim);
        return new AnaliseLucroDTO(totalLucro);
    }

    // ===== LÓGICA PARA O GRÁFICO (SÉRIE DE DADOS) =====
    @Transactional(readOnly = true)
    public List<PontoGrafico> gerarDadosGraficoLucro(PeriodoAnalise periodo) {
        switch (periodo) {
            case SEMANA:
                return getLucroUltimosSeteDias();
            case MES:
                return getLucroMesCorrentePorDia();
            case ANO:
                return getLucroAnoCorrentePorMes();
            default:
                throw new IllegalArgumentException("Período inválido para análise de gráfico: " + periodo);
        }
    }

    private List<PontoGrafico> getLucroUltimosSeteDias() {
        LocalDate hoje = LocalDate.now();
        LocalDate dataInicio = hoje.minusDays(6);

        List<Object[]> lucrosDoBanco = vendaRepository.findLucroAgrupadoPorDia(dataInicio.atStartOfDay(), hoje.atTime(LocalTime.MAX));
        Map<LocalDate, BigDecimal> mapaDeLucros = lucrosDoBanco.stream()
                .collect(Collectors.toMap(row -> (LocalDate) row[0], row -> (BigDecimal) row[1]));

        List<PontoGrafico> resultado = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE", localeBrasil);

        for (int i = 0; i < 7; i++) {
            LocalDate diaCorrente = dataInicio.plusDays(i);
            BigDecimal lucro = mapaDeLucros.getOrDefault(diaCorrente, BigDecimal.ZERO);
            String nomeDia = formatter.format(diaCorrente).substring(0, 1).toUpperCase() + formatter.format(diaCorrente).substring(1);
            resultado.add(new PontoGrafico(nomeDia, lucro));
        }
        return resultado;
    }

    private List<PontoGrafico> getLucroMesCorrentePorDia() {
        LocalDate hoje = LocalDate.now();
        LocalDate primeiroDiaDoMes = hoje.withDayOfMonth(1);

        List<Object[]> lucrosDoBanco = vendaRepository.findLucroAgrupadoPorDia(primeiroDiaDoMes.atStartOfDay(), hoje.atTime(LocalTime.MAX));
        Map<LocalDate, BigDecimal> mapaDeLucros = lucrosDoBanco.stream()
                .collect(Collectors.toMap(row -> (LocalDate) row[0], row -> (BigDecimal) row[1]));

        List<PontoGrafico> resultado = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

        for (LocalDate dia = primeiroDiaDoMes; !dia.isAfter(hoje); dia = dia.plusDays(1)) {
            BigDecimal lucro = mapaDeLucros.getOrDefault(dia, BigDecimal.ZERO);
            resultado.add(new PontoGrafico(dia.format(formatter), lucro));
        }
        return resultado;
    }

    private List<PontoGrafico> getLucroAnoCorrentePorMes() {
        LocalDate hoje = LocalDate.now();
        LocalDateTime primeiroDiaDoAno = hoje.withDayOfYear(1).atStartOfDay();

        List<Object[]> lucrosDoBanco = vendaRepository.findLucroAgrupadoPorMes(primeiroDiaDoAno, hoje.atTime(LocalTime.MAX));
        Map<Integer, BigDecimal> mapaDeLucros = lucrosDoBanco.stream()
                .collect(Collectors.toMap(row -> (Integer) row[0], row -> (BigDecimal) row[1]));

        List<PontoGrafico> resultado = new ArrayList<>();
        String[] nomesDosMeses = new DateFormatSymbols(localeBrasil).getMonths();

        IntStream.rangeClosed(1, hoje.getMonthValue()).forEach(numeroMes -> {
            BigDecimal lucro = mapaDeLucros.getOrDefault(numeroMes, BigDecimal.ZERO);
            String nomeMes = nomesDosMeses[numeroMes - 1];
            nomeMes = nomeMes.substring(0, 1).toUpperCase() + nomeMes.substring(1);
            resultado.add(new PontoGrafico(nomeMes, lucro));
        });
        return resultado;
    }
}