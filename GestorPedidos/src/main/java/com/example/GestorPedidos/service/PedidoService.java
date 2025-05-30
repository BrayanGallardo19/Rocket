package com.example.GestorPedidos.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.GestorPedidos.model.Equipo;
import com.example.GestorPedidos.model.Pedido;
import com.example.GestorPedidos.model.Tipo;
import com.example.GestorPedidos.model.Usuario;
import com.example.GestorPedidos.repository.PedidoRepository;
import com.example.GestorPedidos.repository.TipoRepository;
import com.example.GestorPedidos.webclient.EquipoClient;
import com.example.GestorPedidos.webclient.UsuarioClient;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PedidoService {
    private final TipoRepository tipoRepository;
    private final PedidoRepository pedidoRepository;
    private final EquipoClient equipoClient;
    private final UsuarioClient usuarioClient;

    public PedidoService(PedidoRepository pedidoRepository, EquipoClient equipoClient, TipoRepository tipoRepository,
            UsuarioClient usuarioClient) {
        this.usuarioClient = usuarioClient;
        this.pedidoRepository = pedidoRepository;
        this.equipoClient = equipoClient;
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

    // buscar equipo por id(microservicio de inventario)
    public Equipo obtenerEquipoPorId(Integer idEquipo) {
        return equipoClient.obtenerEquipoPorId(idEquipo);
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
        if (idUsuario == null) {
            throw new IllegalArgumentException("El id de usuario no puede ser nulo");
        }
        return pedidoRepository.findByIdUsuario(idUsuario);
    }

    // eliminar pedido por id
    public void eliminarPedidoPorId(Integer idPedido) {
        if (!pedidoRepository.existsById(idPedido)) {
            throw new RuntimeException("Pedido no encontrado con id: " + idPedido);
        }
        pedidoRepository.deleteById(idPedido);
    }

    public Pedido crearPedido(Pedido pedido) {
        // validar que el usuario existe
        Usuario usuario = usuarioClient.obtenerUsuarioPorId(pedido.getIdUsuario());

        pedido.setFechaPedido(LocalDateTime.now());

        // Guardar el pedido
        return pedidoRepository.save(pedido);
    }

    
}
