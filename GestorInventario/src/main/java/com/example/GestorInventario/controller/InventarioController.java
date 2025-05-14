package com.example.GestorInventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.GestorInventario.model.Inventario;
import com.example.GestorInventario.model.Marca;
import com.example.GestorInventario.model.Modelo;
import com.example.GestorInventario.service.InventarioService;

@RestController
@RequestMapping("api/v1/inventario")
public class InventarioController {
    private final InventarioService inventarioService;

    @Autowired
    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService; // inicializa el servicio
    }
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
    public List<Inventario> buscarPorModeloNombre(@RequestParam String nombreModelo) {
        try {
            return inventarioService.buscarPorModeloNombre(nombreModelo);
        } catch (IllegalArgumentException e) {
            return inventarioService.buscarPorModeloNombre(nombreModelo);
        }
        
    }

    @GetMapping("/marcas/{nombreMarca}")
    public ResponseEntity<?> buscarPorMarcaNombre(@PathVariable String nombreMarca) {
        try {
            return ResponseEntity.ok(inventarioService.buscarPorMarcaNombre(nombreMarca));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
       
    }
}
