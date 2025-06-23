package com.example.SoporteTecnico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.example.SoporteTecnico.model.Ticket;
import com.example.SoporteTecnico.repository.TicketRepository;
import com.example.SoporteTecnico.service.AutorizacionService;
import com.example.SoporteTecnico.service.SoporteTecnicoService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("api/v1")
public class TicketController {


    @Autowired 
    private SoporteTecnicoService sopService;
    @Autowired
    private AutorizacionService autorizacionService;
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/tickets")
    public ResponseEntity<?> obtenerTickets(@RequestHeader("X-User-Id") Integer idUserConectado){
        ResponseEntity<?> autorizacionResponse = autorizacionService.validarRol(idUserConectado, 4);
            if (!autorizacionResponse.getStatusCode().is2xxSuccessful()) {
                return autorizacionResponse;
            }
        List<Ticket> tickets = sopService.getTickets();
        if(tickets.isEmpty()){
            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.ok(tickets);
    }
    @PostMapping("/tickets/crear")
    public ResponseEntity<?> crearProyecto(@RequestHeader("X-User-Id") Integer idUserConectado, @RequestBody Ticket nuevoTicket) {
        ResponseEntity<?> autorizacionResponse = autorizacionService.validarRol(idUserConectado, 4);
        if (!autorizacionResponse.getStatusCode().is2xxSuccessful()) {
            return autorizacionResponse;
        }

        try {
            Ticket tk = sopService.saveTicket(nuevoTicket);
            return ResponseEntity.status(201).body(tk);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
             return ResponseEntity.status(500).body("Error al crear Ticket: " + e.getMessage());
        }
    }



    @DeleteMapping("tickets/eliminar/{id}")
    public ResponseEntity<?> deleteTicket(@RequestHeader("X-User-Id") Integer idUserConectado, @PathVariable Integer id) {
        ResponseEntity<?> autorizacionResponse = autorizacionService.validarRol(idUserConectado, 4);
        if (!autorizacionResponse.getStatusCode().is2xxSuccessful()) {
            return autorizacionResponse;
        }

        boolean deleted = sopService.deleteTicketById(id);
        if (deleted) {
            return ResponseEntity.ok("Ticket eliminado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket no encontrado.");
        }
    }

@PutMapping("/tickets/modificar/{id}")
public ResponseEntity<?> actualizarTicket(@RequestHeader("X-User-Id") Integer idUserConectado, @PathVariable Integer id, @RequestBody Ticket ticket) {
    ResponseEntity<?> autorizacionResponse = autorizacionService.validarRol(idUserConectado, 4);
            if (!autorizacionResponse.getStatusCode().is2xxSuccessful()) {
                return autorizacionResponse;
            }
    if (id == null || id <= 0) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID de ticket no válido"); // 400 Bad Request
    }

    try {
        Ticket actualizado = sopService.actualizarTicket(id, ticket);
        return ResponseEntity.ok(actualizado); // 200 OK

    } catch (EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket no encontrado"); // 404 Not Found

    } catch (DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datos inválidos para el ticket"); // 400 Bad Request

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el ticket"); // 500
    }
}
    @GetMapping("/tickets/usuario/{id}")
    public ResponseEntity<?> listarTicketsPorUsuario(@RequestHeader("X-User-Id") Integer idUserConectado, @PathVariable Integer id) {
        ResponseEntity<?> autorizacionResponse = autorizacionService.validarRol(idUserConectado, 4);
            if (!autorizacionResponse.getStatusCode().is2xxSuccessful()) {
                return autorizacionResponse;
            }
        List<Ticket> tickets = sopService.getTicketsByUsuarioId(id);
        if (tickets == null || tickets.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tickets);
    }
}



