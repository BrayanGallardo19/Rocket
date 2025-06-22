package com.example.Autentication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Autentication.auth.AuthResponse;
import com.example.Autentication.auth.LoginRequest;
import com.example.Autentication.model.UsuarioConectado;
import com.example.Autentication.service.AuthService;
import com.example.Autentication.service.UsuarioConectadoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @Autowired
    private UsuarioConectadoService usuarioConectadoService;
    
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

    @GetMapping("/auth/conectados")
    public ResponseEntity<List<UsuarioConectado>> MostrarConectados(){
        List<UsuarioConectado> lista = usuarioConectadoService.ListarUsuariosConectados();
        try {
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/auth/{id}")
    public ResponseEntity<UsuarioConectado> obtenerPorId(@PathVariable Integer id) {
        UsuarioConectado conectado = usuarioConectadoService.obtenerUsuarioConectadoPorId(id);
        return ResponseEntity.ok(conectado);
    }

}
