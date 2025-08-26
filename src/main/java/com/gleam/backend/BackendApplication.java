package com.gleam.backend;

import jakarta.annotation.PostConstruct; // Importe esta anotação
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.TimeZone; // Importe esta classe

@EnableCaching
@SpringBootApplication
public class BackendApplication {

    /**
     * Este método é executado uma vez após a inicialização da aplicação.
     * Ele define o fuso horário padrão para toda a JVM.
     */
    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}