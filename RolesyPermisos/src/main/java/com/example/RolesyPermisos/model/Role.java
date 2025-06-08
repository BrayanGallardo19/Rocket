package com.example.RolesyPermisos.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@Table(name = "Rol")
@NoArgsConstructor
public class Role {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRole;
    // Nombre del rol
    @Column(nullable = false, length = 50)
    private String nombre;
    // conección con permisos
    @ManyToMany
    @JoinTable(
        name = "role_permiso",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "permiso_id")
    )
    // set de permisos que tiene este rol
    private Set<Permiso> permisos = new HashSet<>();

}
