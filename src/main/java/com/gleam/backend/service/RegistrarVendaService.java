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

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço que encapsula a lógica de negócio para o registo de Vendas.
 * É responsável por orquestrar a criação de "recibos" de venda e o movimento de produtos do stock para o histórico.
 */
@Service
@RequiredArgsConstructor
public class RegistrarVendaService {

    private final RegistrarVendaRepository registrarVendaRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;

    /**
     * Regista a venda de um único produto. Este é o método central da lógica de negócio de vendas.
     * A operação é transacional, o que garante que todas as etapas (criar recibo, mover item, apagar produto)
     * sejam concluídas com sucesso, ou nenhuma delas é aplicada.
     *
     * @param produtoId O ID do produto a ser vendido.
     * @param detalhesVendaDTO O DTO contendo os detalhes da transação (ID do cliente, pagamento, etc.).
     * @return O RegistrarVendaDTO do "recibo" recém-criado.
     * @throws EntityNotFoundException se o produto ou o cliente não forem encontrados.
     * @throws IllegalStateException se o produto não estiver disponível para venda.
     */
    @Transactional
    public RegistrarVendaDTO registrarVenda(Long produtoId, RegistrarVendaDTO detalhesVendaDTO) {
        // 1. Buscar as entidades necessárias (Produto e Cliente)
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new EntityNotFoundException("Produto com ID " + produtoId + " não encontrado."));

        Cliente cliente = clienteRepository.findById(detalhesVendaDTO.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + detalhesVendaDTO.getClienteId() + " não encontrado."));

        // 2. Validar a regra de negócio: o produto deve estar disponível
        if (!produto.getStatus().equals(Produto.STATUS_DISPONIVEL)) {
            throw new IllegalStateException("Este produto não está disponível para venda.");
        }

        // 3. Criar o "recibo" (RegistrarVenda) com os dados da transação
        RegistrarVenda novaVenda = new RegistrarVenda();
        novaVenda.setNome(produto.getNome());
        novaVenda.setCliente(cliente);
        novaVenda.setSituacao(detalhesVendaDTO.getSituacao());
        novaVenda.setFormaPagamento(detalhesVendaDTO.getFormaPagamento());
        novaVenda.setNumeroParcelas(detalhesVendaDTO.getNumeroParcelas());
        novaVenda.setPrecoTotalVenda(produto.getPrecoVenda());

        // 4. Criar o ItemVendido (a cópia histórica) e associá-lo ao recibo
        ItemVendido itemVendido = new ItemVendido(produto);
        itemVendido.setRegistrarVenda(novaVenda);
        novaVenda.getItens().add(itemVendido);

        // 5. Salvar o recibo (o ItemVendido é salvo em cascata)
        RegistrarVenda vendaSalva = registrarVendaRepository.save(novaVenda);

        // 6. Apagar o produto original do stock
        produtoRepository.delete(produto);

        // 7. Retornar o DTO da venda concluída
        return convertToDto(vendaSalva);
    }

    /**
     * Busca uma lista paginada de todos os recibos de venda registados.
     * @param pageable Objeto com informações de paginação.
     * @return Uma página de RegistrarVendaDTOs.
     */
    public Page<RegistrarVendaDTO> findAll(Pageable pageable) {
        return registrarVendaRepository.findAll(pageable).map(this::convertToDto);
    }

    // --- CORREÇÃO AQUI: O MÉTODO findById FOI MOVIDO PARA FORA DO convertToDto ---
    /**
     * Busca um único recibo de venda pelo seu ID.
     * @param id O ID da venda a ser encontrada.
     * @return O DTO da venda correspondente.
     */
    public RegistrarVendaDTO findById(Long id) {
        RegistrarVenda venda = registrarVendaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Venda com ID " + id + " não encontrada."));
        return convertToDto(venda);
    }

    /**
     * Converte uma entidade RegistrarVenda para o seu respectivo DTO.
     * @param venda A entidade a ser convertida.
     * @return O DTO preenchido.
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

        // Converte a lista de entidades ItemVendido para uma lista de DTOs
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