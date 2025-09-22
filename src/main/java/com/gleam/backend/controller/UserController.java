package com.gleam.backend.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

/**
 * Controller REST para obter informações sobre o utilizador autenticado.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    /**
     * GET /api/‘user’/me: retorna os dados do utilizador atualmente autenticado.
     *
     * @param oauth2User Objeto injetado pelo Spring Security que contém os dados
     *                   do utilizador vindos do provedor OAuth2 (ex: Google).
     * @return Um {@link Map} contendo os atributos do utilizador (ex: nome, endereço eletrónico, foto).
     * Retorna uma mensagem de erro se não houver utilizador autenticado.
     */
    @GetMapping("/me")
    public Map<String, Object> getCurrentUser(@AuthenticationPrincipal OAuth2User oauth2User) {
        if (oauth2User == null) {
            return Collections.singletonMap("error", "User not authenticated");
        }
        return oauth2User.getAttributes();
    }
}