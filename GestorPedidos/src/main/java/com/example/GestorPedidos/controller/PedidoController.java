package com.example.GestorPedidos.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.GestorPedidos.model.Pedido;
import com.example.GestorPedidos.service.PedidoService;
import com.example.GestorPedidos.service.TipoService;
import com.example.GestorPedidos.webclient.UsuarioClient;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final UsuarioClient usuarioClient;

    private final TipoService tipoService;

    public PedidoController(PedidoService pedidoService, UsuarioClient usuarioClient, TipoService tipoService) {
        this.pedidoService = pedidoService;
        this.usuarioClient = usuarioClient;
        this.tipoService = tipoService;

    }

    // buscar pedidos de cliente por username
    @GetMapping("/cliente")
    public ResponseEntity<?> obtenerPedidosCliente(@RequestParam String username) {
        List<Map<String, Object>> pedidos = pedidoService.obtenerPedidosParaCliente(username);
        return ResponseEntity.ok(pedidos);
    }

    // mostrar detalle de un pedido completo
    @GetMapping("/detalle/{idPedido}")
    public ResponseEntity<Map<String, Object>> mostrarPedidoCompleto (@PathVariable Integer idPedido) {
        Map<String, Object> pedidoCompleto = pedidoService.obtenerPedidoCompleto(idPedido);
        if (pedidoCompleto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Pedido no encontrado"));
        }
        return ResponseEntity.ok(pedidoCompleto);
    }
     
    // crear un nuevo pedido
    @PostMapping("/crear")
    public ResponseEntity<Pedido> crearPedido(@RequestBody Map<String, Object> body) {
        // obtener username desde el token
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // obtener el usuario como Map desde el microservicio de usuarios
        Map<String, Object> usuario = usuarioClient.obtenerUsuarioPorUsername(username);
        if (usuario == null || !usuario.containsKey("id")) {
            throw new RuntimeException("No se pudo obtener el usuario autenticado.");
        }

        // construir el pedido usando los datos del body
        Pedido pedido = new Pedido();
        pedido.setIdEquipo((Integer) body.get("idEquipo"));
        pedido.setTotal(Double.parseDouble(body.get("total").toString()));
        pedido.setIdUsuario((Integer) usuario.get("id"));

        Integer idTipo = (Integer) body.get("idTipo");

        // crear el pedido con su tipo
        Pedido nuevoPedido = pedidoService.crearPedido(pedido, idTipo);

        return ResponseEntity.ok(nuevoPedido);
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

    // buscar pedidos siendo cliente
    @GetMapping("/cliente/mis-pedidos")
    public ResponseEntity<List<Map<String, Object>>> obtenerMisPedidos() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Map<String, Object>> pedidos = pedidoService.obtenerPedidosParaCliente(username);
        return ResponseEntity.ok(pedidos);
    }

    // eliminar pedido por id
    @DeleteMapping("/eliminar/{idPedido}")
    public ResponseEntity<Void> eliminarPedidoPorId(@PathVariable Integer idPedido) {
        pedidoService.eliminarPedidoPorId(idPedido);
        return ResponseEntity.noContent().build();
    }

}
