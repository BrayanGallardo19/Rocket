package com.example.GestorInventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.GestorInventario.model.Equipo;
import com.example.GestorInventario.model.Marca;
import com.example.GestorInventario.model.Modelo;
import com.example.GestorInventario.service.EquipoService;




@RestController
@RequestMapping("api/v1/equipos")
public class EquipoController {
    @Autowired
    private EquipoService equipoService;

    
    // obtener todos los modelos
    @GetMapping("/modelos")
    public ResponseEntity<List<Modelo>> obtenerTodosLosModelos() {
        List<Modelo> modelos = equipoService.obtenerTodosLosModelos();
        if (modelos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(modelos);
        }
        return ResponseEntity.ok(modelos);
    }

    // obtener todas las marcas
    @GetMapping("/marcas")
    public ResponseEntity<List<Marca>> obtenerTodasLasMarcas() {
        List<Marca> marcas = equipoService.obtenerTodasLasMarcas();
        if (marcas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(marcas);
        }
        return ResponseEntity.ok(marcas);
    }

    @GetMapping("/modelos/{nombreModelo}")
    public ResponseEntity<?> buscarPorModeloNombre(@PathVariable String nombreModelo) {
        try {
            List<Equipo> inventarios = equipoService.buscarPorModeloNombre(nombreModelo);
            return ResponseEntity.ok(inventarios);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/marcas/{nombreMarca}")
    public ResponseEntity<?> buscarPorMarcaNombre(@PathVariable String nombreMarca) {
        try {
            List<Equipo> inventarios = equipoService.buscarPorMarcaNombre(nombreMarca);
            return ResponseEntity.ok(inventarios);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
       
    }

    @GetMapping("/equipos")
    public ResponseEntity<List<Equipo>> obtenerTodosLosEquipos() {
        List<Equipo> equipos = equipoService.obtenerTodosLosEquipos();
        if (equipos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(equipos);
        }
        return ResponseEntity.ok(equipos);
    }

    @PostMapping("/agregar")
    public ResponseEntity<Equipo> agregarEquipo(@RequestBody Equipo equipo,
            @RequestParam String nombreModelo,
            @RequestParam String nombreMarca) {
        try {
            Equipo nuevoEquipo = equipoService.agregarEquipoPorMarcaYModelo(equipo, nombreModelo, nombreMarca);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEquipo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

   }
}
}
