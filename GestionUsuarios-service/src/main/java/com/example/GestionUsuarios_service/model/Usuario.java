package com.example.GestionUsuarios_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private Integer id;
    private String rut;
    private String nombre;
    private String apPaterno;
    private String apMaterno;
    private String correo;
    private String telefono;
    private String contrasena;
    private String fechaNacimiento;
    private boolean activo;
}
