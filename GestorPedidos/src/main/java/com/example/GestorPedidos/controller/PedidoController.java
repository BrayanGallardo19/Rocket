package com.example.GestorPedidos.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.GestorPedidos.dto.PedidoCompletoDTO;
import com.example.GestorPedidos.dto.PedidoRequestDTO;
import com.example.GestorPedidos.dto.UsuarioDTO;
import com.example.GestorPedidos.model.Pedido;
import com.example.GestorPedidos.model.Tipo;
import com.example.GestorPedidos.service.PedidoDTOService;
import com.example.GestorPedidos.service.PedidoService;
import com.example.GestorPedidos.service.TipoService;
import com.example.GestorPedidos.webclient.UsuarioClient;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoDTOService pedidoDtoService;
    private final UsuarioClient usuarioClient;
    private final TipoService tipoService;

    
    public PedidoController(PedidoService pedidoService, UsuarioClient usuarioClient, TipoService tipoService,
            PedidoDTOService pedidoDtoService) {
        this.pedidoService = pedidoService;
        this.usuarioClient = usuarioClient;
        this.tipoService = tipoService;
        this.pedidoDtoService = pedidoDtoService;
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UsuarioDTO> obtenerPorUsername(@PathVariable String username) {
        UsuarioDTO usuarioDTO = usuarioClient.obtenerUsuarioPorUsername(username);
        return ResponseEntity.ok(usuarioDTO);
    }

    @GetMapping("/detalle/{idPedido}")
    public ResponseEntity<PedidoCompletoDTO> obtenerPedidoCompleto(@PathVariable Integer idPedido) {
        PedidoCompletoDTO dto = pedidoDtoService.construirPedidoCompleto(idPedido);
        return ResponseEntity.ok(dto); // 200 OK

    }

    // crear pedido
    @PostMapping("/crear")
    public ResponseEntity<Pedido> crearPedido(@RequestBody PedidoRequestDTO pedidoDTO) {
    // obtener username desde el token 
    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    // llamar al microservicio de usuarios para obtener el id del usuario autenticado
    UsuarioDTO usuario = usuarioClient.obtenerUsuarioPorUsername(username);

    // construir el pedido
    Pedido pedido = new Pedido();
    pedido.setIdEquipo(pedidoDTO.getIdEquipo());
    pedido.setTotal(pedidoDTO.getTotal());
    pedido.setIdUsuario(usuario.getId());

    // usar el método que incluye la lógica del tipo
    Pedido nuevoPedido = pedidoService.crearPedido(pedido, pedidoDTO.getIdTipo());

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
