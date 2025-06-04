package com.example.GestorInventario.webclient;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.GestorInventario.model.Modelo;

import reactor.core.publisher.Mono;

@Component
public class ModeloClient {

    private final WebClient webClient;

    public ModeloClient(@Value("${modelo-service.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Modelo obtenerModeloPorId(Integer idModelo) {
        try {
            return webClient.get()
                    .uri("/{id}", idModelo)
                    .retrieve()
                    .onStatus(status -> status.value() == 404,
                            response -> Mono.error(new RuntimeException("Modelo no encontrado con ID: " + idModelo)))
                    .onStatus(status -> status.is5xxServerError(),
                            response -> response.bodyToMono(String.class)
                                    .flatMap(msg -> Mono
                                            .error(new RuntimeException("Error interno del modelo-service: " + msg))))
                    .bodyToMono(Modelo.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el modelo con ID " + idModelo + ": " + e.getMessage(), e);
        }
    }

    public List<Modelo> obtenerTodosLosModelos() {
        try {
            return webClient.get()
                    .uri("") // path para obtener todos los modelos
                    .retrieve()
                    .bodyToFlux(Modelo.class)
                    .collectList()
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los modelos: " + e.getMessage(), e);
        }
    }
}
