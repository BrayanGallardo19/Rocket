package com.example.GestionUsuarios_service.repository;
import com.example.GestionUsuarios_service.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {

   
}
