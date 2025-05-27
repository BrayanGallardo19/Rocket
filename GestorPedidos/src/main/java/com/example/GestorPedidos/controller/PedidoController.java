package com.example.GestorPedidos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.GestorPedidos.model.Pedido;
import com.example.GestorPedidos.service.PedidoService;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }
    @GetMapping("/protegido")
    public String pedidoProtegido() {
        // Obtener el usuario autenticado (username del token JWT)
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return "Acceso concedido al usuario: " + username;
    }


    // crear pedido
    public ResponseEntity<Pedido> crearPedido(@RequestBody Pedido pedido, @RequestParam Integer idTipo) {
    Pedido nuevoPedido = pedidoService.crearPedido(pedido, idTipo);
    return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido); // 201
}

    // buscar pedido por id
    @GetMapping("/id/{idPedido}")
    public ResponseEntity<Pedido> buscarPedidoPorId(@PathVariable Integer idPedido) {
        Pedido pedido = pedidoService.obtenerPedidoPorId(idPedido);
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
    public ResponseEntity<List<Pedido>> buscarPedidosPorIdUsuario(@PathVariable Integer idUsuario) {
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
