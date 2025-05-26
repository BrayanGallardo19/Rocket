package com.example.GestorEntregas.webclient;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.GestorEntregas.model.Estado;



@Component
public class EstadoClient {
    private final WebClient webClient;
    // cliente para interactuar con el servicio de inventario para obtener estados
    public EstadoClient(@Value("${inventario-service.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)  
                .build();
    }

    public List<Estado> obtenerEstados() {
        return webClient.get()
                .uri("/equipos/estados")  // path para obtener estados
                .retrieve()
                .bodyToFlux(Estado.class)
                .collectList()
                .block();
    }

    public Estado obtenerEstadoPorNombre(String nombreEstado) {
        List<Estado> estados = obtenerEstados();
        //convierte la lista a un stream , funciona como un for
        return estados.stream()
                .filter(estado -> estado.getNombreEstado().equalsIgnoreCase(nombreEstado))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Estado"+nombreEstado+" no encontrado"));

                
}
}
