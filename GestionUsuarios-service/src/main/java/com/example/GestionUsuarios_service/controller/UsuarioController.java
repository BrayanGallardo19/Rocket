package com.example.GestionUsuarios_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public Usuario agregarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.updateUsuario(usuario);
    }
    
    @GetMapping("/{rut}")
    public Usuario getUsuarioPorRut(@PathVariable String rut) {
        return usuarioService.getUsuarioPorRut(rut);
    }
    @PutMapping("/{rut}")
    public Usuario modificarUsuario(@PathVariable String rut, @RequestBody Usuario usuario) {
        usuario.setRut(rut);
        return usuarioService.updateUsuario(usuario);
    }
    @DeleteMapping("/{rut}")
    public String eliminarUsuario(@PathVariable String rut) {
        return usuarioService.deleteUsuario(rut);
    }
}
