package com.example.GestionUsuarios_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.GestionUsuarios_service.model.Usuario;
import com.example.GestionUsuarios_service.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerListaUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuario modificarUsuario(Usuario usuario) {
        Usuario user = usuarioRepository.findById(usuario.getId())
                .orElseThrow(() -> new RuntimeException("No se encontró un usuario"));

        return usuarioRepository.save(usuario);
    }

    public Usuario agregarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);

    }

    public String eliminarUsuario(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("La id:" + id + "no existe");

        }
        usuarioRepository.deleteById(id);
        return "usuario eliminado correctamente";
    }

    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario" + username));
    }

}
