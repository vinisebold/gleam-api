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

/**
 * Encapsula a lógica de negócio para análises financeiras e de performance.
 * Este serviço agrega dados de vendas para gerar resumos de lucro e séries
 * de dados para alimentar dashboards e gráficos.
 */
@Service
@RequiredArgsConstructor
public class AnaliseService {

    private final VendaRepository vendaRepository;
    private final Locale localeBrasil = new Locale("pt", "BR");

    /**
     * Representa um único ponto de dados num gráfico, associando um
     * rótulo de período (ex: "segunda-feira", "janeiro") a um valor de lucro.
     *
     * @param periodo O rótulo do eixo X do gráfico.
     * @param lucro   O valor do eixo Y do gráfico.
     */
    public record PontoGrafico(String periodo, BigDecimal lucro) {
    }

    /**
     * Calcula o lucro total para um determinado período (DIA, SEMANA, MES).
     * Ideal para exibir valores únicos em "cards" de um dashboard.
     *
     * @param periodo O {@link PeriodoAnalise} a ser considerado.
     * @return Um {@link AnaliseLucroDTO} contendo o lucro total consolidado.
     * @throws IllegalArgumentException se o período fornecido for inválido para esta análise.
     */
    @Transactional(readOnly = true)
    public AnaliseLucroDTO calcularLucroTotalPorPeriodo(PeriodoAnalise periodo) {
        LocalDateTime hoje = LocalDateTime.now();
        LocalDateTime inicio;
        LocalDateTime fim = hoje.with(LocalTime.MAX);

        switch (periodo) {
            case DIA -> inicio = hoje.with(LocalTime.MIN);
            case SEMANA -> inicio = hoje.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).with(LocalTime.MIN);
            case MES -> inicio = hoje.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
            default -> throw new IllegalArgumentException("Período inválido para esta análise: " + periodo);
        }

        BigDecimal totalLucro = vendaRepository.sumLucroByDataCriacaoBetween(inicio, fim);
        return new AnaliseLucroDTO(totalLucro);
    }

    /**
     * Gera uma série de dados de lucro para alimentar um gráfico.
     *
     * @param periodo O {@link PeriodoAnalise} (SEMANA, MES, ANO) que define a granularidade dos dados.
     * @return Uma lista de {@link PontoGrafico} pronta para ser renderizada num gráfico.
     * @throws IllegalArgumentException se o período fornecido for inválido.
     */
    @Transactional(readOnly = true)
    public List<PontoGrafico> gerarDadosGraficoLucro(PeriodoAnalise periodo) {
        return switch (periodo) {
            case SEMANA -> getLucroUltimosSeteDias();
            case MES -> getLucroMesCorrentePorDia();
            case ANO -> getLucroAnoCorrentePorMes();
            default -> throw new IllegalArgumentException("Período inválido para análise de gráfico: " + periodo);
        };
    }

    /**
     * Busca o lucro diário dos últimos 7 dias, incluindo o dia atual.
     *
     * @return Lista de {@link PontoGrafico} com os dias da semana como período.
     */
    private List<PontoGrafico> getLucroUltimosSeteDias() {
        LocalDate hoje = LocalDate.now();
        LocalDate dataInicio = hoje.minusDays(6);

        Map<LocalDate, BigDecimal> mapaDeLucros = buscarEAgruparLucroPorDia(dataInicio, hoje);

        List<PontoGrafico> resultado = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE", localeBrasil);

        for (int i = 0; i < 7; i++) {
            LocalDate diaCorrente = dataInicio.plusDays(i);
            BigDecimal lucro = mapaDeLucros.getOrDefault(diaCorrente, BigDecimal.ZERO);
            String nomeDia = formatter.format(diaCorrente);
            nomeDia = nomeDia.substring(0, 1).toUpperCase() + nomeDia.substring(1);
            resultado.add(new PontoGrafico(nomeDia, lucro));
        }
        return resultado;
    }

    /**
     * Busca o lucro diário desde o início do mês corrente até o dia atual.
     *
     * @return Lista de {@link PontoGrafico} com os dias no formato "dd/MM" como período.
     */
    private List<PontoGrafico> getLucroMesCorrentePorDia() {
        LocalDate hoje = LocalDate.now();
        LocalDate primeiroDiaDoMes = hoje.withDayOfMonth(1);

        Map<LocalDate, BigDecimal> mapaDeLucros = buscarEAgruparLucroPorDia(primeiroDiaDoMes, hoje);

        List<PontoGrafico> resultado = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

        for (LocalDate dia = primeiroDiaDoMes; !dia.isAfter(hoje); dia = dia.plusDays(1)) {
            BigDecimal lucro = mapaDeLucros.getOrDefault(dia, BigDecimal.ZERO);
            resultado.add(new PontoGrafico(dia.format(formatter), lucro));
        }
        return resultado;
    }

    /**
     * Busca o lucro mensal desde o início do ano corrente até o mês atual.
     *
     * @return Lista de {@link PontoGrafico} com os nomes dos meses como período.
     */
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

    /**
     * Funcao auxiliar para buscar lucros no repositório e agrupá-los por dia.
     *
     * @param inicio Data de início da consulta.
     * @param fim    Data de fim da consulta.
     * @return Um {@link Map} onde a chave é a {@link LocalDate} e o valor é o lucro total do dia.
     */
    private Map<LocalDate, BigDecimal> buscarEAgruparLucroPorDia(LocalDate inicio, LocalDate fim) {
        List<Object[]> lucrosDoBanco = vendaRepository.findLucroAgrupadoPorDia(inicio.atStartOfDay(), fim.atTime(LocalTime.MAX));
        return lucrosDoBanco.stream()
                .collect(Collectors.toMap(row -> (LocalDate) row[0], row -> (BigDecimal) row[1]));
    }
}