package com.example.GestorInventario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.GestorInventario.model.Equipo;
import com.example.GestorInventario.model.Estado;
import com.example.GestorInventario.model.Marca;
import com.example.GestorInventario.repository.EquipoRepository;
import com.example.GestorInventario.repository.EstadoRepository;
import com.example.GestorInventario.webclient.MarcaModeloClient;

@Service
public class EquipoService {
    private final EquipoRepository equipoRepository;
    private final MarcaModeloClient marcaClient;
    private final EstadoRepository estadoRepository;

    public EquipoService(EquipoRepository equipoRepository, MarcaModeloClient marcaClient, EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
        this.equipoRepository = equipoRepository;
        this.marcaClient = marcaClient; 
    }


    // metodo para mostrar todos los equipos
    public List<Equipo> mostrarTodosLosEquipos() {
        try {
            return equipoRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los equipos: " + e.getMessage());
        }
    }
    // metodo para obtener la marca de un equipo
    public Marca obtenerMarcaDelEquipo(Integer idEquipo) {
    Equipo equipo = equipoRepository.findById(idEquipo)
            .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
    return marcaClient.getMarcaById(equipo.getIdMarca());
}

    //metodo para buscar un equipo por estado
    public List<Equipo> buscarEquiposPorEstado(String nombreEstado) {
        Estado estado = estadoRepository.findByNombreEstado(nombreEstado)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
        return equipoRepository.findByEstado_IdEstado(estado.getIdEstado());
    }
    // metodo para obtener un equipo por id
    public Equipo obtenerEquipoPorId(Integer id) {
        return equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
    }

    // metodo para crear un equipo
    public Equipo crearEquipo(Equipo equipo) {
        // Verificar si la marca existe
        Marca marca = marcaClient.getMarcaById(equipo.getIdMarca());
        if (marca == null) {
            throw new RuntimeException("Marca no encontrada");
        }
        // Verificar si el estado existe
        Integer idEstado = equipo.getEstado().getIdEstado();
        Estado estado = estadoRepository.findById(idEstado)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
        equipo.setEstado(estado);
        return equipoRepository.save(equipo);
    }
}
