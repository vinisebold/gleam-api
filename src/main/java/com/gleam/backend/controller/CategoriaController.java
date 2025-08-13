package com.gleam.backend.controller;

import com.gleam.backend.dto.CategoriaDTO;
import com.gleam.backend.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    public CategoriaDTO save(@RequestBody CategoriaDTO categoriaDTO) {
        categoriaService.save(categoriaDTO);
        return categoriaDTO;
    }
}