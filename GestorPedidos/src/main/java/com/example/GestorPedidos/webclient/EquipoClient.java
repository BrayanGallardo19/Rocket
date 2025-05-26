package com.example.GestorPedidos.webclient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.GestorPedidos.model.Equipo;

import reactor.core.publisher.Mono;

@Component
public class EquipoClient {
    private final WebClient webClient;

    public EquipoClient(@Value("${equipo-service.url}") String equipoServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(equipoServiceUrl)
                .build();
    }

    public Equipo obtenerEquipoPorId(Integer idEquipo) {
        try {
            return webClient.get()
                    .uri("/{idEquipo}", idEquipo)
                    .retrieve()
                    .onStatus(
                    status -> status.is4xxClientError() || status.is5xxServerError(),
                    response -> response.bodyToMono(String.class)
                    .flatMap(body -> Mono
                    .error(new RuntimeException("Error al obtener equipo: " + body))))
                    .bodyToMono(Equipo.class)
                    .blockOptional()
                    .orElseThrow(() -> new RuntimeException(
                            "El equipo con ID " + idEquipo + " no existe o no se pudo obtener"));

        } catch (Exception e) {
            // manejar excepciones de forma más específica si es necesario
            throw new RuntimeException("Error al obtener el equipo con ID " + idEquipo + ": " + e.getMessage(), e);
        }
    }

}
