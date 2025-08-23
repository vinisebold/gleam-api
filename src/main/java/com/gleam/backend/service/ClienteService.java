package com.gleam.backend.service;

import com.gleam.backend.dto.ClienteDTO;
import com.gleam.backend.model.Cliente;
import com.gleam.backend.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     *
     * @param pageable Objeto com informações de paginação.
     * @return Uma página de ClienteDTOs.
     */
    public Page<ClienteDTO> findAll(Pageable pageable) {
        return clienteRepository.findAll(pageable).map(ClienteDTO::new);
    }

    /**
     * Busca um único cliente pelo seu ID.
     *
     * @param id O ID do cliente a ser buscado.
     * @return O ClienteDTO correspondente.
     * @throws EntityNotFoundException se nenhum cliente for encontrado com o ID fornecido.
     */
    public ClienteDTO findById(Long id) {
        return clienteRepository.findById(id)
                .map(ClienteDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + id));
    }

    /**
     * Cria e salva um novo cliente no banco de dados.
     *
     * @param dto O DTO com os dados do novo cliente.
     * @return O ClienteDTO do cliente recém-criado.
     */
    @Transactional
    public ClienteDTO save(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        mapDtoToEntity(dto, cliente); // Reutiliza a lógica de mapeamento
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return new ClienteDTO(clienteSalvo);
    }

    /**
     * Atualiza um cliente existente.
     *
     * @param id  O ID do cliente a ser atualizado.
     * @param dto O DTO com os novos dados para o cliente.
     * @return O ClienteDTO do cliente atualizado.
     */
    @Transactional
    public ClienteDTO update(Long id, ClienteDTO dto) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + id));

        mapDtoToEntity(dto, clienteExistente); // Reutiliza a lógica de mapeamento
        Cliente clienteSalvo = clienteRepository.save(clienteExistente);
        return new ClienteDTO(clienteSalvo);
    }

    /**
     * Apaga um cliente do banco de dados.
     *
     * @param id O ID do cliente a ser apagado.
     */
    @Transactional
    public void delete(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new EntityNotFoundException("Cliente não encontrado com o ID: " + id);
        }
        clienteRepository.deleteById(id);
    }

    /**
     * Realiza o mapeamento dos dados do DTO para a entidade Cliente.
     *
     * @param dto     O DTO com os dados de entrada.
     * @param cliente A entidade Cliente a ser atualizada.
     */
    private void mapDtoToEntity(ClienteDTO dto, Cliente cliente) {
        cliente.setNome(dto.nome());
        cliente.setTelefone(dto.telefone());
        cliente.setCpf(dto.cpf());
    }
}