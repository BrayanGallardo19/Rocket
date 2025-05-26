package com.example.GestorEntregas.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.GestorEntregas.model.Entrega;
import com.example.GestorEntregas.model.Estado;
import com.example.GestorEntregas.repository.EntregaRepository;
import com.example.GestorEntregas.webclient.EstadoClient;

@Service
public class EntregaService {
    private final EstadoClient estadoClient;
    private final EntregaRepository entregaRepository;
    private final EstadoClient webClient;

    public EntregaService(EstadoClient estadoClient, EntregaRepository entregaRepository, EstadoClient webClient) {
        this.webClient = webClient;
        this.estadoClient = estadoClient;
        this.entregaRepository = entregaRepository;
    }
    // metodo para crear una nueva entrega
    public Entrega crearEntrega(Entrega entrega) {
        if (entrega.getIdEntrega() != null) {
            throw new RuntimeException("No debes enviar el id al crear una nueva entrega");
        }
        Estado estado = estadoClient.obtenerEstadoPorNombre("Pendiente");
        entrega.setEstado(estado.getNombreEstado());
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
        
        Estado estado = estadoClient.obtenerEstadoPorNombre(nuevoEstado);
        entrega.setEstado(estado.getNombreEstado());
        
        if(estado.getNombreEstado().equalsIgnoreCase("Entregado")) {
            entrega.setFechaEntrega(LocalDate.now());
        }
        return entregaRepository.save(entrega);

    }
    // metodo para buscar entregas por id del microservicio de pedidos
    /*public List <Entrega> buscarPorPedido(Integer idPedido) {
        return entregaRepository.findByIdPedido(idPedido);
    }/* */

    //metodo para eliminar una entrega por id
    public void eliminarEntrega(Integer idProceso) {
        if (!entregaRepository.existsById(idProceso)) {
            throw new RuntimeException("Entrega no encontrada con id: " + idProceso);
        }
        entregaRepository.deleteById(idProceso);
    }
   
}
