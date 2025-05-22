package com.example.Autenticacion.controller;

import com.example.Autenticacion.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        String token = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @Data
    static class LoginRequest {
        @NotBlank(message = "El username es obligatorio")
        private String username;
        @NotBlank(message = "La contraseña es obligatoria")
        private String password;
    }

    @Data
    static class TokenResponse {
        private final String token;
    }
}
