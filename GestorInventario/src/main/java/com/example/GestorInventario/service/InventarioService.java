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
    @Autowired
    private InventarioRepository inventarioRepository;
    @Autowired
    private MarcaRepository marcaRepository;
    @Autowired
    private ModeloRepository modeloRepository;

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
        return inventarioRepository.findByModeloNombreModelo(nombreModelo);
    }

    // buscar equipos por nombre de marca relacionada al modelo
    public List<Inventario> buscarPorMarcaNombre(String nombreMarca) {
        if (!marcaRepository.existsByNombreMarca(nombreMarca)) {
            throw new IllegalArgumentException("La marca " + nombreMarca + " no existe");
        }
        return inventarioRepository.findByModeloMarcaNombreMarca(nombreMarca);
    }


    // metodo para agregar un nuevo equipo al inventario
    public Inventario agregarEquipoPorNombre(Inventario equipo, String nombreModelo, String nombreMarca) {  
        // validar que el modelo y la marca existan
        Modelo modelo = modeloRepository.findByNombreModelo(nombreModelo)
                .orElseThrow(() -> new IllegalArgumentException("El modelo " + nombreModelo + " no existe")); 
        equipo.setModelo(modelo); // asignar el modelo al equipo
        return inventarioRepository.save(equipo);
    }


}
