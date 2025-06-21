package com.example.SoporteTecnico.controller;

import com.example.SoporteTecnico.model.TipoSoporte;
import com.example.SoporteTecnico.service.SoporteTecnicoService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TipoSoporteControllerTest {

    @Mock
    SoporteTecnicoService sopService;

    @InjectMocks
    TipoSoporteController tipoSoporteController;

    @Test
    void testCrearTipoSoporte_Exitoso() {
        TipoSoporte mockTipo = new TipoSoporte(1, "Soporte1", null);
        when(sopService.createTipoSoporte(any(TipoSoporte.class))).thenReturn(mockTipo);

        TipoSoporte nuevo = new TipoSoporte();
        nuevo.setNombre("Soporte1");

        ResponseEntity<?> response = tipoSoporteController.crearTipoSoporte(nuevo);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo(mockTipo);
    }

    @Test
    void testCrearTipoSoporte_ErrorInterno() {
        when(sopService.createTipoSoporte(any(TipoSoporte.class)))
            .thenThrow(new RuntimeException("Error en BD"));

        TipoSoporte nuevo = new TipoSoporte();
        nuevo.setNombre("Soporte1");

        ResponseEntity<?> response = tipoSoporteController.crearTipoSoporte(nuevo);

        assertThat(response.getStatusCodeValue()).isEqualTo(500);
        assertThat(response.getBody().toString()).contains("Error al crear TipoSoporte");
    }

    @Test
    void testListarTiposSoporte_ConDatos() {
        TipoSoporte t1 = new TipoSoporte(1, "Soporte1", null);
        TipoSoporte t2 = new TipoSoporte(2, "Soporte2", null);
        List<TipoSoporte> lista = Arrays.asList(t1, t2);

        when(sopService.getTipoSoportes()).thenReturn(lista);

        ResponseEntity<List<TipoSoporte>> response = tipoSoporteController.listarTiposSoporte();

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(lista);
    }

    @Test
    void testListarTiposSoporte_SinDatos() {
        when(sopService.getTipoSoportes()).thenReturn(List.of());

        ResponseEntity<List<TipoSoporte>> response = tipoSoporteController.listarTiposSoporte();

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void testEliminarTipoSoporte_Exitoso() {
        when(sopService.deleteTipoSoporteById(1)).thenReturn(true);

        ResponseEntity<String> response = tipoSoporteController.eliminarTipoSoporte(1);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("TipoSoporte eliminado correctamente.");
    }

    @Test
    void testEliminarTipoSoporte_NoEncontrado() {
        when(sopService.deleteTipoSoporteById(999)).thenReturn(false);

        ResponseEntity<String> response = tipoSoporteController.eliminarTipoSoporte(999);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        assertThat(response.getBody()).isEqualTo("TipoSoporte no encontrado.");
    }

    @Test
    void testEditarTipoSoporte_Exitoso() {
        TipoSoporte actualizado = new TipoSoporte(1, "Soporte actualizado", null);

        when(sopService.updateTipoSoporte(eq(1), any(TipoSoporte.class))).thenReturn(actualizado);

        TipoSoporte aActualizar = new TipoSoporte();
        aActualizar.setNombre("Soporte actualizado");

        ResponseEntity<?> response = tipoSoporteController.editarTipoSoporte(1, aActualizar);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(actualizado);
    }

    @Test
    void testEditarTipoSoporte_NoEncontrado() {
        when(sopService.updateTipoSoporte(eq(999), any(TipoSoporte.class)))
            .thenThrow(new EntityNotFoundException("TipoSoporte no encontrado con id 999"));

        TipoSoporte aActualizar = new TipoSoporte();
        aActualizar.setNombre("Nombre");

        ResponseEntity<?> response = tipoSoporteController.editarTipoSoporte(999, aActualizar);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        assertThat(response.getBody()).isEqualTo("TipoSoporte no encontrado con id 999");
    }

    @Test
    void testEditarTipoSoporte_ErrorInterno() {
        when(sopService.updateTipoSoporte(eq(1), any(TipoSoporte.class)))
            .thenThrow(new RuntimeException("Error inesperado"));

        TipoSoporte aActualizar = new TipoSoporte();
        aActualizar.setNombre("Nombre");

        ResponseEntity<?> response = tipoSoporteController.editarTipoSoporte(1, aActualizar);

        assertThat(response.getStatusCodeValue()).isEqualTo(500);
        assertThat(response.getBody().toString()).contains("Error al actualizar TipoSoporte");
    }
}
