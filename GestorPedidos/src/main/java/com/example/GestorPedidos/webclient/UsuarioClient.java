package com.example.GestorPedidos.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.GestorPedidos.model.Usuario;

import reactor.core.publisher.Mono;

@Component
public class UsuarioClient {
    private final WebClient webClientUsuarios;

    public UsuarioClient(@Value("${usuario-service.url}") String usuarioServiceUrl) {
        this.webClientUsuarios = WebClient.builder()
                .baseUrl(usuarioServiceUrl)
                .build();
    }

    public Usuario obtenerUsuarioPorId(Integer idUsuario) {
        try {
            return webClientUsuarios.get()
                    .uri("/{idUsuario}", idUsuario)
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> response.bodyToMono(String.class)
                                    .flatMap(body -> Mono.error(new RuntimeException("Error al obtener usuario: " + body)))
                    )
                    .bodyToMono(Usuario.class)
                    .blockOptional()
                    .orElseThrow(() -> new RuntimeException(
                            "El usuario con ID " + idUsuario + " no existe o no se pudo obtener"));
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el usuario con ID " + idUsuario + ": " + e.getMessage(), e);
        }
    }



}

