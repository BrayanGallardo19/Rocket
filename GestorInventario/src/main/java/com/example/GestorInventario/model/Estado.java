package com.example.GestorInventario.model;


import io.swagger.v3.oas.annotations.media.Schema;
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
@Table(name = "ESTADO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos del estado de un equipo")
public class Estado {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID autoincremental del estado")
    private Integer idEstado;
    @Schema(description = "Nombre del estado del equipo")
    @Column(nullable = false)
    private String nombreEstado; 

   
}
