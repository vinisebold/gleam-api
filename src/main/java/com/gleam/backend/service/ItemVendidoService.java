package com.gleam.backend.service;

import com.gleam.backend.dto.ItemVendidoDTO;
import com.gleam.backend.model.ItemVendido;
import com.gleam.backend.model.Produto;
import com.gleam.backend.repository.ItemVendidoRepository;
import com.gleam.backend.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemVendidoService {

    private final ProdutoRepository produtoRepository;
    private final ItemVendidoRepository itemVendidoRepository;

    @Transactional
    public ItemVendidoDTO registrarVenda(Long produtoId) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new EntityNotFoundException("Produto com ID " + produtoId + " não encontrado para venda."));

        if (!produto.getStatus().equals(Produto.STATUS_DISPONIVEL)) {
            throw new IllegalStateException("Este produto não está disponível para venda.");
        }

        ItemVendido itemVendido = new ItemVendido(produto);
        ItemVendido itemVendidoSalvo = itemVendidoRepository.save(itemVendido);

        produtoRepository.delete(produto);

        return convertToDto(itemVendidoSalvo);
    }

    public Page<ItemVendidoDTO> findAll(Pageable pageable) {
        return itemVendidoRepository.findAll(pageable).map(this::convertToDto);
    }

    private ItemVendidoDTO convertToDto(ItemVendido item) {
        ItemVendidoDTO dto = new ItemVendidoDTO();
        dto.setId(item.getId());
        dto.setProdutoOriginalId(item.getProdutoOriginalId());
        dto.setNome(item.getNome());
        dto.setPrecoVenda(item.getPrecoVenda());
        dto.setLucro(item.getLucro());
        dto.setDataVenda(item.getDataVenda());
        dto.setCategoria(item.getCategoria());
        return dto;
    }
}