package com.example.GestorEntregas.webclient;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;



@Component
public class EstadoClient {
    private final WebClient webClient;

    public EstadoClient(@Value("${inventario-service.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
    // metodo para obtener un estado por su id desde el servicio de inventario
    public Map<String, Object> obtenerEstadoPorId(Integer idEstado) {
        return webClient.get()
                .uri("/equipos/estados/{id}", idEstado)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new RuntimeException("Estado no encontrado: " + body)))
                )
                .bodyToMono(Map.class)
                .doOnNext(body -> System.out.println("Estado obtenido por ID: " + body))
                .block();
    }
    // metodo para obtener todos los estados desde el servicio de inventario
    public List<Map<String, Object>> obtenerTodosLosEstados() {
        return webClient.get()
                .uri("/equipos/estados")
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new RuntimeException("Error al obtener estados: " + body)))
                )
                .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
                .doOnNext(body -> System.out.println("Estados obtenidos: " + body))
                .block();
    }
    // metodo para obtener un estado por su nombre desde el servicio de inventario
    public Map<String, Object> obtenerEstadoPorNombre(String nombreEstado) {
        List<Map<String, Object>> estados = obtenerTodosLosEstados();

        return estados.stream()
                .filter(estado -> estado.get("nombreEstado").toString().equalsIgnoreCase(nombreEstado))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Estado " + nombreEstado + " no encontrado"));
    }
}
