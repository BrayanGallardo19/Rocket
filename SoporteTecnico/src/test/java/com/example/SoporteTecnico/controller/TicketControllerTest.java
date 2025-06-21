package com.example.SoporteTecnico.controller;

import com.example.SoporteTecnico.model.Ticket;
import com.example.SoporteTecnico.service.SoporteTecnicoService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketControllerTest {

    @Mock
    SoporteTecnicoService sopService;

    @InjectMocks
    TicketController ticketController;

    @Test
    void testObtenerTickets_ConDatos() {
        Ticket t1 = new Ticket(1, new Date(), null, "desc1", 1, null, null);
        Ticket t2 = new Ticket(2, new Date(), new Date(), "desc2", 2, null, null);
        List<Ticket> lista = Arrays.asList(t1, t2);

        when(sopService.getTickets()).thenReturn(lista);

        ResponseEntity<List<Ticket>> response = ticketController.obtenerTickets();

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(lista);
    }

    @Test
    void testObtenerTickets_SinDatos() {
        when(sopService.getTickets()).thenReturn(List.of());

        ResponseEntity<List<Ticket>> response = ticketController.obtenerTickets();

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void testCrearProyecto_Exitoso() {
        Ticket ticketMock = new Ticket(1, new Date(), null, "desc", 1, null, null);
        when(sopService.saveTicket(any(Ticket.class))).thenReturn(ticketMock);

        Ticket nuevo = new Ticket();
        nuevo.setDescripcion("desc");
        nuevo.setFecha_inicio(new Date());
        nuevo.setIdUsuario(1);

        ResponseEntity<?> response = ticketController.crearProyecto(nuevo);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo(ticketMock);
    }

    @Test
    void testCrearProyecto_ErrorRuntime() {
        when(sopService.saveTicket(any(Ticket.class))).thenThrow(new RuntimeException("usuario no encontrado"));

        Ticket nuevo = new Ticket();
        nuevo.setDescripcion("desc");
        nuevo.setFecha_inicio(new Date());
        nuevo.setIdUsuario(99);

        ResponseEntity<?> response = ticketController.crearProyecto(nuevo);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        assertThat(response.getBody().toString()).contains("usuario no encontrado");
    }


    @Test
    void testEliminarTicket_Exitoso() {
        when(sopService.deleteTicketById(1)).thenReturn(true);

        ResponseEntity<String> response = ticketController.deleteTicket(1);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Ticket eliminado correctamente.");
    }

    @Test
    void testEliminarTicket_NoEncontrado() {
        when(sopService.deleteTicketById(999)).thenReturn(false);

        ResponseEntity<String> response = ticketController.deleteTicket(999);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        assertThat(response.getBody()).isEqualTo("Ticket no encontrado.");
    }

    @Test
    void testActualizarTicket_Exitoso() {
        Ticket actualizado = new Ticket(1, new Date(), null, "desc actualizado", 1, null, null);

        when(sopService.actualizarTicket(eq(1), any(Ticket.class))).thenReturn(actualizado);

        Ticket aActualizar = new Ticket();
        aActualizar.setDescripcion("desc actualizado");
        aActualizar.setFecha_inicio(new Date());
        aActualizar.setIdUsuario(1);

        ResponseEntity<?> response = ticketController.actualizarTicket(1, aActualizar);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(actualizado);
    }

    @Test
    void testActualizarTicket_NoEncontrado() {
        when(sopService.actualizarTicket(eq(999), any(Ticket.class)))
            .thenThrow(new EntityNotFoundException("Ticket no encontrado"));

        Ticket aActualizar = new Ticket();
        aActualizar.setDescripcion("desc");
        aActualizar.setFecha_inicio(new Date());
        aActualizar.setIdUsuario(1);

        ResponseEntity<?> response = ticketController.actualizarTicket(999, aActualizar);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        assertThat(response.getBody()).isEqualTo("Ticket no encontrado");
    }

    @Test
    void testActualizarTicket_ErrorIntegridadDatos() {
        when(sopService.actualizarTicket(eq(1), any(Ticket.class)))
            .thenThrow(new DataIntegrityViolationException("Datos invalidos"));

        Ticket aActualizar = new Ticket();
        aActualizar.setDescripcion("desc");
        aActualizar.setFecha_inicio(new Date());
        aActualizar.setIdUsuario(1);

        ResponseEntity<?> response = ticketController.actualizarTicket(1, aActualizar);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo("Datos inválidos para el ticket");
    }

    @Test
    void testActualizarTicket_ErrorGenerico() {
        when(sopService.actualizarTicket(eq(1), any(Ticket.class)))
            .thenThrow(new RuntimeException("Error inesperado"));

        Ticket aActualizar = new Ticket();
        aActualizar.setDescripcion("desc");
        aActualizar.setFecha_inicio(new Date());
        aActualizar.setIdUsuario(1);

        ResponseEntity<?> response = ticketController.actualizarTicket(1, aActualizar);

        assertThat(response.getStatusCodeValue()).isEqualTo(500);
        assertThat(response.getBody()).isEqualTo("Error al actualizar el ticket");
    }

    @Test
    void testListarTicketsPorUsuario_ConDatos() {
        Ticket t1 = new Ticket(1, new Date(), null, "desc1", 1, null, null);
        Ticket t2 = new Ticket(2, new Date(), null, "desc2", 1, null, null);
        List<Ticket> lista = Arrays.asList(t1, t2);

        when(sopService.getTicketsByUsuarioId(1)).thenReturn(lista);

        ResponseEntity<List<Ticket>> response = ticketController.listarTicketsPorUsuario(1);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(lista);
    }

    @Test
    void testListarTicketsPorUsuario_SinDatos() {
        when(sopService.getTicketsByUsuarioId(999)).thenReturn(List.of());

        ResponseEntity<List<Ticket>> response = ticketController.listarTicketsPorUsuario(999);

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        assertThat(response.getBody()).isNull();
    }
}
