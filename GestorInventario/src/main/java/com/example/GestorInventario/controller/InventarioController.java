package com.example.GestorInventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.GestorInventario.model.Inventario;
import com.example.GestorInventario.model.Marca;
import com.example.GestorInventario.model.Modelo;
import com.example.GestorInventario.service.InventarioService;

@RestController
@RequestMapping("api/v1/inventario")
public class InventarioController {
    @Autowired
    private InventarioService inventarioService;

    
    // obtener todos los modelos
    @GetMapping("/modelos")
    public ResponseEntity<List<Modelo>> obtenerTodosLosModelos() {
        List<Modelo> modelos = inventarioService.obtenerTodosLosModelos();
        if (modelos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(modelos);
        }
        return ResponseEntity.ok(modelos);
    }

    // obtener todas las marcas
    @GetMapping("/marcas")
    public ResponseEntity<List<Marca>> obtenerTodasLasMarcas() {
        List<Marca> marcas = inventarioService.obtenerTodasLasMarcas();
        if (marcas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(marcas);
        }
        return ResponseEntity.ok(marcas);
    }

    @GetMapping("/modelos/{nombreModelo}")
    public ResponseEntity<?> buscarPorModeloNombre(@PathVariable String nombreModelo) {
        try {
            List<Inventario> inventarios = inventarioService.buscarPorModeloNombre(nombreModelo);
            return ResponseEntity.ok(inventarios);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/marcas/{nombreMarca}")
    public ResponseEntity<?> buscarPorMarcaNombre(@PathVariable String nombreMarca) {
        try {
            List<Inventario> inventarios = inventarioService.buscarPorMarcaNombre(nombreMarca);
            return ResponseEntity.ok(inventarios);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
       
    }
}
