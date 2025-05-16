package com.example.Autenticacion.controller;

import com.example.Autenticacion.service.AutenticacionService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1")  // Ruta base para el microservicio
public class CredencialesController {

    @Autowired
    private AutenticacionService auService;

    @PostMapping("/login")
    public Mono<String> login(@RequestBody AuthRequest request) {
        return auService.autenticar(request.getUsername(), request.getPassword())
                .map(valid -> valid ? "Autenticación exitosa" : "Credenciales inválidas");
    }

    @Data
    public static class AuthRequest {
        private String username;
        private String password;
    }
}