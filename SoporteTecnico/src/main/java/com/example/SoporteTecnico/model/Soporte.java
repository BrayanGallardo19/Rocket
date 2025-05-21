package com.example.SoporteTecnico.model;

import java.util.Date;

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
@Table(name = "SOPORTE")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Soporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = true, length = 100)
    private String observacion;
    @Column(nullable = false)
    private Date fecha_soporte;
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

}
