package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Model.Factura;
import com.example.service.FacturaService;

import jakarta.persistence.Id;

@RestController
@RequestMapping("/api/factura")
public class FacturaController {
    @Autowired
    private FacturaService facturaService;

    @PostMapping
    public ResponseEntity<?> obtenerfactura(@RequestBody Factura factura)  {
        Return facturaService.buscarPorId(id)
            .<ResponseEntity<?>>map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Factura no encontrada"));
        }
}
