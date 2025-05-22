package com.example.SoporteTecnico.controller;

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
import org.springframework.web.bind.annotation.RestController;
import com.example.SoporteTecnico.model.Soporte;
import com.example.SoporteTecnico.service.SoporteTecnicoService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/v1/soportes")
public class SoporteController {

    @Autowired
    private SoporteTecnicoService sopService;

    @PostMapping
    public ResponseEntity<?> crearSoporte(@RequestBody Soporte nuevoSoporte) {
        try {
            Soporte soporte = sopService.createSoporte(nuevoSoporte);
            return ResponseEntity.status(HttpStatus.CREATED).body(soporte);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al crear soporte: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> editarSoporte(@PathVariable Integer id, @RequestBody Soporte soporte) {
        try {
            Soporte actualizado = sopService.updateSoporte(id, soporte);
            return ResponseEntity.ok(actualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al actualizar soporte: " + e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Soporte>> listarSoportes() {
        List<Soporte> lista = sopService.getSoportes();
        if (lista.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarSoporte(@PathVariable Integer id) {
        boolean deleted = sopService.deleteSoporteById(id);
        if (deleted) {
            return ResponseEntity.ok("Soporte eliminado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Soporte no encontrado.");
        }
    }
}
