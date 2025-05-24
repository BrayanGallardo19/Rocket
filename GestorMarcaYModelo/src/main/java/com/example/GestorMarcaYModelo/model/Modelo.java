package com.example.GestorMarcaYModelo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
@Table(name = "modelo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Modelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idModelo;

    @Column(name = "nombre", nullable = false)
    private String nombreModelo;

    @ManyToOne
    @JoinColumn(name = "id_marca", nullable = false)
    @JsonIgnoreProperties("modelos") // evita la recursividad infinita
    private Marca marca;

     // JSON incluya el idMarca
    @JsonProperty("idMarca")
    public Integer getIdMarca() {
        return marca != null ? marca.getIdMarca() : null;
    }
}
