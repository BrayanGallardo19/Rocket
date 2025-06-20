package com.example.GestorEntregas.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.GestorEntregas.model.Entrega;
import com.example.GestorEntregas.repository.EntregaRepository;
import com.example.GestorEntregas.webclient.EstadoClient;
import com.example.GestorEntregas.webclient.PedidoClient;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EntregaService {
    private final EstadoClient estadoClient;
    private final PedidoClient pedidoClient;
    private final EntregaRepository entregaRepository;

    public EntregaService(EstadoClient estadoClient, EntregaRepository entregaRepository,
            PedidoClient pedidoClient) {
        this.estadoClient = estadoClient;
        this.entregaRepository = entregaRepository;
        this.pedidoClient = pedidoClient;
    }

    // metodo para crear una nueva entrega
    public Entrega crearEntrega(Entrega entrega) {
        if (entrega.getIdEntrega() != null) {
            throw new RuntimeException("No debes enviar el id al crear una nueva entrega");
        }

        Map<String, Object> pedidoData = pedidoClient.obtenerPedidoPorId(entrega.getIdPedido());
        if (pedidoData == null) {
            throw new RuntimeException("Pedido no encontrado");
        }

        // estado del pedido
        Integer idEstadoPedido = (Integer) pedidoData.get("idEstado");
        if (idEstadoPedido == null) {
            throw new RuntimeException("El pedido no tiene estado asignado");
        }

        // consultar nombre del estado via estadoClient
        Map<String, Object> estadoPedidoMap = estadoClient.obtenerEstadoPorId(idEstadoPedido);
        String nombreEstadoPedido = estadoPedidoMap != null ? (String) estadoPedidoMap.get("nombreEstado") : null;

        if (!"Listo para entrega".equalsIgnoreCase(nombreEstadoPedido)) {
            throw new RuntimeException("Pedido no está listo para entrega");
        }

        // obtener estado inicial para entrega
        Map<String, Object> estadoMap = estadoClient.obtenerEstadoPorNombre("Pendiente de entrega");
        entrega.setEstado(estadoMap.get("nombreEstado").toString());

        return entregaRepository.save(entrega);
    }

    // metodo para obtener todas las entregas
    public List<Entrega> obtenerTodasLasEntregas() {
        try {
            return entregaRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las entregas: " + e.getMessage());
        }
    }

    // metodo para modificar el estado de una entrega
    public Entrega actualizarEstadoEntrega(Integer idEntrega, String nuevoEstado) {
        Entrega entrega = entregaRepository.findById(idEntrega)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada con id: " + idEntrega));

        // llamamos al WebClient usando Map
        Map<String, Object> estadoMap = estadoClient.obtenerEstadoPorNombre(nuevoEstado);

        String nombreEstado = estadoMap.get("nombreEstado").toString();

        entrega.setEstado(nombreEstado);

        if (nombreEstado.equalsIgnoreCase("Entregado")) {
            entrega.setFechaEntrega(LocalDate.now());
        }

        return entregaRepository.save(entrega);
    }

    // metodo para eliminar una entrega por id
    public void eliminarEntrega(Integer idProceso) {
        if (!entregaRepository.existsById(idProceso)) {
            throw new RuntimeException("Entrega no encontrada con id: " + idProceso);
        }
        entregaRepository.deleteById(idProceso);
    }

}
