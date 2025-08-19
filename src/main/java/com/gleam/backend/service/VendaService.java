package com.gleam.backend.service;

import com.gleam.backend.dto.VendaDTO;
import com.gleam.backend.model.Cliente;
import com.gleam.backend.model.Venda;
import com.gleam.backend.repository.ClienteRepository;
import com.gleam.backend.repository.VendaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public Venda save(VendaDTO dto) {
        Cliente cliente = null;
        if (dto.getIdCliente() != null) {
            cliente = clienteRepository.findById(dto.getIdCliente())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + dto.getIdCliente()));
        }

        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setDataVenda(dto.getDataVenda() != null ? dto.getDataVenda() : LocalDateTime.now());

        // << LÓGICA SIMPLIFICADA >>
        // Atribui a String do status diretamente. Se for nula, define um padrão.
        if (dto.getStatus() != null && !dto.getStatus().trim().isEmpty()) {
            venda.setStatus(dto.getStatus());
        } else {
            venda.setStatus("PENDENTE"); // Define um valor padrão
        }

        venda.setValorTotal(dto.getValorTotal() != null ? dto.getValorTotal() : BigDecimal.ZERO);

        return vendaRepository.save(venda);
    }

    @Transactional
    public Venda update(Long id, VendaDTO dto) {
        Venda vendaExistente = vendaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada com o ID: " + id));

        Cliente cliente = null;
        if (dto.getIdCliente() != null) {
            cliente = clienteRepository.findById(dto.getIdCliente())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + dto.getIdCliente()));
        }
        vendaExistente.setCliente(cliente);

        if (dto.getDataVenda() != null) vendaExistente.setDataVenda(dto.getDataVenda());
        if (dto.getStatus() != null) vendaExistente.setStatus(dto.getStatus()); // Atribuição direta
        if (dto.getValorTotal() != null) vendaExistente.setValorTotal(dto.getValorTotal());

        return vendaRepository.save(vendaExistente);
    }

    // Os métodos delete, findAll e findById não precisam de alterações.
    public void delete(Long id) {
        if (!vendaRepository.existsById(id)) {
            throw new EntityNotFoundException("Venda não encontrada com o ID: " + id);
        }
        vendaRepository.deleteById(id);
    }

    public List<Venda> findAll() {
        return vendaRepository.findAll();
    }

    public Venda findById(Long id) {
        return vendaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada com o ID: " + id));
    }
}
