package com.example.GestionUsuarios_service.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity 
@Table(name = "usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,unique = true)
    private String rut;
    @Column(nullable = false, length = 40)
    private String nombre;
    @Column(nullable = false, length = 20)
    private String apPaterno;
    @Column(length = 20)
    private String apMaterno;
    @Column(nullable = false, length = 80)
    private String correo;
    @Column(nullable = false, length = 15)
    private String telefono;
    @Column(nullable = false, length = 20)
    private String username;
    @Column(nullable = false, length = 30)
    private String contrasena;
    @Column(nullable = false, length = 30)
    private String fechaNacimiento;
}