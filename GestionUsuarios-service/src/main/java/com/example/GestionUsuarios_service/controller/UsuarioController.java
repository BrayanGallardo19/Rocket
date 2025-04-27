package com.example.GestionUsuarios_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.GestionUsuarios_service.model.Usuario;
import com.example.GestionUsuarios_service.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> obtenerListaUsuarios() {
        return usuarioService.obtenerListaUsuarios();
    }

   
   @GetMapping("/nombre/{nombre}")
   public ResponseEntity<Usuario> obtenerUsuarioPorNombre(@PathVariable String nombre) {
       Usuario usuario = usuarioService.getUsuarioPorNombre(nombre);
       if (usuario != null) {
           return ResponseEntity.ok(usuario); // 200 OK
       } else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
       }
   }

   
   @GetMapping("/{id}")
   public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable int id) {
       Usuario usuario = usuarioService.getUsuarioPorId(id);
       if (usuario != null) {
           return ResponseEntity.ok(usuario); // 200 OK
       } else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
       }
   }

   
   @PostMapping
   public ResponseEntity<Usuario> agregarUsuario(@RequestBody Usuario usuario) {
       try {
           Usuario nuevoUsuario = usuarioService.agregarUsuario(usuario);
           return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario); // 201 Created
       } catch (RuntimeException e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400 Bad Request
       }
   }

   
   @PutMapping("/{id}")
   public ResponseEntity<Usuario> modificarUsuario(@PathVariable int id, @RequestBody Usuario usuario) {
       try {
           usuario.setId(id);
           Usuario usuarioModificado = usuarioService.updateUsuario(usuario);
           return ResponseEntity.ok(usuarioModificado); 
       } catch (RuntimeException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
       }
   }


   @DeleteMapping("/{id}")
   public ResponseEntity<String> eliminarUsuario(@PathVariable int id) {
       try {
           String mensaje = usuarioService.deleteUsuario(id);
           return ResponseEntity.ok(mensaje); // 200 OK
       } catch (RuntimeException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado"); // 404 Not Found
       }
   }
}
