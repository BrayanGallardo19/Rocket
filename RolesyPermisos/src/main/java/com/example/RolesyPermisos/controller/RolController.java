package com.example.RolesyPermisos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RolesyPermisos.model.Role;
import com.example.RolesyPermisos.service.RoleService;

@RestController
@RequestMapping("/api/roles1")


public class RolController {
    @Autowired
    private RoleService roleService;    

    @GetMapping
    public List<Role> obtenerTodosLosRoles() {
        return roleService.obtenerTodosLosRoles();
    }
    @GetMapping("/{id}")
    public Role obtenerRolPorId(@PathVariable Integer id) {
        return roleService.obtenerRolPorId(id);
    }

    @GetMapping("/nombre/{nombre}") 
        public List <Role> obtenerRolPorNombre(@PathVariable String nombre) {
        return roleService.obtenerRolPorNombre(nombre);
    }
        @PostMapping
    public Role crearRol(@RequestBody Role role) {
        return roleService.guardarRol(role);
    }

    @PutMapping("/{id}")
    public Role actualizarRol(@PathVariable Integer id, @RequestBody Role roleActualizado) {
        return roleService.actualizarRol(id, roleActualizado);
    }

    @DeleteMapping("/{id}")
    public void eliminarRol(@PathVariable Integer id) {
        roleService.eliminarRol(id);
    }
}
