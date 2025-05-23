package com.example.GestorInventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.GestorInventario.model.Equipo;
import com.example.GestorInventario.model.Marca;
import com.example.GestorInventario.model.Modelo;
import com.example.GestorInventario.service.EquipoService;
import com.example.GestorInventario.webclient.MarcaModeloClient;

@RestController
@RequestMapping("api/v1/equipos")
public class EquipoController {

    @Autowired
    private final EquipoService equipoService;
    private final MarcaModeloClient marcaModeloClient;

    public EquipoController(EquipoService equipoService, MarcaModeloClient marcaModeloClient) {
        this.marcaModeloClient = marcaModeloClient;
        this.equipoService = equipoService;
    }

    // metodo para mostrar todos los equipos
    @GetMapping("")
    public ResponseEntity<List<Equipo>> mostrarTodosLosEquipos() {
        List<Equipo> equipos = equipoService.mostrarTodosLosEquipos();
        return ResponseEntity.ok(equipos);
    }

    // obtener equipo por id
    @GetMapping("/id/{id}")
    public ResponseEntity<Equipo> obtenerEquipoPorId(@PathVariable Integer id) {
        Equipo equipo = equipoService.obtenerEquipoPorId(id);
        return ResponseEntity.ok(equipo);
    }

    // crear un equipo
    @PostMapping("/crear")
    public ResponseEntity<Equipo> crearEquipo(@RequestBody Equipo equipo) {
        Equipo creado = equipoService.crearEquipo(equipo);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/marcas")
    public ResponseEntity<List<Marca>> obtenerTodasLasMarcas() {
        List<Marca> marcas = marcaModeloClient.getAllMarcas();
        return ResponseEntity.ok(marcas);
    }

    // obtener todos los modelos desde el microservicio correspondiente
    @GetMapping("/modelos")
    public ResponseEntity<List<Modelo>> obtenerTodosLosModelos() {
        List<Modelo> modelos = marcaModeloClient.getAllModelos();
        return ResponseEntity.ok(modelos);
    }
}
