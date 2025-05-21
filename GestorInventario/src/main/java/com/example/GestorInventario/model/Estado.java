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


@Entity
@Table(name = "ESTADO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Estado {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEstado;

    @Column(nullable = false, length = 40)
    private String nombreEstado; 

    @OneToMany(mappedBy = "estado", cascade =  CascadeType.ALL)
    @JsonIgnore
    private List<Equipo> equipos; //lista de equipos 
}
