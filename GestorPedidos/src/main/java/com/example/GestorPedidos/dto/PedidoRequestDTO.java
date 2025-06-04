package com.example.GestorPedidos.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequestDTO {
    private Integer idEquipo;
    private Integer idTipo;
    private Double total;
}
