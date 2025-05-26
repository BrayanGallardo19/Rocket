package com.example.PagoFactura.WebClient;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.PagoFactura.Model.Venta;

@Component

public class VentaClient {
    private final WebClient webClient;
    public VentaClient(@Value("${venta-service.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    //obtener venta por id
    public Venta getVentaById(Integer idVenta) {
        return webClient.get()
                .uri("/ventas/{id}", idVenta)
                .retrieve()
                .bodyToMono(Venta.class)
                .block();
    }

    //obtener todas las ventas
    public List<Venta> getAllVentas() {
        return webClient.get()
                .uri("/ventas")
                .retrieve()
                .bodyToFlux(Venta.class)
                .collectList()
                .block();
    }
}
