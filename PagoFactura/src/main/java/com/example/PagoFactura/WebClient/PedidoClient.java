package com.example.PagoFactura.WebClient;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class PedidoClient {
    private final WebClient webClient;

    public PedidoClient(@Value("${pedido-service.base-url}") String pedidoServiceBaseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(pedidoServiceBaseUrl)
                .build();
    }

    public Optional<Map<String, Object>> obtenerPedidoPorId(Integer idPedido) {
        try {
            Map<String, Object> pedido = webClient.get()
                    .uri("/{id}", idPedido)
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            clientResponse -> clientResponse.bodyToMono(String.class)
                                    .flatMap(body -> Mono
                                            .error(new RuntimeException("Error al obtener pedido: " + body))))
                    .bodyToMono(Map.class)
                    .doOnNext(body -> System.out.println("Pedido obtenido: " + body))
                    .block();

            return Optional.ofNullable(pedido);

        } catch (Exception e) {
            System.err.println("Error al buscar pedido por ID " + idPedido + ": " + e.getMessage());
            return Optional.empty();
        }
    }
}
