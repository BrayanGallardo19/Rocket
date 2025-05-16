package com.example.SoporteTecnico.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ticket")
public class Ticket (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Date fecha_creacion;
    @Column(nullable = true)
    private Date fecha_cierre;
    @Column(nullable = false,length = 200)
    private String descripcion;
    
    @OneToMany
    @JoinColumn(name = "tiposoporte_id")
    private TipoSoporte tipoSoporte;

    @
    private Usuario usuario;

    private Estado estado;

)