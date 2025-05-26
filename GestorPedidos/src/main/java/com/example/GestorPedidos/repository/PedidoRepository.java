package com.example.GestorPedidos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.GestorPedidos.model.Pedido;



@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    // buscar ventas por id
    List<Pedido> findByIdUsuario(Integer idUsuario);

    // buscar ventas por tipo 
    List<Pedido> findByTipoIdTipo(Integer idTipo);
      
    }
