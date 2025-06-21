package com.example.Autentication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Autentication.auth.AuthResponse;
import com.example.Autentication.auth.LoginRequest;
import com.example.Autentication.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            // Si se produce una excepción en el proceso de login,
            // devolvemos 401 Unauthorized con el mensaje de error.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(ex.getMessage());
        } catch (Exception ex) {
            // Capturamos cualquier otra excepción y devolvemos un 400 Bad Request.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error: " + ex.getMessage());
        }
    }
}
