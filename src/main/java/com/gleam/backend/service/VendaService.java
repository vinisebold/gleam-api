package com.gleam.backend.service;

import com.gleam.backend.dto.RegistrarVendaRequestDto;
import com.gleam.backend.dto.VendaResponseDto;
import com.gleam.backend.model.*;
import com.gleam.backend.repository.ClienteRepository;
import com.gleam.backend.repository.ProdutoRepository;
import com.gleam.backend.repository.VendaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;

    @Transactional
    public VendaResponseDto registrarVenda(RegistrarVendaRequestDto dto) {
        // 1. Buscar as entidades principais
        Produto produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new EntityNotFoundException("Produto com ID " + dto.produtoId() + " não encontrado."));

        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + dto.clienteId() + " não encontrado."));

        // 2. Validar a regra de negócio principal
        if (produto.getStatus() != StatusProduto.EM_ESTOQUE) {
            throw new IllegalStateException("O produto '" + produto.getNome() + "' não está disponível para venda.");
        }

        // 3. Atualizar o Produto
        produto.setStatus(StatusProduto.VENDIDO);
        produto.setDataVenda(LocalDateTime.now());
        // Não precisamos chamar save() do produto ainda, o @Transactional cuidará disso.

        // 4. Criar e preencher a nova Venda
        Venda novaVenda = getVenda(dto, produto, cliente);

        // 5. Salvar a nova Venda (o @Transactional garante que o produto também será salvo)
        Venda vendaSalva = vendaRepository.save(novaVenda);

        // 6. Retornar o DTO de resposta
        return new VendaResponseDto(vendaSalva);
    }

    private static Venda getVenda(RegistrarVendaRequestDto dto, Produto produto, Cliente cliente) {
        Venda novaVenda = new Venda();
        novaVenda.setProduto(produto);
        novaVenda.setCliente(cliente);
        novaVenda.setPrecoVenda(dto.precoVenda());
        novaVenda.setFormaPagamento(dto.formaPagamento());
        novaVenda.setTotalParcelas(dto.totalParcelas());
        novaVenda.setStatus(dto.status());
        novaVenda.setDataVencimento(dto.dataVencimento());

        // Define as parcelas pagas iniciais
        if (dto.status() == StatusVenda.PAGO) {
            novaVenda.setParcelasPagas(dto.totalParcelas());
        } else {
            novaVenda.setParcelasPagas(0);
        }

        // Calcula o lucro
        BigDecimal lucro = dto.precoVenda().subtract(produto.getPrecoCusto());
        novaVenda.setLucro(lucro);
        return novaVenda;
    }
}
