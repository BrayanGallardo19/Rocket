package com.example.GestorInventario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.GestorInventario.model.Equipo;
import com.example.GestorInventario.model.Estado;
import com.example.GestorInventario.model.Marca;
import com.example.GestorInventario.model.Modelo;
import com.example.GestorInventario.repository.EquipoRepository;
import com.example.GestorInventario.repository.EstadoRepository;
import com.example.GestorInventario.webclient.MarcaClient;
import com.example.GestorInventario.webclient.ModeloClient;

@Service
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

        // para setear la marca y modelo de cada equipo
        equipos.forEach(equipo -> {
            equipo.setMarca(marcaClient.obtenerMarcaPorId(equipo.getIdMarca()));
            equipo.setModelo(modeloClient.obtenerModeloPorId(equipo.getIdModelo()));
        });

        return equipos;
    }

    // metodo para obtener un equipo por id
    public Equipo obtenerEquipoPorId(Integer id) {
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        equipo.setMarca(marcaClient.obtenerMarcaPorId(equipo.getIdMarca()));
        equipo.setModelo(modeloClient.obtenerModeloPorId(equipo.getIdModelo()));

        return equipo;
    }

    // metodo para obtener un modelo de un equipo(modeloclient)
    public Modelo obtenerModeloDelEquipo(Integer idEquipo) {
        Equipo equipo = equipoRepository.findById(idEquipo)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        return modeloClient.obtenerModeloPorId(equipo.getIdModelo());
    }

    // metodo para obtener la marca de un equipo(marcaclient)
    public Marca obtenerMarcaDelEquipo(Integer idEquipo) {
        Equipo equipo = equipoRepository.findById(idEquipo)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        return marcaClient.obtenerMarcaPorId(equipo.getIdMarca());
    }

    // metodo para buscar un equipo por estado
    public List<Equipo> buscarEquiposPorEstado(String nombreEstado) {
        Estado estado = estadoRepository.findByNombreEstado(nombreEstado)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        List<Equipo> equipos = equipoRepository.findByEstado(estado);

        // Setear Marca y Modelo en cada equipo
        equipos.forEach(equipo -> {
            equipo.setMarca(marcaClient.obtenerMarcaPorId(equipo.getIdMarca()));
            equipo.setModelo(modeloClient.obtenerModeloPorId(equipo.getIdModelo()));
        });

        return equipos;
    }

    // metodo para crear un equipo
    public Equipo crearEquipo(Equipo equipo) {
        // validar marca
        Marca marca = marcaClient.obtenerMarcaPorId(equipo.getMarca().getIdMarca());
        if (marca == null)
            throw new RuntimeException("Marca no encontrada");

        // validar modelo
        Modelo modelo = modeloClient.obtenerModeloPorId(equipo.getModelo().getIdModelo());
        if (modelo == null)
            throw new RuntimeException("Modelo no encontrado");

        // validar estado
        Estado estado = estadoRepository.findById(equipo.getEstado().getIdEstado())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        equipo.setIdMarca(marca.getIdMarca());
        equipo.setIdModelo(modelo.getIdModelo());
        equipo.setEstado(estado);

        equipo.setMarca(marca);
        equipo.setModelo(modelo);
        return equipoRepository.save(equipo);
    }

    // metodo para eliminar un equipo
    public void eliminarEquipo(Integer id) {
        if (!equipoRepository.existsById(id)) {
            throw new RuntimeException("Equipo no encontrado");
        }
        equipoRepository.deleteById(id);
    }
}
