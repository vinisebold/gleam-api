package com.gleam.backend.service;

import com.gleam.backend.dto.ClienteDTO;
import com.gleam.backend.model.Cliente;
import com.gleam.backend.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente save(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.getNome());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefone(clienteDTO.getTelefone());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setDescricao(clienteDTO.getDescricao());
        return clienteRepository.save(cliente);
    }
}