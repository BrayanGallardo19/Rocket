package com.example.GestorEntregas.webclient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class UsuarioClient {
    private final WebClient webClient;

   public UsuarioClient(@Value("${usuarios-service.url}") String baseUrl) {
                this.webClient = WebClient.builder()
                                .baseUrl(baseUrl)
                                .build();
        }
    public Map<String, Object> obtenerUsuarioPorId(Integer idUsuario) {
        try {
            return webClient.get()
                    .uri("/{id}", idUsuario)
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError(),
                            response -> response.bodyToMono(String.class)
                                    .flatMap(
                                            body -> Mono.error(new RuntimeException("Usuario no encontrado: " + body))))
                    .bodyToMono(Map.class)
                    .doOnNext(body -> System.out.println("Usuario obtenido por ID: " + body))
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener usuario por ID: " + e.getMessage());
        }
    }
}
