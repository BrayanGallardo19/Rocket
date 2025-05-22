package com.example.GestorLogisticayPedidos.webclient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;



@Component
public class EquipoClient {
    private final WebClient webClient;

    public EquipoClient(@Value("${equipo-service.url}") String equipoServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(equipoServiceUrl)
                .build();
    }

    public Map<String, Object> getEquipoById(Integer id) {
        return this.webClient.get()
                .uri("/{id}", id) // construye la URL /{id}
                .retrieve() // realiza la solicitud y prepara la respuesta
                .onStatus(status -> status.is4xxClientError(),
                response -> response.bodyToMono(String.class)
                .map(body -> new RuntimeException("Equipo no encontrado")))
                .bodyToMono(Map.class) // convierte el JSON en un Map
                .block(); // espera la respuesta antes de continuar
    }
}
