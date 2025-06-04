package com.example.GestorPedidos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.GestorPedidos.model.Tipo;
import com.example.GestorPedidos.repository.TipoRepository;

@Service
public class TipoService {
    private final TipoRepository tipoRepository;
 
    public TipoService(TipoRepository tipoRepository) {
        this.tipoRepository = tipoRepository;
    }

    public Tipo obtenerPorId(Integer id) {
        return tipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo con ID " + id + " no encontrado."));
    }

    public List<Tipo> listarTodos() {
        try {
            return tipoRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los tipos: " + e.getMessage());
        }
    }
}
