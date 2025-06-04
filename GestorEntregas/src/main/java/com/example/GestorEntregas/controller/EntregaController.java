package com.example.GestorEntregas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import com.example.GestorEntregas.model.Entrega;
import com.example.GestorEntregas.model.Estado;
import com.example.GestorEntregas.service.EntregaService;
import com.example.GestorEntregas.webclient.EstadoClient;

@RestController
@RequestMapping("api/v1/entregas")
public class EntregaController {
    private final EntregaService entregaService;
    private final EstadoClient estadoClient;

    
    public EntregaController(EntregaService entregaService, EstadoClient estadoClient) {
        this.estadoClient = estadoClient;
        this.entregaService = entregaService;
    }

    // metodo para crear una nueva entrega
    @PostMapping("/crear")
    public ResponseEntity<Entrega> crearEntrega(@RequestBody Entrega entrega) {
        if (entrega.getIdEntrega() != null) {
        throw new RuntimeException("No debes enviar el id al crear una nueva entrega");
    }
        Entrega nuevaEntrega = entregaService.crearEntrega(entrega);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaEntrega);// 201 
    }
    
    //metodo para obtener todas las entregas
    @GetMapping("")
    public ResponseEntity<List<Entrega>> obtenerTodasLasEntregas() {
        List<Entrega> entregas = entregaService.obtenerTodasLasEntregas();
        return ResponseEntity.ok(entregas); //200
    }

    //metodo para actualizar el estado de una entrega
    @PutMapping("actualizar/{idEntrega}/estado")
    public ResponseEntity<Entrega> actualizarEstadoEntrega(@PathVariable Integer idEntrega, @RequestBody Map<String, String> body) {
        String nuevoEstado = body.get("nuevoEstado");
        Entrega entregaActualizada = entregaService.actualizarEstadoEntrega(idEntrega, nuevoEstado);
        return ResponseEntity.ok(entregaActualizada);//200
    }

    // metodo para buscar entregas por id del pedido
    /*@GetMapping("/pedido/{idPedido}")
    public ResponseEntity<List<Entrega>> buscarPorPedido(@PathVariable Integer idPedido) {
        
        List<Entrega> entregas = entregaService.buscarPorPedido(idPedido);
        return ResponseEntity.ok(entregas);//200
    }/* */

    // metodo para eliminar entrega por id
    
    @DeleteMapping("/{idEntrega}")
    public ResponseEntity<Void> eliminarEntrega(@PathVariable Integer idEntrega) {
        entregaService.eliminarEntrega(idEntrega);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para obtener todos los estados
    @GetMapping("/estados")
    public ResponseEntity<List<Estado>> getEstados() {
        List<Estado> estados = estadoClient.obtenerEstados();
        return ResponseEntity.ok(estados);
    }

    // Endpoint para obtener un estado por nombre
    @GetMapping("/estado/nombre/{nombre}")
    public ResponseEntity<Estado> getEstadoPorNombre(@PathVariable String nombre) {
        Estado estado = estadoClient.obtenerEstadoPorNombre(nombre);
        return ResponseEntity.ok(estado);
    }
}
