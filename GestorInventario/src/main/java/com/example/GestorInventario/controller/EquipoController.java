package com.example.GestorInventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(marcas);//404
        }
        return ResponseEntity.ok(marcas);
    }

    @GetMapping("/modelos/{nombreModelo}")
    public ResponseEntity<?> buscarPorModeloNombre(@PathVariable String nombreModelo) {
        try {
            List<Equipo> inventarios = equipoService.buscarPorModeloNombre(nombreModelo);
            return ResponseEntity.ok(inventarios);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());//404
        }
    }

    @GetMapping("/marcas/{nombreMarca}")
    public ResponseEntity<?> buscarPorMarcaNombre(@PathVariable String nombreMarca) {
        try {
            List<Equipo> inventarios = equipoService.buscarPorMarcaNombre(nombreMarca);
            return ResponseEntity.ok(inventarios);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());//404
        }

    }

    @GetMapping("") // obtener todos los equipos, dejar vacio el path
    public ResponseEntity<List<Equipo>> obtenerTodosLosEquipos() {
        List<Equipo> equipos = equipoService.obtenerTodosLosEquipos();
        if (equipos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(equipos);// 404
        }
        return ResponseEntity.ok(equipos);// 200
    }

    @PostMapping("/agregar")
    public ResponseEntity<?> agregarEquipo(@RequestBody Equipo equipo,
            @RequestParam String nombreModelo,
            @RequestParam String nombreMarca,
            @RequestParam String nombreEstado) {
        try {
            Equipo nuevoEquipo = equipoService.agregarEquipoPorMarcaYModelo(equipo, nombreModelo, nombreMarca, nombreEstado);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEquipo);//201
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());//400

        }
    }

   @PutMapping("/actualizar/{id}")
   public ResponseEntity<?> actualizarEquipo(@PathVariable Integer id, @RequestBody Equipo equipo, @RequestParam String nombreModelo, @RequestParam String nombreMarca, @RequestParam String nombreEstado) {
       try {
           Equipo equipoActualizado = equipoService.actualizarEquipoPorMarcaYModelo(id, equipo, nombreModelo, nombreMarca, nombreEstado);
           return ResponseEntity.ok(equipoActualizado);
       } catch (IllegalArgumentException e) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());//404
       
}   
}
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarEquipo(@PathVariable Integer id) {
        try {
            equipoService.eliminarEquipoPorId(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Equipo eliminado con éxito");//204
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El equipo con id " + id + " no existe");//404
        }
    }
}
