package com.example.GestorInventario.webclient;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.GestorInventario.model.Marca;
import com.example.GestorInventario.model.Modelo;

@Component
public class MarcaModeloClient {
    private final WebClient webClient;

    public MarcaModeloClient(@Value("${marca-modelo-service.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)  
                .build();
    }
    // obtener una marca por id
    public Marca getMarcaById(Integer idMarca) {
        return webClient.get()
                .uri("/marcas/{id}", idMarca)  // path distinto para marca
                .retrieve()
                .bodyToMono(Marca.class)
                .block();
    }
    // obtener un modelo por id
    public Modelo getModeloById(Integer idModelo) {
        return webClient.get()
                .uri("/modelos/{id}", idModelo)  // path distinto para modelo
                .retrieve()
                .bodyToMono(Modelo.class)
                .block();
    }
    // obtener todos las marcas y modelos
    public List<Marca> getAllMarcas() {
        return webClient.get()
                .uri("/marcas")
                .retrieve()
                .bodyToFlux(Marca.class)
                .collectList()
                .block();
    }

    public List<Modelo> getAllModelos() {
        return webClient.get()
                .uri("/modelos")
                .retrieve()
                .bodyToFlux(Modelo.class)
                .collectList()
                .block();
    }
}
