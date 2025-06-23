package com.example.GestorPedidos.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
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
        // simulacion de pedido
        Pedido pedidoEntrada = new Pedido();
        pedidoEntrada.setTotal(100000.0);
        pedidoEntrada.setIdUsuario(1);
        pedidoEntrada.setIdEquipo(5);

        // simulacion de tipo
        Tipo tipoMock = new Tipo();
        tipoMock.setIdTipo(2);
        tipoMock.setNombre("Arriendo");

        // simular los repositorios
        when(tipoRepository.findById(2)).thenReturn(Optional.of(tipoMock));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // llamar al metodo a probar
        Pedido resultado = pedidoService.crearPedido(pedidoEntrada, 2);

        // verificar
        assertThat(resultado.getTipo()).isEqualTo(tipoMock);
        assertThat(resultado.getFechaPedido()).isNotNull();
        assertThat(resultado.getIdEstado()).isEqualTo(1);
        assertThat(resultado.getIdUsuario()).isEqualTo(1);
        assertThat(resultado.getIdEquipo()).isEqualTo(5);
        assertThat(resultado.getTotal()).isEqualTo(100000.0);
    }

    @Test
    void testMostrarTodosLosPedidos() {

        List<Pedido> pedidosMock = Arrays.asList(
                new Pedido(), new Pedido());

        when(pedidoRepository.findAll()).thenReturn(pedidosMock);

        List<Pedido> resultado = pedidoService.mostrarTodosLosPedidos();

        assertThat(resultado).hasSize(2);
        verify(pedidoRepository).findAll();
    }

    @Test
    void testEliminarPedidoPorId() {

        when(pedidoRepository.existsById(1)).thenReturn(true);

        pedidoService.eliminarPedidoPorId(1);

        verify(pedidoRepository).deleteById(1);
    }

    @Test
    void testObtenerPedidoCompleto() {
        // simular pedido
        Pedido pedidoMock = new Pedido();
        pedidoMock.setIdPedido(1);
        pedidoMock.setIdUsuario(10);
        pedidoMock.setIdEquipo(20);
        pedidoMock.setIdEstado(3);
        pedidoMock.setFechaPedido(LocalDateTime.now());
        pedidoMock.setTotal(120000.0);
        pedidoMock.setTipo(new Tipo(2, "Arriendo"));

        when(pedidoRepository.findById(Integer.valueOf(1))).thenReturn(Optional.of(pedidoMock));

        Integer idUserConectado = Integer.valueOf(999); // Asegúrate que es Integer, no int

        // Simular equipo (requiere idUserConectado)
        Map<String, Object> equipoMock = new HashMap<>();
        equipoMock.put("idEquipo", 20);
        equipoMock.put("nombre", "Tractor");
        when(equipoClient.obtenerEquipoPorId(Integer.valueOf(20), idUserConectado)).thenReturn(equipoMock);

        // Simular estado (requiere idUserConectado)
        Map<String, Object> estadoMock = new HashMap<>();
        estadoMock.put("nombreEstado", "Pendiente");
        when(equipoClient.obtenerEstadoPorId(Integer.valueOf(3), idUserConectado)).thenReturn(estadoMock);

        // Ejecutar
        Map<String, Object> resultado = pedidoService.obtenerPedidoPorId(Integer.valueOf(1), idUserConectado);

        // Verificaciones
        assertThat(resultado).containsKeys("idPedido", "idUsuario", "equipo", "estado", "total", "fecha", "tipo");
        assertThat(resultado.get("estado")).isEqualTo("Pendiente");
        assertThat(((Map<?, ?>) resultado.get("equipo")).get("nombre")).isEqualTo("Tractor");
        assertThat(resultado.get("idUsuario")).isEqualTo(10);
    }

}
