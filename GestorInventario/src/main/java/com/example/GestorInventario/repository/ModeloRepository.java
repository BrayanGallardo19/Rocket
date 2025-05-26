package com.example.GestorInventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.GestorInventario.model.Modelo;

public interface ModeloRepository extends JpaRepository<Modelo, Integer> {

}
