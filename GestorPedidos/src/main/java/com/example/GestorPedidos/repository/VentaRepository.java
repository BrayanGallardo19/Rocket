package com.example.GestorPedidos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.GestorPedidos.model.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {
    // buscar ventas por id
    List<Venta> findByIdUsuario(Integer idUsuario);

    // buscar ventas por tipo 
    List<Venta> findByTipoIdTipo(Integer idTipo);
      
    }
