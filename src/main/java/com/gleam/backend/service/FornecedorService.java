package com.gleam.backend.service;

import com.gleam.backend.dto.FornecedorDTO;
import com.gleam.backend.model.Fornecedor;
import com.gleam.backend.repository.FornecedorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Serviço que encapsula a lógica de negócio para a entidade Fornecedor.
 * Responsável por todas as operações de CRUD, validações e conversões de dados.
 */
@Service
@RequiredArgsConstructor
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;

    /**
     * Busca uma lista paginada de todos os fornecedores.
     * @param pageable Objeto com informações de paginação.
     * @return Uma página de FornecedorDTOs.
     */
    public Page<FornecedorDTO> findAll(Pageable pageable) {
        return fornecedorRepository.findAll(pageable).map(this::convertToDto);
    }

    /**
     * Busca um único fornecedor pelo seu ID.
     * @param id O ID do fornecedor a ser buscado.
     * @return O FornecedorDTO correspondente.
     * @throws EntityNotFoundException se nenhum fornecedor for encontrado com o ID fornecido.
     */
    public FornecedorDTO findById(Long id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado com o ID: " + id));
        return convertToDto(fornecedor);
    }

    /**
     * Cria e salva um novo fornecedor no banco de dados.
     * @param dto O DTO com os dados do novo fornecedor.
     * @return O FornecedorDTO do fornecedor recém-criado.
     */
    public FornecedorDTO save(FornecedorDTO dto) {
        Fornecedor fornecedor = new Fornecedor();
        mapDtoToEntity(dto, fornecedor); // Reutiliza a lógica de mapeamento
        Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedor);
        return convertToDto(fornecedorSalvo);
    }

    /**
     * Atualiza um fornecedor existente.
     * @param id O ID do fornecedor a ser atualizado.
     * @param dto O DTO com os novos dados para o fornecedor.
     * @return O FornecedorDTO do fornecedor atualizado.
     */
    public FornecedorDTO update(Long id, FornecedorDTO dto) {
        Fornecedor fornecedorExistente = fornecedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado com o ID: " + id));

        mapDtoToEntity(dto, fornecedorExistente); // Reutiliza a lógica de mapeamento
        Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedorExistente);
        return convertToDto(fornecedorSalvo);
    }

    /**
     * Apaga um fornecedor do banco de dados.
     * @param id O ID do fornecedor a ser apagado.
     */
    public void delete(Long id) {
        if (!fornecedorRepository.existsById(id)) {
            throw new EntityNotFoundException("Fornecedor não encontrado com o ID: " + id);
        }
        fornecedorRepository.deleteById(id);
    }

    // --- Métodos Privados de Conversão ---

    /**
     * Mapeia os dados de um FornecedorDTO para uma entidade Fornecedor.
     * @param dto O DTO de origem.
     * @param fornecedor A entidade de destino.
     */
    private void mapDtoToEntity(FornecedorDTO dto, Fornecedor fornecedor) {
        fornecedor.setNome(dto.getNome());
        fornecedor.setCnpj(dto.getCnpj());
        fornecedor.setTelefone(dto.getTelefone());
        fornecedor.setDescricao(dto.getDescricao());
        fornecedor.setCodigoAnel(dto.getCodigoAnel());
        fornecedor.setCodigoBracelete(dto.getCodigoBracelete());
        fornecedor.setCodigoColar(dto.getCodigoColar());
        fornecedor.setCodigoBrinco(dto.getCodigoBrinco());
        fornecedor.setCodigoPulseira(dto.getCodigoPulseira());
        fornecedor.setCodigoPingente(dto.getCodigoPingente());
        fornecedor.setCodigoConjunto(dto.getCodigoConjunto());
        fornecedor.setCodigoBerloque(dto.getCodigoBerloque());
        fornecedor.setCodigoPiercing(dto.getCodigoPiercing());
    }

    /**
     * Converte uma entidade Fornecedor para um FornecedorDTO.
     * @param fornecedor A entidade a ser convertida.
     * @return O DTO correspondente.
     */
    private FornecedorDTO convertToDto(Fornecedor fornecedor) {
        FornecedorDTO dto = new FornecedorDTO();
        dto.setId(fornecedor.getId());
        dto.setNome(fornecedor.getNome());
        dto.setCnpj(fornecedor.getCnpj());
        dto.setTelefone(fornecedor.getTelefone());
        dto.setDescricao(fornecedor.getDescricao());
        dto.setDataCriacao(fornecedor.getDataCriacao());
        dto.setDataAtualizacao(fornecedor.getDataAtualizacao());
        dto.setCodigoAnel(fornecedor.getCodigoAnel());
        dto.setCodigoBracelete(fornecedor.getCodigoBracelete());
        dto.setCodigoColar(fornecedor.getCodigoColar());
        dto.setCodigoBrinco(fornecedor.getCodigoBrinco());
        dto.setCodigoPulseira(fornecedor.getCodigoPulseira());
        dto.setCodigoPingente(fornecedor.getCodigoPingente());
        dto.setCodigoConjunto(fornecedor.getCodigoConjunto());
        dto.setCodigoBerloque(fornecedor.getCodigoBerloque());
        dto.setCodigoPiercing(fornecedor.getCodigoPiercing());
        return dto;
    }
}