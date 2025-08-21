package com.gleam.backend.service;

import com.gleam.backend.dto.ItemVendidoDTO;
import com.gleam.backend.dto.RegistrarVendaDTO;
import com.gleam.backend.model.ItemVendido;
import com.gleam.backend.model.Produto;
import com.gleam.backend.model.RegistrarVenda;
import com.gleam.backend.repository.ProdutoRepository;
import com.gleam.backend.repository.RegistrarVendaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistrarVendaService {

    private final RegistrarVendaRepository registrarVendaRepository;
    private final ProdutoRepository produtoRepository;

    @Transactional
    public RegistrarVendaDTO registrarVenda(RegistrarVendaDTO registrarVendaDTO) {
        if (registrarVendaDTO.getProdutoIds() == null || registrarVendaDTO.getProdutoIds().isEmpty()) {
            throw new IllegalArgumentException("A lista de produtos não pode ser vazia.");
        }

        // 1. Buscar produtos, calcular total e montar o nome da venda
        List<Produto> produtosParaVender = new ArrayList<>();
        List<String> nomesDosProdutos = new ArrayList<>();
        BigDecimal precoTotal = BigDecimal.ZERO;

        for (Long produtoId : registrarVendaDTO.getProdutoIds()) {
            Produto produto = produtoRepository.findById(produtoId)
                    .orElseThrow(() -> new EntityNotFoundException("Produto com ID " + produtoId + " não encontrado."));
            produtosParaVender.add(produto);
            nomesDosProdutos.add(produto.getNome());
            precoTotal = precoTotal.add(produto.getPrecoVenda());
        }

        // 2. Criar e preencher a entidade RegistrarVenda
        RegistrarVenda novaVenda = new RegistrarVenda();
        novaVenda.setNome(String.join(", ", nomesDosProdutos));
        novaVenda.setNomeCliente(registrarVendaDTO.getNomeCliente());
        novaVenda.setSituacao(registrarVendaDTO.getSituacao());
        novaVenda.setFormaPagamento(registrarVendaDTO.getFormaPagamento());
        novaVenda.setNumeroParcelas(registrarVendaDTO.getNumeroParcelas());
        novaVenda.setPrecoTotalVenda(precoTotal);

        // 3. Mover os produtos para a tabela de itens vendidos
        for (Produto produto : produtosParaVender) {
            ItemVendido itemVendido = new ItemVendido(produto);
            itemVendido.setRegistrarVenda(novaVenda);
            novaVenda.getItens().add(itemVendido);
            produtoRepository.delete(produto);
        }

        RegistrarVenda vendaSalva = registrarVendaRepository.save(novaVenda);
        return convertToDto(vendaSalva);
    }

    public Page<RegistrarVendaDTO> findAll(Pageable pageable) {
        return registrarVendaRepository.findAll(pageable).map(this::convertToDto);
    }

    private RegistrarVendaDTO convertToDto(RegistrarVenda venda) {
        RegistrarVendaDTO dto = new RegistrarVendaDTO();
        dto.setId(venda.getId());
        dto.setNome(venda.getNome());
        dto.setNomeCliente(venda.getNomeCliente());
        dto.setSituacao(venda.getSituacao());
        dto.setFormaPagamento(venda.getFormaPagamento());
        dto.setNumeroParcelas(venda.getNumeroParcelas());
        dto.setPrecoTotalVenda(venda.getPrecoTotalVenda());
        dto.setDataCriacao(venda.getDataCriacao());

        List<ItemVendidoDTO> itemDTOs = venda.getItens().stream().map(item -> {
            ItemVendidoDTO itemDto = new ItemVendidoDTO();
            itemDto.setId(item.getId());
            itemDto.setProdutoOriginalId(item.getProdutoOriginalId());
            itemDto.setNome(item.getNome());
            itemDto.setPrecoVenda(item.getPrecoVenda());
            itemDto.setLucro(item.getLucro());
            itemDto.setDataVenda(item.getDataVenda());
            itemDto.setCategoria(item.getCategoria());
            return itemDto;
        }).collect(Collectors.toList());

        dto.setItens(itemDTOs);
        return dto;
    }
}