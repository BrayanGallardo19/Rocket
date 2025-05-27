package com.example.GestorMarcaYModelo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.GestorMarcaYModelo.model.Modelo;
import com.example.GestorMarcaYModelo.service.ModeloService;

@RestController
@RequestMapping("api/v1/modelos")
public class ModeloController {
    private final ModeloService modeloService;

    public ModeloController(ModeloService modeloService) {
        this.modeloService = modeloService;
    }

    // muestra un modelo por id
    @GetMapping("/{id}")
    public ResponseEntity<Modelo> obtenerModeloPorId(@PathVariable Integer id) {
        Modelo modelo = modeloService.obtenerModeloPorId(id);
        if (modelo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(modelo);
    }

    // muestra todos los modelos
    @GetMapping("")
    public ResponseEntity<List<Modelo>> listarModelos() {
        return ResponseEntity.ok(modeloService.listarModelos());
    }

    // muestra los modelos por marca
    @GetMapping("/marca/{id}")
    public ResponseEntity<List<Modelo>> listarModelosPorMarca(@PathVariable Integer id) {
        return ResponseEntity.ok(modeloService.listarModelosPorMarca(id));
    }

    @PostMapping("/crear")
    public ResponseEntity<Modelo> crearModelo(@RequestBody Modelo modelo) {
        Modelo creado = modeloService.guardarModelo(modelo);
        return ResponseEntity.ok(creado);
    }
}
