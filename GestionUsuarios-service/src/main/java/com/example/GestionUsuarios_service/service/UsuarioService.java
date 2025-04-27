package com.example.GestionUsuarios_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.GestionUsuarios_service.model.Usuario;
import com.example.GestionUsuarios_service.repository.UsuariosRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuariosRepository usuariosRepository;


    public List<Usuario> obtenerListaUsuarios() {
        return usuariosRepository.obtenerListaUsuarios();
    }

    public Usuario getUsuarioPorNombre(String nombre) {
        return usuariosRepository.buscarUsuarioPorNombre(nombre);
    }

    public Usuario getUsuarioPorId(int id) {
        return usuariosRepository.buscarUsuarioPorId(id);
    }

    public Usuario updateUsuario(Usuario usuario) {
        return usuariosRepository.modificarUsuario(usuario);
    }

    public Usuario agregarUsuario(Usuario usuario) {
        usuariosRepository.agregarUsuario(usuario);
        return usuario;
    }
    public String deleteUsuario(int id) {
        usuariosRepository.eliminarUsuario(id);
        return "Usuario eliminado con éxito";
    }
}
