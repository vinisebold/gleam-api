package com.gleam.backend;

import com.gleam.backend.enums.Acabamento;

import com.gleam.backend.model.Cliente;
import com.gleam.backend.model.Fornecedor;
import com.gleam.backend.model.Produto;

import com.gleam.backend.repository.ClienteRepository;
import com.gleam.backend.repository.FornecedorRepository;
import com.gleam.backend.repository.ProdutoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
@EnableCaching
@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }


}