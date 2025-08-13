package com.gleam.backend.service;

import com.gleam.backend.dto.VendaDTO;
import com.gleam.backend.enums.StatusVenda; // ALTERAÇÃO: Import do enum
import com.gleam.backend.model.Cliente;
import com.gleam.backend.model.Venda;
import com.gleam.backend.repository.ClienteRepository; // ALTERAÇÃO: Import do repositório de cliente
import com.gleam.backend.repository.VendaRepository;
import jakarta.persistence.EntityNotFoundException; // ALTERAÇÃO: Import para exceção
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    // ALTERAÇÃO: Injetar o repositório de Cliente para buscar o cliente
    @Autowired
    private ClienteRepository clienteRepository;

    public Venda save(VendaDTO vendaDTO) {
        // ALTERAÇÃO: Buscar o cliente no banco de dados para garantir que ele existe
        Cliente cliente = clienteRepository.findById(vendaDTO.getIdCliente())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + vendaDTO.getIdCliente()));

        Venda venda = new Venda();
        venda.setDataVenda(vendaDTO.getDataVenda() != null ? vendaDTO.getDataVenda() : LocalDateTime.now());
        venda.setCliente(cliente); // Associa a entidade completa que foi buscada
        venda.setValorTotal(vendaDTO.getValorTotal());

        // ALTERAÇÃO: Converte a String do DTO para o Enum correspondente
        try {
            StatusVenda status = StatusVenda.valueOf(vendaDTO.getStatus().toUpperCase());
            venda.setStatus(status);
        } catch (IllegalArgumentException e) {
            // Lança um erro se o status enviado no DTO for inválido (ex: "QUALQUER_COISA")
            throw new IllegalArgumentException("Status de venda inválido: " + vendaDTO.getStatus());
        }

        return vendaRepository.save(venda);
    }
}