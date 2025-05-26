package com.example.GestorPedidos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.GestorPedidos.model.Equipo;
import com.example.GestorPedidos.model.Pedido;
import com.example.GestorPedidos.service.PedidoService;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // metodo de prueba
    @GetMapping("/equipo/{idEquipo}")
    public ResponseEntity<Equipo> obtenerEquipoPorId(@PathVariable Integer idEquipo) {
        Equipo equipo = pedidoService.obtenerEquipoPorId(idEquipo);
        return ResponseEntity.ok(equipo);
    }

    // crear venta
    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestBody Pedido pedido, Integer idTipo) {
        Pedido nuevoPedido = pedidoService.crearPedido(pedido, idTipo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido); // 201
    }

    // buscar pedido por id
    @GetMapping("/{idPedido}")
    public ResponseEntity<Pedido> buscarPedidoPorId(@PathVariable Integer id) {
        Pedido pedido = pedidoService.obtenerPedidoPorId(id);
        return ResponseEntity.ok(pedido); // 200
    }

    // mostrar todos los pedidos
    @GetMapping("")
    public ResponseEntity<List<Pedido>> mostrarTodosLosPedidos() {
        List<Pedido> pedidos = pedidoService.mostrarTodosLosPedidos();
        return ResponseEntity.ok(pedidos); // 200
    }

    // buscar pedidos por id de usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Pedido>> buscarPedidosPorIdUsuario(@RequestParam Integer idUsuario) {
        List<Pedido> pedidos = pedidoService.buscarPedidosPorIdUsuario(idUsuario);
        return ResponseEntity.ok(pedidos); // 200
    }

    // eliminar pedido por id
    @DeleteMapping("/eliminar/{idPedido}")
    public ResponseEntity<Void> eliminarPedidoPorId(@PathVariable Integer idPedido) {
        pedidoService.eliminarPedidoPorId(idPedido);
        return ResponseEntity.noContent().build();
    }
}
