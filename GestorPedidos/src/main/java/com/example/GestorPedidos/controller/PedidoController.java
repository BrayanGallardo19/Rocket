package com.example.GestorPedidos.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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

    // obtener pedidos dependiendo del rol
    @GetMapping("/{idPedido}")
    public ResponseEntity<?> obtenerPedidoPorId(@PathVariable Integer idPedido) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> roles = auth.getAuthorities();

        try {
            if (roles.stream().anyMatch(r -> r.getAuthority().equals("ROLE_GESTOR_INVENTARIO"))) {
                Map<String, Object> pedidoCompleto = pedidoService.obtenerPedidoCompleto(idPedido);
                return ResponseEntity.ok(pedidoCompleto);

            } else if (roles.stream().anyMatch(r -> r.getAuthority().equals("ROLE_CLIENTE"))) {
                // bbtienes el username del auth y usas tu método para cliente
                String username = auth.getName();
                List<Map<String, Object>> pedidos = pedidoService.obtenerPedidosParaCliente(username);

                // filtrar el pedido por idPedido
                Map<String, Object> pedidoBuscado = pedidos.stream()
                        .filter(p -> idPedido.equals(p.get("idPedido")))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Pedido no encontrado para este cliente"));

                return ResponseEntity.ok(pedidoBuscado);

            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tiene permisos para ver este pedido");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
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
        // asignar los datos de la dirección
        Map<String, Object> direccion = new HashMap<>();
        direccion.put("ciudad", body.get("ciudad"));
        direccion.put("comuna", body.get("comuna"));
        direccion.put("calle", body.get("calle"));
        direccion.put("numero", body.get("numero"));
        direccion.put("depto", body.get("depto"));
        direccion.put("referencia", body.get("referencia"));

        // asignar el estado inicial del pedido
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

    // eliminar pedido por id
    @DeleteMapping("/eliminar/{idPedido}")
    public ResponseEntity<Void> eliminarPedidoPorId(@PathVariable Integer idPedido) {
        pedidoService.eliminarPedidoPorId(idPedido);
        return ResponseEntity.noContent().build();
    }

}
