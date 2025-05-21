package com.example.SoporteTecnico.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.SoporteTecnico.model.Ticket;
import com.example.SoporteTecnico.repository.TicketRepository;
import com.example.SoporteTecnico.service.SoporteTecnicoService;

@RestController
@RequestMapping("api/v1")
public class SoporteController {


    @Autowired 
    SoporteTecnicoService sopService;
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> obtenerTickets(){
        List<Ticket> tickets = sopService.getTickets();
        if(tickets.isEmpty()){
            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.ok(tickets);
    }
    @PostMapping("/tickets")
    public ResponseEntity<?> crearProyecto(@RequestBody Ticket nuevoTicket) {
        try {
            Ticket tk = sopService.saveTicket(nuevoTicket);
            return ResponseEntity.status(201).body(tk);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
             return ResponseEntity.status(500).body("Error al crear Ticket: " + e.getMessage());
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTicket(@PathVariable Integer id) {
        boolean deleted = sopService.deleteTicketById(id);
        if (deleted) {
            return ResponseEntity.ok("Ticket eliminado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket no encontrado.");
        }
    }
}



