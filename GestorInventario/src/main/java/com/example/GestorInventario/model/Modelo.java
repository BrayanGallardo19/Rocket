package com.example.GestorInventario.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "MODELO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Modelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idModelo;

    @Column(nullable = false, length = 40)
    private String nombreModelo;

    @ManyToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;


    @OneToMany(mappedBy = "modelo", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Inventario> equipo;
}
