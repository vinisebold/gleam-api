package com.gleam.backend.service;

import com.gleam.backend.dto.ItemVendidoDTO;
import com.gleam.backend.dto.RegistrarVendaDTO;
import com.gleam.backend.model.Cliente; // Importe a entidade Cliente
import com.gleam.backend.model.ItemVendido;
import com.gleam.backend.model.Produto;
import com.gleam.backend.model.RegistrarVenda;
import com.gleam.backend.repository.ClienteRepository; // Importe o repositório do Cliente
import com.gleam.backend.repository.ProdutoRepository;
import com.gleam.backend.repository.RegistrarVendaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistrarVendaService {

    private final RegistrarVendaRepository registrarVendaRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository; // Nova dependência

    @Transactional
    public RegistrarVendaDTO registrarVenda(Long produtoId, RegistrarVendaDTO detalhesVendaDTO) {
        // 1. Buscar o produto a ser vendido
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new EntityNotFoundException("Produto com ID " + produtoId + " não encontrado."));

        // 2. Buscar a entidade Cliente usando o ID fornecido no DTO
        Cliente cliente = clienteRepository.findById(detalhesVendaDTO.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + detalhesVendaDTO.getClienteId() + " não encontrado."));

        // 3. Criar e preencher a entidade RegistrarVenda (o "recibo")
        RegistrarVenda novaVenda = new RegistrarVenda();
        novaVenda.setNome(produto.getNome());
        novaVenda.setCliente(cliente); // <-- CORREÇÃO AQUI: Associa a entidade Cliente completa
        novaVenda.setSituacao(detalhesVendaDTO.getSituacao());
        novaVenda.setFormaPagamento(detalhesVendaDTO.getFormaPagamento());
        novaVenda.setNumeroParcelas(detalhesVendaDTO.getNumeroParcelas());
        novaVenda.setPrecoTotalVenda(produto.getPrecoVenda());

        // 4. Criar o ItemVendido e ligá-lo ao recibo
        ItemVendido itemVendido = new ItemVendido(produto);
        itemVendido.setRegistrarVenda(novaVenda);
        novaVenda.getItens().add(itemVendido);

        // 5. Salvar o recibo
        RegistrarVenda vendaSalva = registrarVendaRepository.save(novaVenda);

        // 6. Apagar o produto original do stock
        produtoRepository.delete(produto);

        return convertToDto(vendaSalva);
    }

    public Page<RegistrarVendaDTO> findAll(Pageable pageable) {
        return registrarVendaRepository.findAll(pageable).map(this::convertToDto);
    }

    private RegistrarVendaDTO convertToDto(RegistrarVenda venda) {
        RegistrarVendaDTO dto = new RegistrarVendaDTO();
        dto.setId(venda.getId());
        dto.setNome(venda.getNome());
        // Preenche os dados do cliente no DTO de resposta
        if (venda.getCliente() != null) {
            dto.setClienteId(venda.getCliente().getId());
            dto.setNomeCliente(venda.getCliente().getNome());
        }
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