package com.example.Model;

import java.lang.reflect.GenericArrayType;
import java.time.LocalDate;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
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
@Table(name = "factura")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Factura {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   
    private Long id;

    private Integer VentaId;

    private LocalDate fechaEmision = LocalDate.now();

    private Double monto;

    @OneToMany(mappedBy = "factura",cascade = CascadeType.ALL)

    private Pago pago;
}
