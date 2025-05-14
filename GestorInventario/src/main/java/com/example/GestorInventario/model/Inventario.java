package com.example.GestorInventario.model;

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
@Table(name = "inventario")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEquipo;

    @Column(nullable = false, length = 40)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "modelo_id")
    private Modelo modelo;

    @Column(nullable = false, length = 40)
    private Integer precioVenta;
    @Column(nullable = false, length = 40)
    private Integer precioArriendo;
    @Column(nullable = false, length = 40)
    private String patente;
}
