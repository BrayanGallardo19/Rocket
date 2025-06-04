package com.example.GestorPedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipoDTO {
    private Integer idEquipo;
    private String nombre;
    private Double precioVenta;
    private Double precioArriendo;
    private String patente;
    private Integer idModelo;
    private Integer idMarca;

    private ModeloDTO modelo; // para traerlo completo
    private MarcaDTO marca;
}
