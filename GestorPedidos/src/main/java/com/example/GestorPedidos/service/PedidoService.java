package com.example.GestorPedidos.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.GestorPedidos.model.Pedido;
import com.example.GestorPedidos.model.Tipo;
import com.example.GestorPedidos.repository.PedidoRepository;
import com.example.GestorPedidos.repository.TipoRepository;
import com.example.GestorPedidos.webclient.EquipoClient;
import com.example.GestorPedidos.webclient.PagoFacturaClient;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PedidoService {
    private final TipoRepository tipoRepository;
    private final PedidoRepository pedidoRepository;
    private final EquipoClient equipoClient;
    private final PagoFacturaClient pagoFacturaClient;

    public PedidoService(PedidoRepository pedidoRepository, TipoRepository tipoRepository,
            EquipoClient equipoClient, PagoFacturaClient pagoFacturaClient) {
        this.pedidoRepository = pedidoRepository;
        this.tipoRepository = tipoRepository;
        this.equipoClient = equipoClient;
        this.pagoFacturaClient = pagoFacturaClient;
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

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        // informar a pagoyfactura que hay un nuevo pedido confirmado
        pagoFacturaClient.informarNuevoPedidoConfirmado(pedidoGuardado.getIdPedido())
                .ifPresentOrElse(
                        v -> System.out.println("Notificación enviada para pedido " + pedidoGuardado.getIdPedido()),
                        () -> System.err.println(
                                "No se pudo notificar a pagoyfactura para pedido " + pedidoGuardado.getIdPedido()));

        return pedidoGuardado;
    }

    // mostrar todos los pedidos
    public List<Pedido> mostrarTodosLosPedidos() {
        try {
            return pedidoRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los pedidos: " + e.getMessage());
        }
    }

    // eliminar pedido por id
    public void eliminarPedidoPorId(Integer idPedido) {
        if (!pedidoRepository.existsById(idPedido)) {
            throw new RuntimeException("Pedido no encontrado con id: " + idPedido);
        }
        pedidoRepository.deleteById(idPedido);
    }

    // obtener pedido completo para el encargado
    public Map<String, Object> obtenerPedidoPorId(Integer idPedido, Integer idUserConectado) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        Integer idUsuario = pedido.getIdUsuario();

        Map<String, Object> equipo = equipoClient.obtenerEquipoPorId(pedido.getIdEquipo(), idUserConectado);

        Tipo tipo = pedido.getTipo();
        if (tipo == null) {
            throw new RuntimeException("El pedido no tiene tipo asignado");
        }

        Map<String, Object> estado = equipoClient.obtenerEstadoPorId(pedido.getIdEstado(), idUserConectado);
        if (estado == null) {
            throw new RuntimeException("El pedido no tiene estado asignado");
        }

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("idPedido", pedido.getIdPedido());
        resultado.put("fecha", pedido.getFechaPedido());
        resultado.put("estado", estado.get("nombreEstado")); // campo según microservicio inventario
        resultado.put("total", pedido.getTotal());

        resultado.put("idUsuario", idUsuario);
        resultado.put("equipo", equipo);
        resultado.put("tipo", tipo.getNombre()); // usa objeto local Tipo

        return resultado;
    }

    public Pedido modificarPedido(Integer idPedido, Pedido pedidoActualizado, Integer idTipo) {
        // Buscar el pedido existente
        Pedido pedidoExistente = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + idPedido));

        Tipo tipo = tipoRepository.findById(idTipo)
                .orElseThrow(() -> new RuntimeException("Tipo no encontrado con id: " + idTipo));

        // Actualizar campos permitidos
        pedidoExistente.setIdUsuario(pedidoActualizado.getIdUsuario());
        pedidoExistente.setIdEquipo(pedidoActualizado.getIdEquipo());
        pedidoExistente.setIdEstado(pedidoActualizado.getIdEstado());
        pedidoExistente.setTotal(pedidoActualizado.getTotal());
        pedidoExistente.setTipo(tipo);

        return pedidoRepository.save(pedidoExistente);
    }
}
