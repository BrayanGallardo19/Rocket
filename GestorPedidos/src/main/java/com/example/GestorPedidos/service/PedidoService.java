package com.example.GestorPedidos.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.GestorPedidos.model.Pedido;
import com.example.GestorPedidos.model.Tipo;
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
    private final UsuarioClient usuarioClient;
    private final EquipoClient equipoClient;

    public PedidoService(PedidoRepository pedidoRepository, TipoRepository tipoRepository,
            UsuarioClient usuarioClient, EquipoClient equipoClient) {
        this.pedidoRepository = pedidoRepository;
        this.tipoRepository = tipoRepository;
        this.usuarioClient = usuarioClient;
        this.equipoClient = equipoClient;
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
        pedido.setFechaPedido(LocalDateTime.now()); // establecer fecha actual
        final int ID_ESTADO_PENDIENTE = 1; // id del estado pendiente
        pedido.setIdEstado(ID_ESTADO_PENDIENTE); // establecer estado por defecto

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
        if (idUsuario == null || idUsuario <= 0) {
            throw new IllegalArgumentException("El id de usuario debe ser un número positivo");
        }
        List<Pedido> pedidos = pedidoRepository.findByIdUsuario(idUsuario);
        if (pedidos.isEmpty()) {
            throw new RuntimeException("No se encontraron pedidos para el usuario con id: " + idUsuario);
        }
        return pedidos;
    }

    // eliminar pedido por id
    public void eliminarPedidoPorId(Integer idPedido) {
        if (!pedidoRepository.existsById(idPedido)) {
            throw new RuntimeException("Pedido no encontrado con id: " + idPedido);
        }
        pedidoRepository.deleteById(idPedido);
    }

    // obtener pedidos para un cliente por username, con datos simplificados para
    // cliente
    public List<Map<String, Object>> obtenerPedidosParaCliente(String username) {
        Map<String, Object> usuario = usuarioClient.obtenerUsuarioPorUsername(username);
        if (usuario == null || !usuario.containsKey("id")) {
            throw new RuntimeException("Usuario no encontrado o ID no disponible");
        }

        Integer idUsuario = (Integer) usuario.get("id");
        List<Pedido> pedidos = pedidoRepository.findByIdUsuario(idUsuario);

        return pedidos.stream().map(pedido -> {
            Map<String, Object> pedidoMap = new HashMap<>();
            pedidoMap.put("idPedido", pedido.getIdPedido());
            pedidoMap.put("fecha", pedido.getFechaPedido());
            pedidoMap.put("total", pedido.getTotal());

            // obtener el estado como Map desde el microservicio inventario
            Map<String, Object> estadoMap = equipoClient.obtenerEstadoPorId(pedido.getIdEstado());
            String nombreEstado = "Desconocido";
            if (estadoMap != null && estadoMap.get("nombreEstado") != null) {
                nombreEstado = estadoMap.get("nombreEstado").toString();
            }
            pedidoMap.put("estado", nombreEstado);
            // obtiene objeto tipo, se verifica si es nulo y se asigna el nombre
            pedidoMap.put("tipo", pedido.getTipo() != null ? pedido.getTipo().getNombre() : null);

            // obtener equipo completo
            Map<String, Object> equipoCompleto = equipoClient.obtenerEquipoPorId(pedido.getIdEquipo());

            // crear equipo simplificado
            Map<String, Object> equipoSimple = new HashMap<>();
            if (equipoCompleto != null) {
                equipoSimple.put("nombre", equipoCompleto.get("nombre"));
                equipoSimple.put("precioArriendo", equipoCompleto.get("precioArriendo"));
                equipoSimple.put("patente", equipoCompleto.get("patente"));

                // marca y modelo (solo nombre)
                Map<String, Object> marca = (Map<String, Object>) equipoCompleto.get("marca");
                if (marca != null) {
                    equipoSimple.put("marca", marca.get("nombre"));
                }

                Map<String, Object> modelo = (Map<String, Object>) equipoCompleto.get("modelo");
                if (modelo != null) {
                    equipoSimple.put("modelo", modelo.get("nombre"));
                }
            }

            pedidoMap.put("equipo", equipoSimple);

            return pedidoMap;
        }).collect(Collectors.toList());
    }

    // obtener pedido completo para el encargado
    public Map<String, Object> obtenerPedidoCompleto(Integer idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        Map<String, Object> usuario = usuarioClient.obtenerUsuarioPorId(pedido.getIdUsuario());
        Map<String, Object> equipo = equipoClient.obtenerEquipoPorId(pedido.getIdEquipo());

        Tipo tipo = pedido.getTipo();
        if (tipo == null) {
            throw new RuntimeException("El pedido no tiene tipo asignado");
        }

        // Obtener estado vía WebClient con idEstado en Pedido (supongo que tienes ese
        // campo en Pedido)
        Map<String, Object> estado = equipoClient.obtenerEstadoPorId(pedido.getIdEstado());
        if (estado == null) {
            throw new RuntimeException("El pedido no tiene estado asignado");
        }

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("idPedido", pedido.getIdPedido());
        resultado.put("fecha", pedido.getFechaPedido());
        resultado.put("estado", estado.get("nombreEstado")); // campo según microservicio inventario
        resultado.put("total", pedido.getTotal());

        resultado.put("usuario", usuario);
        resultado.put("equipo", equipo);
        resultado.put("tipo", tipo.getNombre()); // usa objeto local Tipo

        return resultado;
    }
}
