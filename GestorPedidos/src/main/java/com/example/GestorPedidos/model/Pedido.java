package com.example.GestorPedidos.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pedido")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPedido;

    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "id_equipo")
    private Integer idEquipo;
    @Column(name = "fecha_pedido")
    private LocalDateTime fechaPedido;
    @Column(name = "id_estado")
    private Integer idEstado;
    @Column(name = "total")
    private Double total;

    @ManyToOne
    @JoinColumn(name = "id_tipo")
    private Tipo tipo;

}
