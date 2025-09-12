package com.gleam.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Classe de configuração global para o Cross-Origin Resource Sharing (CORS).
 * Permite que o frontend acesse os recursos do backend de forma segura.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Aplica a configuração de CORS a todos os endpoints da aplicação ("/**")
        registry.addMapping("/**")
                // Define as origens permitidas para fazer requisições
                .allowedOrigins(
                        "http://localhost:4200",
                        "https://xjzkzx-4200.csb.app",
                        "https://gleam.software",
                        "https://gleam-tcc.vercel.app"
                )
                // Define os métodos HTTP permitidos (GET, POST, PUT, DELETE, etc.)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // Permite todos os cabeçalhos na requisição
                .allowedHeaders("*")
                // Permite o envio de credenciais (como cookies e tokens de autenticação)
                .allowCredentials(true);
    }
}
