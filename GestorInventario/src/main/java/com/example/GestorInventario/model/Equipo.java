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
@Table(name = "equipo")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEquipo;

    @Column(nullable = false, length = 40)
    private String nombre;

    @Column(nullable = false, length = 40)
    private Double precioVenta;

    @Column(nullable = false, length = 40)
    private Double precioArriendo;

    @Column(nullable = false, length = 40)
    private String patente;

    private Integer idModelo;

    private Integer idMarca;
 
    @ManyToOne
    @JoinColumn(name = "id_estado")
    private Estado estado;

    // constructor para crear un equipo sin contar la idauto
    public Equipo(String nombre, Double precioVenta, Double precioArriendo, String patente, Integer idModelo, Integer idMarca, Estado estado) {
    this.nombre = nombre;
    this.precioVenta = precioVenta;
    this.precioArriendo = precioArriendo;
    this.patente = patente;
    this.idModelo = idModelo;
    this.idMarca = idMarca;
    this.estado = estado;
}
}
