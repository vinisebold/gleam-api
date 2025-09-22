package com.gleam.backend.model;

/**
 * <p>Define o status financeiro de uma venda.</p>
 *
 * <p>Este enum é essencial para controlar o estado dos pagamentos (e venda).</p>
 *
 * <ul>
 * <li><b>PENDENTE:</b> venda foi registada, mas aguarda o pagamento completo.</li>
 * <li><b>PAGO:</b> transação concluída e o valor total foi recebido.</li>
 * <li><b>CANCELADO:</b> venda anulada. Produto associado deve voltar ao status EM_ESTOQUE.</li>
 * </ul>
 */
public enum StatusVenda {
    PENDENTE,
    PAGO,
    CANCELADO
}