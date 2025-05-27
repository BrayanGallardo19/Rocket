package com.example.RolesyPermisos.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RolesyPermisos.model.Permiso;
import com.example.RolesyPermisos.service.PermisoService;

@RestController
@RequestMapping("/api/v1/permisos")
public class PermisoController {
    private final PermisoService permisoService;

    public PermisoController(PermisoService permisoService) {
        this.permisoService = permisoService;
    }
    
    //mostrar todos los permisos
    @GetMapping("")
    public ResponseEntity<List<Permiso>> obtenerTodosLosPermisos() {
        List<Permiso> permisos = permisoService.obtenerTodosLosPermisos();
        return ResponseEntity.ok(permisos);
    }

    //mostrar permiso por id
    @GetMapping("/{id}")
    public ResponseEntity<Permiso> obtenerPermisoPorId(@PathVariable Integer id) {
        Permiso permiso = permisoService.obtenerPermisoPorId(id);
        return ResponseEntity.ok(permiso);
    }

    //modificar un permiso por id
    @PatchMapping("/modificar/{id}")
    public ResponseEntity<Permiso> modificarPermiso(@PathVariable Integer id, @RequestBody Permiso permiso) {
        Permiso permisoExistente = permisoService.obtenerPermisoPorId(id);
        permiso.setIdPermiso(permisoExistente.getIdPermiso());
        Permiso actualizado = permisoService.guardarPermiso(permiso);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar un permiso por id
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPermiso(@PathVariable Integer id) {
        permisoService.eliminarPermisoPorId(id);
        return ResponseEntity.ok("Permiso eliminado correctamente");
        }
    


}
