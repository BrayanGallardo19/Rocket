package com.example.GestorLogisticayPedidos.model;

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
@Table(name = "solicitud")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sol")
    private Integer idSolicitud;

    @Column(name = "f_sol")
    private LocalDateTime fechaSolicitud;

    @ManyToOne  
    @JoinColumn(name = "id_tipo", referencedColumnName = "id_tipo")
    private Tipo tipo;

    @Column(name = "id_equipo")
    private Integer idEquipo;

    @Column(name = "monto_total")
    private Double montoTotal;

    @Column(name = "f_aprob")
    private LocalDateTime fechaAprobacion;

    @Column(name = "id_direccion")
    private Integer idDireccion;
}
