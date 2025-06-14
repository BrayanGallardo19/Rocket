package com.example.RolesyPermisos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RolesyPermisos.dto.RolDTO;
import com.example.RolesyPermisos.model.Role;
import com.example.RolesyPermisos.service.RoleService;

@RestController
@RequestMapping("/api/v1/roles")


public class RolController {
    @Autowired
    private RoleService roleService;    

    // mostrar todos los roles
    @GetMapping
    public List<RolDTO> obtenerTodosLosRoles() {
        return roleService.obtenerTodosLosRoles();
    }

    // obtener rol por id
    @GetMapping("/{id}")
    public Role obtenerRolPorId(@PathVariable Integer id) {
        return roleService.obtenerRolPorId(id);
    }

    // crear roles
    @PostMapping ("/crear")
    public Role crearRol(@RequestBody Role role) {
        return roleService.guardarRol(role);
    }
     // actualizar rol por id
    @PutMapping("/actualizar/{id}")
    public Role actualizarRol(@PathVariable Integer id, @RequestBody Role roleActualizado) {
        return roleService.actualizarRol(id, roleActualizado);
    }
    // eliminar rol por id
    @DeleteMapping("/eliminar/{id}")
    public void eliminarRol(@PathVariable Integer id) {
        roleService.eliminarRol(id);
    }

    // obtener rol por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Role> obtenerRolPorNombre(@PathVariable String nombre) {
        try {
            Role role = roleService.obtenerRolPorNombre(nombre);
            return ResponseEntity.ok(role);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
     }
}
