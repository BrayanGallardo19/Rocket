package com.example.GestorEntregas.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.GestorEntregas.model.Entrega;
import com.example.GestorEntregas.repository.EntregaRepository;
import com.example.GestorEntregas.webclient.EstadoClient;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EntregaService {
    private final EstadoClient estadoClient;
    private final EntregaRepository entregaRepository;

    public EntregaService(EstadoClient estadoClient, EntregaRepository entregaRepository) {
        this.estadoClient = estadoClient;
        this.entregaRepository = entregaRepository;
    }
    // metodo para crear una nueva entrega
    public Entrega crearEntrega(Entrega entrega) {
        if (entrega.getIdEntrega() != null) {
            throw new RuntimeException("No debes enviar el id al crear una nueva entrega");
        }

        // llamar al WebClient usando Map
        Map<String, Object> estadoMap = estadoClient.obtenerEstadoPorNombre("Pendiente de entrega");

        // Obtenemos el campo nombreEstado desde el Map
        String nombreEstado = estadoMap.get("nombreEstado").toString();

        entrega.setEstado(nombreEstado);

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
