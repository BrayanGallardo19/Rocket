package com.example.GestorPedidos.model;

import lombok.Data;

@Data
public class Equipo { //clase para la conexion 
    private Integer idEquipo;
    private String nombre;
    private Modelo modelo;
    private Marca marca;
    private Integer precioVenta;
}
