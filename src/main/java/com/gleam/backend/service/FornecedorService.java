package com.gleam.backend.service;

import com.gleam.backend.dto.FornecedorDTO;
import com.gleam.backend.model.Fornecedor;
import com.gleam.backend.repository.FornecedorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    /**
     * Busca todos os fornecedores.
     * Na primeira chamada, busca no banco e guarda o resultado no cache "fornecedores".
     * Nas chamadas seguintes, retorna o resultado diretamente do cache.
     */
    @Cacheable("fornecedores")
    public List<Fornecedor> findAll() {
        System.out.println("A BUSCAR FORNECEDORES NO BANCO DE DADOS...");
        return fornecedorRepository.findAll();
    }

    /**
     * Cria e salva um novo fornecedor, limpando o cache de fornecedores.
     * @param dto Os dados do novo fornecedor.
     * @return A entidade Fornecedor salva.
     */
    @CacheEvict(value = "fornecedores", allEntries = true)
    public Fornecedor save(FornecedorDTO dto) {
        System.out.println("A SALVAR NOVO FORNECEDOR E A LIMPAR O CACHE...");
        Fornecedor fornecedor = new Fornecedor();
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

        return fornecedorRepository.save(fornecedor);
    }

    /**
     * Atualiza um fornecedor existente e limpa o cache de fornecedores.
     * @param id O ID do fornecedor a ser atualizado.
     * @param dto Os novos dados para o fornecedor.
     * @return A entidade Fornecedor atualizada.
     */
    @CacheEvict(value = "fornecedores", allEntries = true)
    public Fornecedor update(Long id, FornecedorDTO dto) {
        System.out.println("A ATUALIZAR FORNECEDOR E A LIMPAR O CACHE...");
        Fornecedor fornecedorExistente = fornecedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado com o ID: " + id));

        // Atualiza todos os campos
        fornecedorExistente.setNome(dto.getNome());
        fornecedorExistente.setCnpj(dto.getCnpj());
        fornecedorExistente.setTelefone(dto.getTelefone());
        fornecedorExistente.setDescricao(dto.getDescricao());
        fornecedorExistente.setCodigoAnel(dto.getCodigoAnel());
        fornecedorExistente.setCodigoBracelete(dto.getCodigoBracelete());
        fornecedorExistente.setCodigoColar(dto.getCodigoColar());
        fornecedorExistente.setCodigoBrinco(dto.getCodigoBrinco());
        fornecedorExistente.setCodigoPulseira(dto.getCodigoPulseira());
        fornecedorExistente.setCodigoPingente(dto.getCodigoPingente());
        fornecedorExistente.setCodigoConjunto(dto.getCodigoConjunto());
        fornecedorExistente.setCodigoBerloque(dto.getCodigoBerloque());
        fornecedorExistente.setCodigoPiercing(dto.getCodigoPiercing());

        return fornecedorRepository.save(fornecedorExistente);
    }

    /**
     * Apaga um fornecedor e limpa o cache de fornecedores.
     * @param id O ID do fornecedor a ser apagado.
     */
    @CacheEvict(value = "fornecedores", allEntries = true)
    public void delete(Long id) {
        System.out.println("A APAGAR FORNECEDOR E A LIMPAR O CACHE...");
        if (!fornecedorRepository.existsById(id)) {
            throw new EntityNotFoundException("Fornecedor não encontrado com o ID: " + id);
        }
        fornecedorRepository.deleteById(id);
    }
}
