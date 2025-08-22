package com.gleam.backend.service;

import com.gleam.backend.dto.ClienteDTO;
import com.gleam.backend.model.Cliente;
import com.gleam.backend.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Serviço que encapsula a lógica de negócio para a entidade Cliente.
 * Responsável por todas as operações de CRUD, validações e conversões de dados.
 */
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    /**
     * Busca uma lista paginada de todos os clientes.
     * @param pageable Objeto com informações de paginação.
     * @return Uma página de ClienteDTOs.
     */
    public Page<ClienteDTO> findAll(Pageable pageable) {
        return clienteRepository.findAll(pageable).map(this::convertToDto);
    }

    /**
     * Busca um único cliente pelo seu ID.
     * @param id O ID do cliente a ser buscado.
     * @return O ClienteDTO correspondente.
     * @throws EntityNotFoundException se nenhum cliente for encontrado com o ID fornecido.
     */
    public ClienteDTO findById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + id));
        return convertToDto(cliente);
    }

    /**
     * Cria e salva um novo cliente no banco de dados.
     * @param dto O DTO com os dados do novo cliente.
     * @return O ClienteDTO do cliente recém-criado.
     */
    public ClienteDTO save(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        mapDtoToEntity(dto, cliente); // Reutiliza a lógica de mapeamento
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return convertToDto(clienteSalvo);
    }

    /**
     * Atualiza um cliente existente.
     * @param id O ID do cliente a ser atualizado.
     * @param dto O DTO com os novos dados para o cliente.
     * @return O ClienteDTO do cliente atualizado.
     */
    public ClienteDTO update(Long id, ClienteDTO dto) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + id));

        mapDtoToEntity(dto, clienteExistente); // Reutiliza a lógica de mapeamento
        Cliente clienteSalvo = clienteRepository.save(clienteExistente);
        return convertToDto(clienteSalvo);
    }

    /**
     * Apaga um cliente do banco de dados.
     * @param id O ID do cliente a ser apagado.
     */
    public void delete(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new EntityNotFoundException("Cliente não encontrado com o ID: " + id);
        }
        clienteRepository.deleteById(id);
    }

    // --- Métodos Privados de Conversão ---

    /**
     * Mapeia os dados de um ClienteDTO para uma entidade Cliente.
     * @param dto O DTO de origem.
     * @param cliente A entidade de destino.
     */
    private void mapDtoToEntity(ClienteDTO dto, Cliente cliente) {
        cliente.setNome(dto.getNome());
        cliente.setTelefone(dto.getTelefone());
        cliente.setEmail(dto.getEmail()); // Campo que faltava ser mapeado
    }

    /**
     * Converte uma entidade Cliente para um ClienteDTO.
     * @param cliente A entidade a ser convertida.
     * @return O DTO correspondente.
     */
    private ClienteDTO convertToDto(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setTelefone(cliente.getTelefone());
        dto.setEmail(cliente.getEmail());
        dto.setDataCriacao(cliente.getDataCriacao());
        dto.setDataAtualizacao(cliente.getDataAtualizacao());
        return dto;
    }
}