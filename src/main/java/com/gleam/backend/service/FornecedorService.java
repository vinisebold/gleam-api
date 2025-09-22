package com.gleam.backend.service;

import com.gleam.backend.dto.FornecedorDTO;
import com.gleam.backend.model.Fornecedor;
import com.gleam.backend.repository.FornecedorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Esta classe é responsável por todas as operações de CRUD, validações
 * e transformações de dados (DTO) relacionadas a fornecedores.
 */
@Service
@RequiredArgsConstructor
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;

    /**
     * Busca uma lista paginada de todos os fornecedores.
     *
     * @param pageable Objeto que contém as informações de paginação.
     * @return Uma {@link Page} de {@link FornecedorDTO} contendo os fornecedores encontrados.
     */
    @Transactional(readOnly = true)
    public Page<FornecedorDTO> findAll(Pageable pageable) {
        return fornecedorRepository.findAll(pageable).map(FornecedorDTO::new);
    }

    /**
     * Busca um único fornecedor pelo seu ‘ID’.
     *
     * @param id O ID do fornecedor a ser procurado.
     * @return O {@link FornecedorDTO} correspondente ao fornecedor encontrado.
     * @throws EntityNotFoundException se nenhum fornecedor for encontrado com o ‘ID’ fornecido.
     */
    @Transactional(readOnly = true)
    public FornecedorDTO findById(Long id) {
        return fornecedorRepository.findById(id)
                .map(FornecedorDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado com o ID: " + id));
    }

    /**
     * Cria e persiste um novo fornecedor no banco de dados.
     *
     * @param dto O {@link FornecedorDTO} com os dados do novo fornecedor.
     * @return O {@link FornecedorDTO} do fornecedor recém-criado, incluindo o ‘ID’ gerado.
     */
    @Transactional
    public FornecedorDTO save(FornecedorDTO dto) {
        Fornecedor fornecedor = new Fornecedor();
        mapDtoToEntity(dto, fornecedor);
        Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedor);
        return new FornecedorDTO(fornecedorSalvo);
    }

    /**
     * Atualiza os dados de um fornecedor existente.
     *
     * @param id  O ‘ID’ do fornecedor a ser atualizado.
     * @param dto O {@link FornecedorDTO} com os novos dados.
     * @return O {@link FornecedorDTO} do fornecedor atualizado.
     * @throws EntityNotFoundException se nenhum fornecedor for encontrado com o ‘ID’ fornecido.
     */
    @Transactional
    public FornecedorDTO update(Long id, FornecedorDTO dto) {
        Fornecedor fornecedorExistente = fornecedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado com o ID: " + id));

        mapDtoToEntity(dto, fornecedorExistente);
        Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedorExistente);
        return new FornecedorDTO(fornecedorSalvo);
    }

    /**
     * Remove um fornecedor do banco de dados.
     *
     * @param id O ID do fornecedor a ser removido.
     * @throws EntityNotFoundException se nenhum fornecedor for encontrado com o ‘ID’ fornecido.
     */
    @Transactional
    public void delete(Long id) {
        if (!fornecedorRepository.existsById(id)) {
            throw new EntityNotFoundException("Fornecedor não encontrado com o ID: " + id);
        }
        fornecedorRepository.deleteById(id);
    }

    /**
     * Mapeia os dados de um {@link FornecedorDTO} para uma entidade {@link Fornecedor}.
     *
     * @param dto        O DTO de origem com os dados.
     * @param fornecedor A entidade de destino a ser preenchida.
     */
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