package com.example.GestorPedidos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.GestorPedidos.model.Pedido;



@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
   //buscar pedidos por id
    Optional<Pedido> findById(Integer idPedido);

    //buscar pedidos por usuario id
    List<Pedido> findByIdUsuario(Integer idUsuario);
    }
