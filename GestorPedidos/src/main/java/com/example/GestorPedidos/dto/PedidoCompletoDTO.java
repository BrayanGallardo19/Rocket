package com.example.GestorPedidos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoCompletoDTO {
    private Integer idPedido;
    private LocalDateTime fecha;
    private Double total;

    private UsuarioDTO usuario;
    private EquipoDTO equipo;
    private MarcaDTO marca;
    private ModeloDTO modelo;
    private TipoDTO tipo;
}
