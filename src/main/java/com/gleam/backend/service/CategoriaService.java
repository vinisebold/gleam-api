package com.gleam.backend.service;

import com.gleam.backend.dto.CategoriaDTO;
import com.gleam.backend.model.Categoria;
import com.gleam.backend.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria save(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setNome(categoriaDTO.getNome());
        categoria.setDescricao(categoriaDTO.getDescricao());
        return categoriaRepository.save(categoria);
    }
}