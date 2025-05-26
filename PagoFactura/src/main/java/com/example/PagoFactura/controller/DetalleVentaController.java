package com.example.PagoFactura.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PagoFactura.Model.DetalleVenta;
import com.example.PagoFactura.service.DetalleVentaService;

@RestController
@RequestMapping("/api/detalle-venta")

public class DetalleVentaController {

    private final DetalleVentaService detalleVentaService;

    public DetalleVentaController(DetalleVentaService detalleVentaService) {
        this.detalleVentaService = detalleVentaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleVenta> obtenerDetallePorId(@PathVariable Integer id) {
        DetalleVenta detalleVenta = detalleVentaService.obtenerDetalleVentaporId(id);
        return ResponseEntity.ok(detalleVenta);
    }

    // Método para modificar un detalle de venta por ID
    @PutMapping("/modificar{id}")
    public ResponseEntity<DetalleVenta> modificarDetalleVenta(@PathVariable Integer id, @RequestBody DetalleVenta detalleVenta) {
        DetalleVenta detalleExistente = detalleVentaService.obtenerDetalleVentaporId(id);
        detalleVenta.setCantidad(detalleExistente.getIdDetalle());
        DetalleVenta actualizado = detalleVentaService.crearDetalleVenta(detalleVenta);
        return ResponseEntity.ok(actualizado);
    }

}
