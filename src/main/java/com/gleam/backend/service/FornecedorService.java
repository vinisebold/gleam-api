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
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço que encapsula a lógica de negócio para a entidade Fornecedor.
 * Responsável por todas as operações de CRUD, validações e conversões de dados.
 */
@Service
@RequiredArgsConstructor
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;

    public Page<FornecedorDTO> findAll(Pageable pageable) {
        return fornecedorRepository.findAll(pageable).map(FornecedorDTO::new);
    }

    public FornecedorDTO findById(Long id) {
        return fornecedorRepository.findById(id)
                .map(FornecedorDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado com o ID: " + id));
    }

    @Transactional
    public FornecedorDTO save(FornecedorDTO dto) {
        Fornecedor fornecedor = new Fornecedor();
        mapDtoToEntity(dto, fornecedor); // Reutiliza a lógica de mapeamento
        Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedor);
        return new FornecedorDTO(fornecedorSalvo);
    }

    @Transactional
    public FornecedorDTO update(Long id, FornecedorDTO dto) {
        Fornecedor fornecedorExistente = fornecedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado com o ID: " + id));

        mapDtoToEntity(dto, fornecedorExistente); // Reutiliza a lógica de mapeamento
        Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedorExistente);
        return new FornecedorDTO(fornecedorSalvo);
    }

    @Transactional
    public void delete(Long id) {
        if (!fornecedorRepository.existsById(id)) {
            throw new EntityNotFoundException("Fornecedor não encontrado com o ID: " + id);
        }
        fornecedorRepository.deleteById(id);
    }

    private void mapDtoToEntity(FornecedorDTO dto, Fornecedor fornecedor) {
        fornecedor.setNome(dto.nome());
        fornecedor.setCnpj(dto.cnpj());
        fornecedor.setTelefone(dto.telefone());
        fornecedor.setDescricao(dto.descricao());
        fornecedor.setCodigoAnel(dto.codigoAnel());
        fornecedor.setCodigoBracelete(dto.codigoBracelete());
        fornecedor.setCodigoColar(dto.codigoColar());
        fornecedor.setCodigoBrinco(dto.codigoBrinco());
        fornecedor.setCodigoPulseira(dto.codigoPulseira());
        fornecedor.setCodigoPingente(dto.codigoPingente());
        fornecedor.setCodigoConjunto(dto.codigoConjunto());
        fornecedor.setCodigoBerloque(dto.codigoBerloque());
        fornecedor.setCodigoPiercing(dto.codigoPiercing());
    }
}