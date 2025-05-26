package com.example.GestionUsuarios.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.GestionUsuarios.auth.AuthResponse;
import com.example.GestionUsuarios.auth.RegisterRequest;
import com.example.GestionUsuarios.model.User;
import com.example.GestionUsuarios.service.RegistrationService;
import com.example.GestionUsuarios.service.UserService;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final UserService userService;

    // Registro de usuario con código 200 (OK) y 400 (Bad Request)
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        if (request.getUsername() == null || request.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new AuthResponse("Error: El nombre de usuario y la contraseña son obligatorios."));
        }

        AuthResponse response = registrationService.register(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

 @GetMapping("/{id}")
public ResponseEntity<?> getUserById(@PathVariable Integer id) {
    if (id == null || id <= 0) {
        return ResponseEntity
                 .status(HttpStatus.BAD_REQUEST)
                 .body("Error: El ID debe ser un número positivo.");
    }

    Optional<User> userOptional = userService.getUserById(id);
    if (userOptional.isPresent()) {
        // Devuelve el usuario con código 200 OK
        return ResponseEntity
                 .status(HttpStatus.OK)
                 .body(userOptional.get());
    } else {
        // Devuelve un mensaje de error con código 400 Bad Request
        return ResponseEntity
                 .status(HttpStatus.BAD_REQUEST)
                 .body("Error: Usuario no encontrado.");
    }
}


    // Editar usuario con código 200 (OK) y 400 (Bad Request)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody User updatedUser) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error: El ID debe ser un número positivo.");
        }

        User user = userService.updateUser(id, updatedUser);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    // Eliminar usuario con código 200 (OK) y 400 (Bad Request)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error: El ID debe ser un número positivo.");
        }

        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("Usuario eliminado correctamente.");
    }
}
