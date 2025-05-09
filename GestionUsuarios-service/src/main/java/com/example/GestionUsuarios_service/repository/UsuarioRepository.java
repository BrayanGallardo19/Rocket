package com.example.GestionUsuarios_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.example.GestionUsuarios_service.model.Usuario;

//interface con el nuevo repositorio, hay que traspasar lo del repositorio actual y adaptarlo

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsername(String username);
}
