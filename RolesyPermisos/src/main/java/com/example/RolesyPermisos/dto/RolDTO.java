package com.example.RolesyPermisos.dto;

import java.util.Set;

public class RolDTO {
    private Integer id;
    private String nombre;
    private Set<String> permisos;

    public RolDTO() {}

    public RolDTO(Integer id, String nombre, Set<String> permisos) {
        this.id = id;
        this.nombre = nombre;
        this.permisos = permisos;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Set<String> getPermisos() {
        return permisos;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPermisos(Set<String> permisos) {
        this.permisos = permisos;
    }
}
