package com.example.PagoFactura.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Equipo {
    private Integer idEquipo;
    private String nombre;
    private Double precioVenta;
    private Double precioArriendo;
    private String patente;
    private Integer idModelo;
    private Integer idMarca;

}
