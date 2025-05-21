package com.example.GestorInventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.GestorInventario.model.Equipo;
import com.example.GestorInventario.model.Estado;
import com.example.GestorInventario.model.Marca;
import com.example.GestorInventario.model.Modelo;
import com.example.GestorInventario.repository.EquipoRepository;
import com.example.GestorInventario.repository.EstadoRepository;
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
    @Autowired
    private EstadoRepository estadoRepository;

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

    // buscar equipos por id
    public Equipo buscarPorId(Integer id) {
        return equipoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El equipo con id " + id + " no existe"));
    }

    // metodo para agregar un nuevo equipo al inventario
    public Equipo agregarEquipoPorMarcaYModelo(Equipo equipo, String nombreModelo, String nombreMarca,
            String nombreEstado) {
        // validar que el modelo y la marca existan
        Marca marca = marcaRepository.findByNombreMarca(nombreMarca)
                .orElseThrow(() -> new IllegalArgumentException("La marca " + nombreMarca + " no existe"));

        Modelo modelo = modeloRepository.findByNombreModeloAndMarca(nombreModelo, marca)// valida que el modelo existe y
                                                                                        // pertenece a la marca indicada
                .orElseThrow(() -> new IllegalArgumentException("El modelo " + nombreModelo + " no existe"));

        Estado estado = estadoRepository.findByNombreEstado(nombreEstado)
                .orElseThrow(() -> new IllegalArgumentException("Estado no encontrado"));

        equipo.setEstado(estado); // asignar el estado al equipo
        equipo.setModelo(modelo); // asignar el modelo al equipo

        return equipoRepository.save(equipo);
    }

    // metodo para eliminar un equipo por id
    public void eliminarEquipoPorId(Integer id) {
        if (!equipoRepository.existsById(id)) {
            throw new IllegalArgumentException("El equipo con id " + id + " no existe");
        }
        equipoRepository.deleteById(id);
    }

    // metodo para actualizar un equipo por id
    public Equipo actualizarEquipoPorMarcaYModelo(Integer id, Equipo equipoActualizado, String nombreModelo,
            String nombreMarca, String nombreEstado) {
        Equipo equipoExistente = equipoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El equipo con id " + id + " no existe"));

        // validar modelo y marca
        Marca marca = marcaRepository.findByNombreMarca(nombreMarca)
                .orElseThrow(() -> new IllegalArgumentException("La marca " + nombreMarca + " no existe"));

        Modelo modelo = modeloRepository.findByNombreModeloAndMarca(nombreModelo, marca)
                .orElseThrow(() -> new IllegalArgumentException("El modelo " + nombreModelo + " no existe"));

        Estado estado = estadoRepository.findByNombreEstado(nombreEstado)
                .orElseThrow(() -> new IllegalArgumentException("Estado no encontrado"));
        // asignar el modelo al equipo

        equipoExistente.setNombre(equipoActualizado.getNombre());
        equipoExistente.setModelo(modelo);
        equipoExistente.setEstado(estado);
        equipoExistente.setPrecioVenta(equipoActualizado.getPrecioVenta());
        equipoExistente.setPrecioArriendo(equipoActualizado.getPrecioArriendo());
        equipoExistente.setPatente(equipoActualizado.getPatente());

        return equipoRepository.save(equipoExistente);

    }
}
