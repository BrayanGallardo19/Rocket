package com.example.GestorEntregas.model;

import java.time.LocalDate;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "entrega")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos de la Entrega")
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

    private String direccionEntrega;
    private String comuna;
    private String ciudad;
    // datos del transportista
    private Integer idCoordinadorLogistico;
    @Transient
    private Map<String, Object> coordinadorLogistico; // datos completos del usuario coordinador
    // variable de otro microservicio
    private String estado;
    private String usuario;
}
