package com.example.PagoFactura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PagoFactura.Model.Pago;
import com.example.PagoFactura.service.PagoService;

@RestController
@RequestMapping("/apiv1/pagos")

public class PagoController {
    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }
    // Método para mostrar todos los pagos
    @PostMapping
    public ResponseEntity<Pago> RegistrarPago(@RequestBody Pago pago) {
        return ResponseEntity.ok(pagoService.RegistrarPago(pago));
    }
    // Método para crear un pago
    @GetMapping("/{id}")
    public Pago obtenerPagoPor(@PathVariable Integer id) {
        return pagoService.obtenerPagoPorId(id);

    }

}
