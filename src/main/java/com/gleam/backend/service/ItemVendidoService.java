package com.gleam.backend.service;

import com.gleam.backend.dto.ItemVendidoDTO;
import com.gleam.backend.dto.RegistrarVendaDTO;
import com.gleam.backend.model.ItemVendido;
import com.gleam.backend.model.Produto;
import com.gleam.backend.model.RegistrarVenda;
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

    /**
     * Registra a venda de um único produto, usando os detalhes de pagamento fornecidos.
     */
    @Transactional
    public RegistrarVendaDTO registrarVendaDeProdutoUnico(Long produtoId, RegistrarVendaDTO detalhesVendaDTO) {
        // 1. Encontrar o produto que será vendido
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new EntityNotFoundException("Produto com ID " + produtoId + " não encontrado para venda."));

        if (!produto.getStatus().equals(Produto.STATUS_DISPONIVEL)) {
            throw new IllegalStateException("Este produto não está disponível para venda.");
        }

        // 2. Criar o "Recibo" (RegistrarVenda)
        RegistrarVenda recibo = new RegistrarVenda();
        recibo.setNome(produto.getNome()); // O nome do recibo é o nome do produto
        recibo.setPrecoTotalVenda(produto.getPrecoVenda());

        // LÓGICA ATUALIZADA: Usa os dados recebidos do DTO
        recibo.setNomeCliente(detalhesVendaDTO.getNomeCliente());
        recibo.setSituacao(detalhesVendaDTO.getSituacao());
        recibo.setFormaPagamento(detalhesVendaDTO.getFormaPagamento());
        recibo.setNumeroParcelas(detalhesVendaDTO.getNumeroParcelas());

        // 3. Criar o ItemVendido e ligar ao recibo
        ItemVendido itemVendido = new ItemVendido(produto);
        itemVendido.setRegistrarVenda(recibo);
        recibo.getItens().add(itemVendido);

        // 4. Salvar o recibo (que salva o item junto)
        RegistrarVenda reciboSalvo = registrarVendaRepository.save(recibo);

        // 5. Apagar o produto original
        produtoRepository.delete(produto);

        // 6. Retornar o DTO do recibo criado
        return convertToDto(reciboSalvo);
    }

    public Page<ItemVendidoDTO> findAllItensVendidos(Pageable pageable) {
        return itemVendidoRepository.findAll(pageable).map(this::convertItemToDto);
    }

    // --- MÉTODOS DE CONVERSÃO (sem alteração) ---

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

        List<ItemVendidoDTO> itemDTOs = venda.getItens().stream()
                .map(this::convertItemToDto)
                .collect(Collectors.toList());

        dto.setItens(itemDTOs);
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