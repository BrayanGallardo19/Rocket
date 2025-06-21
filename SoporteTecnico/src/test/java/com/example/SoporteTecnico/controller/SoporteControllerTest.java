package com.example.SoporteTecnico.controller;

import com.example.SoporteTecnico.model.Soporte;
import com.example.SoporteTecnico.model.Ticket;
import com.example.SoporteTecnico.service.SoporteTecnicoService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SoporteControllerTest {

    @Mock
    SoporteTecnicoService sopService;

    @InjectMocks
    SoporteController soporteController;

    @Test
    void testCrearSoporte_Exitoso() {
        Soporte soporteMock = new Soporte(1, "Observacion test", new Date(), new Ticket());
        when(sopService.createSoporte(any(Soporte.class))).thenReturn(soporteMock);

        Soporte nuevo = new Soporte();
        nuevo.setObservacion("Observacion test");
        nuevo.setFecha_soporte(new Date());
        nuevo.setTicket(new Ticket());

        ResponseEntity<?> response = soporteController.crearSoporte(nuevo);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo(soporteMock);
    }

    @Test
    void testCrearSoporte_ErrorInterno() {
        when(sopService.createSoporte(any(Soporte.class))).thenThrow(new RuntimeException("Error en BD"));

        Soporte nuevo = new Soporte();
        nuevo.setObservacion("Observacion test");
        nuevo.setFecha_soporte(new Date());
        nuevo.setTicket(new Ticket());

        ResponseEntity<?> response = soporteController.crearSoporte(nuevo);

        assertThat(response.getStatusCodeValue()).isEqualTo(500);
        assertThat(response.getBody().toString()).contains("Error al crear soporte");
    }

    @Test
    void testListarSoportes_ConDatos() {
        Soporte s1 = new Soporte(1, "obs1", new Date(), new Ticket());
        Soporte s2 = new Soporte(2, "obs2", new Date(), new Ticket());
        List<Soporte> lista = Arrays.asList(s1, s2);

        when(sopService.getSoportes()).thenReturn(lista);

        ResponseEntity<List<Soporte>> response = soporteController.listarSoportes();

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(lista);
    }

    @Test
    void testListarSoportes_SinDatos() {
        when(sopService.getSoportes()).thenReturn(List.of());

        ResponseEntity<List<Soporte>> response = soporteController.listarSoportes();

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void testEliminarSoporte_Exitoso() {
        when(sopService.deleteSoporteById(1)).thenReturn(true);

        ResponseEntity<String> response = soporteController.eliminarSoporte(1);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Soporte eliminado correctamente.");
    }

    @Test
    void testEliminarSoporte_NoEncontrado() {
        when(sopService.deleteSoporteById(999)).thenReturn(false);

        ResponseEntity<String> response = soporteController.eliminarSoporte(999);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        assertThat(response.getBody()).isEqualTo("Soporte no encontrado.");
    }

    @Test
    void testEditarSoporte_Exitoso() {
        Soporte soporteActualizado = new Soporte(1, "Observacion actualizada", new Date(), new Ticket());

        when(sopService.updateSoporte(eq(1), any(Soporte.class))).thenReturn(soporteActualizado);

        Soporte aActualizar = new Soporte();
        aActualizar.setObservacion("Observacion actualizada");
        aActualizar.setFecha_soporte(new Date());
        aActualizar.setTicket(new Ticket());

        ResponseEntity<?> response = soporteController.editarSoporte(1, aActualizar);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(soporteActualizado);
    }

    @Test
    void testEditarSoporte_NoEncontrado() {
        when(sopService.updateSoporte(eq(999), any(Soporte.class)))
            .thenThrow(new EntityNotFoundException("Soporte no encontrado con id 999"));

        Soporte aActualizar = new Soporte();
        aActualizar.setObservacion("Observacion");
        aActualizar.setFecha_soporte(new Date());
        aActualizar.setTicket(new Ticket());

        ResponseEntity<?> response = soporteController.editarSoporte(999, aActualizar);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        assertThat(response.getBody()).isEqualTo("Soporte no encontrado con id 999");
    }

    @Test
    void testEditarSoporte_ErrorInterno() {
        when(sopService.updateSoporte(eq(1), any(Soporte.class)))
            .thenThrow(new RuntimeException("Error inesperado"));

        Soporte aActualizar = new Soporte();
        aActualizar.setObservacion("Observacion");
        aActualizar.setFecha_soporte(new Date());
        aActualizar.setTicket(new Ticket());

        ResponseEntity<?> response = soporteController.editarSoporte(1, aActualizar);

        assertThat(response.getStatusCodeValue()).isEqualTo(500);
        assertThat(response.getBody().toString()).contains("Error al actualizar soporte");
    }
}
