package com.example.GestionUsuarios.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.GestionUsuarios.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
