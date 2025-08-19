package com.gleam.backend.service;

import com.gleam.backend.dto.ItemVendaDTO;
import com.gleam.backend.model.ItemVenda;
import com.gleam.backend.model.ItemVendaId;
import com.gleam.backend.model.Produto;
import com.gleam.backend.model.Venda;
import com.gleam.backend.repository.ItemVendaRepository;
import com.gleam.backend.repository.ProdutoRepository;
import com.gleam.backend.repository.VendaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemVendaService {

    @Autowired
    private ItemVendaRepository itemVendaRepository;
    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    /**
     * Adiciona um item a uma venda existente.
     * @param dto Os dados do item a ser adicionado.
     * @return O ItemVenda salvo.
     */
    @Transactional
    public ItemVenda save(ItemVendaDTO dto) {
        // 1. Busca a Venda e o Produto para garantir que ambos existem.
        Venda venda = vendaRepository.findById(dto.getIdVenda())
                .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada com o ID: " + dto.getIdVenda()));

        Produto produto = produtoRepository.findById(dto.getIdProduto())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + dto.getIdProduto()));

        // 2. Cria a chave primária composta.
        ItemVendaId id = new ItemVendaId(venda.getId(), produto.getId());

        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setId(id);
        itemVenda.setVenda(venda);
        itemVenda.setProduto(produto);
        itemVenda.setQuantidade(dto.getQuantidade());

        // 3. PONTO CRÍTICO DE SEGURANÇA: O preço é definido pelo backend.
        // Ignoramos qualquer preço que possa vir do frontend e usamos o preço
        // que está cadastrado no banco de dados para o produto.
        itemVenda.setPrecoUnitario(produto.getPrecoVenda());

        return itemVendaRepository.save(itemVenda);
    }

    /**
     * Busca todos os itens de uma venda específica.
     * @param idVenda O ID da venda.
     * @return Uma lista de ItemVenda.
     */
    public List<ItemVenda> findByVenda(Long idVenda) {
        return itemVendaRepository.findByVendaId(idVenda);
    }
}
