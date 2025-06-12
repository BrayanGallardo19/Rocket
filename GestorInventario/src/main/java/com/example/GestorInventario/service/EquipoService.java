package com.example.GestorInventario.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.GestorInventario.model.Equipo;
import com.example.GestorInventario.model.Estado;
import com.example.GestorInventario.repository.EquipoRepository;
import com.example.GestorInventario.repository.EstadoRepository;
import com.example.GestorInventario.webclient.MarcaClient;
import com.example.GestorInventario.webclient.ModeloClient;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EquipoService {
    private final EquipoRepository equipoRepository;
    private final EstadoRepository estadoRepository;
    private final MarcaClient marcaClient;
    private final ModeloClient modeloClient;

    public EquipoService(EquipoRepository equipoRepository, EstadoRepository estadoRepository,
                         MarcaClient marcaClient, ModeloClient modeloClient) {
        this.modeloClient = modeloClient;
        this.marcaClient = marcaClient;
        this.estadoRepository = estadoRepository;
        this.equipoRepository = equipoRepository;
    }

    public List<Equipo> mostrarTodosLosEquipos() {
        List<Equipo> equipos = equipoRepository.findAll();
        equipos.forEach(equipo -> {
            equipo.setMarca(marcaClient.obtenerMarcaPorId(equipo.getIdMarca()));
            equipo.setModelo(modeloClient.obtenerModeloPorId(equipo.getIdModelo()));
        });
        return equipos;
    }

    public Equipo obtenerEquipoPorId(Integer id) {
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        equipo.setMarca(marcaClient.obtenerMarcaPorId(equipo.getIdMarca()));
        equipo.setModelo(modeloClient.obtenerModeloPorId(equipo.getIdModelo()));
        return equipo;
    }

    public Map<String, Object> obtenerModeloDelEquipo(Integer idEquipo) {
        Equipo equipo = equipoRepository.findById(idEquipo)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        return modeloClient.obtenerModeloPorId(equipo.getIdModelo());
    }

    public Map<String, Object> obtenerMarcaDelEquipo(Integer idEquipo) {
        Equipo equipo = equipoRepository.findById(idEquipo)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        return marcaClient.obtenerMarcaPorId(equipo.getIdMarca());
    }

    public List<Equipo> buscarEquiposPorEstado(String nombreEstado) {
        Estado estado = estadoRepository.findByNombreEstado(nombreEstado)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        List<Equipo> equipos = equipoRepository.findByEstado(estado);

        equipos.forEach(equipo -> {
            equipo.setMarca(marcaClient.obtenerMarcaPorId(equipo.getIdMarca()));
            equipo.setModelo(modeloClient.obtenerModeloPorId(equipo.getIdModelo()));
        });

        return equipos;
    }

    public Equipo crearEquipo(Equipo equipo) {
        Map<String, Object> marca = marcaClient.obtenerMarcaPorId(equipo.getMarca().get("idMarca") != null ? (Integer) equipo.getMarca().get("idMarca") : null);
        if (marca == null)
            throw new RuntimeException("Marca no encontrada");

        Map<String, Object> modelo = modeloClient.obtenerModeloPorId(equipo.getModelo().get("idModelo") != null ? (Integer) equipo.getModelo().get("idModelo") : null);
        if (modelo == null)
            throw new RuntimeException("Modelo no encontrado");

        Estado estado = estadoRepository.findById(equipo.getEstado().getIdEstado())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        equipo.setIdMarca((Integer) marca.get("idMarca"));
        equipo.setIdModelo((Integer) modelo.get("idModelo"));
        equipo.setEstado(estado);

        equipo.setMarca(marca);
        equipo.setModelo(modelo);

        return equipoRepository.save(equipo);
    }

    public void eliminarEquipo(Integer id) {
        if (!equipoRepository.existsById(id)) {
            throw new RuntimeException("Equipo no encontrado");
        }
        equipoRepository.deleteById(id);
    }
}
