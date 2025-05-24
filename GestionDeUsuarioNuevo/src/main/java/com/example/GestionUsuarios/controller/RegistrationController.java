package com.example.GestionUsuarios.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.GestionUsuarios.auth.AuthResponse;
import com.example.GestionUsuarios.auth.RegisterRequest;
import com.example.GestionUsuarios.service.RegistrationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/auth/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return registrationService.register(request);
    }
}
