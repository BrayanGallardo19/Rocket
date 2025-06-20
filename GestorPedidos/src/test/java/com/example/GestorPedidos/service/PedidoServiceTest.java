package com.example.GestorPedidos.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.GestorPedidos.model.Pedido;
import com.example.GestorPedidos.model.Tipo;
import com.example.GestorPedidos.repository.PedidoRepository;
import com.example.GestorPedidos.repository.TipoRepository;
import com.example.GestorPedidos.webclient.EquipoClient;
import com.example.GestorPedidos.webclient.UsuarioClient;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {
    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private TipoRepository tipoRepository;
    @Mock
    private UsuarioClient usuarioClient;
    @Mock
    private EquipoClient equipoClient;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    void testObtenerTodosLosPedidos() {
        List<Pedido> mockPedidos = Arrays.asList(
                new Pedido(1, 1, 2, LocalDateTime.now(), 4, 20.0, null),
                new Pedido(2, 2, 3, LocalDateTime.now(), 5, 30.0, null));

        when(pedidoRepository.findAll()).thenReturn(mockPedidos);

        List<Pedido> resultado = pedidoService.mostrarTodosLosPedidos();

        assertThat(resultado).isEqualTo(mockPedidos);

    }

    @Test
    void testCrearPedido() {
        // lo que se envia al servicio
        Pedido nuevoPedido = new Pedido(null, 1, 2, LocalDateTime.now(), 4, 20.0, null);
        Tipo tipoMock = new Tipo(1, "Tipo1");
        // lo que el repositorio devuelve
        Pedido pedidoGuardado = new Pedido(1, 1, 2, LocalDateTime.now(), 4, 20.0, tipoMock);
        when(tipoRepository.findById(1)).thenReturn(java.util.Optional.of(tipoMock));
        when(pedidoRepository.save(nuevoPedido)).thenReturn(pedidoGuardado);

        Pedido resultado = pedidoService.crearPedido(nuevoPedido, 1);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getIdPedido()).isEqualTo(1);
        assertThat(resultado.getTipo()).isEqualTo(tipoMock);
        assertThat(resultado.getIdEstado()).isEqualTo(4);
    }

    @Test
    void testObtenerPedidoPorId() {
        Pedido pedidoMock = new Pedido(1, 1, 2, LocalDateTime.now(), 4, 20.0, null);
        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedidoMock));
        Pedido resultado = pedidoService.obtenerPedidoPorId(1);
        assertThat(resultado).isNotNull();

    }

    @Test
    void testObtenerPedidoPorIdUsuario() {
        List<Pedido> pedidos = List.of(new Pedido(null, 10, 2, LocalDateTime.now(), 1, 5000.0, null));
        when(pedidoRepository.findByIdUsuario(10)).thenReturn(pedidos);

        List<Pedido> resultado = pedidoService.buscarPedidosPorIdUsuario(10);

        assertThat(resultado).isEqualTo(pedidos);
    }

    @Test
    void testEliminarPedidoPorId() {
        when(pedidoRepository.existsById(1)).thenReturn(true);

        pedidoService.eliminarPedidoPorId(1);

        verify(pedidoRepository).deleteById(1);

    }

    @Test
    void testObtenerPedidosParaCliente() {
        Map<String, Object> usuarioMap = Map.of("id", 10);
        List<Pedido> pedidos = List.of(
                new Pedido(1, 10, 100, LocalDateTime.now(), 1, 10000.0, new Tipo(1, "Arriendo")));
        Map<String, Object> estadoMap = Map.of("nombreEstado", "Pendiente");
        Map<String, Object> equipoMap = Map.of(
                "nombre", "Tractor", "precioArriendo", 20000.0, "patente", "XY-1234",
                "marca", Map.of("nombre", "John Deere"),
                "modelo", Map.of("nombre", "X500"));

        when(usuarioClient.obtenerUsuarioPorUsername("carlos")).thenReturn(usuarioMap);
        when(pedidoRepository.findByIdUsuario(10)).thenReturn(pedidos);
        when(equipoClient.obtenerEstadoPorId(1)).thenReturn(estadoMap);
        when(equipoClient.obtenerEquipoPorId(100)).thenReturn(equipoMap);

        List<Map<String, Object>> resultado = pedidoService.obtenerPedidosParaCliente("carlos");

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0)).containsKeys("idPedido", "fecha", "total", "estado", "tipo", "equipo");
    }

    @Test
    void testObtenerPedidoCompleto() {
        Pedido pedido = new Pedido(1, 10, 100, LocalDateTime.now(), 1, 10000.0, new Tipo(1, "Arriendo"));
        Map<String, Object> usuarioMap = Map.of("nombre", "Carlos");
        Map<String, Object> equipoMap = Map.of("nombre", "Tractor");
        Map<String, Object> estadoMap = Map.of("nombreEstado", "Pendiente");

        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedido));
        when(usuarioClient.obtenerUsuarioPorId(10)).thenReturn(usuarioMap);
        when(equipoClient.obtenerEquipoPorId(100)).thenReturn(equipoMap);
        when(equipoClient.obtenerEstadoPorId(1)).thenReturn(estadoMap);

        Map<String, Object> resultado = pedidoService.obtenerPedidoCompleto(1);

        assertThat(resultado).containsKeys("idPedido", "fecha", "estado", "total", "usuario", "equipo", "tipo");
    }

}
