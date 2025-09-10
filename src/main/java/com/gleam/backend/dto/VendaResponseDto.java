package com.gleam.backend.dto;

import com.gleam.backend.model.FormaPagamento;
import com.gleam.backend.model.StatusVenda;
import com.gleam.backend.model.Venda;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record VendaResponseDto(
        Long id,
        ProdutoDTO produto,
        Long clienteId,
        String nomeCliente,
        BigDecimal precoVenda,
        BigDecimal lucro,
        FormaPagamento formaPagamento,
        Integer totalParcelas,
        Integer parcelasPagas,
        BigDecimal valorTotalPago, // <-- NOVO CAMPO ADICIONADO AQUI
        StatusVenda status,
        LocalDate dataVencimento,
        LocalDateTime dataCriacao
) {
    /**
     * Construtor para converter uma entidade Venda em um DTO,
     * incluindo o cálculo do valor total pago.
     */
    public VendaResponseDto(Venda venda) {
        this(
                venda.getId(),
                new ProdutoDTO(venda.getProduto()),
                venda.getCliente().getId(),
                venda.getCliente().getNome(),
                venda.getPrecoVenda(),
                venda.getLucro(),
                venda.getFormaPagamento(),
                venda.getTotalParcelas(),
                venda.getParcelasPagas(),
                calcularValorTotalPago(venda), // <-- LÓGICA DE CÁLCULO CHAMADA AQUI
                venda.getStatus(),
                venda.getDataVencimento(),
                venda.getDataCriacao()
        );
    }

    /**
     * Método privado para calcular o valor total pago das parcelas.
     */
    private static BigDecimal calcularValorTotalPago(Venda venda) {
        // Se não houver parcelas ou valor, retorna zero.
        if (venda.getTotalParcelas() == null || venda.getTotalParcelas() <= 0 ||
                venda.getPrecoVenda() == null || venda.getParcelasPagas() == null || venda.getParcelasPagas() <= 0) {
            return BigDecimal.ZERO;
        }

        // Se a venda já estiver PAGA, retorna o valor total para evitar erros de arredondamento.
        if (venda.getStatus() == StatusVenda.PAGO) {
            return venda.getPrecoVenda();
        }

        // Calcula o valor da parcela
        BigDecimal valorParcela = venda.getPrecoVenda().divide(
                new BigDecimal(venda.getTotalParcelas()), 2, RoundingMode.HALF_UP
        );

        // Multiplica pelo número de parcelas pagas
        return valorParcela.multiply(new BigDecimal(venda.getParcelasPagas()));
    }
}