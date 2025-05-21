package com.example.GestorInventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.GestorInventario.model.Equipo;
import com.example.GestorInventario.model.Marca;
import com.example.GestorInventario.model.Modelo;
import com.example.GestorInventario.repository.EquipoRepository;
import com.example.GestorInventario.repository.MarcaRepository;
import com.example.GestorInventario.repository.ModeloRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EquipoService {
    @Autowired
    private EquipoRepository equipoRepository;
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


    public List<Equipo> obtenerTodosLosEquipos() {
        return equipoRepository.findAll();
    }
    
    // buscar equipos por nombre de modelo
    public List<Equipo> buscarPorModeloNombre(String nombreModelo) {
        if (!modeloRepository.existsByNombreModelo(nombreModelo)) {
            throw new IllegalArgumentException("El modelo" + nombreModelo + " no existe");
        }
        return equipoRepository.findByModeloNombreModelo(nombreModelo);
    }

    // buscar equipos por nombre de marca relacionada al modelo
    public List<Equipo> buscarPorMarcaNombre(String nombreMarca) {
        if (!marcaRepository.existsByNombreMarca(nombreMarca)) {
            throw new IllegalArgumentException("La marca " + nombreMarca + " no existe");
        }
        return equipoRepository.findByModeloMarcaNombreMarca(nombreMarca);
    }


    // metodo para agregar un nuevo equipo al inventario
    public Equipo agregarEquipoPorMarcaYModelo(Equipo equipo, String nombreModelo, String nombreMarca) {  
        // validar que el modelo y la marca existan
        Marca marca = marcaRepository.findByNombreMarca(nombreMarca)
            .orElseThrow(() -> new IllegalArgumentException("La marca " + nombreMarca + " no existe"));

        Modelo modelo = modeloRepository.findByNombreModeloAndMarca(nombreModelo,marca)// valida que el modelo existe y pertenece a la marca indicada
                .orElseThrow(() -> new IllegalArgumentException("El modelo " + nombreModelo + " no existe")); 

        equipo.setModelo(modelo); // asignar el modelo al equipo

        return equipoRepository.save(equipo);
    }


}
