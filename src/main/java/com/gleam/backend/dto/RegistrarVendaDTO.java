package com.gleam.backend.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO (Data Transfer Object) para a entidade RegistrarVenda.
 * Define a estrutura de dados (JSON) que é enviada e recebida
 * pela API para operações de venda.
 */
@Data
public class RegistrarVendaDTO {

    // --- Campos de ENTRADA (Enviados no corpo da requisição POST) ---

    /**
     * O ID do Cliente que está a fazer a compra.
     * Este campo é obrigatório para registar uma nova venda.
     */
    private Long clienteId;

    /**
     * A situação do pagamento da venda.
     * Utiliza as constantes da entidade RegistrarVenda (0 = Pendente, 1 = Pago).
     */
    private Integer situacao;

    /**
     * A forma de pagamento utilizada.
     * Utiliza as constantes da entidade RegistrarVenda (1 = PIX, 2 = Dinheiro, etc.).
     */
    private Integer formaPagamento;

    /**
     * O número de parcelas, se aplicável (ex: para cartão de crédito).
     */
    private Integer numeroParcelas;

    // --- Campos de SAÍDA (Retornados pela API como resposta) ---

    /**
     * O ID único do registo da venda, gerado após a criação.
     */
    private Long id;

    /**
     * O nome descritivo da venda, gerado automaticamente a partir do nome do produto.
     */
    private String nome;

    /**
     * O nome do cliente associado à venda.
     * Incluído na resposta para conveniência do frontend.
     */
    private String nomeCliente;

    /**
     * O preço total da venda.
     */
    private BigDecimal precoTotalVenda;

    /**
     * A lista detalhada de todos os itens que foram vendidos nesta transação.
     */
    private List<ItemVendidoDTO> itens;

    /**
     * A data e hora em que a venda foi registada.
     */
    private LocalDateTime dataCriacao;
}