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
 * Esta classe é responsável por todas as operações de CRUD, validações
 * e transformações de dados (DTO) relacionadas a clientes.
 */
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    /**
     * Busca uma lista paginada de todos os clientes.
     *
     * @param pageable Objeto que contém as informações de paginação (página, tamanho, ordenação).
     * @return Uma {@link Page} de {@link ClienteDTO} contendo os clientes encontrados.
     */
    @Transactional(readOnly = true)
    public Page<ClienteDTO> findAll(Pageable pageable) {
        return clienteRepository.findAll(pageable).map(ClienteDTO::new);
    }

    /**
     * Busca um único cliente pelo seu ‘ID’.
     *
     * @param id O ID do cliente a ser procurado.
     * @return O {@link ClienteDTO} correspondente ao cliente encontrado.
     * @throws EntityNotFoundException se nenhum cliente for encontrado com o ‘ID’ fornecido.
     */
    @Transactional(readOnly = true)
    public ClienteDTO findById(Long id) {
        return clienteRepository.findById(id)
                .map(ClienteDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + id));
    }

    /**
     * Cria e persiste um novo cliente no banco de dados.
     *
     * @param dto O {@link ClienteDTO} com os dados do novo cliente.
     * @return O {@link ClienteDTO} do cliente recém-criado, incluindo o ‘ID’ gerado.
     */
    @Transactional
    public ClienteDTO save(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        mapDtoToEntity(dto, cliente);
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return new ClienteDTO(clienteSalvo);
    }

    /**
     * Atualiza os dados de um cliente existente.
     *
     * @param id  O ‘ID’ do cliente a ser atualizado.
     * @param dto O {@link ClienteDTO} com os novos dados para o cliente.
     * @return O {@link ClienteDTO} do cliente atualizado.
     * @throws EntityNotFoundException se nenhum cliente for encontrado com o ‘ID’ fornecido.
     */
    @Transactional
    public ClienteDTO update(Long id, ClienteDTO dto) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + id));

        mapDtoToEntity(dto, clienteExistente);
        Cliente clienteSalvo = clienteRepository.save(clienteExistente);
        return new ClienteDTO(clienteSalvo);
    }

    /**
     * Remove um cliente do banco de dados.
     *
     * @param id O ID do cliente a ser removido.
     * @throws EntityNotFoundException se nenhum cliente for encontrado com o ‘ID’ fornecido.
     */
    @Transactional
    public void delete(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new EntityNotFoundException("Cliente não encontrado com o ID: " + id);
        }
        clienteRepository.deleteById(id);
    }

    /**
     * Mapeia os dados de um {@link ClienteDTO} para uma entidade {@link Cliente}.
     * Esta funcao auxiliar é utilizado tanto na criação quanto na atualização de clientes
     * para evitar a duplicação de código.
     *
     * @param dto     O DTO de origem com os dados.
     * @param cliente A entidade de destino a ser preenchida.
     */
    private void mapDtoToEntity(ClienteDTO dto, Cliente cliente) {
        cliente.setNome(dto.nome());
        cliente.setTelefone(dto.telefone());
        cliente.setCpf(dto.cpf());
        cliente.setDescricao(dto.descricao());
        cliente.setEmail(dto.email());
    }
}