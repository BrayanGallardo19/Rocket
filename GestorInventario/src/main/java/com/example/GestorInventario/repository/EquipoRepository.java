package com.example.GestorInventario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.GestorInventario.model.Equipo;
@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Integer> {
    //busca una lista de registros de la entidad equipo cuyo modelo tenga un nombre especifico
    List<Equipo> findByModeloNombreModelo(String nombreModelo);
    //busca una lista de equipos que esten asociados a un modelo
    //el modelo tiene una marca asociada
    List<Equipo> findByModeloMarcaNombreMarca(String nombreMarca);
    

}
