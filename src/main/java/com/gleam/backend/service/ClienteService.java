package com.gleam.backend.service;

import com.gleam.backend.dto.ClienteDTO;
import com.gleam.backend.model.Cliente;
import com.gleam.backend.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Cria e salva um novo cliente, limpando o cache de clientes.
     * @param dto Os dados do novo cliente.
     * @return A entidade Cliente salva.
     */
    @CacheEvict(value = "clientes", allEntries = true)
    public Cliente save(ClienteDTO dto) {
        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório.");
        }
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());

        cliente.setTelefone(dto.getTelefone());

        return clienteRepository.save(cliente);
    }

    /**
     * Atualiza um cliente existente e limpa o cache de clientes.
     * @param id  O ID do cliente a ser atualizado.
     * @param dto Os novos dados para o cliente.
     * @return A entidade Cliente atualizada.
     */
    @CacheEvict(value = "clientes", allEntries = true)
    public Cliente update(Long id, ClienteDTO dto) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + id));

        clienteExistente.setNome(dto.getNome());
       
        clienteExistente.setTelefone(dto.getTelefone());


        return clienteRepository.save(clienteExistente);
    }

    /**
     * Apaga um cliente e limpa o cache de clientes.
     * @param id O ID do cliente a ser apagado.
     */
    @CacheEvict(value = "clientes", allEntries = true)
    public void delete(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new EntityNotFoundException("Cliente não encontrado com o ID: " + id);
        }
        clienteRepository.deleteById(id);
    }

    /**
     * Retorna uma lista de todos os clientes, usando o cache "clientes".
     * @return Uma lista de entidades Cliente.
     */
    @Cacheable("clientes")
    public List<Cliente> findAll() {
        System.out.println("A BUSCAR CLIENTES NO BANCO DE DADOS...");
        return clienteRepository.findAll();
    }

    /**
     * Busca um único cliente pelo seu ID, usando o cache "clientes".
     * A chave do cache será o ID do cliente.
     * @param id O ID do cliente a ser encontrado.
     * @return A entidade Cliente encontrada.
     */
    @Cacheable(value = "clientes", key = "#id")
    public Cliente findById(Long id) {
        System.out.println("A BUSCAR CLIENTE COM ID " + id + " NO BANCO DE DADOS...");
        return clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + id));
    }
}
