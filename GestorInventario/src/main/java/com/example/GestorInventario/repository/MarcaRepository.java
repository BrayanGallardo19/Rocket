package com.example.GestorInventario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.GestorInventario.model.Marca;
@Repository
public interface MarcaRepository extends JpaRepository<Marca, Integer> {
    boolean existsByNombreMarca(String nombreMarca); 

    // buscar un unico modelo por nombre 
    Optional<Marca> findByNombreMarca(String nombreMarca); 
    
}
