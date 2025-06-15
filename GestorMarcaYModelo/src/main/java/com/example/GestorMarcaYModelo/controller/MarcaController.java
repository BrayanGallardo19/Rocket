package com.example.GestorMarcaYModelo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.GestorMarcaYModelo.model.Marca;
import com.example.GestorMarcaYModelo.service.MarcaService;
@RestController
@RequestMapping("api/v1/marcas")
public class MarcaController {
    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }
    //mostrar todas las marcas
    @GetMapping("")
    public ResponseEntity<List<Marca>> listarMarcas() {
        return ResponseEntity.ok(marcaService.obtenerTodasLasMarcas());
    }

    // obtener una marca por id
    @GetMapping("/{id}")
    public ResponseEntity<Marca> obtenerMarcaPorId(@PathVariable Integer id) {
        Marca marca = marcaService.obtenerMarcaPorId(id);
        return ResponseEntity.ok(marca);
    }
    //crear una marca
    @PostMapping("/crear")
    public ResponseEntity<Marca> crearMarca(@RequestBody Marca marca) {
        Marca creada = marcaService.guardarMarca(marca);
        return ResponseEntity.ok(creada);
    }

    @PutMapping("modificar/{id}")
    public ResponseEntity<Marca> modificarMarca(@PathVariable Integer id, @RequestBody Marca marca) {
        Marca marcaExistente = marcaService.obtenerMarcaPorId(id);
        if (marcaExistente == null) {
            return ResponseEntity.notFound().build();
        }
        marca.setIdMarca(id);
        Marca actualizada = marcaService.guardarMarca(marca);
        return ResponseEntity.ok(actualizada);
    }

}
