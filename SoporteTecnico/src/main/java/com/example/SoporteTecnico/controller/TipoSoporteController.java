package com.example.SoporteTecnico.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.SoporteTecnico.model.TipoSoporte;
import com.example.SoporteTecnico.service.AutorizacionService;
import com.example.SoporteTecnico.service.SoporteTecnicoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;


@RestController
@RequestMapping("/api/v1/tiposSoporte")
public class TipoSoporteController {

    @Autowired
    private SoporteTecnicoService sopService;
    @Autowired
    private AutorizacionService autorizacionService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearTipoSoporte(@RequestHeader("X-User-Id") Integer idUserConectado,
            @RequestBody TipoSoporte nuevoTipo) {
        try {
            ResponseEntity<?> autorizacionResponse = autorizacionService.validarRol(idUserConectado, 4);
            if (!autorizacionResponse.getStatusCode().is2xxSuccessful()) {
                return autorizacionResponse;
            }
            TipoSoporte ts = sopService.createTipoSoporte(nuevoTipo);
            return ResponseEntity.status(HttpStatus.CREATED).body(ts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear TipoSoporte: " + e.getMessage());
        }
    }

    @PutMapping("modificar/{id}")
    public ResponseEntity<?> editarTipoSoporte(@RequestHeader("X-User-Id") Integer idUserConectado,
            @PathVariable Integer id, @RequestBody TipoSoporte tipoSoporte) {
        ResponseEntity<?> autorizacionResponse = autorizacionService.validarRol(idUserConectado, 4);
        if (!autorizacionResponse.getStatusCode().is2xxSuccessful()) {
            return autorizacionResponse;
        }
        try {
            TipoSoporte actualizado = sopService.updateTipoSoporte(id, tipoSoporte);
            return ResponseEntity.ok(actualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar TipoSoporte: " + e.getMessage());
        }
    }

    @Operation(summary = "Listar todos los tipos de soporte")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipos de soporte encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TipoSoporte.class)))),
            @ApiResponse(responseCode = "204", description = "No hay tipos de soporte registrados"),
            @ApiResponse(responseCode = "401", description = "Acceso denegado: usuario no autenticado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado: rol inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<?> listarTiposSoporte(@RequestHeader("X-User-Id") Integer idUserConectado) {
        try {
            ResponseEntity<?> autorizacionResponse = autorizacionService.validarRol(idUserConectado, 4);
            if (!autorizacionResponse.getStatusCode().is2xxSuccessful()) {
                return autorizacionResponse; // 401 o 403 según el caso
            }

            List<TipoSoporte> lista = sopService.getTipoSoportes(); // Suponiendo este método
            if (lista.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204 No Content
            }

            return ResponseEntity.ok(lista); // 200 OK

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al obtener los tipos de soporte");
        }
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminarTipoSoporte(@RequestHeader("X-User-Id") Integer idUserConectado, @PathVariable Integer id) {
        ResponseEntity<?> autorizacionResponse = autorizacionService.validarRol(idUserConectado, 4);
            if (!autorizacionResponse.getStatusCode().is2xxSuccessful()) {
                return autorizacionResponse; // 401 o 403 según el caso
            }
        boolean deleted = sopService.deleteTipoSoporteById(id);
        if (deleted) {
            return ResponseEntity.ok("TipoSoporte eliminado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TipoSoporte no encontrado.");
        }
    }
}
