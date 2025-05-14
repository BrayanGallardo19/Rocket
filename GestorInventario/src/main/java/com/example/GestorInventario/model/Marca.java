package com.example.GestorInventario.model;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Table(name = "marca")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Marca {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer idMarca;

    @Column(nullable = false, length = 40)
    private String nombreMarca;

    @OneToMany(mappedBy = "marca", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Modelo> modelos;

}
