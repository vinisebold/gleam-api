package com.gleam.backend.controller;

import com.gleam.backend.dto.VendaDTO;
import com.gleam.backend.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {
    @Autowired
    private VendaService vendaService;

    @PostMapping
    public VendaDTO save(@RequestBody VendaDTO vendaDTO) {
        vendaService.save(vendaDTO);
        return vendaDTO;
    }
}