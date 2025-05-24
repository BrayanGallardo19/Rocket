package com.example.GestorInventario.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Modelo {//clase para la conexion
    private Integer idModelo;
    private String nombreModelo;
    private Integer idMarca;
}
