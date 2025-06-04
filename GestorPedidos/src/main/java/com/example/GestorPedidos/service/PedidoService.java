package com.example.GestorPedidos.service;


import java.time.LocalDateTime;
import java.util.List;


import org.springframework.stereotype.Service;

import com.example.GestorPedidos.model.Pedido;
import com.example.GestorPedidos.model.Tipo;
import com.example.GestorPedidos.repository.PedidoRepository;
import com.example.GestorPedidos.repository.TipoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PedidoService {
    private final TipoRepository tipoRepository;
    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository, TipoRepository tipoRepository
            ) {
        this.pedidoRepository = pedidoRepository;
        this.tipoRepository = tipoRepository;
    }

    // crear un pedido
    public Pedido crearPedido(Pedido pedido, Integer idTipo) {
        if (pedido.getIdPedido() != null) {
            throw new RuntimeException("No debes enviar el id al crear un nuevo pedido");
        }
        // buscar el tipo por id
        Tipo tipo = tipoRepository.findById(idTipo)
                .orElseThrow(() -> new RuntimeException("Tipo no encontrado con id: " + idTipo));

        pedido.setTipo(tipo);
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstado("PENDIENTE"); // establecer estado por defecto

        return pedidoRepository.save(pedido);
    }
    // mostrar todos los pedidos
    public List<Pedido> mostrarTodosLosPedidos() {
        try {
            return pedidoRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los pedidos: " + e.getMessage());
        }
    }

    // buscar pedido por id
    public Pedido obtenerPedidoPorId(Integer idPedido) {
        return pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + idPedido));
    }

    // buscar pedidos por id de usuario
    public List<Pedido> buscarPedidosPorIdUsuario(Integer idUsuario) {
    if (idUsuario == null) throw new IllegalArgumentException("El id de usuario no puede ser nulo");
    return pedidoRepository.findByIdUsuario(idUsuario);
}

    // eliminar pedido por id
    public void eliminarPedidoPorId(Integer idPedido) {
        if (!pedidoRepository.existsById(idPedido)) {
            throw new RuntimeException("Pedido no encontrado con id: " + idPedido);
        }
        pedidoRepository.deleteById(idPedido);
    }

}
