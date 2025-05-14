package com.example.GestorInventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.GestorInventario.model.Inventario;
import com.example.GestorInventario.model.Marca;
import com.example.GestorInventario.model.Modelo;
import com.example.GestorInventario.repository.InventarioRepository;
import com.example.GestorInventario.repository.MarcaRepository;
import com.example.GestorInventario.repository.ModeloRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class InventarioService {
    private final InventarioRepository inventarioRepository;
    private final MarcaRepository marcaRepository;
    private final ModeloRepository modeloRepository;

    @Autowired
    public InventarioService(InventarioRepository inventarioRepository,
                             MarcaRepository marcaRepository,
                             ModeloRepository modeloRepository) {
        this.inventarioRepository = inventarioRepository;// inicializa el repositorio
        this.marcaRepository = marcaRepository;
        this.modeloRepository = modeloRepository;
    } 
      // metodo para obtener todos los modelos
    public List<Modelo> obtenerTodosLosModelos() {
        return modeloRepository.findAll();
    }

    // metodo para obtener todas las marcas
    public List<Marca> obtenerTodasLasMarcas() {
        return marcaRepository.findAll();
    }
    // buscar equipos por nombre de modelo
    public List<Inventario> buscarPorModeloNombre(String nombreModelo) {
        if (!modeloRepository.existsByNombreModelo(nombreModelo)) {
            throw new IllegalArgumentException("El modelo" + nombreModelo + " no existe");
        }
        return inventarioRepository.findByModeloMarcaNombreMarca(nombreModelo);
    }

    // buscar equipos por nombre de marca relacionada al modelo
    public List<Inventario> buscarPorMarcaNombre(String nombreMarca) {
        if (!marcaRepository.existsByNombreMarca(nombreMarca)) {
            throw new IllegalArgumentException("La marca " + nombreMarca + " no existe");
        }
        return inventarioRepository.findByModeloMarcaNombreMarca(nombreMarca);
    }

}
