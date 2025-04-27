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

    public Usuario getUsuario(String nombre) {
        return usuariosRepository.buscarUsuario(nombre);
    }

    public Usuario getUsuarioPorRut(String rut) {
        return usuariosRepository.buscarUsuarioPorRut(rut);
    }

    public Usuario updateUsuario(Usuario usuario) {
        return usuariosRepository.modificarUsuario(usuario);
    }
    public String deleteUsuario(String rut) {
        usuariosRepository.eliminarUsuario(rut);
        return "Usuario eliminado con exito";
    }
}
