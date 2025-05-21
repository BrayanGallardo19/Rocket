package com.example.SoporteTecnico.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SoporteTecnico.model.Ticket;
import com.example.SoporteTecnico.service.SoporteTecnicoService;

@RestController
@RequestMapping("api/v1")
public class SoporteController {


    @Autowired 
    SoporteTecnicoService sopService;

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
}
