package com.example.GestorInventario.webclient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class UsuarioConectadoClient {
    private final WebClient webClient;

    public UsuarioConectadoClient(@Value("${usuario-conectado-service.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Map<String, Object> obtenerUsuarioConectadoPorToken(String token) {
    return webClient.get()
            .uri("/token")
            .header("Authorization", "Bearer " + token) 
            .retrieve()
            .onStatus(
                    status -> status.is4xxClientError(),
                    response -> response.bodyToMono(String.class)
                            .flatMap(body -> Mono.error(new RuntimeException("Token no válido: " + body)))
            )
            .bodyToMono(Map.class)
            .doOnNext(body -> System.out.println("Usuario conectado obtenido: " + body))
            .block();
}
}
