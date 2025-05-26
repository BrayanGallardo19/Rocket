package com.example.GestorInventario.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Modelo {//clase para la conexion
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idModelo;

    private String nombre;
    private Integer idMarca;
}
