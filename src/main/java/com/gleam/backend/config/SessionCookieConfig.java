package com.gleam.backend.config;

import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Configura a política de SameSite para o ‘cookie’ de sessão (JSESSIONID).</p>
 *
 * <p>Esta configuração é crucial para permitir que a autenticação baseada em sessão
 * funcione num ambiente com domínios diferentes para o frontend e o backend
 * (ex: frontend no Vercel e backend no Railway).</p>
 */
@Configuration
public class SessionCookieConfig {

    /**
     * <p>
     * Ao configurar SameSite como 'None', o navegador é instruído a enviar o ‘cookie’
     * de sessão em todas as requisições, incluindo as de origem cruzada (cross-site).
     * Isso é um requisito para que o backend consiga identificar a sessão do utilizador
     * quando a requisição vem de um domínio diferente.
     * <p>
     */
    @Bean
    public CookieSameSiteSupplier applicationCookieSameSiteSupplier() {
        return CookieSameSiteSupplier.ofNone();
    }
}