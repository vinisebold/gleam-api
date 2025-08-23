package com.gleam.backend.service;

import com.gleam.backend.dto.ItemVendidoDTO;
import com.gleam.backend.dto.RegistrarVendaDTO;
import com.gleam.backend.model.Cliente; // Importe a entidade Cliente
import com.gleam.backend.model.ItemVendido;
import com.gleam.backend.model.Produto;
import com.gleam.backend.model.RegistrarVenda;
import com.gleam.backend.repository.ClienteRepository; // Importe o repositório do Cliente
import com.gleam.backend.repository.ItemVendidoRepository;
import com.gleam.backend.repository.ProdutoRepository;
import com.gleam.backend.repository.RegistrarVendaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemVendidoService {

    private final ProdutoRepository produtoRepository;
    private final ItemVendidoRepository itemVendidoRepository;
    private final RegistrarVendaRepository registrarVendaRepository;
    private final ClienteRepository clienteRepository; // Nova dependência

    @Transactional
    public RegistrarVendaDTO registrarVendaDeProdutoUnico(Long produtoId, RegistrarVendaDTO detalhesVendaDTO) {
        // 1. Encontrar o produto a ser vendido
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new EntityNotFoundException("Produto com ID " + produtoId + " não encontrado para venda."));

        if (!produto.getStatus().equals(Produto.STATUS_DISPONIVEL)) {
            throw new IllegalStateException("Este produto não está disponível para venda.");
        }

        // 2. Buscar a entidade Cliente usando o ID fornecido no DTO
        Cliente cliente = clienteRepository.findById(detalhesVendaDTO.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + detalhesVendaDTO.getClienteId() + " não encontrado."));

        // 3. Criar o "Recibo" (RegistrarVenda)
        RegistrarVenda recibo = new RegistrarVenda();
        recibo.setNome(produto.getNome());
        recibo.setPrecoTotalVenda(produto.getPrecoVenda());
        recibo.setCliente(cliente); // <-- CORREÇÃO AQUI: Associa a entidade Cliente completa
        recibo.setSituacao(detalhesVendaDTO.getSituacao());
        recibo.setFormaPagamento(detalhesVendaDTO.getFormaPagamento());
        recibo.setNumeroParcelas(detalhesVendaDTO.getNumeroParcelas());

        // 4. Criar o ItemVendido e ligar ao recibo
        ItemVendido itemVendido = new ItemVendido(produto);
        itemVendido.setRegistrarVenda(recibo);
        recibo.setItem(itemVendido);;

        // 5. Salvar o recibo (que salva o item junto)
        RegistrarVenda reciboSalvo = registrarVendaRepository.save(recibo);

        // 6. Apagar o produto original
        produtoRepository.delete(produto);

        // 7. Retornar o DTO do recibo criado
        return convertToDto(reciboSalvo);
    }

    public Page<ItemVendidoDTO> findAllItensVendidos(Pageable pageable) {
        return itemVendidoRepository.findAll(pageable).map(this::convertItemToDto);
    }

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

        if (venda.getItem() != null) {
            dto.setItem(convertItemToDto(venda.getItem()));
        }
        return dto;
    }

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