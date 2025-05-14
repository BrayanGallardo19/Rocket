package com.example.GestorInventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.GestorInventario.model.Modelo;
@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Integer> {
    boolean existsByNombreModelo(String nombreModelo);

}
