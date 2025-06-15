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

    // metodo para mostrar todos los equipos
    public List<Equipo> mostrarTodosLosEquipos() {
        List<Equipo> equipos = equipoRepository.findAll();
        equipos.forEach(equipo -> {
            Map<String, Object> marcaMap = marcaClient.obtenerMarcaPorId(equipo.getIdMarca());
            Map<String, Object> modeloMap = modeloClient.obtenerModeloPorId(equipo.getIdModelo());

            equipo.setMarca((String) marcaMap.get("nombre"));
            equipo.setModelo((String) modeloMap.get("nombre"));

        });
        return equipos;
    }

    // metodo para obtener un equipo por su id
    public Equipo obtenerEquipoPorId(Integer id) {
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        Map<String, Object> marcaMap = marcaClient.obtenerMarcaPorId(equipo.getIdMarca());
        Map<String, Object> modeloMap = modeloClient.obtenerModeloPorId(equipo.getIdModelo());
        equipo.setMarca((String) marcaMap.get("nombre"));
        equipo.setModelo((String) modeloMap.get("nombre"));
        return equipo;
    }

    // metodo para obtener el modelo y la marca de un equipo por su id
    public Map<String, Object> obtenerModeloDelEquipo(Integer idEquipo) {
        Equipo equipo = equipoRepository.findById(idEquipo)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        return modeloClient.obtenerModeloPorId(equipo.getIdModelo());
    }

    // metodo para obtener la marca de un equipo por su id
    public Map<String, Object> obtenerMarcaDelEquipo(Integer idEquipo) {
        Equipo equipo = equipoRepository.findById(idEquipo)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        return marcaClient.obtenerMarcaPorId(equipo.getIdMarca());
    }

    // metodo para guardar un nuevo equipo
    public Equipo guardarEquipo(Equipo equipo) {

        // obtener marca por id desde el otro microservicio
        Map<String, Object> marca = marcaClient.obtenerMarcaPorId(equipo.getIdMarca());
        if (marca == null) {
            throw new RuntimeException("Marca no encontrada");
        }

        // obtener modelo por id desde el otro microservicio
        Map<String, Object> modelo = modeloClient.obtenerModeloPorId(equipo.getIdModelo());
        if (modelo == null) {
            throw new RuntimeException("Modelo no encontrado");
        }
        // obtner estado por id desde el repositorio local
        Estado estado = estadoRepository.findById(equipo.getEstado().getIdEstado())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
        // setear los id y los nombres de marca y modelo
        equipo.setIdMarca((Integer) marca.get("idMarca"));
        equipo.setIdModelo((Integer) modelo.get("idModelo"));
        equipo.setEstado(estado);

        equipo.setMarca((String) marca.get("nombre"));
        equipo.setModelo((String) modelo.get("nombre"));

        return equipoRepository.save(equipo);
    }

    // metodo para actualizar un equipo
    public void eliminarEquipo(Integer id) {
        if (!equipoRepository.existsById(id)) {
            throw new RuntimeException("Equipo no encontrado");
        }
        equipoRepository.deleteById(id);
    }
}
