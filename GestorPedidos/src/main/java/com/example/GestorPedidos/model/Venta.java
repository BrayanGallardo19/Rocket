package com.example.GestorPedidos.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
@Table(name = "venta")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Integer idVenta;
    
    @JoinColumn(name = "usuario_id_usuario", nullable = true)
    private Integer idUsuario;

    @ManyToOne  
    @JoinColumn(name = "tipo_id_tipo")
    private Tipo tipo;

    @Column(name = "fec_solicitud" ,nullable = false)
    private LocalDate fechaSolicitud = LocalDate.now();// fecha actual

    @Column(name = "monto_total")
    private Double montoTotal;

    @Column(name = "fec_aprob")
    private LocalDate fechaAprobacion;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    //para eliminar los detalles de la venta al eliminar la venta
    private List<DetalleVenta> detalleVentas = new ArrayList<>();
}
