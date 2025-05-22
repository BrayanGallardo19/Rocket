package com.example.GestorLogisticayPedidos.model;

import lombok.Data;

@Data
public class Equipo { //clase para la conexion 
    private Integer idEquipo;
    private String nombre;
    private String modelo;
    private String marca;
    private Integer precioVenta;
}
