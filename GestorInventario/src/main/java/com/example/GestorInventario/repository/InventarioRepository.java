package com.example.GestorInventario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.GestorInventario.model.Inventario;
@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Integer> {
    //buscar por nombre de modelo
    List<Inventario> findByModeloNombreModelo(String nombreModelo);
    //buscar por nombre de marca
    List<Inventario> findByModeloMarcaNombreMarca(String nombreMarca);
    

}
