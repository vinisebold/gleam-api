package com.gleam.backend.model;

/**
 * <p>Define as formas de pagamento aceitas pelo sistema.</p>
 *
 * <ul>
 * <li><b>PIX</b></li>
 * <li><b>DINHEIRO</b></li>
 * <li><b>CARTAO_CREDITO</b></li>
 * <li><b>CARTAO_DEBITO</b></li>
 * </ul>
 */
public enum FormaPagamento {
    PIX,
    DINHEIRO,
    CARTAO_CREDITO,
    CARTAO_DEBITO
}