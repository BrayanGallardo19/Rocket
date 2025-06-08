package com.example.GestorPedidos.webclient;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class UsuarioClient {
    private final WebClient webClientUsuarios;

    public UsuarioClient(@Value("${usuario-service.url}") String usuarioServiceUrl) {
        this.webClientUsuarios = WebClient.builder()
                .baseUrl(usuarioServiceUrl)
                .build();
    }

    public Map<String, Object> obtenerUsuarioPorUsername(String username) {
        return webClientUsuarios.get()
                .uri("/username/{username}", username)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new RuntimeException("Usuario no encontrado: " + body))))
                .bodyToMono(Map.class)
                .doOnNext(body -> System.out.println("Usuario obtenido por username: " + body))
                .block();
    }

    public List<Map<String, Object>> obtenerTodosLosUsuarios() {
        return webClientUsuarios.get()
                .uri("/usuarios")
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(
                                        body -> Mono.error(new RuntimeException("Error al obtener usuarios: " + body))))
                .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() { // para que WebClient sepa que esperamos una lista de mapas
                })
                .doOnNext(body -> System.out.println("Usuarios obtenidos: " + body))
                .block();
    }

    public Map<String, Object> obtenerUsuarioPorId(Integer id) {
        return webClientUsuarios.get()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new RuntimeException("Usuario no encontrado: " + body))))
                .bodyToMono(Map.class)
                .doOnNext(body -> System.out.println("Usuario obtenido por ID: " + body))
                .block();
    }
}
