package com.example.GestorLogisticayPedidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.GestorLogisticayPedidos.model.Venta;
import com.example.GestorLogisticayPedidos.service.VentaService;

@RestController
@RequestMapping("/api/v1/ventas")
public class VentaController {
    @Autowired
    private VentaService ventaService;

    // crear venta
    @PostMapping
    public ResponseEntity<Venta> crearVenta(@RequestBody Venta venta) {
        Venta ventaGuardada = ventaService.guardarVenta(venta);
        return ResponseEntity.ok(ventaGuardada);
    }

    // buscar venta por id
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarVentaPorId(@PathVariable Integer id) {
        try {
            return ventaService.buscarVentaPorId(id)
                    .<ResponseEntity<?>>map(venta -> ResponseEntity.ok(venta)) // se usa ? ya que el metodo es optional
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venta no encontrada"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar la venta");
        }
    }

    // mostrar todas las ventas
    @GetMapping("")
    public ResponseEntity<?> mostrarTodasLasVentas() {
        try {
            return ResponseEntity.ok(ventaService.mostrarTodasLasVentas());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al mostrar las ventas");
        }
    }

    // buscar ventas por id
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> buscarVentasPorIdUsuario(@PathVariable Integer idUsuario) {
        try {
            return ResponseEntity.ok(ventaService.buscarVentasPorIdUsuario(idUsuario));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar las ventas");
        }
    }

    // eliminar venta por id
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarVentaPorId(@PathVariable Integer id) {
        try {
            ventaService.eliminarVentaPorId(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Venta eliminada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la venta");
        }
    }
}
