package com.example.GestorPedidos.model;

import lombok.Data;

@Data
//clase para la conexion con el microservicio de inventario
public class Equipo { 
    private Integer idEquipo;
    private String nombre;
    private Modelo modelo;
    private Marca marca;
    private Integer precioVenta;
}
