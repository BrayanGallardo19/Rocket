package com.example.PagoFactura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PagoFactura.Model.Factura;
import com.example.PagoFactura.service.FacturaService;

import jakarta.persistence.Id;

@RestController
@RequestMapping("/api/v1/facturas")
public class FacturaController {
    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }
    // Método para crear una factura
    @GetMapping("/{id}")
    public ResponseEntity<Factura> obtenerFacturaPorId(@PathVariable Integer id) { {
        Factura factura = facturaService.obtenerFacturaPorId(id);
        return ResponseEntity.ok(factura);
            
        }

     }
}
