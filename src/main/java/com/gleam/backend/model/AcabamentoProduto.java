package com.gleam.backend.model;

/**
 * <p>Define os tipos de acabamento que um produto pode ter.</p>
 *
 * <p>Esta classificação ajuda a categorizar os produtos com base na sua
 * composição, diferenciando materiais maciços de peças banhadas.</p>
 *
 * <ul>
 * <li><b>DOURADO:</b> peça fabricada em ouro maciço.</li>
 * <li><b>BANHO_DOURADO:</b> peça banhada ou folheada a ouro.</li>
 * <li><b>PRATA:</b> peça fabricada em prata maciça.</li>
 * <li><b>BANHO_PRATA:</b> peça banhada ou folheada a prata.</li>
 * <li><b>ACO:</b> peça fabricada em aço.</li>
 * </ul>
 */
public enum AcabamentoProduto {
    DOURADO,
    BANHO_DOURADO,
    PRATA,
    BANHO_PRATA,
    ACO
}