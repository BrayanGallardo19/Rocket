package com.example.Autenticacion.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "ap_paterno", nullable = false)
    private String apPaterno;

    @Column(name = "ap_materno") // Puede ser null
    private String apMaterno;

    @Column(name = "correo", nullable = false)
    private String correo;

    @Column(name = "rut", nullable = false)
    private String rut;

    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "fecha_nacimiento", nullable = false)
    private String fechaNacimiento; // Considera LocalDate si usas DATE en BD.

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "contrasena", nullable = false)
    private String password;
}
