package com.example.GestorInventario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.GestorInventario.model.Marca;
import com.example.GestorInventario.model.Modelo;
@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Integer> {
    boolean existsByNombreModelo(String nombreModelo);

    // buscar un unico modelo por nombre 
    Optional<Modelo> findByNombreModelo(String nombreModelo);

    // buscar un unico modelo por nombre y marca
    Optional<Modelo> findByNombreModeloAndMarca(String nombreModelo,Marca marca);
}
