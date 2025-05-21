package com.example.SoporteTecnico.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SoporteTecnico.model.Ticket;
import com.example.SoporteTecnico.service.SoporteTecnicoService;

@RestController
@RequestMapping("api/v1")
public class SoporteController {


    @Autowired 
    SoporteTecnicoService sopService;

    @GetMapping("/Tickets")
    public ResponseEntity<List<Ticket>> obtenerTickets(){
        List<Ticket> tickets = sopService.getTickets();
        if(tickets.isEmpty()){
            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.ok(tickets);
    }

}
