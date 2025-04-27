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

    public Usuario buscarUsuario(String nombre) {//buscar usuario por nombre
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getNombre().equals(nombre)) {
                return usuario;
            }
        }
        return null;    
    }

    public Usuario buscarUsuarioPorRut(String rut) {//buscar usuario por rut
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getRut().equals(rut)) {
                return usuario;
            }
        }
        return null;    
    }

    public void eliminarUsuario(String rut) {//eliminar usuario por rut
        Usuario usuario = buscarUsuarioPorRut(rut);
        if (usuario != null) {
            listaUsuarios.remove(usuario);
        }
    }
    
    public void agregarUsuario(Usuario usuario) {
        listaUsuarios.add(usuario);
    }

    public Usuario modificarUsuario(Usuario usuario) {
        for (int i = 0; i < listaUsuarios.size(); i++) {
            // verifcar si el rut es el correcto y si el usuario existe
            if (listaUsuarios.get(i).getRut().equals(usuario.getRut())) {
                listaUsuarios.set(i, usuario);
                Usuario usuarioModificado = new Usuario();
                usuarioModificado.setRut(usuario.getRut());
                usuarioModificado.setNombre(usuario.getNombre());
                usuarioModificado.setApPaterno(usuario.getApPaterno());
                usuarioModificado.setApMaterno(usuario.getApMaterno());
                usuarioModificado.setCorreo(usuario.getCorreo());
                usuarioModificado.setTelefono(usuario.getTelefono());
                usuarioModificado.setContrasena(usuario.getContrasena());
                usuarioModificado.setFechaNacimiento(usuario.getFechaNacimiento());
                usuarioModificado.setActivo(usuario.isActivo());
                listaUsuarios.set(i, usuarioModificado); // Actualiza el usuario en la lista
                return usuario;  // usuario modificado
            }
        }
        // Si no encontramos el usuario
        throw new RuntimeException("Usuario con el RUT " + usuario.getRut() + " no encontrado.");
    }

}
