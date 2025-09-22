package com.gleam.backend;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.TimeZone;

/**
 * <p>Classe principal que serve como ponto de entrada para a aplicação Spring Boot.</p>
 *
 * <p>A anotação {@code @SpringBootApplication} engloba as seguintes configurações:</p>
 * <ul>
 * <li>{@code @Configuration}: Marca a classe como uma fonte de beans de configuração.</li>
 * <li>{@code @EnableAutoConfiguration}: Tenta configurar automaticamente o Spring com base nas dependências do classpath.</li>
 * <li>{@code @ComponentScan}: Procura por outros componentes, configurações e serviços no pacote atual e subpacotes.</li>
 * </ul>
 */
@EnableCaching
@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }
}