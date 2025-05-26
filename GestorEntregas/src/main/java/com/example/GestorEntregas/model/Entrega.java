package com.example.GestorEntregas.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "entrega")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEntrega;

    @Column(name = "comentario", nullable = true)
    private String comentario;

    @Column(name = "fecha_proceso", nullable = false)
    private LocalDate fechaProceso;

    @Column(name = "fecha_entrega", nullable = false)
    private LocalDate fechaEntrega;

    private Integer idPedido;

    // variable de otro microservicio
    private String estado;
}
