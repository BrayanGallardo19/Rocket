package com.example.SoporteTecnico.model;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "ticket")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    
    @ManyToOne
    @JoinColumn(name = "tiposoporte_id")
    private TipoSoporte tipoSoporte;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @OneToMany
    @JoinColumn(name = "estado_id")
    private Estado estado;

    @OneToMany(mappedBy = "soporte", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Soporte> soporte;

)