package com.gleam.backend.controller;

import com.gleam.backend.dto.ItemVendaDTO;
import com.gleam.backend.service.ItemVendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/item-vendas")
public class ItemVendaController {
    @Autowired
    private ItemVendaService itemVendaService;

    @PostMapping
    public ItemVendaDTO save(@RequestBody ItemVendaDTO itemVendaDTO) {
        itemVendaService.save(itemVendaDTO);
        return itemVendaDTO;
    }
}