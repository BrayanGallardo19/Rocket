package com.example.RolesyPermisos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RolesyPermisos.model.Role;
import com.example.RolesyPermisos.repository.RoleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class RoleService {
    @Autowired
    private  RoleRepository roleRepository;
    
    public List<Role> obtenerTodosLosRoles() {
        return roleRepository.findAll();
    }

    public Role obtenerRolPorId(Integer id) {
        return roleRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + id));
    }

    public List <Role>obtenerRolPorNombre(String nombre) {
        if (!roleRepository.existsByNombre(nombre)) {
            throw new IllegalArgumentException("El rol " + nombre + " no existe");
        }
         //return roleRepository.findByNombre(nombre);
        return roleRepository.findByNombre(nombre);
    }


    //public Role guardarRol(Role role) {
        //return roleRepository.save(role);
    
}
