package com.gleam.backend.dto;

import com.gleam.backend.model.FormaPagamento;
import com.gleam.backend.model.StatusVenda;
import com.gleam.backend.model.Venda;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO para representar uma Venda na resposta da API.
 * Fornece uma visão detalhada da transação, incluindo dados do produto e cliente.
 *
 * @param id                      O identificador único da venda.
 * @param produto                 O DTO do produto vendido.
 * @param clienteId               O ID do cliente.
 * @param nomeCliente             O nome do cliente.
 * @param precoVenda              O preço final da venda.
 * @param lucro                   O lucro obtido na venda.
 * @param formaPagamento          A forma de pagamento.
 * @param totalParcelas           O número total de parcelas.
 * @param parcelasPagas           O número de parcelas já pagas.
 * @param valorTotalParcelasPagas O valor monetário total já pago.
 * @param status                  O status financeiro da venda.
 * @param dataVencimento          A data de vencimento para vendas pendentes.
 * @param dataCriacao             A data em que a venda foi registada.
 */
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
        BigDecimal valorTotalParcelasPagas,
        StatusVenda status,
        LocalDate dataVencimento,
        LocalDateTime dataCriacao
) {
    /**
     * Construtor de conversão para criar um DTO a partir de uma entidade Venda.
     *
     * @param venda A entidade {@link Venda} de origem.
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
                calcularValorTotalPago(venda),
                venda.getStatus(),
                venda.getDataVencimento(),
                venda.getDataCriacao()
        );
    }

    /**
     * Calcula o valor total pago com base no número de parcelas pagas.
     *
     * @param venda A entidade Venda.
     * @return O valor total pago como {@link BigDecimal}.
     */
    private static BigDecimal calcularValorTotalPago(Venda venda) {
        if (venda.getTotalParcelas() == null || venda.getTotalParcelas() <= 0 ||
                venda.getPrecoVenda() == null || venda.getParcelasPagas() == null || venda.getParcelasPagas() <= 0) {
            return BigDecimal.ZERO;
        }

        if (venda.getStatus() == StatusVenda.PAGO) {
            return venda.getPrecoVenda();
        }

        BigDecimal valorParcela = venda.getPrecoVenda().divide(
                new BigDecimal(venda.getTotalParcelas()), 2, RoundingMode.HALF_UP
        );

        return valorParcela.multiply(new BigDecimal(venda.getParcelasPagas()));
    }
}