package com.example.GestorInventario.webclient;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.GestorInventario.model.Marca;

@Component
public class MarcaClient {
    private final WebClient webClient;

    public MarcaClient(@Value("${marca-service.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Marca obtenerMarcaPorId(Integer idMarca) {
        try {
        return webClient.get()
                .uri("/{id}", idMarca)
                .retrieve()
                .onStatus(
                status -> status.is4xxClientError() || status.is5xxServerError(),
                response -> response.bodyToMono(String.class)
                .map(msg -> new RuntimeException("Marca no encontrada: " + msg))
                )
                .bodyToMono(Marca.class)
                .block();  // espera la respuesta y retorna Marca o null si no existe
    } catch (Exception e) {
        throw new RuntimeException("Error al obtener la marca con ID " + idMarca + ": " + e.getMessage(), e);
    }
}
    public List<Marca> obtenerTodasLasMarcas() {
        try {
            return webClient.get()
                    .uri("/")  // path para obtener todas las marcas
                    .retrieve()
                    .bodyToFlux(Marca.class)
                    .collectList()
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las marcas: " + e.getMessage(), e);
        }
    }
}
