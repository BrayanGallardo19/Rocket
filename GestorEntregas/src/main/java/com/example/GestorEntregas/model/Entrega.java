package com.example.GestorEntregas.model;

import java.time.LocalDate;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Table(name = "entrega")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa una entrega asociada a un pedido.")
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID autogenerado de la entrega", example = "1001", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idEntrega;

    @Column(name = "comentario")
    @Schema(description = "Comentario adicional sobre la entrega", example = "Cliente no estaba disponible.")
    private String comentario;

    @Column(name = "fecha_proceso", nullable = false)
    @Schema(description = "Fecha en la que se procesó la entrega", example = "2025-06-15")
    private LocalDate fechaProceso;

    @Column(name = "fecha_entrega", nullable = false)
    @Schema(description = "Fecha programada para la entrega", example = "2025-06-18")
    private LocalDate fechaEntrega;

    @Schema(description = "ID del pedido asociado a la entrega", example = "501")
    private Integer idPedido;

    @Schema(description = "Dirección exacta donde se realizará la entrega", example = "Av. Siempre Viva 742")
    private String direccionEntrega;

    @Schema(description = "Nombre de la comuna de la entrega", example = "Providencia")
    private String comuna;

    @Schema(description = "Nombre de la ciudad de la entrega", example = "Santiago")
    private String ciudad;

    // Datos del coordinador logístico (usuario responsable)
    @Schema(description = "ID del coordinador logístico responsable de la entrega", example = "25")
    private Integer idCoordinadorLogistico;

    @Transient
    @Schema(
        description = "Datos adicionales del coordinador logístico (obtenidos desde otro microservicio)",
        example = """
        {
            "nombre": "Laura Torres",
            "email": "laura.torres@logistica.com"
        }
        """
    )
    private Map<String, Object> coordinadorLogistico;

    @Schema(description = "Estado actual de la entrega", example = "En camino")
    private String estado;

    @Schema(description = "Usuario que registró o modificó la entrega", example = "admin@empresa.cl")
    private String usuario;
}
