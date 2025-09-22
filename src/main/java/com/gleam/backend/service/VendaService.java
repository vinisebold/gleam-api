package com.gleam.backend.service;

import com.gleam.backend.dto.RegistrarVendaRequestDto;
import com.gleam.backend.dto.VendaResponseDto;
import com.gleam.backend.model.*;
import com.gleam.backend.repository.ClienteRepository;
import com.gleam.backend.repository.ProdutoRepository;
import com.gleam.backend.repository.VendaRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Encapsula a lógica de negócio para a gestão de Vendas.
 * Orquestra as operações que envolvem produtos e clientes durante o
 * registo, pagamento e cancelamento de transações.
 */
@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;

    /**
     * Lista as vendas de forma paginada, com a opção de filtrar por status.
     *
     * @param pageable Objeto com as informações de paginação.
     * @param status   O {@link StatusVenda} para filtrar os resultados (opcional).
     * @return Uma {@link Page} de {@link VendaResponseDto} com as vendas encontradas.
     */
    @Transactional(readOnly = true)
    public Page<VendaResponseDto> findAll(Pageable pageable, StatusVenda status) {
        Page<Venda> vendas;
        if (status != null) {
            vendas = vendaRepository.findByStatus(status, pageable);
        } else {
            vendas = vendaRepository.findAll(pageable);
        }
        return vendas.map(VendaResponseDto::new);
    }

    /**
     * Regista uma nova venda no sistema.
     * Esta operação é transacional e atómica: atualiza o status do produto para VENDIDO
     * e cria o registo da venda.
     *
     * @param dto O {@link RegistrarVendaRequestDto} com os detalhes da transação.
     * @return O {@link VendaResponseDto} da venda recém-criada.
     * @throws EntityNotFoundException se o produto ou o cliente não forem encontrados.
     * @throws IllegalStateException   se o produto não estiver disponível para venda (não está EM_ESTOQUE).
     */
    @Transactional
    public VendaResponseDto registrarVenda(RegistrarVendaRequestDto dto) {
        Produto produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new EntityNotFoundException("Produto com ID " + dto.produtoId() + " não encontrado."));
        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + dto.clienteId() + " não encontrado."));

        if (produto.getStatus() != StatusProduto.EM_ESTOQUE) {
            throw new IllegalStateException("O produto '" + produto.getNome() + "' não está disponível para venda.");
        }

        produto.setStatus(StatusProduto.VENDIDO);
        produto.setDataVenda(LocalDateTime.now());

        Venda novaVenda = getVenda(dto, produto, cliente);
        Venda vendaSalva = vendaRepository.save(novaVenda);

        return new VendaResponseDto(vendaSalva);
    }

    /**
     * Regista o pagamento da próxima parcela de uma venda.
     * Se for a última parcela, o status da venda é atualizado para PAGO.
     *
     * @param vendaId O ‘ID’ da venda a ser atualizada.
     * @return O {@link VendaResponseDto} com os dados da venda atualizada.
     * @throws EntityNotFoundException se a venda não for encontrada.
     * @throws IllegalStateException   se a venda já estiver totalmente paga.
     */
    @Transactional
    public VendaResponseDto pagarProximaParcela(Long vendaId) {
        Venda venda = vendaRepository.findById(vendaId)
                .orElseThrow(() -> new EntityNotFoundException("Venda com ID " + vendaId + " não encontrada."));

        if (venda.getStatus() == StatusVenda.PAGO) {
            throw new IllegalStateException("Esta venda já está totalmente paga.");
        }

        venda.setParcelasPagas(venda.getParcelasPagas() + 1);

        if (venda.getParcelasPagas() >= venda.getTotalParcelas()) {
            venda.setStatus(StatusVenda.PAGO);
        }

        Venda vendaAtualizada = vendaRepository.save(venda);
        return new VendaResponseDto(vendaAtualizada);
    }

    /**
     * Cancela uma venda existente.
     * Esta operação altera o status da venda para CANCELADO e retorna o produto
     * associado ao estoque, alterando o seu status para EM_ESTOQUE.
     *
     * @param vendaId O ID da venda a ser cancelada.
     * @return O {@link VendaResponseDto} da venda com o status atualizado.
     * @throws EntityNotFoundException se a venda não for encontrada.
     * @throws IllegalStateException   se a venda já estiver cancelada.
     */
    @Transactional
    public VendaResponseDto cancelarVenda(Long vendaId) {
        Venda venda = vendaRepository.findById(vendaId)
                .orElseThrow(() -> new EntityNotFoundException("Venda com ID " + vendaId + " não encontrada."));

        if (venda.getStatus() == StatusVenda.CANCELADO) {
            throw new IllegalStateException("Esta venda já está cancelada.");
        }

        Produto produto = venda.getProduto();
        if (produto != null) {
            produto.setStatus(StatusProduto.EM_ESTOQUE);
            produto.setDataVenda(null);
        }

        venda.setStatus(StatusVenda.CANCELADO);

        Venda vendaCancelada = vendaRepository.save(venda);
        return new VendaResponseDto(vendaCancelada);
    }

    /**
     * Monta uma nova entidade {@link Venda} a partir dos dados de entrada.
     *
     * @param dto     O DTO com os dados da requisição.
     * @param produto A entidade {@link Produto} associada.
     * @param cliente A entidade {@link Cliente} associada.
     * @return Uma nova instância de {@link Venda}, preenchida e pronta para ser persistida.
     */
    private static Venda getVenda(RegistrarVendaRequestDto dto, Produto produto, Cliente cliente) {
        Venda novaVenda = new Venda();
        novaVenda.setProduto(produto);
        novaVenda.setCliente(cliente);
        novaVenda.setPrecoVenda(dto.precoVenda());
        novaVenda.setFormaPagamento(dto.formaPagamento());
        novaVenda.setTotalParcelas(dto.totalParcelas());
        novaVenda.setStatus(dto.status());
        novaVenda.setDataVencimento(dto.dataVencimento());

        if (dto.status() == StatusVenda.PAGO) {
            novaVenda.setParcelasPagas(dto.totalParcelas());
        } else {
            novaVenda.setParcelasPagas(0);
        }

        BigDecimal lucro = dto.precoVenda().subtract(produto.getPrecoCusto());
        novaVenda.setLucro(lucro);
        return novaVenda;
    }
}