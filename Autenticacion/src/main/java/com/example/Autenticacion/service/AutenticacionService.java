package com.example.Autenticacion.service;

import com.example.Autenticacion.model.CredencialesUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AutenticacionService {

    @Autowired
    private WebClient webClient;

    public Mono<Boolean> autenticar(String username, String password) {
        return webClient.get()
                .uri("/api/usuarios/{username}", username)
                .retrieve()
                .bodyToMono(CredencialesUsuario.class)
                .map(usuario -> usuario.getPassword().equals(password))
                .onErrorReturn(false);
    }
}