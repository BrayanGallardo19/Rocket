package com.example.GestorPedidos.webclient;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class PagoFacturaClient {
    private final WebClient webClient;

    public PagoFacturaClient(@Value("${pagoyfactura.base-url}") String pagoyfacturaBaseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(pagoyfacturaBaseUrl)
                .build();
    }

    public Optional<Void> informarNuevoPedidoConfirmado(Integer idPedido) {
        try {
            Void response = webClient.post()
                    .uri("/generar")
                    .bodyValue(Map.of("idPedido", idPedido))
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            clientResponse -> clientResponse.bodyToMono(String.class)
                                    .flatMap(body -> Mono
                                            .error(new RuntimeException("Error al informar nuevo pedido: " + body))))
                    .bodyToMono(Void.class)
                    .doOnSuccess(v -> System.out
                            .println("Pedido informado correctamente a pagoyfactura, idPedido: " + idPedido))
                    .block();

            return Optional.ofNullable(response);
        } catch (Exception e) {
            System.err.println(
                    "Error al informar nuevo pedido a pagoyfactura, idPedido " + idPedido + ": " + e.getMessage());
            return Optional.empty();
        }
    }
}
