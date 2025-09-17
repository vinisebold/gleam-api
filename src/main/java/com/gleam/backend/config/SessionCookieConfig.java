package com.gleam.backend.config;

import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Isso permite que o cookie JSESSIONID seja enviado em requisições
// entre sites diferentes (cross-site), como do Vercel (frontend)
// para o Railway (backend). O 'None' é o valor necessário.
@Configuration
public class SessionCookieConfig {

    @Bean
    public CookieSameSiteSupplier applicationCookieSameSiteSupplier() {
        return CookieSameSiteSupplier.ofNone();
    }
}