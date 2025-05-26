package com.example.GestorMarcaYModelo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.GestorMarcaYModelo.model.Modelo;
import com.example.GestorMarcaYModelo.repository.ModeloRepository;
@Service
public class ModeloService {
    private final ModeloRepository modeloRepository;

    public ModeloService(ModeloRepository modeloRepository) {
        this.modeloRepository = modeloRepository;
    }

    public List<Modelo> listarModelos() {
        return modeloRepository.findAll();
    }
    // metodo para obtener un modelo por id
    public Modelo obtenerModeloPorId(Integer id) {
        return modeloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Modelo no encontrado"));
    }
    // mostrar modelos con marca
    public List<Modelo> listarModelosPorMarca(Integer idMarca) {
        return modeloRepository.findByMarcaIdMarca(idMarca);
    }
    // metodo para guardar un modelo
    public Modelo guardarModelo(Modelo modelo) {
        return modeloRepository.save(modelo);
    }
}
