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
import com.example.GestorInventario.service.EquipoService;
import com.example.GestorInventario.service.EstadoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("api/v1/equipos")
public class EquipoController {

    private final EquipoService equipoService;
    private final EstadoService estadoService;

    public EquipoController(EquipoService equipoService, EstadoService estadoService) {
        this.estadoService = estadoService;
        this.equipoService = equipoService;
    }

    // mostrar todos los equipos
    @Operation(summary = "Mostrar la lista con todos los equipos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipos encontrados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = Equipo.class)))
    })
    @GetMapping
    public ResponseEntity<List<Equipo>> mostrarTodosLosEquipos() {
        try {
            List<Equipo> equipos = equipoService.mostrarTodosLosEquipos();
            if (equipos == null || equipos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 si no hay equipos
            }
            return ResponseEntity.ok(equipos); // 200 OK si hay equipos
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 si algo falla
        }

    }

    // obtener equipo por id
    @Operation(summary = "Obtener un equipo por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo encontrado"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = Equipo.class)))
    })
    @GetMapping("/{idEquipo}")
    public ResponseEntity<Equipo> obtenerEquipoPorId(@PathVariable Integer idEquipo) {
        try {
            Equipo equipo = equipoService.obtenerEquipoPorId(idEquipo);
            return ResponseEntity.ok(equipo); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 si no existe
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 si algo falla inesperadamente
        }

    }

    // guardar un equipo
    @Operation(summary = "Ingresar un nuevo equipo al sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Equipo creado correctamente"),
            @ApiResponse(responseCode = "404", description = "Marca, modelo o estado no encontrados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = Equipo.class)))
    })
    @PostMapping("/guardar")
    public ResponseEntity<Equipo> guardarEquipo(@RequestBody Equipo equipo) {
        try {
            Equipo creado = equipoService.guardarEquipo(equipo);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado); // 201
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 si marca, modelo o estado no existen
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 500 si otro error ocurre
        }
    }

    // buscar equipos por estado
    @Operation(summary = "Buscar equipos por estado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipos encontrados"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado para el estado dado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = Equipo.class)))
    })

    @GetMapping("/estado/{nombreEstado}")
    public ResponseEntity<List<Equipo>> buscarEquiposPorEstado(@PathVariable String nombreEstado) {
        try {
            List<Equipo> equipos = estadoService.buscarEquiposPorEstado(nombreEstado);
            return ResponseEntity.ok(equipos); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 si no se encuentran equipos para el
                                                                           // estado dado
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 500 si ocurre otro error
        }
    }

    // eliminar un equipo por id
    @Operation(summary = "Eliminar un equipo por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = Equipo.class)))
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarEquipo(@PathVariable Integer id) {
        try {
            equipoService.eliminarEquipo(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 si no existe
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 si algo más falla
        }
    }

    // obtener todos los estados locales
    @Operation(summary = "Obtener una lista de todos los estados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estados obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = Estado.class)))
    })
    @GetMapping("/estados")
    public ResponseEntity<List<Estado>> obtenerEstados() {
        try {
            return ResponseEntity.ok(estadoService.mostrarTodosLosEstados()); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 500 internal server error
        }
    }

    // obtener un estado por id
    @Operation(summary = "Obtener un estado por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado encontrado"),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado", content = @Content(schema = @Schema(implementation = Estado.class)))
    })
    @GetMapping("/estados/{idEstado}")
    public ResponseEntity<Estado> obtenerEstadoPorId(@PathVariable Integer idEstado) {
        try {
            Estado estado = estadoService.obtenerEstadoPorId(idEstado);
            return ResponseEntity.ok(estado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }

    // modificar un equipo por id (mismo método que crear)
    @Operation(summary = "Modificar un equipo por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo modificado correctamente"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado", content = @Content(schema = @Schema(implementation = Equipo.class)))
    })
    @PutMapping("/modificar/{idEquipo}")
    public ResponseEntity<Equipo> modificarEquipo(@PathVariable Integer idEquipo, @RequestBody Equipo equipo) {
        try {
            Equipo equipoModificado = equipoService.modificarEquipo(idEquipo, equipo);
            return ResponseEntity.ok(equipoModificado); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }

    }
}
