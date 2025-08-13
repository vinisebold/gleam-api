package com.gleam.backend.service;

import com.gleam.backend.dto.FornecedorDTO;
import com.gleam.backend.model.Fornecedor;
import com.gleam.backend.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public Fornecedor save(FornecedorDTO dto) {
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
}
