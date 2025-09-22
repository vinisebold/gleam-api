package com.gleam.backend.model;

/**
 * <p>Define os períodos disponíveis para as análises financeiras.</p>
 *
 * <p>Este enum serve como um filtro para os endpoints da API de análise,
 * permitindo que o frontend solicite dados de lucro para diferentes intervalos de tempo</p>
 */
public enum PeriodoAnalise {
    DIA,    // Usado pelo card
    SEMANA, // Usado por ambos
    MES,    // Usado por ambos
    ANO     // Usado pelo gráfico
}