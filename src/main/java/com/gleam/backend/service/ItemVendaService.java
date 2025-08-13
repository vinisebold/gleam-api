package com.gleam.backend.service;

import com.gleam.backend.dto.ItemVendaDTO;
import com.gleam.backend.model.ItemVenda;
import com.gleam.backend.model.ItemVendaId;
import com.gleam.backend.model.Produto;
import com.gleam.backend.model.Venda;
import com.gleam.backend.repository.ItemVendaRepository;
import com.gleam.backend.repository.ProdutoRepository; // ALTERAÇÃO: Import
import com.gleam.backend.repository.VendaRepository;   // ALTERAÇÃO: Import
import jakarta.persistence.EntityNotFoundException;     // ALTERAÇÃO: Import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemVendaService {

    @Autowired
    private ItemVendaRepository itemVendaRepository;

    // ALTERAÇÃO: Injetar repositórios para buscar as entidades
    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional // ALTERAÇÃO: Garante que todas as operações sejam feitas em uma única transação
    public ItemVenda save(ItemVendaDTO itemVendaDTO) {
        // ALTERAÇÃO: Buscar a Venda para garantir que ela existe
        Venda venda = vendaRepository.findById(itemVendaDTO.getIdVenda())
                .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada com o ID: " + itemVendaDTO.getIdVenda()));

        // ALTERAÇÃO: Buscar o Produto para garantir que ele existe e para pegar seu preço
        Produto produto = produtoRepository.findById(itemVendaDTO.getIdProduto())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + itemVendaDTO.getIdProduto()));

        // Criar o ID composto para o ItemVenda
        ItemVendaId id = new ItemVendaId(venda.getId(), produto.getId());

        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setId(id);
        itemVenda.setVenda(venda);     // Associa a entidade Venda completa
        itemVenda.setProduto(produto); // Associa a entidade Produto completa
        itemVenda.setQuantidade(itemVendaDTO.getQuantidade());

        // ALTERAÇÃO CRÍTICA: Definir o preço unitário com base no preço de venda do produto
        // Isso evita que o frontend manipule o preço.
        itemVenda.setPrecoUnitario(produto.getPrecoVenda());

        return itemVendaRepository.save(itemVenda);
    }
}