package com.example.RolesyPermisos.mapper;

import com.example.RolesyPermisos.dto.RolDTO;
import com.example.RolesyPermisos.model.Role;
import com.example.RolesyPermisos.model.Permiso;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RolMapper {

    public static RolDTO toDTO(Role role) {
        // Crear una copia segura de la colección antes de procesarla
        Set<Permiso> permisosCopia = new HashSet<>(role.getPermisos());

        // Transformar la copia en un Set de nombres de permisos
        Set<String> nombresPermisos = permisosCopia.stream()
                                                   .map(Permiso::getNombre)
                                                   .collect(Collectors.toSet());

        return new RolDTO(role.getIdRole(), role.getNombre(), nombresPermisos);
    }
}
