package com.example.PagoFactura.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Venta {

    private Integer idVenta;
    private Integer idUsuario;
    private LocalDate fechaSolicitud = LocalDate.now(); // fecha actual
    private Double montoTotal;
    private LocalDate fechaAprobacion;
    private List<DetalleVenta> detalleVentas = new ArrayList<>();

    // Constructor, getters, setters, etc. pueden ser generados por Lombok

}
