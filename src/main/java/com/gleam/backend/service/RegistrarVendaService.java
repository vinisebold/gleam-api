package com.gleam.backend.service;

import com.gleam.backend.dto.ItemVendidoDTO;
import com.gleam.backend.dto.RegistrarVendaDTO;
import com.gleam.backend.model.Cliente;
import com.gleam.backend.model.ItemVendido;
import com.gleam.backend.model.Produto;
import com.gleam.backend.model.RegistrarVenda;
import com.gleam.backend.repository.ClienteRepository;
import com.gleam.backend.repository.ProdutoRepository;
import com.gleam.backend.repository.RegistrarVendaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço que encapsula a lógica de negócio para o registo de Vendas.
 */
@Service
@RequiredArgsConstructor
public class RegistrarVendaService {

    private final RegistrarVendaRepository registrarVendaRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;

    /**
     * Regista a venda de um único produto.
     */
    @Transactional
    public RegistrarVendaDTO registrarVenda(Long produtoId, RegistrarVendaDTO detalhesVendaDTO) {
        // 1. Buscar as entidades necessárias
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new EntityNotFoundException("Produto com ID " + produtoId + " não encontrado."));

        Cliente cliente = clienteRepository.findById(detalhesVendaDTO.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + detalhesVendaDTO.getClienteId() + " não encontrado."));

        // 2. Validar a regra de negócio
        if (!produto.getStatus().equals(Produto.STATUS_DISPONIVEL)) {
            throw new IllegalStateException("Este produto não está disponível para venda.");
        }

        // 3. Criar o "recibo" (RegistrarVenda)
        RegistrarVenda novaVenda = new RegistrarVenda();
        novaVenda.setNome(produto.getNome());
        novaVenda.setCliente(cliente);
        novaVenda.setSituacao(detalhesVendaDTO.getSituacao());
        novaVenda.setFormaPagamento(detalhesVendaDTO.getFormaPagamento());
        novaVenda.setNumeroParcelas(detalhesVendaDTO.getNumeroParcelas());
        novaVenda.setPrecoTotalVenda(produto.getPrecoVenda());

        // 4. Criar o ItemVendido e associá-lo ao recibo
        ItemVendido itemVendido = new ItemVendido(produto);
        itemVendido.setRegistrarVenda(novaVenda);
        novaVenda.getItens().add(itemVendido);

        // 5. Salvar e apagar
        RegistrarVenda vendaSalva = registrarVendaRepository.save(novaVenda);
        produtoRepository.delete(produto);

        return convertToDto(vendaSalva);
    }

    /**
     * Busca uma lista paginada de todos os recibos de venda registados.
     */
    public Page<RegistrarVendaDTO> findAll(Pageable pageable) {
        return registrarVendaRepository.findAll(pageable).map(this::convertToDto);
    }

    /**
     * Busca um único recibo de venda pelo seu ID.
     */
    public RegistrarVendaDTO findById(Long id) {
        RegistrarVenda venda = registrarVendaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Venda com ID " + id + " não encontrada."));
        return convertToDto(venda);
    }

    /**
     * Converte uma entidade RegistrarVenda para o seu respectivo DTO.
     */
    private RegistrarVendaDTO convertToDto(RegistrarVenda venda) {
        RegistrarVendaDTO dto = new RegistrarVendaDTO();
        dto.setId(venda.getId());
        dto.setNome(venda.getNome());
        if (venda.getCliente() != null) {
            dto.setClienteId(venda.getCliente().getId());
            dto.setNomeCliente(venda.getCliente().getNome());
        }
        dto.setSituacao(venda.getSituacao());
        dto.setFormaPagamento(venda.getFormaPagamento());
        dto.setNumeroParcelas(venda.getNumeroParcelas());
        dto.setPrecoTotalVenda(venda.getPrecoTotalVenda());
        dto.setDataCriacao(venda.getDataCriacao());

        // --- LÓGICA DE CONVERSÃO ATUALIZADA ---
        // Se a lista de itens não estiver vazia, pegamos o primeiro (e único) item.
        if (venda.getItens() != null && !venda.getItens().isEmpty()) {
            ItemVendido primeiroItem = venda.getItens().get(0);
            dto.setItem(convertItemToDto(primeiroItem));
        }

        return dto;
    }

    /**
     * Converte uma entidade ItemVendido para um ItemVendidoDTO.
     */
    private ItemVendidoDTO convertItemToDto(ItemVendido item) {
        ItemVendidoDTO itemDto = new ItemVendidoDTO();
        itemDto.setId(item.getId());
        itemDto.setProdutoOriginalId(item.getProdutoOriginalId());
        itemDto.setNome(item.getNome());
        itemDto.setPrecoVenda(item.getPrecoVenda());
        itemDto.setLucro(item.getLucro());
        itemDto.setDataVenda(item.getDataVenda());
        itemDto.setCategoria(item.getCategoria());
        return itemDto;
    }
}