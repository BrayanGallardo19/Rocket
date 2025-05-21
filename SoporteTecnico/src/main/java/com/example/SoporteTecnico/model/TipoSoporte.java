package com.example.SoporteTecnico.model;

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
@Table(name = "TIPOSOPORTE")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TipoSoporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length = 10)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;



}
