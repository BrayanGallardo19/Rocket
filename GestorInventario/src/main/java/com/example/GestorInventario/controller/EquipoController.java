package com.example.GestorInventario.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.GestorInventario.model.Equipo;
import com.example.GestorInventario.model.Estado;
import com.example.GestorInventario.repository.EstadoRepository;
import com.example.GestorInventario.service.EquipoService;
import com.example.GestorInventario.webclient.MarcaClient;
import com.example.GestorInventario.webclient.ModeloClient;

@RestController
@RequestMapping("api/v1/equipos")
public class EquipoController {

    private final EquipoService equipoService;
    private final MarcaClient marcaClient;
    private final ModeloClient modeloClient;
    private final EstadoRepository estadoRepo;

    public EquipoController(EquipoService equipoService, MarcaClient marcaClient, ModeloClient modeloClient,
            EstadoRepository estadoRepo) {
        this.estadoRepo = estadoRepo;
        this.marcaClient = marcaClient;
        this.modeloClient = modeloClient;
        this.equipoService = equipoService;
    }

    // mostrar todos los equipos
    @GetMapping
    public ResponseEntity<List<Equipo>> mostrarTodosLosEquipos() {
        List<Equipo> equipos = equipoService.mostrarTodosLosEquipos();
        return ResponseEntity.ok(equipos);
    }

    // obtener equipo por id
    @GetMapping("/{idEquipo}")
    public ResponseEntity<Equipo> obtenerEquipoPorId(@PathVariable Integer idEquipo) {
        Equipo equipo = equipoService.obtenerEquipoPorId(idEquipo);
        return ResponseEntity.ok(equipo);
    }

    // crear un equipo
    @PostMapping("/crear")
    public ResponseEntity<Equipo> crearEquipo(@RequestBody Equipo equipo) {
        Equipo creado = equipoService.crearEquipo(equipo);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // buscar equipos por estado
    @GetMapping("/estado/{nombreEstado}")
    public ResponseEntity<List<Equipo>> buscarEquiposPorEstado(@PathVariable String nombreEstado) {
        List<Equipo> equipos = equipoService.buscarEquiposPorEstado(nombreEstado);
        return ResponseEntity.ok(equipos);
    }

    // eliminar un equipo por id
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarEquipo(@PathVariable Integer id) {
        equipoService.eliminarEquipo(id);
        return ResponseEntity.ok("Equipo eliminado correctamente");
    }

    // obtener todas las marcas desde el microservicio Marca
    @GetMapping("/marcas")
    public ResponseEntity<List<Map<String, Object>>> obtenerTodasLasMarcas() {
        List<Map<String, Object>> marcas = marcaClient.obtenerTodasLasMarcas();
        return ResponseEntity.ok(marcas);
    }

    // obtener todos los modelos desde el microservicio Modelo
    @GetMapping("/modelos")
    public ResponseEntity<List<Map<String, Object>>> obtenerTodosLosModelos() {
        List<Map<String, Object>> modelos = modeloClient.obtenerTodosLosModelos();
        return ResponseEntity.ok(modelos);
    }

    // obtener todos los estados locales
    @GetMapping("/estados")
    public ResponseEntity<List<Estado>> obtenerEstados() {
        return ResponseEntity.ok(estadoRepo.findAll());
    }

    // modificar un equipo por id (mismo método que crear)
    @PutMapping("/modificar/{idEquipo}")
    public ResponseEntity<Equipo> modificarEquipo(@PathVariable Integer idEquipo, @RequestBody Equipo equipo) {
        equipo.setIdEquipo(idEquipo); 
        Equipo actualizado = equipoService.crearEquipo(equipo);
        return ResponseEntity.ok(actualizado);
    }
}
