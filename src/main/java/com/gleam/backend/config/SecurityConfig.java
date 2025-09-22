package com.gleam.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * <p>Classe central de configuração de segurança da aplicação.</p>
 *
 * <p>Utiliza o Spring Security para configurar:</p>
 * <ul>
 * <li>Regras de CORS (Cross-Origin Resource Sharing).</li>
 * <li>Proteção contra CSRF (Cross-Site Request Forgery).</li>
 * <li>Autorização de rotas (endpoints públicos e protegidos).</li>
 * <li>O fluxo de autenticação via OAuth2 com o Google.</li>
 * <li>A lógica de logout.</li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${app.oauth2.redirect-uri}")
    private String redirectUri;

    /**
     * Configura a cadeia de filtros de segurança do Spring Security.
     *
     * @param http O objeto {@link HttpSecurity} para construir a configuração.
     * @return O {@link SecurityFilterChain} configurado.
     * @throws Exception se ocorrer um erro durante a configuração.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/login", "/oauth2/**", "/error", "/logout").permitAll()
                        // Exige autenticação para qualquer outra requisição.
                        .anyRequest().authenticated()
                )
                // Configura o fluxo de login com OAuth2.
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl(redirectUri, true)
                        .failureUrl("/")
                )
                // Configura o processo de logout.
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    /**
     * Define as regras de CORS para a aplicação.
     * <p>
     * CORS é um mecanismo de segurança que permite que uma aplicação ‘web’
     * num domínio aceda a recursos de outro domínio. Esta configuração
     * especifica quais origens (frontends), métodos e cabeçalhos são permitidos.
     *
     * @return A fonte de configuração CORS.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:4200",
                "https://gleam.software",
                "https://gleam-tcc.vercel.app"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}