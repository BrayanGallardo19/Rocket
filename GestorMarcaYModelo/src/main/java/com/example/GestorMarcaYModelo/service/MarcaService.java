package com.example.GestorMarcaYModelo.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.example.GestorMarcaYModelo.model.Marca;
import com.example.GestorMarcaYModelo.repository.MarcaRepository;

@Service
public class MarcaService {
    private final MarcaRepository marcaRepository;

    public MarcaService(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }
    //mostrar todas las marcas
    public List<Marca> obtenerTodasLasMarcas() {
        try {
            return marcaRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al mostrar las marcas"+ e.getMessage(),e); 
        }
    }

    //guardar una marca
    public Marca guardarMarca(Marca marca) {
        if (marca == null) {
            throw new IllegalArgumentException("La marca no puede ser nula");
        }
        if (marca.getNombreMarca() == null || marca.getNombreMarca().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la marca es obligatorio");
        }

        try {
            return marcaRepository.save(marca);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al guardar la marca en la base de datos", e);
        }
    }
}
