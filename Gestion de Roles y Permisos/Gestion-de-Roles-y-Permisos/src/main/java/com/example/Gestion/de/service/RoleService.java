package com.example.Gestion.de.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Gestion.de.model.Role;
import com.example.Gestion.de.repository.PermisoRepository;
import com.example.Gestion.de.repository.RoleRepository;

@Service

public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermisoRepository permisoRepository;
    
    public List<Role> obtenerListaRole() {
        return roleRepository.findAll();
    }
    public Role buscarRolePorId(Integer id) {
        return roleRepository.findById(id).orElseThrow(()-> new RuntimeException("Rol no encontrado"));
    }
    public Role modificarRole(Role role) {
        Role rol = roleRepository.findById(role.getIdRol()).orElseThrow(()-> new RuntimeException("No se encontró un rol"));
        return roleRepository.save(role);
    }
}
