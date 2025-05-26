package com.example.GestorInventario.webclient;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.GestorInventario.model.Modelo;

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
        Modelo modelo = webClient.get()
                .uri("/{id}", idModelo)
                .retrieve()
                .onStatus(
                    status -> status.is4xxClientError() || status.is5xxServerError(),
                    response -> response.bodyToMono(String.class)
                        .map(msg -> new RuntimeException("Modelo no encontrado: " + msg))
                )
                .bodyToMono(Modelo.class)
                .block();

        if (modelo == null) {
            throw new RuntimeException("Modelo no encontrado con ID: " + idModelo);
        }

        return modelo;
    } catch (Exception e) {
        throw new RuntimeException("Error al obtener el modelo con ID " + idModelo + ": " + e.getMessage(), e);
    }
}
    public List <Modelo> obtenerTodosLosModelos() {
        try {
            return webClient.get()
                    .uri("/modelos")  // path para obtener todos los modelos
                    .retrieve()
                    .bodyToFlux(Modelo.class)
                    .collectList()
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los modelos: " + e.getMessage(), e);
        }   
}
}

