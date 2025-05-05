package com.example.GestionUsuarios_service.repository;
import com.example.GestionUsuarios_service.model.Usuario;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class UsuariosRepository {

    private List<Usuario> listaUsuarios = new ArrayList<>();

    public List <Usuario> obtenerListaUsuarios() {
        return listaUsuarios;
    }
    public Usuario buscarUsuarioPorNombre(String nombre) {//buscar usuario por nombre
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getNombre().equals(nombre)) {
                return usuario;
            }
        }
        return null;    
    }

    public Usuario buscarUsuarioPorId(int id) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null;
    }


    public Usuario modificarUsuario(Usuario usuario) {
        for (int i = 0; i < listaUsuarios.size(); i++) {
            if (listaUsuarios.get(i).getId() == usuario.getId()) {
                listaUsuarios.set(i, usuario);
                 return usuario;
            }
        }
        throw new RuntimeException("Usuario con ID " + usuario.getId() + " no encontrado.");
    }


    public void eliminarUsuario(int id) {
        Usuario usuario = buscarUsuarioPorId(id);
        if (usuario != null) {
            listaUsuarios.remove(usuario);
        } else {
            throw new RuntimeException("Usuario con ID " + id + " no encontrado.");
        }
    }
    

    //controlar fallos
    public void agregarUsuario(Usuario usuario) {
        if (existeUsuarioPorRut(usuario.getRut())) {
            throw new RuntimeException("Ya existe un usuario con el RUT: " + usuario.getRut());
        }
    
        if (existeUsuarioPorCorreo(usuario.getCorreo())) {
            throw new RuntimeException("Ya existe un usuario con el correo: " + usuario.getCorreo());
        }
    
        listaUsuarios.add(usuario);
    }
    
    // validar existencia
    public boolean existeUsuarioPorRut(String rut) {
        return listaUsuarios.stream()
                .anyMatch(usuario -> usuario.getRut().equalsIgnoreCase(rut));
    }
    
    public boolean existeUsuarioPorCorreo(String correo) {
        return listaUsuarios.stream()
                .anyMatch(usuario -> usuario.getCorreo().equalsIgnoreCase(correo));
    }
}
