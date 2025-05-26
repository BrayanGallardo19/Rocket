package com.example.GestorInventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.GestorInventario.model.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Integer> {

}
